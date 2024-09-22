package de.mide.android.schimpfmeister;

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


/**
 * Demo für die mit Android 3.0 (API-Level 11) eingeführte <i>ActionBar</i>.
 * In der Datei {@code }values/themes.xml} muss als parent auch ein Theme gesetzt sein,
 * dass die ActionBar unterstützt, z.B. {@code Theme.AppCompat.Light.DarkActionBar}.
 * <br><br>
 *
 * This project is licensed under the terms of the BSD 3-Clause License.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG4LOGGING = "Schimpfmeister";

    /** Objekt für zufällige Erzeugung von Schimpfwörtern. */
    private SchimpfwortGenerator _schimpfwortGenerator = new SchimpfwortGenerator(this);

    /** UI-Element zur Anzeige des Adjektivs am Anfang des Schimpfworts. */
    private TextView _adjektivTextview = null;

    /** UI-Element zur Anzeige des Substantivs am Ende des Schimpfworts. */
    private TextView _substantivTextview = null;
    
    
    /**
     * Lifecycle-Methode: Layout-Datei für Activity setzen und ActionBar konfigurieren.
     * Es wird auch gleich ein Schimpfwort erzeugt und angezeigt.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _adjektivTextview = findViewById(R.id.adjektiv_textview);
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

        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Event-Handler für Menu-Items in ActionBar.
     *
     * @param item  Menu-Item, welches gerade ein Event ausgelöst hat.
     *
     * @return Es wird genau dann {@code true} zurückgegeben, wenn wir
     *         in dieser Methode das Ereignis verarbeiten konnten.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final int selectedMenuId = item.getItemId();

        // switch-case kann nicht verwendet werden, siehe auch https://stackoverflow.com/questions/2100520

        if (selectedMenuId == R.id.action_neuerspruch) {

            neuesSchimpfwort();
            return true;

        } else if (selectedMenuId == R.id.action_ueber) {

            aboutDialogAnzeigen();
            return true;

        } else if (selectedMenuId == R.id.action_hilfe) {

            hilfeAnzeigen();
            return true;

        } else {

            return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Erzeugt ein neues Schimpfwort und stellt es dar.
     */
    private void neuesSchimpfwort() {

        String schimpfwort = _schimpfwortGenerator.getSchimpfwort();
        _adjektivTextview.setText(schimpfwort);
    }


    /**
     * Zeigt einen "About"-Dialog mit Informationen über die App.
     */
    private void aboutDialogAnzeigen() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setTitle(R.string.ueber_titel);
        dialogBuilder.setMessage(R.string.ueber_satz);
        dialogBuilder.setPositiveButton("Ok", null);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }


    /**
     * Öffnet Hilfeseite im Web-Browser. Es wird nicht überprüft, ob tatsächlich
     * ein Browser auf dem Gerät installiert ist.
     */
    private void hilfeAnzeigen() {

        Uri httpUri = Uri.parse("https://bit.ly/schimpfmeister"); // Kurz-URL für GitHub-Seite
        Intent intent = new Intent(ACTION_VIEW);
        intent.setData(httpUri);

        startActivity(intent);
    }

}