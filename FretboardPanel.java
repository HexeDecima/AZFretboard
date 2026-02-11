import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class FretboardPanel extends JPanel implements MouseListener {
  // Configuration
  private int numStrings = 6;                     // can be changed later
  private static final int NUM_FRETS = 25;        // fixed number of frets
  private static final int PANEL_WIDTH = 1200;    // fixed width
  private int panelHeight;                        // calculated dynamically
  private FretsCalculator fretsCalculator;

  // Background generator
  private BackgroundGenerator backgroundGenerator;
  private BufferedImage background;

  // Original coordinates (same as before)
//  private int[] originalX = {6, 70, 132, 190, 246, 301, 357, 410, 462, 513, 564, 612, 660, 708, 755, 799, 843, 886, 926, 967, 1007, 1045, 1082, 1119, 1154};
//  private int[] originalWidth = {50, 42, 36, 35, 32, 31, 29, 27, 27, 25, 23, 23, 22, 22, 21, 21, 21, 20, 21, 21, 20, 20, 20, 20, 20};
//  private int[] originalStringY = {12, 52, 90, 129, 169, 208};

  // Arrays for positions
  private int[] fretX = new int[NUM_FRETS];
  private int[] fretWidth = new int[NUM_FRETS];
  private int[] stringY;    // will be created in initializeStringPositions()

  // Rectangle array
  private Rectangle[] rects;

  // Other variables
  Color fretColor = new Color(0f, 0f, 0f, .1f);
  int rectRoundness = 10;
  Hashtable<String, Boolean> hits = new Hashtable<String, Boolean>();
  NoteCalculator noteCalc = new NoteCalculator();

  public FretboardPanel() {
    // Set preferred size for the panel
    setPreferredSize(new Dimension(PANEL_WIDTH, panelHeight));
    initializeStringPositions();
    createRectangles();

    // Set dark background for the panel
    setBackground(new Color(45, 45, 50));

    // Initialize calculator
    fretsCalculator = new FretsCalculator();
    fretsCalculator.printAllPositions(); // This will show the 51 positions

    // Initialize background generator
    backgroundGenerator = new BackgroundGenerator(fretsCalculator);
    regenerateBackground();

    // Calculate initial panel height
    panelHeight = backgroundGenerator.calculateFretboardHeight(numStrings);

    // Get calculated positions from the calculator
    int[] redRectLeft = fretsCalculator.getRedRectLeftEdges();
    int[] redRectWidth = fretsCalculator.getRedRectWidths();

    // Copy to our arrays
    System.arraycopy(redRectLeft, 0, fretX, 0, Math.min(redRectLeft.length, NUM_FRETS));
    System.arraycopy(redRectWidth, 0, fretWidth, 0, Math.min(redRectWidth.length, NUM_FRETS));

    // Initialize string positions and create rectangles
    initializeStringPositions();
    createRectangles();

    // Initialize hit tracking
    for(Rectangle r : rects) {
      hits.put(r.toString(), false);
    }

    addMouseListener(this);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(PANEL_WIDTH, panelHeight);
  }

  private void regenerateBackground() {
    backgroundGenerator = new BackgroundGenerator(fretsCalculator);
    int height = backgroundGenerator.calculateFretboardHeight(numStrings);
    this.panelHeight = height;
    // DEBUG: print values
    System.out.println("numStrings: " + numStrings);
    System.out.println("panelHeight: " + panelHeight);
    int lastStringY = stringY[numStrings - 1];
    int bottomY = lastStringY + 20; // rectangle bottom
    System.out.println("lastStringY: " + lastStringY + ", bottomY: " + bottomY + ", margin: " + (panelHeight - bottomY));

    background = backgroundGenerator.generateFretboard(numStrings, NUM_FRETS, PANEL_WIDTH, height);
    revalidate();
    repaint();
  }

  public void setNumStrings(int strings) {
    this.numStrings = strings;

    // Recalculate string positions and rectangles
    initializeStringPositions();
    createRectangles();

    // Reset hit states for new rectangles
    hits.clear();
    for (Rectangle r : rects) {
      hits.put(r.toString(), false);
    }

    // Regenerate background with new string count
    regenerateBackground();

    // Update panel size
    revalidate();
    repaint();
  }

  private void initializeStringPositions() {
    stringY = new int[numStrings];
    int topMargin = 20;
    int spacing = 40;
    for (int i = 0; i < numStrings; i++) {
      stringY[i] = topMargin + (i * spacing); // rectangle top
    }
  }

  private void createRectangles() {
    rects = new Rectangle[numStrings * NUM_FRETS];

    for(int string = 0; string < numStrings; string++) {
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