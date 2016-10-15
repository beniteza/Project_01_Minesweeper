import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

/**
 * The MyMouseAdapter class executes different methods from the MyPanel class after pressing
 * the squares displayed on the frame.
 * @author Axviel Benitez Dorta & Ariel Silva Troche
 *
 */
public class MyMouseAdapter extends MouseAdapter 
{
	/**
	 * A mouse button was pressed.
	 */
	public void mousePressed(MouseEvent e) 
	{
		Component c;
		JFrame myFrame;
		MyPanel myPanel;
		Insets myInsets;
		int x1;
		int y1;
		int x;
		int y;

		switch (e.getButton()) 
		{
		case 1: //Left mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame)) 
			{
				c = c.getParent();
				if (c == null) 
				{
					return;
				}
			}

			myFrame = (JFrame) c;
			myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			myInsets = myFrame.getInsets();

			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);

			x = e.getX();
			y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y); //Where it clicks
			myPanel.mouseDownGridY = myPanel.getGridY(x, y); //Where it clicks
			myPanel.repaint();
			break;
		case 3: //Right mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame)) 
			{
				c = c.getParent();
				if (c == null) 
				{
					return;
				}
			}

			myFrame = (JFrame) c;
			myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			myInsets = myFrame.getInsets();

			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);

			x = e.getX();
			y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y); //Where it clicks
			myPanel.mouseDownGridY = myPanel.getGridY(x, y); //Where it clicks
			myPanel.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}

	/**
	 * A mouse button was released.
	 */
	public void mouseReleased(MouseEvent e) 
	{
		Component c;
		JFrame myFrame;
		MyPanel myPanel;
		Insets myInsets;
		int x1;
		int y1;
		int x;
		int y;
		int gridX;
		int gridY;

		switch (e.getButton()) 
		{
		case 1: //Left mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame)) 
			{
				c = c.getParent();
				if (c == null) 
				{
					return;
				}
			}

			myFrame = (JFrame)c;
			myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			gridX = myPanel.getGridX(x, y); //Where it was released
			gridY = myPanel.getGridY(x, y); //Where it was released

			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) 
			{
				//Had pressed outside
				//Do nothing
			}
			else 
			{
				if ((gridX == -1) || (gridY == -1)) 
				{
					//Is releasing outside
					//Do nothing
				} 
				else 
				{
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) 
					{
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} 
					else 
					{
						//Released the mouse button on the same cell where it was pressed

						//Pressed a gray square
						if ((myPanel.mouseDownGridX == 0) || (myPanel.mouseDownGridY == 0))
						{
							//Do nothing
						}
						//Pressed a mine
						else if (myPanel.hasMine(myPanel.mouseDownGridX, myPanel.mouseDownGridY))
						{
							//Reveals all squares and mines, displays lost message and ends game
							myPanel.gameLost(); 
						}
						//Square has no mine
						else 
						{
							//uncovers the square
							myPanel.uncover(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
							
							//Uncovers adjacent squares if the contain no mines
							myPanel.uncoverAdjancentSquares(myPanel.mouseDownGridX, myPanel.mouseDownGridY);

							//Check if all safe squares have been uncovered and wins if they are
							myPanel.gameWon(); //*********UNCOMMENT*********
						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3: //Right mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame)) 
			{
				c = c.getParent();
				if (c == null) 
				{
					return;
				}
			}

			myFrame = (JFrame)c;
			myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			gridX = myPanel.getGridX(x, y); //Where it gets released
			gridY = myPanel.getGridY(x, y); //Where it gets released

			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) 
			{
				//Had pressed outside
				//Do nothing
			}
			else 
			{
				if ((gridX == -1) || (gridY == -1)) 
				{
					//Is releasing outside
					//Do nothing
				} 
				else 
				{
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) 
					{
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} 
					else 
					{
						//Released the mouse button on the same cell where it was pressed

						if ((myPanel.mouseDownGridX == 0) || (myPanel.mouseDownGridY == 0))
						{
							//Pressed outside or on the gray cells
							//Do nothing
						}
						//Right click flags possible mines with red color
						else
						{
							myPanel.flag(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
						}
					}
				}
			}
			myPanel.repaint();
			break;

		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}

