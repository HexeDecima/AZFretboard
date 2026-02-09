public class NoteCalculator {
    // Chromatic note arrays
    private String[] sharpNotes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private String[] flatNotes = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};

    // 10-string tuning (highest to lowest):
    // String1:E, 2:B, 3:G, 4:D, 5:A, 6:E, 7:B, 8:F#, 9:C#, 10:G#
    private int[] tuning = {4, 11, 7, 2, 9, 4, 11, 6, 1, 8};

    // Current note system
    private boolean useSharps = true;

    // Get note name - stringIndex: 0=string1(highest), 1=string2, etc.
    public String getNoteName(int stringIndex, int fret) {
        if (stringIndex < 0 || stringIndex >= tuning.length) return "";

        int openNoteIndex = tuning[stringIndex];
        int noteIndex = (openNoteIndex + fret) % 12;

        return useSharps ? sharpNotes[noteIndex] : flatNotes[noteIndex];
    }

    // Toggle between sharps and flats
    public void toggleSharpFlat() {
        useSharps = !useSharps;
    }

    // Get current state
    public boolean isUsingSharps() {
        return useSharps;
    }
}