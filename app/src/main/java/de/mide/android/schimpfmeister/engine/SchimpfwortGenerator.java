package de.mide.android.schimpfmeister.engine;

import static de.mide.android.schimpfmeister.MainActivity.TAG4LOGGING;

import static de.mide.android.schimpfmeister.engine.GenusEnum.MASKULINUM;
import static de.mide.android.schimpfmeister.engine.GenusEnum.FEMININUM;
import static de.mide.android.schimpfmeister.engine.GenusEnum.NEUTRUM;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.Random;

import de.mide.android.schimpfmeister.R;


/**
 * Diese Klasse enthält die eigentliche Logik für die Erzeugung der zufälligen Schimpfwörter.
 */
public class SchimpfwortGenerator {

    /**
     * Referenz auf App-Kontext (=Selbstreferenz auf aufrufende Activity) für Zugriff
     * auf Array-Ressourcen.
     */
    private Context _context = null;

    /** Generator für Zufallszahlen */
    private Random _random = new Random();


    /**
     * Konstruktor.
     *
     * @param context App-Kontext (Self-Referenz auf aufrufende Activity)
     */
    public SchimpfwortGenerator(Context context) {

        _context = context;
    }


    /**
     * Methode für Erzeugung des eigentlichen Schimpfworts.
     *
     * @return Zufällig erzeugtes Schimpfwort.
     */
    public String getSchimpfwort() {

        GenusEnum genus = getGenus();

        String wort1 = getWort1(genus);
        String wort2 = getWort2();
        String wort3 = getWort3(genus);


        return wort1 + "\n" + wort2 + wort3;
    }


    /**
     * Methode gibt Wort 3 (Wort am Ende von Substantiv), muss richtigen Genus haben.
     *
     * @param genus Geschlecht, welches das Wort haben muss
     *
     * @return Wort für Ende von Substantiv
     */
    private String getWort3(GenusEnum genus) {

        switch(genus) {

            case MASKULINUM: return getZufallsString(R.array.array_wort3maennlich);
            case FEMININUM:  return getZufallsString(R.array.array_wort3weiblich );
            case NEUTRUM:    return getZufallsString(R.array.array_wort3neutral  );
            default:
                Log.w(TAG4LOGGING, "Unerwarteter Wert für Genus: " + genus);
                return "???";
        }
    }


    /**
     * Methode gibt einen zufällig gewählten Genus (Maskulinum, Femininum, Neutrum) zurück.
     *
     * @return grammatikalisches Geschlecht
     */
    private GenusEnum getGenus() {

        int zufallszahl = _random.nextInt(2);
        switch (zufallszahl) {

            case 0: return MASKULINUM;
            case 1: return FEMININUM;
            case 2: return NEUTRUM;
            default:
                Log.w(TAG4LOGGING, "Unerwarteter Wert für Zufallszahl für Genus: " + zufallszahl);
                return NEUTRUM;
        }
    }


    /**
     * Methode wählt aus einer String-Array-Ressource ein zufälliger Element aus.
     *
     * @param stringArrayResId Ressourcen-ID des Arrays, aus dem zufällig ein String
     *                         ausgewählt werden soll
     *
     * @return Aus Array zufällig ausgewählter String
     */
    private String getZufallsString( int stringArrayResId ) {

        Resources resources  =  _context.getResources();
        String[] stringArray = resources.getStringArray(stringArrayResId);
        int zufallsIndex     = _random.nextInt(stringArray.length);

        return stringArray[ zufallsIndex ];
    }


    /**
     * Methode gibt zufällig ausgewähltes Adjektiv zurück; dieses Adjektiv soll
     * als erstes Wort verwendet werden.
     *
     * @param genus Geschlecht des Adjektiv
     *
     * @return Adjektiv in weiblicher Form, z.B. "Miefende".
     */
    private String getWort1(GenusEnum genus) {

        String adjektiv = getZufallsString(R.array.array_adjektive);

        switch (genus) {

            case MASKULINUM: adjektiv += "r"; break;
            case NEUTRUM:    adjektiv += "s"; break;
        };

        return adjektiv;
    }


    /**
     * Methode gibt zufällig gewähltes Anfangswort für zweites Wort zurück.
     *
     * @return Erster Teil für zweites Wort (Substantiv), z.B. "Wabbel".
     */
    private String getWort2() {

        return getZufallsString(R.array.array_wort2);
    }

}
