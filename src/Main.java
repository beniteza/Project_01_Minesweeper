import javax.swing.JFrame;

/**
 * The Main class creates and displays the frame.
 * @author Axviel Benitez Dorta & Ariel Silva Troche
 *
 */
public class Main 
{
	public static void main(String[] args) 
	{
		JFrame myFrame = new JFrame("CIIC4010 Project 01: Minesweeper");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(400, 400);

		MyPanel myPanel = new MyPanel();
		myFrame.add(myPanel);

		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);

		myFrame.setVisible(true);
	}
}