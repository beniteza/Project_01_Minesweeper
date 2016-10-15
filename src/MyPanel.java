import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The MyPanel class creates the grid, squares, and holds the methods used for the Minesweeper game.
 * @author Axviel Benitez Dorta & Ariel Silva Troche
 *
 */
public class MyPanel extends JPanel 
{
	private static final long serialVersionUID = 3426940946811133635L;

	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 10;
	private static final int TOTAL_ROWS = 11;   //Last row has only one cell
	private static final int NUMBER_OF_MINES = 10; //Total mines
	private static final int NUMBER_SAFE_SQUARES = 71; //Total safe squares
	//This array holds the 'coordinates' of the cells that have mines
	private int[][] squaresWithMines = new int[TOTAL_ROWS - 2][TOTAL_COLUMNS - 1]; //Should be 9x9
	//This array holds the 'coordinates' of the cells that were uncovered
	private int[][] uncoveredSquares = new int[TOTAL_ROWS - 2][TOTAL_COLUMNS - 1];
	//This array holds the number of mines surrounding an uncovered square
	private int[][] surroundingMines = new int[10][10]; //10x10 to avoid errors while storing and drawing

	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];

	/**
	 * Sets the initial color for the squares and randomly selects which ones will contain mines.
	 */
	public MyPanel() 
	{   
		//This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) 
		{	
			//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}

		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) 
		{	
			//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}

		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) 
		{	
			//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}

		for (int x = 0; x < TOTAL_COLUMNS; x++) 
		{   
			//Top row
			colorArray[x][0] = Color.LIGHT_GRAY;
		}

		for (int y = 0; y < TOTAL_ROWS; y++) 
		{   
			//Left column
			colorArray[0][y] = Color.LIGHT_GRAY;
		}

		for (int x = 1; x < TOTAL_COLUMNS; x++) 
		{   
			//The rest of the grid
			for (int y = 1; y < TOTAL_ROWS; y++) 
			{
				colorArray[x][y] = Color.WHITE;
			}
		}

		//This randomly selects which cells are going to have mines.
		Random randomNumber = new Random();
		for(int i = 0; i < NUMBER_OF_MINES; i++)
		{
			int rowValue = randomNumber.nextInt(8);
			int columnValue = randomNumber.nextInt(8);
			while(hasMine(rowValue, columnValue))
			{
				rowValue = randomNumber.nextInt(8);
				columnValue = randomNumber.nextInt(8);
			}
			System.out.println("X:" + (rowValue + 1) + ", " + "Y:" + (columnValue + 1));

			squaresWithMines[rowValue][columnValue] = 1;
		}
	}

	/**
	 * Draws the grid and squares.
	 */
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS - 1; y++) 
		{
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}

		for (int x = 0; x <= TOTAL_COLUMNS; x++) 
		{
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)));
		}

		//Draw an additional cell at the bottom left
		g.drawRect(x1 + GRID_X, y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)), INNER_CELL_SIZE + 1, INNER_CELL_SIZE + 1);

		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) 
		{
			for (int y = 0; y < TOTAL_ROWS; y++) 
			{
				if ((x == 0) || (y != TOTAL_ROWS - 1)) 
				{
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
				}
			}
		}
	
		//***********************REMOVE SOON***********************************
		//Stores the number of surrounding mines in its array
		//MAYBE THIS COULD BE A METHOD OR PART OF hasSurroundingMines********
