import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Random;

public class BackgroundGenerator {
    // Maple colors
    private static final Color[] MAPLE_COLORS = {
            new Color(205, 175, 140),
            new Color(200, 170, 135),
            new Color(195, 165, 130),
            new Color(190, 160, 125),
            new Color(185, 155, 120)
    };

    // Fiber colors
    private static final Color[] FIBER_COLORS = {
            new Color(180, 150, 120),
            new Color(170, 140, 110),
            new Color(160, 130, 100),
            new Color(150, 120, 90),
            new Color(140, 110, 80)
    };

    private static final Color MOTHER_OF_PEARL_LIGHT = new Color(255, 255, 255);
    private static final Color MOTHER_OF_PEARL_DARK = new Color(100, 80, 150);

    private static final int[] FRET_X = {6, 70, 132, 190, 246, 301, 357, 410, 462, 513,
            564, 612, 660, 708, 755, 799, 843, 886, 926, 967,
            1007, 1045, 1082, 1119, 1154};
    private static final int[] FRET_WIDTH = {50, 42, 36, 35, 32, 31, 29, 27, 27, 25,
            23, 23, 22, 22, 21, 21, 21, 20, 21, 21,
            20, 20, 20, 20, 20};
    private static final int[] STRING_Y_6 = {12, 52, 90, 129, 169, 208};
    private static final int RECT_HEIGHT = 20;
    private static final int FRET_THICKNESS = 6;

    public BufferedImage generateFretboard(int numStrings, int numFrets, int width, int height) {
        BufferedImage baseImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D baseG2d = baseImage.createGraphics();

        baseG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        baseG2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawMapleBackground(baseG2d, width, height);

        baseG2d.dispose();

        BufferedImage blurredImage = applyBlur(baseImage, 2.5f);

        BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = finalImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.drawImage(blurredImage, 0, 0, null);

        int[] stringYPositions = calculateStringPositions(numStrings, height);
        int[] stringHeights = calculateStringHeights(numStrings);

        drawFretMarkers(g2d, numFrets, numStrings, stringYPositions);
        drawAllFrets(g2d, numFrets, width, height);

        for (int i = 0; i < numStrings; i++) {
            drawString(g2d, stringYPositions[i], stringHeights[i], width, i);
        }

        g2d.dispose();
        return finalImage;
    }

    private BufferedImage applyBlur(BufferedImage image, float blurRadius) {
        if (blurRadius <= 0) return image;

        int size = (int)(blurRadius * 2 + 1);
        if (size < 3) size = 3;

        float[] matrix = new float[size * size];
        float sigma = blurRadius / 2.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float)Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;

