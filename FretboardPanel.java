import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;

public class FretboardPanel extends JPanel implements MouseListener {

  Rectangle string1_fret00 = new Rectangle(6, 12, 50, 20);
  Rectangle string1_fret01 = new Rectangle(70, 12, 42, 20);
  Rectangle string1_fret02 = new Rectangle(132, 12, 36, 20);
  Rectangle string1_fret03 = new Rectangle(190, 12, 35, 20);
  Rectangle string1_fret04 = new Rectangle(246, 12, 32, 20);
  Rectangle string1_fret05 = new Rectangle(301, 12, 31, 20);
  Rectangle string1_fret06 = new Rectangle(357, 12, 29, 20);
  Rectangle string1_fret07 = new Rectangle(410, 12, 27, 20);
  Rectangle string1_fret08 = new Rectangle(462, 12, 27, 20);
  Rectangle string1_fret09 = new Rectangle(513, 12, 25, 20);
  Rectangle string1_fret10 = new Rectangle(564, 12, 23, 20);
  Rectangle string1_fret11 = new Rectangle(612, 12, 23, 20);
  Rectangle string1_fret12 = new Rectangle(660, 12, 22, 20);
  Rectangle string1_fret13 = new Rectangle(708, 12, 22, 20);
  Rectangle string1_fret14 = new Rectangle(755, 12, 21, 20);
  Rectangle string1_fret15 = new Rectangle(799, 12, 21, 20);
  Rectangle string1_fret16 = new Rectangle(843, 12, 21, 20);
  Rectangle string1_fret17 = new Rectangle(886, 12, 20, 20);
  Rectangle string1_fret18 = new Rectangle(926, 12, 21, 20);
  Rectangle string1_fret19 = new Rectangle(967, 12, 21, 20);
  Rectangle string1_fret20 = new Rectangle(1007, 12, 20, 20);
  Rectangle string1_fret21 = new Rectangle(1045, 12, 20, 20);
  Rectangle string1_fret22 = new Rectangle(1082, 12, 20, 20);
  Rectangle string1_fret23 = new Rectangle(1119, 12, 20, 20);
  Rectangle string1_fret24 = new Rectangle(1154, 12, 20, 20);

  Rectangle string2_fret00 = new Rectangle(6, 52, 50, 20);
  Rectangle string2_fret01 = new Rectangle(70, 52, 42, 20);
  Rectangle string2_fret02 = new Rectangle(132, 52, 36, 20);
  Rectangle string2_fret03 = new Rectangle(190, 52, 35, 20);
  Rectangle string2_fret04 = new Rectangle(246, 52, 32, 20);
  Rectangle string2_fret05 = new Rectangle(301, 52, 31, 20);
  Rectangle string2_fret06 = new Rectangle(357, 52, 29, 20);
  Rectangle string2_fret07 = new Rectangle(410, 52, 27, 20);
  Rectangle string2_fret08 = new Rectangle(462, 52, 27, 20);
  Rectangle string2_fret09 = new Rectangle(513, 52, 25, 20);
  Rectangle string2_fret10 = new Rectangle(564, 52, 23, 20);
  Rectangle string2_fret11 = new Rectangle(612, 52, 23, 20);
  Rectangle string2_fret12 = new Rectangle(660, 52, 22, 20);
  Rectangle string2_fret13 = new Rectangle(708, 52, 22, 20);
  Rectangle string2_fret14 = new Rectangle(755, 52, 21, 20);
  Rectangle string2_fret15 = new Rectangle(799, 52, 21, 20);
  Rectangle string2_fret16 = new Rectangle(843, 52, 21, 20);
  Rectangle string2_fret17 = new Rectangle(886, 52, 20, 20);
  Rectangle string2_fret18 = new Rectangle(926, 52, 21, 20);
  Rectangle string2_fret19 = new Rectangle(967, 52, 21, 20);
  Rectangle string2_fret20 = new Rectangle(1007, 52, 20, 20);
  Rectangle string2_fret21 = new Rectangle(1045, 52, 20, 20);
  Rectangle string2_fret22 = new Rectangle(1082, 52, 20, 20);
  Rectangle string2_fret23 = new Rectangle(1119, 52, 20, 20);
  Rectangle string2_fret24 = new Rectangle(1154, 52, 20, 20);

