package de.mide.android.schimpfmeister.favoritenActivity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.mide.android.schimpfmeister.R;
import de.mide.android.schimpfmeister.engine.SchimpfwortRecord;


/**
 * Ein Objekt dieser Klasse repräsentiert einen Schimpfwort-Eintrag im {@code RecyclerView}-Element.
 */
public class SchimpfViewHolder extends RecyclerView.ViewHolder {

    /** Textview-Element in Eintrag in {@code RecyclerView}-Element. */
    private TextView _textView = null;


    /**
     * Konstruktor
     *
     * @param itemView View-Element, das den Eintrag in der Liste repräsentiert.
     */
    public SchimpfViewHolder(@NonNull View itemView) {

        super(itemView);

        _textView = itemView.findViewById(R.id.schimpfwortTextview);
    }


    /**
     * Als Listeneintrag anzuzeigendes Schimpfwort setzen.
     *
     * @param schimpfRecord Record-Objekt mit anzuzeigendem Schimpfwort.
     */
    public void setSchimpfwort(SchimpfwortRecord schimpfRecord) {

        String schimpfwortString = schimpfRecord.toString();
        _textView.setText(schimpfwortString);
    }

}
