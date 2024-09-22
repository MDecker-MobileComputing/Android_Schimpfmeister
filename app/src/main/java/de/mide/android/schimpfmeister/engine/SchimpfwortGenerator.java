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
 * Diese Klasse enthält die eigentliche Logik für die Erzeugung der zufälligen Schimpfwörter
 * anhand der Wortlisten und dem Algorithmus von "Schimpfolino" (Nikolai Radke).
 * <br><br>
 *
 * Ein Schimpfwort besteht aus zwei Wörtern: das erste Wort ist ein Adjektiv, das zweite
 * Wort ein Substantiv, welches aus zwei Wörtern zusammengesetzt ist (Wortkomposition);
 * es müssen insgesamt also drei zufällige Wörter ausgewählt werden.
 * <br><br>
 *
 * Schritte zur Erzeugung eines Schimpfworts:
 * <ul>
 *     <li>Es wird zunächst ein zufälliger Genus ausgewählt.</li>
 *     <li>Danach wird ein zufälliges Adjektiv ausgewählt, welches durch
 *         Anhängen von "r" oder "s" (für Maskulinum und Neutrum) an den
 *         Genus angepasst werden kann (für Femininum ist keine Anpassung
 *         erforderlich).</li>
 *      <li>Es wird dann ein Substantiv ausgewählt, welches der Anfang
 *          des zweiten Worts ist.</li>
 *      <li>In Abhängigkeit des Genus wird dann das zweite Substantiv ausgewählt,
 *          welche das Ende des zweiten Worts ist.</li>
 * </ul>
 */
public class SchimpfwortGenerator {

    /**
     * Referenz auf App-Kontext (=Selbstreferenz auf aufrufende Activity) für Zugriff
     * auf Array-Ressourcen.
     */
    private Context _context = null;

    /** Generator für Zufallszahlen. */
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
    public SchimpfwortRecord getSchimpfwort() {

        GenusEnum genus = getGenus();

        String wort1 = getWort1(genus);
        String wort2 = getWort2();
        String wort3 = getWort3(genus);

        return new SchimpfwortRecord(wort1, wort2 + wort3);
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
     * Methode gibt zufällig ausgewähltes Adjektiv zurück, welches in Abhängigkeit
     * von {@code genus} dekliniert wird.
     *
     * @param genus Geschlecht des Adjektiv
     *
     * @return dekliniertes Adjektiv
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
     * Berechnung der möglichen Anzahl der Schimpfwortkombinationen.
     *
     * @return Anzahl der möglichen Schimpfwortkombinationen
     */
    public int getAnzahlKombinationen() {

        int anzahlAjektive = getAnzahlElementeArray(R.array.array_adjektive);

        int anzahlWort2 = getAnzahlElementeArray(R.array.array_wort2);

        int anzahlWort3maennlich = getAnzahlElementeArray(R.array.array_wort3maennlich);
        int anzahlWort3weiblich  = getAnzahlElementeArray(R.array.array_wort3weiblich);
        int anzahlWort3neutral   = getAnzahlElementeArray(R.array.array_wort3neutral);

        int summeWort3 = anzahlWort3maennlich + anzahlWort3weiblich + anzahlWort3neutral;

        return anzahlAjektive * anzahlWort2 * summeWort3;
    }


    /**
     * Methode zum Zählen der Elemente in einer String-Array-Ressource
     *
     * @param stringArrayResId ID der String-Array-Ressource
     *
     * @return Anzahl der Elemente im Array
     */
    public int getAnzahlElementeArray(int stringArrayResId) {

        Resources resources  =  _context.getResources();
        String[] stringArray = resources.getStringArray(stringArrayResId);

        return stringArray.length;
    }

}
