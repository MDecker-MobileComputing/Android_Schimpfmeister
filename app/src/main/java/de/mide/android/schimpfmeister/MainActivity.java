package de.mide.android.schimpfmeister;

import static de.mide.android.schimpfmeister.BuildConfig.BUILD_ZEITPUNKT;
import static android.content.Intent.ACTION_VIEW;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import de.mide.android.schimpfmeister.engine.SchimpfwortGenerator;
import de.mide.android.schimpfmeister.engine.SchimpfwortRecord;
import de.mide.android.schimpfmeister.favoritenActivity.FavoritenActivity;


/**
 * Activity zur Anzeige zufällig generierter Schimpfwörter.
 * <br><br>
 *
 * Die App verwendet die in Android 3.0 (API-Level 11) eingeführte <i>ActionBar</i>.
 * In der Datei {@code values/themes.xml} muss als parent auch ein Theme gesetzt sein,
 * dass die ActionBar unterstützt, z.B. {@code Theme.AppCompat.Light.DarkActionBar}.
 * <br><br>
 *
 * This project is licensed under the terms of the BSD 3-Clause License.
 */
public class MainActivity extends AppCompatActivity {

    /** Dieser Tag soll von allen Klassen im Projekt verwendet werden. */
    public static final String TAG4LOGGING = "Schimpfmeister";

    /** URL zur Hilfeseite für die App, die in externer Browser-App geöffnet wird. */
    private static final String URL_SCHIMPFMEISTER =
            "https://github.com/MDecker-MobileComputing/Android_Schimpfmeister?tab=readme-ov-file#schimpfmeister";

    /** URL zur Homepage von "Schimpfolino" auf GitHub, die in externer Browser-App geöffnet wird. */
    private static final String URL_SCHIMPFOLOINO =
            "https://github.com/NikolaiRadke/Schimpfolino/blob/main/README.md";

    /** Objekt für zufällige Erzeugung von Schimpfwörtern. */
    private SchimpfwortGenerator _schimpfwortGenerator = new SchimpfwortGenerator(this);

    /** UI-Element zur Anzeige des Adjektivs am Anfang des Schimpfworts. */
    private TextView _adjektivTextview = null;

    /** UI-Element zur Anzeige des Substantivs am Ende des Schimpfworts. */
    private TextView _substantivTextview = null;

    /** Aktuell angezeigtes Schimpfwort. */
    private SchimpfwortRecord _schimpfwort = null;

    /**
     * Referenz auf Icon in ActionBar zum Merken eines Schimpfworts als Favorit; dieses
     * Icon soll nach dem Merken eines Schimpfworts deaktiviert werden bis
     * ein neues Schimpfwort angezeigt wird.
     */
    private MenuItem _merkenMenuItem = null;


    /**
     * Lifecycle-Methode: Layout-Datei für Activity setzen und ActionBar konfigurieren.
     * Es wird auch gleich ein Schimpfwort erzeugt und angezeigt.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _adjektivTextview   = findViewById(R.id.adjektiv_textview);
        _substantivTextview = findViewById(R.id.subjektiv_textview);

        actionBarKonfigurieren();

        neuesSchimpfwort();
    }


    /**
     * Konfiguriert die ActionBar: Titel, Untertitel und Icon setzen.
     * Die Menü-Einträge werden in der Methode {@link #onCreateOptionsMenu(Menu)} hinzugefügt.
     */
    private void actionBarKonfigurieren() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {

            Toast.makeText(this, "Keine ActionBar vorhanden.", Toast.LENGTH_LONG).show();
            return;
        }

