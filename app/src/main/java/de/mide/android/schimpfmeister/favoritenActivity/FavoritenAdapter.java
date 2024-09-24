package de.mide.android.schimpfmeister.favoritenActivity;

import static de.mide.android.schimpfmeister.MainActivity.TAG4LOGGING;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.mide.android.schimpfmeister.FavoritenSingleton;
import de.mide.android.schimpfmeister.R;
import de.mide.android.schimpfmeister.engine.SchimpfwortRecord;


/**
 * Adapter-Klasse für Datenversorgung eines {@code RecyclerView}-Elements
 * mit den gemerkten Schimpfwörtern.
 */
public class FavoritenAdapter extends RecyclerView.Adapter {

    /** Singleton-Objekt für Zugriff auf Favoriten-Liste. */
    private FavoritenSingleton _favoritenSingleton = null;


    /**
     * Konstruktor.
     */
    FavoritenAdapter(FavoritenSingleton favoritenSingleton) {

        super();

        _favoritenSingleton = favoritenSingleton;
    }


    /**
     * {@code ViewHolder} für Eintrag in RecyclerView erzeugen.
     *
     * @return Neuer {@code ViewHolder} für Eintrag in RecyclerView.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {

        Context parentKontext = parentViewGroup.getContext();

        LayoutInflater luftpumpe = LayoutInflater.from( parentKontext );

        View view = luftpumpe.inflate(R.layout.schimpfwort_eintrag, parentViewGroup, false);

        return new SchimpfViewHolder(view);
    }


    /**
     * Einzelnen Eintrag im {@code }RecyclerView} mit Daten versorgen.
     *
     * @param holder   ViewHolder-Objekt, das einen Eintrag repräsentiert und
     *                 mit Daten versorgt werden soll.
     * @param position Position des Eintrags in der RecyclerView (0-basiert)
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if ( holder instanceof SchimpfViewHolder schimpfHolder) {

            SchimpfwortRecord schimpfwortRecord = _favoritenSingleton.getSchimpfwort(position);
            schimpfHolder.setSchimpfwort(schimpfwortRecord);

            holder.itemView.setOnClickListener( view -> {

                Log.i(TAG4LOGGING, "Schimpfwort in Favoriten-Liste geklickt: " + schimpfwortRecord);
            });

        } else {

            Log.w(TAG4LOGGING, "Ungültiger ViewHolder-Typ: " + holder.getClass().getName());
        }
    }


    /**
     * Anzahl der im {@code RecyclerView} anzuzeigenden Einträge zurückeben.
     *
     * @return Anzahl der Einträge in der RecyclerView, nämlich Anzahl der
     *         Schimpfwörter in der Favoriten-Liste.
     */
    @Override
    public int getItemCount() {

        return _favoritenSingleton.getAnzahl();
    }

}
