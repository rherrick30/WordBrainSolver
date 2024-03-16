package WordBrainSolver;

import java.util.*;
import java.util.stream.Collectors;

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

    private ArrayList<String> _GetPermutationsInternal(int[] len, WordCube board, String answersofar, int dimptr, String PresolvedWords)
    {
    	//System.out.println(board.printBoard());
    	
        if (dimptr == 0)
            potentialSolutions = new ArrayList<String>();

        if(!English.hasWords(len))
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
                    _GetPermutationsInternal(len, w, answersofar + ',' + s, dimptr + 1, "");
                }
            }
        }
        return potentialSolutions;
    }

    public List<String> GetPermutations(int [] lenOfUnfound, String board, String[] answersSoFar, String hint) throws Exception {
        List<Integer> answerLens = Arrays.stream(answersSoFar).sequential().map(String::length).collect(Collectors.toList());
        Arrays.stream(lenOfUnfound).sequential().forEach(a-> answerLens.add(a));
        String answersInCommaString = Arrays.stream(answersSoFar).sequential().collect(Collectors.joining(","));
        ArrayList<String> rawResults = _GetPermutationsInternal(answerLens.stream().mapToInt(Integer::intValue).toArray(), new WordCube(board), "", 0, answersInCommaString);
        if(hint.trim().length()==0){
            return rawResults.stream().map(r-> r.substring(1)).collect(Collectors.toList());
        }
        return rawResults.stream()
                .map(r-> r.substring(1))
                .filter(r->{
                    String[] answers = r.split(",");
                    return answers[answers.length-1].startsWith(hint);
                })
                .collect(Collectors.toList());
    }

}