  Rectangle string3_fret00 = new Rectangle(6, 90, 50, 20);
  Rectangle string3_fret01 = new Rectangle(70, 90, 42, 20);
  Rectangle string3_fret02 = new Rectangle(132, 90, 36, 20);
  Rectangle string3_fret03 = new Rectangle(190, 90, 35, 20);
  Rectangle string3_fret04 = new Rectangle(246, 90, 32, 20);
  Rectangle string3_fret05 = new Rectangle(301, 90, 31, 20);
  Rectangle string3_fret06 = new Rectangle(357, 90, 29, 20);
  Rectangle string3_fret07 = new Rectangle(410, 90, 27, 20);
  Rectangle string3_fret08 = new Rectangle(462, 90, 27, 20);
  Rectangle string3_fret09 = new Rectangle(513, 90, 25, 20);
  Rectangle string3_fret10 = new Rectangle(564, 90, 23, 20);
  Rectangle string3_fret11 = new Rectangle(612, 90, 23, 20);
  Rectangle string3_fret12 = new Rectangle(660, 90, 22, 20);
  Rectangle string3_fret13 = new Rectangle(708, 90, 22, 20);
  Rectangle string3_fret14 = new Rectangle(755, 90, 21, 20);
  Rectangle string3_fret15 = new Rectangle(799, 90, 21, 20);
  Rectangle string3_fret16 = new Rectangle(843, 90, 21, 20);
  Rectangle string3_fret17 = new Rectangle(886, 90, 20, 20);
  Rectangle string3_fret18 = new Rectangle(926, 90, 21, 20);
  Rectangle string3_fret19 = new Rectangle(967, 90, 21, 20);
  Rectangle string3_fret20 = new Rectangle(1007, 90, 20, 20);
  Rectangle string3_fret21 = new Rectangle(1045, 90, 20, 20);
  Rectangle string3_fret22 = new Rectangle(1082, 90, 20, 20);
  Rectangle string3_fret23 = new Rectangle(1119, 90, 20, 20);
  Rectangle string3_fret24 = new Rectangle(1154, 90, 20, 20);

  Rectangle string4_fret00 = new Rectangle(6, 129, 50, 20);
  Rectangle string4_fret01 = new Rectangle(70, 129, 42, 20);
  Rectangle string4_fret02 = new Rectangle(132, 129, 36, 20);
  Rectangle string4_fret03 = new Rectangle(190, 129, 35, 20);
  Rectangle string4_fret04 = new Rectangle(246, 129, 32, 20);
  Rectangle string4_fret05 = new Rectangle(301, 129, 31, 20);
  Rectangle string4_fret06 = new Rectangle(357, 129, 29, 20);
  Rectangle string4_fret07 = new Rectangle(410, 129, 27, 20);
  Rectangle string4_fret08 = new Rectangle(462, 129, 27, 20);
  Rectangle string4_fret09 = new Rectangle(513, 129, 25, 20);
  Rectangle string4_fret10 = new Rectangle(564, 129, 23, 20);
  Rectangle string4_fret11 = new Rectangle(612, 129, 23, 20);
  Rectangle string4_fret12 = new Rectangle(660, 129, 22, 20);
  Rectangle string4_fret13 = new Rectangle(708, 129, 22, 20);
  Rectangle string4_fret14 = new Rectangle(755, 129, 21, 20);
  Rectangle string4_fret15 = new Rectangle(799, 129, 21, 20);
  Rectangle string4_fret16 = new Rectangle(843, 129, 21, 20);
  Rectangle string4_fret17 = new Rectangle(886, 129, 20, 20);
  Rectangle string4_fret18 = new Rectangle(926, 129, 21, 20);
  Rectangle string4_fret19 = new Rectangle(967, 129, 21, 20);
  Rectangle string4_fret20 = new Rectangle(1007, 129, 20, 20);
  Rectangle string4_fret21 = new Rectangle(1045, 129, 20, 20);
  Rectangle string4_fret22 = new Rectangle(1082, 129, 20, 20);
  Rectangle string4_fret23 = new Rectangle(1119, 129, 20, 20);
  Rectangle string4_fret24 = new Rectangle(1154, 129, 20, 20);

