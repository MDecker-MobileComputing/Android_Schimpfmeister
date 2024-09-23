package de.mide.android.schimpfmeister.favoriten;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import de.mide.android.schimpfmeister.FavoritenSingleton;
import de.mide.android.schimpfmeister.R;


/**
 * Activity zur Anzeige der als Favoriten gespeicherten Schimpfwörter.
 */
public class FavoritenActivity extends AppCompatActivity implements View.OnClickListener {

    private FavoritenSingleton _favoritenSingleton = FavoritenSingleton.getInstance();

    /**
     * Lifecycle-Methode: Layout-Datei für Activity laden, RecyclerView initialisieren
     * und Event-Handler für "Zurück"-Button setzen.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoriten);

        int anzahl   = _favoritenSingleton.getAnzahl();
        String titel = getString(R.string.activit_titel_favoriten, anzahl);
        setTitle( titel );

        Button button = findViewById(R.id.zurueckButton);
        button.setOnClickListener(this);
    }


    /**
     * Event-Handler für "Zurück"-Button.
     *
     * @param view Button, der das Event ausgelöst hat
     */
    @Override
    public void onClick(View view) {

        finish();
    }

}