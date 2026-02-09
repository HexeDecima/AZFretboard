import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import javax.swing.plaf.ColorUIResource;

public class FretboardViewer extends JFrame implements ActionListener {

  FretboardPanel fretboardPanel = new FretboardPanel();

  JPanel controlPanel = new JPanel();
  JButton resetButton = new JButton("Reset");
  JButton toggleButton = new JButton("Sharps/Flats");  // New toggle button
  FlowLayout controlPanelFlow = new FlowLayout(FlowLayout.LEFT);

  private void setDarkTheme() {
    try {
      // Try to set system look and feel
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      // Get current look and feel to modify
      UIManager.put("control", new ColorUIResource(30, 30, 35));
      UIManager.put("Panel.background", new ColorUIResource(30, 30, 35));
      UIManager.put("Button.background", new ColorUIResource(60, 60, 70));
      UIManager.put("Button.foreground", Color.WHITE);
      UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 15, 5, 15));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

//  public FretboardViewer() {
//    setTitle("AZ Fretboard");
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    setSize(1200, 307);
//    setResizable(false);
//    setLocationRelativeTo(null);
//
//    resetButton.addActionListener(this);
//    toggleButton.addActionListener(this);  // Add listener for toggle button
//
//    controlPanel.setLayout(controlPanelFlow);
//    controlPanel.add(resetButton);
//    controlPanel.add(toggleButton);  // Add toggle button to control panel
//
//    add(controlPanel, BorderLayout.PAGE_START);
//    add(fretboardPanel, BorderLayout.CENTER);
//  }
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

  // Set dark colors for control panel and buttons
  controlPanel.setBackground(new Color(30, 30, 35));
  resetButton.setBackground(new Color(60, 60, 70));
  resetButton.setForeground(Color.WHITE);
  toggleButton.setBackground(new Color(60, 60, 70));
  toggleButton.setForeground(Color.WHITE);

  // Remove button borders for cleaner look
  resetButton.setBorderPainted(false);
  toggleButton.setBorderPainted(false);

  // Add padding to buttons
  resetButton.setMargin(new Insets(5, 15, 5, 15));
  toggleButton.setMargin(new Insets(5, 15, 5, 15));

  controlPanel.setLayout(controlPanelFlow);
  controlPanel.add(resetButton);
  controlPanel.add(toggleButton);

  add(controlPanel, BorderLayout.PAGE_START);
  add(fretboardPanel, BorderLayout.CENTER);

  // Set window background
  getContentPane().setBackground(new Color(30, 30, 35));
}

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == resetButton) {
      // Clear all active rectangles
      fretboardPanel.resetAllHits();
      fretboardPanel.repaint();
    } else if (e.getSource() == toggleButton) {
      // Toggle the note system
      fretboardPanel.toggleNoteSystem();

      // Update button text based on current state
      if (fretboardPanel.isUsingSharps()) {
        toggleButton.setText("Switch to Flats");
      } else {
        toggleButton.setText("Switch to Sharps");
      }

      // Repaint to update existing red notes with new note names
      fretboardPanel.repaint();
    }
  }

  public static void main(String[] args) {
    // Enable macOS dark mode support
    System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
    System.setProperty("apple.laf.useScreenMenuBar", "true");

    FretboardViewer fv = new FretboardViewer();
    fv.setVisible(true);
  }
}

