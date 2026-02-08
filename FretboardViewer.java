import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FretboardViewer extends JFrame implements ActionListener {

  FretboardPanel fretboardPanel = new FretboardPanel();

  JPanel controlPanel = new JPanel();
  JButton resetButton = new JButton("Reset");
  JButton toggleButton = new JButton("Sharps/Flats");  // New toggle button
  FlowLayout controlPanelFlow = new FlowLayout(FlowLayout.LEFT);

  public FretboardViewer() {
    setTitle("AZ Fretboard");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200, 307);
    setResizable(false);
    setLocationRelativeTo(null);

    resetButton.addActionListener(this);
    toggleButton.addActionListener(this);  // Add listener for toggle button

    controlPanel.setLayout(controlPanelFlow);
    controlPanel.add(resetButton);
    controlPanel.add(toggleButton);  // Add toggle button to control panel

    add(controlPanel, BorderLayout.PAGE_START);
    add(fretboardPanel, BorderLayout.CENTER);
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
    FretboardViewer fv = new FretboardViewer();
    fv.setVisible(true);
  }
}

