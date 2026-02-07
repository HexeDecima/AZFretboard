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
  FlowLayout controlPanelFlow = new FlowLayout(FlowLayout.LEFT);

  public FretboardViewer() {
    setTitle("Fretboard Viewer");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200, 307);
    setResizable(false);
    setLocationRelativeTo(null);

    resetButton.addActionListener(this);

    controlPanel.setLayout(controlPanelFlow);
    controlPanel.add(resetButton);

    add(controlPanel, BorderLayout.PAGE_START);
    add(fretboardPanel, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent e) {
    fretboardPanel.repaint();
  }

  public static void main(String[] args) {
    FretboardViewer fv = new FretboardViewer();
    fv.setVisible(true);
  }
}

