import java.awt.Color; 
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter 
{
	Color newColor = null; //Maybe move back down?
	int safeSuqareCounter = 0; //Holds how many safe cells have been uncovered

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

						//Presses on gray cells
						if ((myPanel.mouseDownGridX == 0) || (myPanel.mouseDownGridY == 0))
						{
							//Do nothing
							System.out.println("Left clicked in a gray cell.");
						}
						//Pressed a mine
						else if (myPanel.hasMine(myPanel.mouseDownGridX-1, myPanel.mouseDownGridY-1))
						{
							this.newColor = Color.BLACK;

							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
							myPanel.repaint();

							System.out.println("Square has mine.");

							//Displays lost message and ends game
							myPanel.gameLost();  
						}
						//Square has no mine
						else 
						{
							//On the grid other than on the left column and on the top row:
							//Checks if square was already uncovered
							if(myPanel.isUncovered(myPanel.mouseDownGridX-1, myPanel.mouseDownGridY-1))
							{
								//Do nothing
								System.out.println("Square was already uncovered");
							}
							else
							{
								//Marks square as uncovered
								myPanel.uncover(myPanel.mouseDownGridX-1, myPanel.mouseDownGridY-1);

								this.newColor = Color.GRAY;

								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
								myPanel.repaint();

								System.out.println("Square is safe.");

								//Checks if there are mines around the uncovered square
								if(myPanel.hasSurroundingMines(myPanel.mouseDownGridX, myPanel.mouseDownGridY))
								{
									System.out.println("Has mine(s) around it.");
								}
								else
								{
									System.out.println("No mine(s) around it.");
									myPanel.uncoverAdjancentSquares(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
								}

								safeSuqareCounter++;
								System.out.println(this.safeSuqareCounter); //temp

								//Check if all safe squares have been uncovered and wins if they are
								//myPanel.gameWon(safeSuqareCounter); ********UNCOMMENT
									
							}
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
				System.out.println("Right clicked outside.");
			}
			else 
			{
				if ((gridX == -1) || (gridY == -1)) 
				{
					//Is releasing outside
					//Do nothing
					System.out.println("Released outside.");
				} 
				else 
				{
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) 
					{
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
						System.out.println("Released on a gray cell.");
					} 
					else 
					{
						//Released the mouse button on the same cell where it was pressed

						if ((myPanel.mouseDownGridX == 0) || (myPanel.mouseDownGridY == 0))
						{
							//Pressed outside or on the gray cells
							//Do nothing
							System.out.println("Right clicked in a gray cell.");
						}
						//Right click flags possible mines with red color
						else
						{
							if(myPanel.isUncovered(myPanel.mouseDownGridX-1, myPanel.mouseDownGridY-1))
							{
								//Do nothing
								System.out.println("CANNOT FLAG: Square was already uncovered");
							}
							else
							{
								//On the grid other than on the left column and on the top row:
								this.newColor = Color.RED;

								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
								myPanel.repaint();

								//****************************************************************
								System.out.println("Square flagged.");
								//****************************************************************
							}
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

