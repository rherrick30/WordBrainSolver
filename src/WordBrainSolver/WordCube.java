package WordBrainSolver;
import java.util.*;


public class WordCube {

	public ArrayList<Cell> theBoard;
    public Language Dict;
    
    public WordCube( char[][] board )
    {
        //boardasarray = board;
        this.theBoard = new ArrayList<Cell>();
        for (int y = 0; y < board.length; y++)
            for (int x = 0; x < board[y].length; x++)
            {
                theBoard.add(new Cell( board[y][x], x, y));
            }
    }
    
    public WordCube ( String board) throws Exception
    {
        if (Math.round(Math.sqrt(board.length())) != Math.sqrt(board.length()))
        {
        	throw new Exception(String.format("Board must be square. You have specified %s letters", board.length()));
        }

        int sizer = (int)Math.sqrt(board.length());
        int loc = 0;

        // initialize the array
        char[][] tempboard = new char[sizer][sizer];
        for (int y = 0; y < sizer; y++)
            for (int x = 0; x < sizer; x++)
            {
                tempboard[x][y] = '\0';
            }


        for( int y = 0; y<sizer; y++)
            for( int x = 0; x< sizer; x++)
            {
                if (board.substring(loc, loc + 1)!=" ")
                    tempboard[x][y] =  board.substring(loc, loc + 1).toCharArray()[0];
                loc++;
            }
        
        
        this.theBoard = new ArrayList<Cell>();
        for (int y = 0; y < sizer; y++)
            for (int x = 0; x < sizer; x++)
                theBoard.add(new Cell(tempboard[x][y], x, y ));

    }

    // return all neighbors of a cell
    public ArrayList<Cell> GetNeighbors(ArrayList<Cell> selected)
    {
        int sizer = (int)Math.sqrt(theBoard.size());

        Cell last_cell = selected.get(selected.size() - 1);
        ArrayList<Cell> retval = new ArrayList<Cell>();
        ArrayList<Integer> valid_xvals = new ArrayList<Integer>( Arrays.asList( last_cell.xcord ));
        ArrayList<Integer> valid_yvals = new ArrayList<Integer>( Arrays.asList( last_cell.ycord ));

        if (last_cell.xcord > 0)
            valid_xvals.add(last_cell.xcord-1);
        if (last_cell.xcord < (sizer - 1))
            valid_xvals.add(last_cell.xcord + 1);

        if (last_cell.ycord > 0)
            valid_yvals.add(last_cell.ycord - 1);
        if (last_cell.ycord < (sizer - 1))
            valid_yvals.add(last_cell.ycord + 1);
        
        for (Integer i : valid_xvals)
        	for (Integer j : valid_yvals)
        	{
        		Cell isitthere = getCell(selected, i, j);
                if (isitthere.val == '\0')
                {
                    Cell toAdd = getCell(theBoard,i,j);
                    if( toAdd.val !='\0')
                        retval.add(toAdd);
                }
        	}
        return retval;
    }

    public void allPerms( ArrayList<Cell> word, int depth, Map< Integer, ArrayList<Cell>> theListSoFar)
    {
        String s = ListCellHelp.ToWord(word);
        if (!Dict.SubstringFound(s, depth))
            return;

        if (word.size() == depth)
        {
            int newindex = theListSoFar.size();
            theListSoFar.put(newindex, new ArrayList<Cell>());
            for(Cell c : word)
                theListSoFar.get(newindex).add(c);

            return;
        }

        for( Cell c : GetNeighbors(word))
        {
            word.add(c);
            allPerms(word, depth, theListSoFar);
            word.remove(word.size() - 1);
        }
        
    }

    public String printBoard()
    {
    	StringBuilder s = new StringBuilder();
        int sizer = (int)Math.sqrt(theBoard.size());
        for (int y = 0; y < sizer; y++)
        {
            for (int x = 0; x < sizer; x++)
            {
                Cell c = getCell(theBoard, x, y);
                if (c.val == '\0')
                    s.append(" ");
                else
                	s.append(c.val);
            }
        }
        return s.toString();
    }

    public void SlideEmDown()
    {
        int sizer = (int)Math.sqrt(theBoard.size());
        ArrayList<Cell> newBoard = new ArrayList<Cell>();
        for (int x = 0; x < sizer; x++)
        {
            ArrayList<Cell> column = getCells(theBoard,x);
            int yval = sizer - 1;
            for (Cell c : column)
            {
                newBoard.add(new Cell( c.val,  x, yval ));
                yval--;
            }

            while( yval>=0 )
            {
                newBoard.add(new Cell( '\0', x, yval ));
                yval--;
            }
        }
        theBoard = newBoard;
    }

    public WordCube takeOut( ArrayList<Cell> exclusions)
    {
    	try
    	{
	        WordCube w = new WordCube(printBoard());
	        w.theBoard = new ArrayList<Cell>();
	        w.Dict = this.Dict;
	
	        for(Cell c : this.theBoard )
	        { 
	            Cell toInclude = getCell(exclusions, c.xcord, c.ycord);
	            if (toInclude.val == '\0')
	                w.theBoard.add(c);
	            else
	                w.theBoard.add(new Cell('\0',  c.xcord, c.ycord ));
	        }
	        w.SlideEmDown();
	        return w;
    	}
    	catch(Exception ex)
    	{
    		System.out.println("Could not create WordCube in takeOut");
    		return null;
    	}
    }
    
    private ArrayList<Cell> getCells(ArrayList<Cell> board, int xcord)
    {
    	ArrayList<Cell> returnValue = new ArrayList<Cell>();
    	for( Cell c : board)
    	{
    		if(c.xcord == xcord && c.val!='\0')
    			returnValue.add(c);
    	}
    	
    	Collections.sort(returnValue, new Comparator<Cell>() {
            @Override
            public int compare(Cell cell2, Cell cell1)
            {
                return  cell1.ycord - cell2.ycord;
            }
        });
    	
    	return returnValue; 
    }
    
    private Cell getCell(ArrayList<Cell> cellList, int x, int y)
    {
    	for(Cell c : cellList)
    	{
    		if(c.xcord==x && c.ycord==y)
    			return c;
    	}
    	return new Cell( '\0', -1, -1 );
    }
    
}
