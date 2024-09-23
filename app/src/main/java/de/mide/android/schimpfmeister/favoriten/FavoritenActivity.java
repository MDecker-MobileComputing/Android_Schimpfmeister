package de.mide.android.schimpfmeister.favoriten;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.mide.android.schimpfmeister.FavoritenSingleton;
import de.mide.android.schimpfmeister.R;


/**
 * Activity zur Anzeige der als Favoriten gespeicherten Schimpfwörter.
 */
public class FavoritenActivity extends AppCompatActivity implements View.OnClickListener {

    /** Singleton-Objekt für Zugriff auf Favoriten-Liste. */
    private FavoritenSingleton _favoritenSingleton = FavoritenSingleton.getInstance();


    /**
     * Lifecycle-Methode: Layout-Datei für Activity laden, {@code }RecyclerView} initialisieren
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

        // RecyclerView initialisieren
        RecyclerView recyclerView = findViewById(R.id.favoritenRecyclerView);

        recyclerView.setLayoutManager( new LinearLayoutManager(this) );

        FavoritenAdapter adapter = new FavoritenAdapter( _favoritenSingleton );
        recyclerView.setAdapter(adapter);
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