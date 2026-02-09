public class FretsCalculator {
    private static final int TOTAL_POSITIONS = 50; // 25 red rects + 25 frets
    private static final int NUM_FRETS = 25;
    private static final int PANEL_WIDTH = 1200;

    // Polynomial coefficients that approximate your original red rectangle centers
    // These were calculated to match your original positions
    private static final double A = 31.0;
    private static final double B = 59.4578;
    private static final double C = -0.5104;

    // Width formula coefficients
    private static final double D = 50.0;
    private static final double E = -3.416;
    private static final double F = 0.09025;

    // Arrays to store calculated positions
    private int[] redRectCenters;
    private int[] redRectWidths;
    private int[] redRectLeftEdges;
    private int[] fretWireCenters;

    public FretsCalculator() {
        calculateAllPositions();
    }

    private void calculateAllPositions() {
        // Step 1: Calculate red rectangle centers using polynomial formula
        redRectCenters = new int[NUM_FRETS];
        for (int i = 0; i < NUM_FRETS; i++) {
            redRectCenters[i] = (int)(A + B*i + C*i*i);
        }

        // Step 2: Calculate red rectangle widths
        redRectWidths = new int[NUM_FRETS];
        for (int i = 0; i < NUM_FRETS; i++) {
            double width = D + E*i + F*i*i;
            redRectWidths[i] = Math.max((int)width, 20); // Minimum width 20
        }

        // Step 3: Calculate left edges of red rectangles
        redRectLeftEdges = new int[NUM_FRETS];
        for (int i = 0; i < NUM_FRETS; i++) {
            redRectLeftEdges[i] = redRectCenters[i] - redRectWidths[i] / 2;
        }

        // Step 4: Calculate fret wire centers (between red rectangles)
        fretWireCenters = new int[NUM_FRETS];

        // Fret 0 (nut) is between red rect 0 and red rect 1
        int rightEdgeOfRedRect0 = redRectLeftEdges[0] + redRectWidths[0];
        fretWireCenters[0] = (rightEdgeOfRedRect0 + redRectLeftEdges[1]) / 2;

        // Fret 1-23: between red rect i and red rect i+1
        for (int i = 1; i < NUM_FRETS - 1; i++) {
            int rightEdge = redRectLeftEdges[i] + redRectWidths[i];
            fretWireCenters[i] = (rightEdge + redRectLeftEdges[i + 1]) / 2;
        }

        // Fret 24: after last red rect, use same spacing as previous
        int lastGap = redRectLeftEdges[23] - (redRectLeftEdges[22] + redRectWidths[22]);
        fretWireCenters[24] = redRectLeftEdges[24] + redRectWidths[24] + (lastGap / 2);
    }

    // Getters for all calculated arrays
    public int[] getRedRectLeftEdges() {
        return redRectLeftEdges;
    }

    public int[] getRedRectWidths() {
        return redRectWidths;
    }

    public int[] getRedRectCenters() {
        return redRectCenters;
    }

    public int[] getFretWireCenters() {
        return fretWireCenters;
    }

    // Helper method to print positions for debugging
    public void printPositions() {
        System.out.println("=== Calculated Positions ===");
        for (int i = 0; i < NUM_FRETS; i++) {
            System.out.println("Fret " + i +
                    ": RedRect Left=" + redRectLeftEdges[i] +
                    ", Width=" + redRectWidths[i] +
                    ", Center=" + redRectCenters[i] +
                    ", FretWire Center=" + fretWireCenters[i]);
        }
    }
}