package hwEmulators;

import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.text.DefaultCaret;
import java.awt.event.WindowEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//======================================================================
// ATMSSDBugConsole
public class ATMSSDBugConsole {
    private String id;
    private Logger log = null;
    private ATMSS atmss = null;
    private JTextArea textArea = null;
    private MyFrame myFrame = null;


    //------------------------------------------------------------
    // ATMSSDBugConsole
    public ATMSSDBugConsole(String id, ATMSS atmss) {
	// init
	this.id = id;
	this.atmss = atmss;
	log = ATMKickstarter.getLogger();

	// create textArea and frame
	textArea = new JTextArea(21, 50);
	textArea.setEditable(false);
	myFrame = new MyFrame("ATMSS-Console");
    } // ATMSSDBugConsole


    //------------------------------------------------------------
    // print
    public void print(String str) {
	textArea.append(str);
	textArea.setCaretPosition(textArea.getDocument().getLength());
    } // println


    //------------------------------------------------------------
    // println
    public void println(String str) {
	print(str);
	textArea.append("\n");
	textArea.setCaretPosition(textArea.getDocument().getLength());
    } // println


    //------------------------------------------------------------
    // MyFrame
    private class MyFrame extends JFrame {
	//----------------------------------------
	// MyFrame
	public MyFrame(String title) {
	    setTitle(title);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocation(300, 100);
	    MyPanel myPanel = new MyPanel();
	    add(myPanel);
	    pack();
	    setSize(580,385);
	    setResizable(false);
	    setVisible(true);
	} // MyFrame
    } // MyFrame


    //------------------------------------------------------------
    // MyPanel
    private class MyPanel extends JPanel {
	//----------------------------------------
	// MyPanel
	public MyPanel() {
	    // clear button
	    JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    textArea.setText("");
		}
	    });

	    // exit button
	    JButton exitButton = new JButton("Exit");
            exitButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    log.info(id + ": Exit button clicked");
		    myFrame.dispatchEvent(new WindowEvent(myFrame,
			    WindowEvent.WINDOW_CLOSING));
		}
	    });

	    JScrollPane textScrollPane = new JScrollPane(textArea);
	    add(textScrollPane);
	    add(clearButton);
	    add(exitButton);
	} // MyPanel
    } // MyPanel
} // ATMSSDBugConsole