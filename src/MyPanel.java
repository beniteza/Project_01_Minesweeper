import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyPanel extends JPanel 
{
	private static final long serialVersionUID = 3426940946811133635L;

	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 10;
	private static final int TOTAL_ROWS = 11;   //Last row has only one cell

	//******************ADDED 4 PROJECT***************************
	private static final int NUMBER_OF_MINES = 10;
	public final int NUMBER_SAFE_SQUARES = 71;
	//******************ADDED 4 PROJECT***************************

	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];

	//******************ADDED 4 PROJECT*****************************
	/*
	 * This array holds the 'coordinates' of the cells that have mines
	 */
	private int[][] squaresWithMines = new int[TOTAL_ROWS - 2][TOTAL_COLUMNS - 1]; //Should be 9x9

	/*
	 * This array holds the 'coordinates' of the cells that were uncovered
	 */
	private int[][] uncoveredSquares = new int[TOTAL_ROWS - 2][TOTAL_COLUMNS - 1];

	/*
	 * This method checks if the randomly generated cell already has a mine or not.
	 */
	public boolean hasMine(int iKey, int jKey)
	{
		if(this.squaresWithMines[iKey][jKey] == 1)
		{
			return true;
		}
		return false;
	}

	/*
	 * This method returns if the game was won or not
	 */
	public void gameWon(int safeSuqareCounter)
	{
		if(safeSuqareCounter == NUMBER_SAFE_SQUARES)
		{
			JOptionPane.showMessageDialog(null, "YOU WIN!");
			System.exit(0);
		}
	}

	/*
	 * This method returns if the game was lost
	 */
	public void gameLost()
	{
		//Uncovers every square
		for(int i = 1; i <= 9; i++)
		{
			for(int j = 1; j <= 9; j++)
			{
				Color squareColor = Color.GRAY;
				if(this.hasMine(i-1, j-1))
				{
					squareColor = Color.BLACK;
				}
				this.colorArray[i][j] = squareColor;
				this.repaint();
			}
		}
		
		//Displays lost message and exits program
		JOptionPane.showMessageDialog(null, "YOU LOSE!");
		
		//Stops the program
		System.exit(0);
	}

	/*
	 * This method checks if the cell was already uncovered
	 */
	public boolean isUncovered(int x, int y)
	{

		if(this.uncoveredSquares[x][y] == 1)
		{
			return true;
		}
		return false;
	}

	/*
	 * This method adds cell to uncovered array
	 */
	public void uncover(int x, int y)
	{
		if(!isUncovered(x, y))
		{
			this.uncoveredSquares[x][y] = 1;
		}
	}

	/*
	 * This method returns if there are mines around the clicked cell
	 */
	public boolean hasSurroundingMines(int x, int y)
	{
		/*
		 * Como todavia estoy usando los gray cells, en vez de el limite menor ser 0, es 1
		 * Ver como poder usar 'hasMine' to return true instead of doing that aparte
		 */
		for(int i = x-1; i <= x+1; i++)
		{
			for(int j = y-1; j <= y+1; j++)
			{
				if(i > 0 && i <= 9 && j > 0 && j <= 9)
				{
					if(this.hasMine(i-1, j-1))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	/*
	 * This method uncovers all adjacent squares if none of them have mines
	 */
	public void uncoverAdjancentSquares(int x, int y)
	{
			for(int i = x-1; i <= x+1; i++)
			{
				for(int j = y-1; j <= y+1; j++)
				{
					if(i > 0 && i <= 9 && j > 0 && j <= 9)
					{
						this.colorArray[i][j] = Color.GRAY;
						this.repaint();
					}
				}
			}
	}

	//******************ADDED 4 PROJECT*****************************

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

		//******************ADDED 4 PROJECT*****************************
		/*
		 * This randomly selects which cells are going to have mines.
		 */
		Random randomNumber = new Random();
		for(int i = 0; i < NUMBER_OF_MINES; i++)
		{
			int iKey = randomNumber.nextInt(8);
			int jKey = randomNumber.nextInt(8);
			while(hasMine(iKey, jKey))
			{
				iKey = randomNumber.nextInt(8);
				jKey = randomNumber.nextInt(8);
			}
			System.out.println("X:" + (iKey + 1) + ", " + "Y:" + (jKey + 1));

			squaresWithMines[iKey][jKey] = 1;
		}
		//******************ADDED 4 PROJECT*****************************

	}

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