        int center = size / 2;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                float x = center - i;
                float y = center - j;
                float distance = x * x + y * y;
                matrix[i * size + j] = (float)Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
                total += matrix[i * size + j];
            }
        }

        for (int i = 0; i < size * size; i++) {
            matrix[i] /= total;
        }

        Kernel kernel = new Kernel(size, size, matrix);
        ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return convolve.filter(image, null);
    }

    private void drawMapleBackground(Graphics2D g2d, int width, int height) {
        g2d.setColor(MAPLE_COLORS[2]);
        g2d.fillRect(0, 0, width, height);

        Random random = new Random(42);

        for (int i = 0; i < 2000; i++) {
            drawSingleFiber(g2d, width, height, random, false);
        }

        for (int i = 0; i < 500; i++) {
            drawSingleFiber(g2d, width, height, random, true);
        }
    }

    private void drawSingleFiber(Graphics2D g2d, int width, int height, Random random, boolean thick) {
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);

        int fiberLength = random.nextInt(80) + 40;

        float strokeWidth;
        int alpha;

        if (thick) {
            strokeWidth = 1.2f + random.nextFloat() * 0.8f;
            alpha = 80 + random.nextInt(80);
        } else {
            strokeWidth = 0.5f + random.nextFloat() * 0.7f;
            alpha = 40 + random.nextInt(60);
        }

        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        Color fiberColor = FIBER_COLORS[random.nextInt(FIBER_COLORS.length)];
        fiberColor = new Color(
                fiberColor.getRed(),
                fiberColor.getGreen(),
                fiberColor.getBlue(),
                alpha
        );

        g2d.setColor(fiberColor);

        float angle = (float) (random.nextDouble() * Math.PI / 30 - Math.PI / 60);

        int endX = startX + (int)(fiberLength * Math.cos(angle));
        int endY = startY + (int)(fiberLength * Math.sin(angle));

        endX = Math.max(0, Math.min(width, endX));
        endY = Math.max(0, Math.min(height, endY));

        g2d.drawLine(startX, startY, endX, endY);

        g2d.setStroke(new BasicStroke(1));
    }

    private int[] calculateStringPositions(int numStrings, int height) {
        int[] positions = new int[numStrings];

        if (numStrings == 6) {
            for (int i = 0; i < numStrings; i++) {
                positions[i] = STRING_Y_6[i] + RECT_HEIGHT / 2;
            }
        } else {
            int firstY = STRING_Y_6[0] + RECT_HEIGHT / 2;
            int lastY = STRING_Y_6[5] + RECT_HEIGHT / 2;

            for (int i = 0; i < numStrings; i++) {
                positions[i] = firstY + (lastY - firstY) * i / (numStrings - 1);
            }
        }

        return positions;
    }

    private int[] calculateStringHeights(int numStrings) {
        int[] heights = new int[numStrings];
        for (int i = 0; i < numStrings; i++) {
            heights[i] = 2 + (i * 6 / Math.max(1, numStrings - 1));
        }
        return heights;
    }

    private void drawAllFrets(Graphics2D g2d, int numFrets, int width, int height) {
        drawNut(g2d, width, height);

        for (int fret = 1; fret < numFrets; fret++) {
            drawFret(g2d, fret, width, height);
        }
    }

    private void drawNut(Graphics2D g2d, int width, int height) {
        int redRect0Right = FRET_X[0] + FRET_WIDTH[0];
        int redRect1Left = FRET_X[1];
        int nutRightEdge = (redRect0Right + redRect1Left) / 2;

        drawFretAtPosition(g2d, 0, nutRightEdge, height);
    }

    private void drawFret(Graphics2D g2d, int fretNum, int width, int height) {
        int leftEdge, rightEdge;

        if (fretNum <= 23) {
            int leftRectIdx = fretNum;
            int rightRectIdx = fretNum + 1;

            leftEdge = FRET_X[leftRectIdx] + FRET_WIDTH[leftRectIdx];
            rightEdge = FRET_X[rightRectIdx];
        } else if (fretNum == 24) {
            int lastIndex = 24;
            int secondLastIndex = 23;
            int gap = FRET_X[24] - (FRET_X[23] + FRET_WIDTH[23]);

            leftEdge = FRET_X[lastIndex] + FRET_WIDTH[lastIndex];
            rightEdge = leftEdge + gap;
        } else {
            int lastIndex = 24;
            int secondLastIndex = 23;
            int gap = FRET_X[24] - (FRET_X[23] + FRET_WIDTH[23]);

            int positionFromLast = fretNum - 24;
            leftEdge = FRET_X[lastIndex] + FRET_WIDTH[lastIndex] + (gap * positionFromLast);
            rightEdge = leftEdge + gap;
        }

        int fretCenterX = (leftEdge + rightEdge) / 2;
        int fretLeftX = fretCenterX - FRET_THICKNESS / 2;
        int fretRightX = fretLeftX + FRET_THICKNESS;

        drawFretAtPosition(g2d, fretLeftX, fretRightX, height);
    }

    private void drawFretAtPosition(Graphics2D g2d, int leftX, int rightX, int height) {
        int fretWidth = rightX - leftX;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(leftX, 0, fretWidth, height);

        GradientPaint fretGradient = new GradientPaint(
                leftX, 0, Color.BLACK,
                rightX, 0, Color.WHITE
        );
        g2d.setPaint(fretGradient);
        g2d.fillRect(leftX + 1, 1, fretWidth - 2, height - 2);
    }

    private void drawString(Graphics2D g2d, int y, int height, int panelWidth, int stringNum) {
        int stringY = y - height/2;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, stringY, panelWidth, height);

        GradientPaint stringGradient = new GradientPaint(
                0, stringY, Color.WHITE,
                0, stringY + height, Color.BLACK
        );
        g2d.setPaint(stringGradient);
        g2d.fillRect(1, stringY + 1, panelWidth - 2, height - 2);
    }

    private void drawFretMarkers(Graphics2D g2d, int numFrets, int numStrings, int[] stringY) {
        int[] markerFrets = {3, 5, 7, 9, 12, 15, 17, 19, 21, 24};
        int dotDiameter = 25;

        // Debug output to see exact positions
        System.out.println("=== Dot Position Calculations ===");

        for (int fret : markerFrets) {
            if (fret < numFrets) {
                // Calculate based on actual red rectangle center
                int redRectLeft = FRET_X[fret];
                int redRectWidth = FRET_WIDTH[fret];
                int redRectCenter = redRectLeft + redRectWidth / 2;

                System.out.println("Fret " + fret + ": " +
                        "Left=" + redRectLeft +
                        ", Width=" + redRectWidth +
                        ", Center=" + redRectCenter);

                if (fret == 12 || fret == 24) {
                    int topY = stringY[0];
                    int bottomY = stringY[numStrings - 1];

                    drawPearlDot(g2d, redRectCenter, topY, dotDiameter);
                    drawPearlDot(g2d, redRectCenter, bottomY, dotDiameter);
                } else {
                    int middleY = (stringY[0] + stringY[numStrings - 1]) / 2;
                    drawPearlDot(g2d, redRectCenter, middleY, dotDiameter);
                }
            }
        }

        System.out.println("=== End Dot Calculations ===");
    }

    private void drawPearlDot(Graphics2D g2d, int x, int y, int diameter) {
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x - diameter/2, y - diameter/2, diameter, diameter);

        GradientPaint pearlGradient = new GradientPaint(
                x, y - diameter/2, MOTHER_OF_PEARL_LIGHT,
                x, y + diameter/2, MOTHER_OF_PEARL_DARK
        );
        g2d.setPaint(pearlGradient);
        g2d.fillOval(x - diameter/2 + 1, y - diameter/2 + 1, diameter - 2, diameter - 2);
    }
}