        actionBar.setTitle   ( R.string.app_name );
        actionBar.setSubtitle( R.string.app_untertitel );

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
    }


    /**
     * Hinzufügen von Menu-Einträgen zur ActionBar.
     * <br><br>
     *
     * Das Event-Handling für die Einträge wird in der Methode
     * {@link #onOptionsItemSelected(MenuItem)} implementiert.
     *
     * @param menu  Menü-Objekt, zu dem die Einträge hinzugefügt werden sollen.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_items, menu);

        _merkenMenuItem = menu.findItem(R.id.action_merken);

        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Event-Handler für Menu-Items in der ActionBar.
     *
     * @param item  Menu-Item, welches gerade ein Event ausgelöst hat.
     *
     * @return Es wird {@code true} zurückgegeben, wenn wir in dieser
     *          Methode das Ereignis verarbeiten konnten, ansonsten
     *          der Wert der Super-Methode.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final int selectedMenuId = item.getItemId();

        // switch-case kann nicht verwendet werden, siehe auch
        // https://stackoverflow.com/questions/2100520

        if (selectedMenuId == R.id.action_neuerspruch) {

            neuesSchimpfwort();
            return true;

        } else if (selectedMenuId == R.id.action_merken) {

            lesezeichenSetzen();
            return true;

        } else if (selectedMenuId == R.id.action_ueber) {

            aboutDialogAnzeigen();
            return true;

        } else if (selectedMenuId == R.id.action_hilfe) {

            hilfeAnzeigen();
            return true;

        } else if (selectedMenuId == R.id.action_favoriten_anzeigen) {

            Intent intent = new Intent(this, FavoritenActivity.class);
            startActivity(intent);

            return true;

        } else {

            return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Erzeugt ein neues Schimpfwort und stellt es dar.
     */
    private void neuesSchimpfwort() {

        _schimpfwort = _schimpfwortGenerator.getSchimpfwort();

        _adjektivTextview.setText(   _schimpfwort.adjektiv()   );
        _substantivTextview.setText( _schimpfwort.substantiv() );

        if (_merkenMenuItem != null) {

            _merkenMenuItem.setEnabled(true);
            _merkenMenuItem.setIcon(R.drawable.baseline_favorite_24);
        }
    }


    /**
     * Event-Handler für ActionBar-Icon zum Speichern eines Schimpfworts als Favorit.
     * Das Icon in der ActionBar wird nach dem Speichern deaktiviert, damit ein Schimpfwort
     * nicht mehrfach als Favorit gespeichert werden kann.
     */
    private void lesezeichenSetzen() {

        FavoritenSingleton favoritenSingleton = FavoritenSingleton.getInstance();
        int anzahl = favoritenSingleton.hinzufuegen(_schimpfwort);

        _merkenMenuItem.setEnabled(false);
        _merkenMenuItem.setIcon(R.drawable.baseline_favorite_24_deaktiviert);

        String favoritHinzugefuegtText = getString(R.string.favorit_hinzugefuegt, anzahl);
        Toast.makeText(this, favoritHinzugefuegtText, Toast.LENGTH_LONG).show();
    }


    /**
     * Zeigt einen "About"-Dialog mit Informationen über die App.
     */
    private void aboutDialogAnzeigen() {

        int anzahlKombinationen = _schimpfwortGenerator.getAnzahlKombinationen();
        String ueberText = getString(R.string.ueber_text, anzahlKombinationen, BUILD_ZEITPUNKT);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setTitle(R.string.ueber_titel);
        dialogBuilder.setMessage(ueberText);

        dialogBuilder.setPositiveButton(R.string.button_ok, null);

        dialogBuilder.setNeutralButton(R.string.button_schimpfolino_besuchen, (dialog, which) -> {

            urlInBrowserOeffnen( URL_SCHIMPFOLOINO );
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }


    /**
     * Öffnet Hilfeseite im Web-Browser. Es wird nicht überprüft, ob tatsächlich
     * ein Browser auf dem Gerät installiert ist.
     */
    private void hilfeAnzeigen() {

        urlInBrowserOeffnen( URL_SCHIMPFMEISTER );
    }


    /**
     * URL in externer Web-Browser-App auf Gerät öffnen.
     *
     * @param url URL, die im Browser geöffnet werden soll.
     */
    private void urlInBrowserOeffnen(String url) {

        Uri httpUri = Uri.parse(url);
        Intent intent = new Intent(ACTION_VIEW);
        intent.setData(httpUri);
        startActivity(intent);
    }

}