//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.util.Hashtable;
//import java.util.ArrayList;
//
//public class FretboardPanel extends JPanel implements MouseListener {
//
//  // Configuration
//  private static final int NUM_STRINGS = 6;
//  private static final int NUM_FRETS = 25;
//
//
//  // Original coordinates
//  private int[] originalX = {6, 70, 132, 190, 246, 301, 357, 410, 462, 513, 564, 612, 660, 708, 755, 799, 843, 886, 926, 967, 1007, 1045, 1082, 1119, 1154};
//  private int[] originalWidth = {50, 42, 36, 35, 32, 31, 29, 27, 27, 25, 23, 23, 22, 22, 21, 21, 21, 20, 21, 21, 20, 20, 20, 20, 20};
//  private int[] originalStringY = {12, 52, 90, 129, 169, 208};
//
//  // Arrays for positions
//  private int[] fretX = new int[NUM_FRETS];
//  private int[] fretWidth = new int[NUM_FRETS];
//  private int[] stringY = new int[NUM_STRINGS];
//
//  // Rectangle array
//  private Rectangle[] rects;
//
//  // Other variables
//  BufferedImage background;
//  BufferedImage subImage;
//  Color fretColor = new Color(0f, 0f, 0f, .1f);
//  int rectRoundness = 10;
//  Hashtable<String, Boolean> hits = new Hashtable<String, Boolean>();
//  java.util.ArrayList<Rectangle> activeRects = new java.util.ArrayList<>();
//  NoteCalculator noteCalc = new NoteCalculator();
//
//  public FretboardPanel() {
//    initializeFretPositions();
//    initializeStringPositions();
//    createRectangles();
//
//    try {
//      background = ImageIO.read(getClass().getResource("fretboard.png"));
//    } catch(Exception e) {
//      e.printStackTrace();
//    }
//
//    for(Rectangle r : rects) {
//      hits.put(r.toString(), false);
//    }
//
//    addMouseListener(this);
//  }
//
//  private void initializeFretPositions() {
//    for(int i = 0; i < NUM_FRETS; i++) {
//      fretX[i] = originalX[i];
//      fretWidth[i] = originalWidth[i];
//    }
//  }
//
//  private void initializeStringPositions() {
//    for(int i = 0; i < NUM_STRINGS; i++) {
//      stringY[i] = originalStringY[i];
//    }
//  }
//
//  private void createRectangles() {
//    rects = new Rectangle[NUM_STRINGS * NUM_FRETS];
//
//    for(int string = 0; string < NUM_STRINGS; string++) {
//      for(int fret = 0; fret < NUM_FRETS; fret++) {
//        int index = string * NUM_FRETS + fret;
//        rects[index] = new Rectangle(
//                fretX[fret],
//                stringY[string],
//                fretWidth[fret],
//                20
//        );
//      }
//    }
//  }
//
//  void onHit(Rectangle r) {
//    // Toggle the hit state
//    boolean wasActive = hits.get(r.toString());
//    hits.put(r.toString(), !wasActive);
//    repaint();  // This will cause paintComponent to redraw everything
//  }
//
//  @Override
//  protected void paintComponent(Graphics g) {
//    super.paintComponent(g);
//    Graphics2D g2d = (Graphics2D) g;
//    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//    g2d.drawImage(background, 0, 0, this);
//    g2d.setColor(fretColor);
//
//    // Draw all fret placeholders
//    for(int i = 0; i < rects.length; i++) {
//      g2d.fillRoundRect(rects[i].x, rects[i].y, rects[i].width, rects[i].height,
//              rectRoundness, rectRoundness);
//    }
//
//    // Draw red notes for active frets
//    for(int i = 0; i < rects.length; i++) {
//      if(hits.get(rects[i].toString())) {
//        Rectangle r = rects[i];
//
//        // Find string and fret indices
//        int stringIndex = i / NUM_FRETS;
//        int fretNumber = i % NUM_FRETS;
//
//        // Draw red rectangle
//        g2d.setColor(Color.RED);
//        g2d.fillRoundRect(r.x, r.y, r.width, r.height, rectRoundness, rectRoundness);
//
//        // Draw note name
//        String noteName = noteCalc.getNoteName(stringIndex, fretNumber);
//        g2d.setColor(Color.WHITE);
//        g2d.setFont(new Font("Arial", Font.BOLD, 14));
//
//        FontMetrics fm = g2d.getFontMetrics();
//        int textWidth = fm.stringWidth(noteName);
//        int textHeight = fm.getAscent();
//        int x = r.x + (r.width - textWidth) / 2;
//        int y = r.y + (r.height + textHeight) / 2 - 2;
//
//        g2d.drawString(noteName, x, y);
//      }
//    }
//  }
//
//  public void mousePressed(MouseEvent e) {}
//  public void mouseReleased(MouseEvent e) {
//    for(int i = 0; i < rects.length; i++) {
//      if(rects[i].contains(e.getX(), e.getY())) {
//        onHit(rects[i]);
//        break;
//      }
//    }
//  }
//  public void mouseEntered(MouseEvent e) {}
//  public void mouseExited(MouseEvent e) {}
//  public void mouseClicked(MouseEvent e) {}
//
//  public void toggleNoteSystem() {
//    noteCalc.toggleSharpFlat();
//  }
//
//  public boolean isUsingSharps() {
//    return noteCalc.isUsingSharps();
//  }
//
//  public void resetAllHits() {
//    for(Rectangle r : rects) {
//      hits.put(r.toString(), false);
//    }
//    repaint();
//  }
//
//
//}

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class FretboardPanel extends JPanel implements MouseListener {
  // Configuration
  private static final int NUM_STRINGS = 6;
  private static final int NUM_FRETS = 25;
  private static final int PANEL_WIDTH = 1200;
  private static final int PANEL_HEIGHT = 307;

  // Background generator
  private BackgroundGenerator backgroundGenerator;
  private BufferedImage background;

  // Original coordinates (same as before)
  private int[] originalX = {6, 70, 132, 190, 246, 301, 357, 410, 462, 513, 564, 612, 660, 708, 755, 799, 843, 886, 926, 967, 1007, 1045, 1082, 1119, 1154};
  private int[] originalWidth = {50, 42, 36, 35, 32, 31, 29, 27, 27, 25, 23, 23, 22, 22, 21, 21, 21, 20, 21, 21, 20, 20, 20, 20, 20};
  private int[] originalStringY = {12, 52, 90, 129, 169, 208};

  // Arrays for positions
  private int[] fretX = new int[NUM_FRETS];
  private int[] fretWidth = new int[NUM_FRETS];
  private int[] stringY = new int[NUM_STRINGS];

  // Rectangle array
  private Rectangle[] rects;

  // Other variables
  Color fretColor = new Color(0f, 0f, 0f, .1f);
  int rectRoundness = 10;
  Hashtable<String, Boolean> hits = new Hashtable<String, Boolean>();
  NoteCalculator noteCalc = new NoteCalculator();

  public FretboardPanel() {
    // Set preferred size for the panel
    setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

    // Initialize background generator and create background
    backgroundGenerator = new BackgroundGenerator();
    regenerateBackground();

    // Initialize positions and rectangles
    initializeFretPositions();
    initializeStringPositions();
    createRectangles();

    // Initialize hit tracking
    for(Rectangle r : rects) {
      hits.put(r.toString(), false);
    }

    addMouseListener(this);
  }

  private void regenerateBackground() {
    background = backgroundGenerator.generateFretboard(NUM_STRINGS, NUM_FRETS, PANEL_WIDTH, PANEL_HEIGHT);
  }

  private void initializeFretPositions() {
    for(int i = 0; i < NUM_FRETS; i++) {
      fretX[i] = originalX[i];
      fretWidth[i] = originalWidth[i];
    }
  }

  private void initializeStringPositions() {
    for(int i = 0; i < NUM_STRINGS; i++) {
      stringY[i] = originalStringY[i];
    }
  }

  private void createRectangles() {
    rects = new Rectangle[NUM_STRINGS * NUM_FRETS];

    for(int string = 0; string < NUM_STRINGS; string++) {
      for(int fret = 0; fret < NUM_FRETS; fret++) {
        int index = string * NUM_FRETS + fret;
        rects[index] = new Rectangle(
                fretX[fret],
                stringY[string],
                fretWidth[fret],
                20
        );
      }
    }
  }

  void onHit(Rectangle r) {
    // Toggle the hit state
    boolean wasActive = hits.get(r.toString());
    hits.put(r.toString(), !wasActive);
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Draw generated background
    if (background != null) {
      g2d.drawImage(background, 0, 0, this);
    }

    // Draw all fret placeholders (semi-transparent rectangles)
    g2d.setColor(fretColor);
    for(int i = 0; i < rects.length; i++) {
      g2d.fillRoundRect(rects[i].x, rects[i].y, rects[i].width, rects[i].height,
              rectRoundness, rectRoundness);
    }

    // Draw red notes for active frets
    for(int i = 0; i < rects.length; i++) {
      if(hits.get(rects[i].toString())) {
        Rectangle r = rects[i];

        // Find string and fret indices
        int stringIndex = i / NUM_FRETS;
        int fretNumber = i % NUM_FRETS;

        // Draw red rectangle
        g2d.setColor(Color.RED);
        g2d.fillRoundRect(r.x, r.y, r.width, r.height, rectRoundness, rectRoundness);

        // Draw note name
        String noteName = noteCalc.getNoteName(stringIndex, fretNumber);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(noteName);
        int textHeight = fm.getAscent();
        int x = r.x + (r.width - textWidth) / 2;
        int y = r.y + (r.height + textHeight) / 2 - 2;

        g2d.drawString(noteName, x, y);
      }
    }
  }

  // MouseListener methods
  public void mousePressed(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {
    for(int i = 0; i < rects.length; i++) {
      if(rects[i].contains(e.getX(), e.getY())) {
        onHit(rects[i]);
        break;
      }
    }
  }
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}

  // Public methods for external control
  public void toggleNoteSystem() {
    noteCalc.toggleSharpFlat();
  }

  public boolean isUsingSharps() {
    return noteCalc.isUsingSharps();
  }

  public void resetAllHits() {
    for(Rectangle r : rects) {
      hits.put(r.toString(), false);
    }
    repaint();
  }

  // New method to get screenshot for export
  public BufferedImage getFretboardScreenshot() {
    BufferedImage screenshot = new BufferedImage(
            getWidth(),
            getHeight(),
            BufferedImage.TYPE_INT_ARGB
    );
    Graphics2D g2d = screenshot.createGraphics();
    paintComponent(g2d);
    g2d.dispose();
    return screenshot;
  }
}