//		int numberOfMines = this.hasSurroundingMines(this.mouseDownGridX, this.mouseDownGridY);
//		if(numberOfMines != 0 && !isUncovered(this.mouseDownGridX-1, this.mouseDownGridY-1) 
//									&& !this.hasMine(this.mouseDownGridX-1, this.mouseDownGridY-1))
//		{
//			surroundingMines[this.mouseDownGridX][this.mouseDownGridY] = numberOfMines;
//		}
		//***********************REMOVE SOON***********************************
		
		//Loop draws numbers on squares
		for(int x = 0; x <= 9; x++)
		{
			for(int y = 0; y <= 9; y++)
			{
				if(surroundingMines[x][y] != 0)
				{
					//(29,30) coordinates for the inside of the top left white square
					//+45 to skip the gray squares
					g.setColor(Color.GREEN);
					g.drawString("" + surroundingMines[x][y], (x*29)+45, (y*30)+45);
				}
			}
		}
		

	}
	
	/**
	 * Checks if the square contains a mine.
	 * @param x Row value
	 * @param y Column value
	 * @return Returns true if the square contains a mine and false if it doesn't
	 */
	public boolean hasMine(int x, int y)
	{
		if(this.squaresWithMines[x][y] == 1)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the square has already been uncovered.
	 * @param x Row value
	 * @param y Column value
	 * @return Returns true if the square was previously uncovered and false if it wasn't
	 */
	public boolean isUncovered(int x, int y)
	{
		if(this.uncoveredSquares[x][y] == 1)
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks if the are any mines surrounding the selected square.
	 * @param x Row value
	 * @param y Column value
	 * @return Returns true if there are adjacent mines and false if not
	 */
	public int hasSurroundingMines(int x, int y)
	{
		/*
		 * Como todavia estoy usando los gray cells, en vez de el limite menor ser 0, es 1
		 * Ver como poder usar 'hasMine' to return true instead of doing that aparte
		 */
		
		int mines = 0;
		//Loops checks if there are mines in any the of adjacent squares
		for(int i = x-1; i <= x+1; i++)
		{
			for(int j = y-1; j <= y+1; j++)
			{
				if(i > 0 && i <= 9 && j > 0 && j <= 9)
				{
					if(this.hasMine(i-1, j-1))
					{
						mines++;
					}
				}
			}
		}

		if(mines != 0 && !this.hasMine(x-1, y-1))
		{
			surroundingMines[x][y] = mines;
		}
		
		return mines;
	}
	
	/**
	 * Uncovers the selected square.
	 * @param x Row value
	 * @param y Column value
	 */
	public void uncover(int x, int y)
	{
		if(!isUncovered(x, y))
		{
			this.uncoveredSquares[x][y] = 1;
		}
	}

	/**
	 * Uncovers all surrounding squares if none of them have mines.
	 * @param x Row value
	 * @param y Column value
	 */
	public void uncoverAdjancentSquares(int x, int y)
	{
			for(int i = x-1; i <= x+1; i++)
			{
				for(int j = y-1; j <= y+1; j++)
				{
					if(i > 0 && i <= 9 && j > 0 && j <= 9)
					{
						this.colorArray[i][j] = Color.GRAY; //Colors squares gray
						this.uncover(i-1,j-1); //Marks the square as uncovered
						this.repaint();
						
						//Checks and returns how many mines are around the newly uncovered square
						this.hasSurroundingMines(i, j);
					}
				}
			}
	}

	/**
	 * Exits the program if all safe squares were uncovered.
	 * @param safeSuqareCounter The number of uncovered squares
	 */
	public void gameWon(int safeSuqareCounter)
	{
		if(safeSuqareCounter == NUMBER_SAFE_SQUARES)
		{
			JOptionPane.showMessageDialog(null, "YOU WIN!");
			System.exit(0);
		}
	}

	/**
	 * Exits the program if a mine was pressed and uncovers all remaining squares.
	 */
	public void gameLost()
	{
		//Uncovers every square
		for(int x = 1; x <= 9; x++)
		{
			for(int y = 1; y <= 9; y++)
			{
				Color squareColor = Color.GRAY;
				if(this.hasMine(x-1, y-1))
				{
					squareColor = Color.BLACK;
				}
				this.colorArray[x][y] = squareColor;
				this.repaint();
			}
		}
		
		//Displays lost message and exits program
		JOptionPane.showMessageDialog(null, "YOU LOSE!");
		
		//Stops the program
		System.exit(0);
	}

	public int getGridX(int x, int y) 
	{
		Insets myInsets = getInsets();

		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;

		if (x < 0) 
		{   
			//To the left of the grid
			return -1;
		}

		if (y < 0) 
		{   
			//Above the grid
			return -1;
		}

		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) 
		{   
			//Coordinate is at an edge; not inside a cell
			return -1;
		}

		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);

		if (x == 0 && y == TOTAL_ROWS - 1) 
		{    
			//The lower left extra cell
			return x;
		}

		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) 
		{   
			//Outside the rest of the grid
			return -1;
		}
		return x;
	}

	public int getGridY(int x, int y) 
	{
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;

		if (x < 0) 
		{   
			//To the left of the grid
			return -1;
		}

		if (y < 0) 
		{   
			//Above the grid
			return -1;
		}

		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) 
		{   
			//Coordinate is at an edge; not inside a cell
			return -1;
		}

		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);

		if (x == 0 && y == TOTAL_ROWS - 1) 
		{    
			//The lower left extra cell
			return y;
		}

		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) 
		{   
			//Outside the rest of the grid
			return -1;
		}

		return y;
	}
}