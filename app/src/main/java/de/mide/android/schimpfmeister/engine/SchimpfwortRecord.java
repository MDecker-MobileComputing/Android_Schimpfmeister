package de.mide.android.schimpfmeister.engine;


/**
 * Record-Klasse zur Kapselung der beiden Wörter eines Schimpfworts.
 *
 * @param adjektiv Adjektiv des Schimpfworts
 *
 * @param substantiv Substantiv des Schimpfworts, besteht aus zwei Wörtern (Komposition)
 */
public record SchimpfwortRecord( String adjektiv,
                                 String substantiv ) {
}
