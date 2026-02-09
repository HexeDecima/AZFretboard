import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FretboardViewer extends JFrame implements ActionListener {

  FretboardPanel fretboardPanel = new FretboardPanel();
  JPanel controlPanel = new JPanel();
  JButton resetButton = new JButton("Reset");
  JButton toggleButton = new JButton("Sharps/Flats");
  JButton exportButton = new JButton("Export Screenshot");
  FlowLayout controlPanelFlow = new FlowLayout(FlowLayout.LEFT, 10, 5);

  public FretboardViewer() {
    // Set dark theme for the entire application
    setDarkTheme();

    setTitle("AZ Fretboard");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200, 307);
    setResizable(false);
    setLocationRelativeTo(null);

    resetButton.addActionListener(this);
    toggleButton.addActionListener(this);
    exportButton.addActionListener(this);

    // Set button sizes to be consistent
    Dimension buttonSize = new Dimension(150, 30);
    resetButton.setPreferredSize(buttonSize);
    toggleButton.setPreferredSize(buttonSize);
    exportButton.setPreferredSize(buttonSize);

    // Style buttons
    styleButton(resetButton);
    styleButton(toggleButton);
    styleButton(exportButton);

    // Set dark colors for control panel
    controlPanel.setBackground(new Color(45, 45, 50));
    controlPanel.setLayout(controlPanelFlow);
    controlPanel.add(resetButton);
    controlPanel.add(toggleButton);
    controlPanel.add(exportButton);

    add(controlPanel, BorderLayout.PAGE_START);
    add(fretboardPanel, BorderLayout.CENTER);

    // Set window background
    getContentPane().setBackground(new Color(45, 45, 50));
  }

  private void setDarkTheme() {
    try {
      // Set platform-specific properties for dark title bars
      String os = System.getProperty("os.name").toLowerCase();

      if (os.contains("mac")) {
        // macOS specific dark mode for title bars
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
      } else if (os.contains("windows")) {
        // Windows: try to force dark mode
        System.setProperty("sun.awt.noerasebackground", "true");
        // Use Windows look and feel
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      } else {
        // Linux/Unix: use GTK if available
        try {
          UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (Exception e) {
          // Fall back to Metal
          UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }
      }

      // Customize for dark mode
      UIManager.put("Panel.background", new Color(45, 45, 50));
      UIManager.put("OptionPane.background", new Color(45, 45, 50));
      UIManager.put("OptionPane.messageForeground", Color.WHITE);
      UIManager.put("OptionPane.messageBackground", new Color(45, 45, 50));
      UIManager.put("OptionPane.foreground", Color.WHITE);

      // File chooser customization
      UIManager.put("FileChooser.background", new Color(50, 50, 55));
      UIManager.put("FileChooser.foreground", Color.WHITE);

      // Button customization
      UIManager.put("Button.background", new Color(70, 70, 80));
      UIManager.put("Button.foreground", Color.WHITE);
      UIManager.put("Button.select", new Color(90, 90, 100));

      // Text field customization
      UIManager.put("TextField.background", new Color(60, 60, 65));
      UIManager.put("TextField.foreground", Color.WHITE);
      UIManager.put("TextField.caretForeground", Color.WHITE);
      UIManager.put("TextField.selectionBackground", new Color(80, 80, 90));
      UIManager.put("TextField.selectionForeground", Color.WHITE);

      // List and combo box customization
      UIManager.put("List.background", new Color(50, 50, 55));
      UIManager.put("List.foreground", Color.WHITE);
      UIManager.put("List.selectionBackground", new Color(80, 80, 90));
      UIManager.put("List.selectionForeground", Color.WHITE);

    } catch (ClassNotFoundException | InstantiationException |
             IllegalAccessException | UnsupportedLookAndFeelException e) {
      // If any look and feel fails, fall back to Metal
      try {
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      } catch (Exception ex) {
        System.err.println("Could not set any look and feel: " + ex.getMessage());
      }
      // Fallback to basic dark colors
      getContentPane().setBackground(new Color(45, 45, 50));
    }
  }

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

    // Simple hover effect
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

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == resetButton) {
      fretboardPanel.resetAllHits();
      fretboardPanel.repaint();
    } else if (e.getSource() == toggleButton) {
      fretboardPanel.toggleNoteSystem();

      if (fretboardPanel.isUsingSharps()) {
        toggleButton.setText("Switch to Flats");
      } else {
        toggleButton.setText("Switch to Sharps");
      }

      // Keep button size consistent
      toggleButton.setPreferredSize(new Dimension(150, 30));
      fretboardPanel.repaint();
    } else if (e.getSource() == exportButton) {
      exportScreenshot();
    }
  }

  private void exportScreenshot() {
    // Simple dialog for save location
    JFileChooser fileChooser = new JFileChooser();

    // Set simple default filename
    String defaultName = "Fretboard";
    fileChooser.setSelectedFile(new File(defaultName + ".png"));

    // Filter for PNG files only
    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
    fileChooser.setFileFilter(filter);

    // Try to select just the filename (without extension) after dialog is shown
    // We need to do this after the dialog is visible
    SwingUtilities.invokeLater(() -> {
      try {
        // Try to find the filename text field in the file chooser
        selectFilenameWithoutExtension(fileChooser);
      } catch (Exception e) {
        // If it fails, no problem - just continue
      }
    });

    // Show save dialog
    int result = fileChooser.showSaveDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();

      // Ensure file has .png extension
      if (!file.getName().toLowerCase().endsWith(".png")) {
        file = new File(file.getAbsolutePath() + ".png");
      }

      try {
        // Get screenshot from FretboardPanel
        BufferedImage screenshot = fretboardPanel.getFretboardScreenshot();

        // Save as PNG
        ImageIO.write(screenshot, "PNG", file);

        // Show simple confirmation
        JOptionPane.showMessageDialog(this,
                "Screenshot saved!\n" + file.getName(),
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

  // Helper method to select filename without extension
  private void selectFilenameWithoutExtension(Container container) {
    for (Component comp : container.getComponents()) {
      if (comp instanceof JTextField) {
        JTextField textField = (JTextField) comp;
        String text = textField.getText();

        // Find where .png starts (or . if any extension)
        int dotIndex = text.lastIndexOf('.');
        if (dotIndex > 0) {
          // Select text up to the dot (without extension)
          textField.setSelectionStart(0);
          textField.setSelectionEnd(dotIndex);
          textField.requestFocus();
          break;
        }
      } else if (comp instanceof Container) {
        selectFilenameWithoutExtension((Container) comp);
      }
    }
  }

  // Helper method to find and enable text selection in the JFileChooser
  private void enableTextFieldSelection(JFileChooser fileChooser) {
    // Try to find the text field component in the file chooser
    findAndEnableTextFields(fileChooser);
  }

  private void findAndEnableTextFields(java.awt.Container container) {
    for (java.awt.Component comp : container.getComponents()) {
      if (comp instanceof JTextField) {
        JTextField textField = (JTextField) comp;
        // Enable text selection
        textField.setSelectionStart(0);
        textField.setSelectionEnd(textField.getText().length());
        // Make sure it's editable and focusable
        textField.setEditable(true);
        textField.setFocusable(true);
        // Enable copy/paste
        textField.putClientProperty("JTextField.variant", "search");
      } else if (comp instanceof java.awt.Container) {
        findAndEnableTextFields((java.awt.Container) comp);
      }
    }
  }

  public static void main(String[] args) {
    // Enable platform-specific dark mode
    String os = System.getProperty("os.name").toLowerCase();

    if (os.contains("mac")) {
      // macOS specific dark mode
      System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
      System.setProperty("apple.laf.useScreenMenuBar", "true");
    } else if (os.contains("windows")) {
      // Windows: try to force dark mode
      System.setProperty("sun.awt.noerasebackground", "true");
    }

    // Create and show the window on the Event Dispatch Thread
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          FretboardViewer fv = new FretboardViewer();
          fv.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}