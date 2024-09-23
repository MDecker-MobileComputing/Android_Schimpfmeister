package de.mide.android.schimpfmeister.engine;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static de.mide.android.schimpfmeister.MainActivity.TAG4LOGGING;


/**
 * Singleton-Klasse für Favoriten-Liste von Schimpfwörtern; simuliert Persistenz.
 */
public class FavoritenSingleton {

    /** Einzige Instanz dieser Singleton-Klasse. */
    private static FavoritenSingleton _singletonInstanz = null;

    /** Liste von Schimpfworten, die als Favoriten markiert wurden. */
    private List<SchimpfwortRecord> _liste = null;


    /**
     * Privater Konstruktor, so dass außerhalb der Klasse keine Instanzen erzeugt werden können.
     */
    private FavoritenSingleton() {

        _liste = new ArrayList<>(10);
    }


    /**
     * "Speichern" eines Schimpfworts in der Favoriten-Liste.
     *
     * @param schimpfwort Schimpfwort-Objekt, das als Favorit hinzugefügt werden soll
     *
     * @return Anzahl der Schimpfwörter in der Favoriten-Liste nach dem Hinzufügen.
     */
    public int hinzufuegen(SchimpfwortRecord schimpfwort) {

        _liste.add(schimpfwort);

        return _liste.size();
    }


    /**
     * Gibt Anzahl der Schimpfwörter in der Favoriten-Liste zurück.
     *
     * @return Anzahl der Schimpfwörter in der Favoriten-Liste
     */
    public int getAnzahl() {

        return _liste.size();
    }


    /**
     * Liefert Schimpfwort-Objekt an gegebener Position in Liste.
     *
     * @param index 0-basierter Index.
     *
     * @return Schimpfwort-Objekt an gegebener Position.
     */
    public SchimpfwortRecord getSchimpfwort(int index) {

        if ( index < 0 || index >= _liste.size() ) {

            Log.e( TAG4LOGGING, "Ungültiger Index für Zugriff auf Favoriten-Liste: " + index);
            new SchimpfwortRecord("???", "???");
        }

        return _liste.get(index);
    }


    /**
     * Liefert Instanz dieser Singleton-Klasse, welche bei Bedarf vorher erzeugt wird.
     *
     * @return Instanz dieser Singleton-Klasse.
     */
    public static FavoritenSingleton getInstance() {

        if (_singletonInstanz == null) {

            _singletonInstanz = new FavoritenSingleton();
        }

        return _singletonInstanz;
    }

}
