import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.DefaultListCellRenderer;
import javax.swing.BorderFactory;

public class FretboardViewer extends JFrame implements ActionListener {

  FretboardPanel fretboardPanel = new FretboardPanel();
  JPanel controlPanel = new JPanel();
  JButton resetButton = new JButton("Reset");
  JButton toggleButton = new JButton("Sharps/Flats");
  JButton exportButton = new JButton("Export Screenshot");
  FlowLayout controlPanelFlow = new FlowLayout(FlowLayout.LEFT, 10, 5);

  public FretboardViewer() {
    setDarkTheme();
    setTitle("AZ Fretboard");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    resetButton.addActionListener(this);
    toggleButton.addActionListener(this);
    exportButton.addActionListener(this);

    Dimension buttonSize = new Dimension(150, 30);
    resetButton.setPreferredSize(buttonSize);
    toggleButton.setPreferredSize(buttonSize);
    exportButton.setPreferredSize(buttonSize);

    styleButton(resetButton);
    styleButton(toggleButton);
    styleButton(exportButton);

    controlPanel.setBackground(new Color(45, 45, 50));
    controlPanel.setLayout(controlPanelFlow);
    controlPanel.add(resetButton);
    controlPanel.add(toggleButton);
    controlPanel.add(exportButton);

    // --- Strings dropdown ---
    JLabel stringsLabel = new JLabel("Strings:");
    stringsLabel.setForeground(Color.WHITE);
    controlPanel.add(stringsLabel);

    String[] stringOptions = {"4", "5", "6", "7", "8", "9", "10"};
    JComboBox<String> stringCombo = new JComboBox<>(stringOptions);
    stringCombo.setSelectedItem("6");

    // Force‑white renderer for this combo box
    stringCombo.setRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value,
                                                    int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        c.setForeground(Color.WHITE);
        c.setBackground(isSelected ? new Color(60, 60, 70) : new Color(45, 45, 52));
        return c;
      }
    });

    stringCombo.addActionListener(e -> {
      int newStrings = Integer.parseInt((String) stringCombo.getSelectedItem());
      fretboardPanel.setNumStrings(newStrings);
      pack();
      setSize(1200, getHeight());
      setLocationRelativeTo(null);
    });
    controlPanel.add(stringCombo);

    add(controlPanel, BorderLayout.PAGE_START);
    add(fretboardPanel, BorderLayout.CENTER);

    controlPanel.revalidate();
    fretboardPanel.revalidate();

    pack();
    setSize(1200, getHeight());
    setResizable(false);
    setLocationRelativeTo(null);

    getContentPane().setBackground(new Color(45, 45, 50));
  }

  // ------------------------------------------------------------------------
  //  DARK THEME – PLATFORM SPECIFIC BUT CONSISTENT UI
  // ------------------------------------------------------------------------
  private void setDarkTheme() {
    try {
      String os = System.getProperty("os.name").toLowerCase();

      // ---------- macOS ----------
      if (os.contains("mac")) {
        try {
          UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
          UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
      }
      // ---------- Windows / Linux ----------
      else {
        // Use Nimbus on Windows/Linux – fully stylable
        try {
          UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
          UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }

        if (os.contains("windows")) {
          System.setProperty("sun.awt.noerasebackground", "true");
        }
      }

      // --- Dark window decorations (title bar, borders) ---
      JFrame.setDefaultLookAndFeelDecorated(true);
      JDialog.setDefaultLookAndFeelDecorated(true);

      // === GLOBAL DARK THEME – APPLIES TO ALL COMPONENTS ===
      UIManager.put("nimbusBase", new Color(45, 45, 50));
      UIManager.put("nimbusBlueGrey", new Color(35, 35, 40));
      UIManager.put("nimbusBorder", new Color(30, 30, 35));

      UIManager.put("Panel.background", new Color(45, 45, 50));
      UIManager.put("OptionPane.background", new Color(45, 45, 50));
      UIManager.put("OptionPane.foreground", Color.WHITE);
      UIManager.put("OptionPane.messageForeground", Color.WHITE);
      UIManager.put("OptionPane.messageBackground", new Color(45, 45, 50));

      UIManager.put("Button.background", new Color(70, 70, 80));
      UIManager.put("Button.foreground", Color.WHITE);
      UIManager.put("Button.select", new Color(60, 60, 70));

      UIManager.put("TextField.background", new Color(60, 60, 65));
      UIManager.put("TextField.foreground", Color.WHITE);
      UIManager.put("TextField.caretForeground", Color.WHITE);
      UIManager.put("TextField.selectionBackground", new Color(50, 50, 60));
      UIManager.put("TextField.selectionForeground", Color.WHITE);

      UIManager.put("List.background", new Color(60, 60, 65));
      UIManager.put("List.foreground", Color.WHITE);
      UIManager.put("List.selectionBackground", new Color(50, 50, 60));
      UIManager.put("List.selectionForeground", Color.WHITE);

      UIManager.put("Table.background", new Color(60, 60, 65));
      UIManager.put("Table.foreground", Color.WHITE);
      UIManager.put("Table.selectionBackground", new Color(50, 50, 60));
      UIManager.put("Table.selectionForeground", Color.WHITE);

      UIManager.put("Tree.background", new Color(60, 60, 65));
      UIManager.put("Tree.foreground", Color.WHITE);
      UIManager.put("Tree.selectionBackground", new Color(50, 50, 60));
      UIManager.put("Tree.selectionForeground", Color.WHITE);

      // === COMBO BOX – FULLY DARK ===
      UIManager.put("ComboBox.background", new Color(50, 50, 58));
      UIManager.put("ComboBox.foreground", Color.WHITE);
      UIManager.put("ComboBox.selectionBackground", new Color(60, 60, 70));
      UIManager.put("ComboBox.selectionForeground", Color.WHITE);
      UIManager.put("ComboBox.buttonBackground", new Color(50, 50, 58));
      UIManager.put("ComboBox.buttonForeground", Color.WHITE);
      UIManager.put("ComboBox.buttonDarkShadow", new Color(35, 35, 40));
      UIManager.put("ComboBox.buttonHighlight", new Color(35, 35, 40));
      UIManager.put("ComboBox.buttonShadow", new Color(35, 35, 40));
      UIManager.put("ComboBox.listBackground", new Color(45, 45, 52));
      UIManager.put("ComboBox.listForeground", Color.WHITE);
      UIManager.put("ComboBox.border", BorderFactory.createLineBorder(new Color(40, 40, 45), 1));
      UIManager.put("ComboBox.editorBorder", BorderFactory.createLineBorder(new Color(40, 40, 45), 1));

      UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(new Color(40, 40, 45), 1));

      // === GLOBAL COMBO BOX RENDERER – forces white text everywhere ===
      UIManager.getLookAndFeelDefaults().put("ComboBox.renderer", new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
          Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
          c.setForeground(Color.WHITE);
          c.setBackground(isSelected ? new Color(60, 60, 70) : new Color(45, 45, 52));
          return c;
        }
      });

    } catch (Exception e) {
      try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      } catch (Exception ex) {
        System.err.println("Could not set Look and Feel: " + ex.getMessage());
      }
      getContentPane().setBackground(new Color(45, 45, 50));
    }
  }

  // ------------------------------------------------------------------------
  //  BUTTON STYLING
  // ------------------------------------------------------------------------
  private void styleButton(JButton button) {
    button.setBackground(new Color(70, 70, 80));
    button.setForeground(Color.WHITE);
    button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 110), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
    ));
    button.setFocusPainted(false);
    button.setOpaque(true);
    button.setContentAreaFilled(true);

    button.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(90, 90, 100));
      }
      public void mouseExited(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(70, 70, 80));
      }
      public void mousePressed(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(50, 50, 60));
      }
    });
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == resetButton) {
      fretboardPanel.resetAllHits();
      fretboardPanel.repaint();
    } else if (e.getSource() == toggleButton) {
      fretboardPanel.toggleNoteSystem();
      toggleButton.setText(fretboardPanel.isUsingSharps() ? "Switch to Flats" : "Switch to Sharps");
      toggleButton.setPreferredSize(new Dimension(150, 30));
      fretboardPanel.repaint();
    } else if (e.getSource() == exportButton) {
      exportScreenshot();
    }
  }

  // ------------------------------------------------------------------------
  //  EXPORT – NATIVE DARK ON MAC, DARK NIMBUS ON WINDOWS/LINUX
  // ------------------------------------------------------------------------
  private void exportScreenshot() {
    String os = System.getProperty("os.name").toLowerCase();
    File selectedFile = null;

    if (os.contains("mac")) {
      // --- macOS: native dark file dialog (automatic in dark mode) ---
      FileDialog fileDialog = new FileDialog(this, "Save Screenshot", FileDialog.SAVE);
      fileDialog.setFile("Fretboard.png");
      fileDialog.setVisible(true);

      String directory = fileDialog.getDirectory();
      String filename = fileDialog.getFile();
      if (directory != null && filename != null) {
        selectedFile = new File(directory, filename);
        if (!selectedFile.getName().toLowerCase().endsWith(".png")) {
          selectedFile = new File(selectedFile.getAbsolutePath() + ".png");
        }
      }
    } else {
      // --- Windows / Linux: fully dark Nimbus JFileChooser ---
      JFileChooser fileChooser = new JFileChooser();
      darkenFileChooser(fileChooser);
      fileChooser.setSelectedFile(new File("Fretboard.png"));
      fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));

      if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        selectedFile = fileChooser.getSelectedFile();
        if (!selectedFile.getName().toLowerCase().endsWith(".png")) {
          selectedFile = new File(selectedFile.getAbsolutePath() + ".png");
        }
      }
    }

    if (selectedFile != null) {
      try {
        BufferedImage screenshot = fretboardPanel.getFretboardScreenshot();
        ImageIO.write(screenshot, "PNG", selectedFile);
        JOptionPane.showMessageDialog(this,
                "Screenshot saved!\n" + selectedFile.getName(),
                "Export Complete",
                JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error saving: " + ex.getMessage(),
                "Export Error",
                JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  // ------------------------------------------------------------------------
  //  DARKEN JFileChooser FOR WINDOWS / LINUX (Nimbus LAF)
  //  - Recursively forces dark backgrounds & white text
  //  - No custom renderers on combo boxes – uses global UIManager renderer
  // ------------------------------------------------------------------------
  private void darkenFileChooser(JFileChooser chooser) {
    SwingUtilities.updateComponentTreeUI(chooser);
    chooser.setBackground(new Color(45, 45, 50));
    chooser.setForeground(Color.WHITE);
    darkenComponents(chooser.getComponents());
  }

  private void darkenComponents(Component[] components) {
    for (Component c : components) {
      c.setBackground(new Color(45, 45, 50));
      c.setForeground(Color.WHITE);

      if (c instanceof JPanel || c instanceof JScrollPane || c instanceof JViewport) {
        c.setBackground(new Color(45, 45, 50));
        c.setForeground(Color.WHITE);
      }
      if (c instanceof JLabel) {
        c.setForeground(Color.WHITE);
      }
      if (c instanceof JButton) {
        c.setBackground(new Color(70, 70, 80));
        c.setForeground(Color.WHITE);
      }
      if (c instanceof JComboBox) {
        JComboBox<?> cb = (JComboBox<?>) c;
        cb.setBackground(new Color(50, 50, 58));
        cb.setForeground(Color.WHITE);
        // IMPORTANT: Do NOT set a custom renderer here!
        // The global UIManager renderer handles colors correctly.
      }
      if (c instanceof JList) {
        JList<?> list = (JList<?>) c;
        list.setBackground(new Color(60, 60, 65));
        list.setForeground(Color.WHITE);
        list.setSelectionBackground(new Color(50, 50, 60));
        list.setSelectionForeground(Color.WHITE);
      }
      if (c instanceof JTextField) {
        c.setBackground(new Color(60, 60, 65));
        c.setForeground(Color.WHITE);
      }
      if (c instanceof Container) {
        darkenComponents(((Container) c).getComponents());
      }
    }
  }

  // ------------------------------------------------------------------------
  //  MAIN
  // ------------------------------------------------------------------------
  public static void main(String[] args) {
    String os = System.getProperty("os.name").toLowerCase();

    if (os.contains("mac")) {
      System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      // DO NOT set apple.awt.fileDialog – we want the native dark dialog
    } else if (os.contains("windows")) {
      System.setProperty("sun.awt.noerasebackground", "true");
    }

    SwingUtilities.invokeLater(() -> {
      try {
        new FretboardViewer().setVisible(true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
}