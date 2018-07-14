package WordBrainSolver;

import java.util.*;

public class Solver {

	private Logger l;
    private Language English;
    private ArrayList<String> potentialSolutions;

    public Solver( Logger lgr, int[] validLengths)
    {
        this.l = lgr;
        English = new Language(validLengths,"");
    }

    public Solver(Logger lgr, int[] validLengths, char[][] gameboard)
    {
        this.l = lgr;
    }

    public ArrayList<String> GetPermutations(int[] len, WordCube board, String answersofar, int dimptr, String PresolvedWords )
    {
    	//System.out.println(board.printBoard());
    	
        if (dimptr == 0)
            potentialSolutions = new ArrayList<String>();

        if(!English.hasWords())
            English = new Language(len, PresolvedWords);

        board.Dict = English;

        Map<Integer, ArrayList<Cell>> allCombos = (Map) new HashMap<Integer, ArrayList<Cell>>();
        int thislen = len[dimptr];
        for( Cell c : board.theBoard)
        {
            board.allPerms(new ArrayList<Cell>(Arrays.asList(c)), thislen, allCombos);
        }
        
        for(Map.Entry<Integer, ArrayList<Cell>> lc : allCombos.entrySet())
        {
            String s = ListCellHelp.ToWord(lc.getValue());

            if (board.Dict.Contains(s, dimptr))
            {
                if (dimptr >= len.length- 1)
                {
                    l.log(answersofar + ',' + s);
                    if(! potentialSolutions.contains(answersofar + ',' + s))
                    	potentialSolutions.add(answersofar + ',' + s);
                }
                else
                {
                    WordCube w = board.takeOut(lc.getValue());
                    //System.out.println(w.printBoard());
                    w.Dict = English;
                    GetPermutations(len, w, answersofar + ',' + s, dimptr + 1, "");
                }
            }
        }
        return potentialSolutions;
    }


}