  Rectangle string5_fret00 = new Rectangle(6, 169, 50, 20);
  Rectangle string5_fret01 = new Rectangle(70, 169, 42, 20);
  Rectangle string5_fret02 = new Rectangle(132, 169, 36, 20);
  Rectangle string5_fret03 = new Rectangle(190, 169, 35, 20);
  Rectangle string5_fret04 = new Rectangle(246, 169, 32, 20);
  Rectangle string5_fret05 = new Rectangle(301, 169, 31, 20);
  Rectangle string5_fret06 = new Rectangle(357, 169, 29, 20);
  Rectangle string5_fret07 = new Rectangle(410, 169, 27, 20);
  Rectangle string5_fret08 = new Rectangle(462, 169, 27, 20);
  Rectangle string5_fret09 = new Rectangle(513, 169, 25, 20);
  Rectangle string5_fret10 = new Rectangle(564, 169, 23, 20);
  Rectangle string5_fret11 = new Rectangle(612, 169, 23, 20);
  Rectangle string5_fret12 = new Rectangle(660, 169, 22, 20);
  Rectangle string5_fret13 = new Rectangle(708, 169, 22, 20);
  Rectangle string5_fret14 = new Rectangle(755, 169, 21, 20);
  Rectangle string5_fret15 = new Rectangle(799, 169, 21, 20);
  Rectangle string5_fret16 = new Rectangle(843, 169, 21, 20);
  Rectangle string5_fret17 = new Rectangle(886, 169, 20, 20);
  Rectangle string5_fret18 = new Rectangle(926, 169, 21, 20);
  Rectangle string5_fret19 = new Rectangle(967, 169, 21, 20);
  Rectangle string5_fret20 = new Rectangle(1007, 169, 20, 20);
  Rectangle string5_fret21 = new Rectangle(1045, 169, 20, 20);
  Rectangle string5_fret22 = new Rectangle(1082, 169, 20, 20);
  Rectangle string5_fret23 = new Rectangle(1119, 169, 20, 20);
  Rectangle string5_fret24 = new Rectangle(1154, 169, 20, 20);

  Rectangle string6_fret00 = new Rectangle(6, 208, 50, 20);
  Rectangle string6_fret01 = new Rectangle(70, 208, 42, 20);
  Rectangle string6_fret02 = new Rectangle(132, 208, 36, 20);
  Rectangle string6_fret03 = new Rectangle(190, 208, 35, 20);
  Rectangle string6_fret04 = new Rectangle(246, 208, 32, 20);
  Rectangle string6_fret05 = new Rectangle(301, 208, 31, 20);
  Rectangle string6_fret06 = new Rectangle(357, 208, 29, 20);
  Rectangle string6_fret07 = new Rectangle(410, 208, 27, 20);
  Rectangle string6_fret08 = new Rectangle(462, 208, 27, 20);
  Rectangle string6_fret09 = new Rectangle(513, 208, 25, 20);
  Rectangle string6_fret10 = new Rectangle(564, 208, 23, 20);
  Rectangle string6_fret11 = new Rectangle(612, 208, 23, 20);
  Rectangle string6_fret12 = new Rectangle(660, 208, 22, 20);
  Rectangle string6_fret13 = new Rectangle(708, 208, 22, 20);
  Rectangle string6_fret14 = new Rectangle(755, 208, 21, 20);
  Rectangle string6_fret15 = new Rectangle(799, 208, 21, 20);
  Rectangle string6_fret16 = new Rectangle(843, 208, 21, 20);
  Rectangle string6_fret17 = new Rectangle(886, 208, 20, 20);
  Rectangle string6_fret18 = new Rectangle(926, 208, 21, 20);
  Rectangle string6_fret19 = new Rectangle(967, 208, 21, 20);
  Rectangle string6_fret20 = new Rectangle(1007, 208, 20, 20);
  Rectangle string6_fret21 = new Rectangle(1045, 208, 20, 20);
  Rectangle string6_fret22 = new Rectangle(1082, 208, 20, 20);
  Rectangle string6_fret23 = new Rectangle(1119, 208, 20, 20);
  Rectangle string6_fret24 = new Rectangle(1154, 208, 20, 20);

