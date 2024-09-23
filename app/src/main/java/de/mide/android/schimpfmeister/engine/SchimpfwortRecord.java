package de.mide.android.schimpfmeister.engine;


/**
 * Record-Klasse zur Kapselung der beiden Wörter eines Schimpfworts; wird statt einem String
 * verwendet, damit man die beiden Wörter in getrennten TextView-Elementen auf der UI
 * darstellen kann.
 *
 * @param adjektiv Adjektiv des Schimpfworts, muss dekliniert sein passend zu Genus
 *                 zweites Wort von {@code substantiv}
 *
 * @param substantiv Substantiv des Schimpfworts, besteht aus zwei Wörtern (Wortkompositum),
 *                   z.B. "Würgkröte"
 */
public record SchimpfwortRecord( String adjektiv,
                                 String substantiv ) {
}
