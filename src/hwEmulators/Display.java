package hwEmulators;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Display extends Thread {
	private String id;
	private Logger log = null;
	private ATMSS atmss = null;
	private MBox atmssMBox = null;
	private JTextArea upperArea = null;
	private JTextArea lowerArea = null;
	private MyFrame myFrame = null;
	public final static int type = 5;
	private int status = 500;

	public Display(String id) {
		this.id = id;
		log = ATMKickstarter.getLogger();

		// create frame
		upperArea = new JTextArea(19, 48);
		upperArea.setEditable(false);
		lowerArea = new JTextArea(2, 48);
		lowerArea.setEditable(false);
		MyFrame myFrame = new MyFrame("Display");
	} // Display

	public int getStatus() {
		return status;
	}

	protected void setDisStatus(int Status) {
		this.status = Status;
	}

	// ------------------------------------------------------------
	// setATMSS
	public void setATMSS(ATMSS newAtmss) {
		atmss = newAtmss;
		atmssMBox = atmss.getMBox();
	} // setATMSS

	// upper --------------------------------------------------
	public void displayUpper(String[] lines) {
		upperArea.setText("");
		for (int i = 0; i < lines.length; i++) {
			upperArea.append(lines[i]);
			upperArea.append("\n");
			upperArea.setCaretPosition(upperArea.getDocument().getLength());
		}
	}

	public List<String> getUpperContentList() { // get current displayed text
		return Arrays.asList(upperArea.getText().split("\n"));
	}

	public String getUpperContent() {
		return upperArea.getText();
	}
	// --------------------------------------------------------


	// lower --------------------------------------------------
	public void displayLower(String line) { lowerArea.setText(line); }

	public String getLowerContent() {
		return lowerArea.getText();
	}
	// --------------------------------------------------------


	private class MyFrame extends JFrame {
		// ----------------------------------------
		// MyFrame
		public MyFrame(String title) {
			setTitle(title);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocation(UIManager.x + 350, UIManager.y);
			MyPanel myPanel = new MyPanel();
			add(myPanel);
			pack();
			setSize(550, 400);
			setResizable(false);
			setVisible(true);
		} // MyFrame
	} // MyFrame

	private class MyPanel extends JPanel {
		// ----------------------------------------
		// MyPanel
		public MyPanel() {
			//JScrollPane upperPane = new JScrollPane(upperArea);
			add(upperArea);
			//JScrollPane lowerPane = new JScrollPane(upperArea);
			add(lowerArea);
		} // MyPanel
	} // MyPanel
} // Display