  Rectangle[] rects =
          {string1_fret00, string1_fret01, string1_fret02, string1_fret03, string1_fret04, string1_fret05,
          string1_fret06, string1_fret07, string1_fret08, string1_fret09, string1_fret10, string1_fret11,
          string1_fret12, string1_fret13, string1_fret14, string1_fret15, string1_fret16, string1_fret17,
          string1_fret18, string1_fret19, string1_fret20, string1_fret21, string1_fret22, string1_fret23, string1_fret24,

          string2_fret00, string2_fret01, string2_fret02, string2_fret03, string2_fret04, string2_fret05,
          string2_fret06, string2_fret07, string2_fret08, string2_fret09, string2_fret10, string2_fret11,
          string2_fret12, string2_fret13, string2_fret14, string2_fret15, string2_fret16, string2_fret17,
          string2_fret18, string2_fret19, string2_fret20, string2_fret21, string2_fret22, string2_fret23, string2_fret24,

          string3_fret00, string3_fret01, string3_fret02, string3_fret03, string3_fret04, string3_fret05,
          string3_fret06, string3_fret07, string3_fret08, string3_fret09, string3_fret10, string3_fret11,
          string3_fret12, string3_fret13, string3_fret14, string3_fret15, string3_fret16, string3_fret17,
          string3_fret18, string3_fret19, string3_fret20, string3_fret21, string3_fret22, string3_fret23, string3_fret24,

          string4_fret00, string4_fret01, string4_fret02, string4_fret03, string4_fret04, string4_fret05,
          string4_fret06, string4_fret07, string4_fret08, string4_fret09, string4_fret10, string4_fret11,
          string4_fret12, string4_fret13, string4_fret14, string4_fret15, string4_fret16, string4_fret17,
          string4_fret18, string4_fret19, string4_fret20, string4_fret21, string4_fret22, string4_fret23, string4_fret24,

          string5_fret00, string5_fret01, string5_fret02, string5_fret03, string5_fret04, string5_fret05,
          string5_fret06, string5_fret07, string5_fret08, string5_fret09, string5_fret10, string5_fret11,
          string5_fret12, string5_fret13, string5_fret14, string5_fret15, string5_fret16, string5_fret17,
          string5_fret18, string5_fret19, string5_fret20, string5_fret21, string5_fret22, string5_fret23, string5_fret24,

          string6_fret00, string6_fret01, string6_fret02, string6_fret03, string6_fret04, string6_fret05,
          string6_fret06, string6_fret07, string6_fret08, string6_fret09, string6_fret10, string6_fret11,
          string6_fret12, string6_fret13, string6_fret14, string6_fret15, string6_fret16, string6_fret17,
          string6_fret18, string6_fret19, string6_fret20, string6_fret21, string6_fret22, string6_fret23, string6_fret24};

  BufferedImage background;
  BufferedImage subImage;

  Color fretColor = new Color(0f, 0f, 0f, .1f);

  int rectRoundness = 10;

  Hashtable<String, Boolean> hits = new Hashtable<String, Boolean>();

  public FretboardPanel() {
    try {
      background = ImageIO.read(getClass().getResource("fretboard.png"));
    } catch(MalformedURLException e) {
      e.printStackTrace();
    } catch(IOException e) {
      e.printStackTrace();
    }

    for(Rectangle r : rects) {
      hits.put(r.toString(), false);
    }

    addMouseListener(this);
  }

  void onHit(Rectangle r) {
    Graphics2D g2d = (Graphics2D) this.getGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    if(!hits.get(r.toString())) {
      g2d.setColor(Color.RED);
      g2d.fillRoundRect(r.x, r.y, r.width, r.height, rectRoundness, rectRoundness);
      hits.replace(r.toString(), false, true);
    } else {
      subImage = background.getSubimage(r.x, r.y, r.width, r.height);
      g2d.drawImage(subImage, r.x, r.y, this);
      g2d.setColor(fretColor);
      g2d.fillRoundRect(r.x, r.y, r.width, r.height, rectRoundness, rectRoundness);
      hits.replace(r.toString(), true, false);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;


    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2d.drawImage(background, 0, 0, this);
    g2d.setColor(fretColor);

    for(int i = 0; i < rects.length; i++) {
      g2d.fillRoundRect(rects[i].x, rects[i].y, rects[i].width, rects[i].height,
              rectRoundness, rectRoundness);
    }
  }

  public void mousePressed(MouseEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
    for(int i = 0; i < rects.length; i++) {
      if(rects[i].contains(e.getX(), e.getY())) {
        onHit(rects[i]);
        //System.out.println("Hit on: "+ rects[i].toString());
      }
    }
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
  }
}