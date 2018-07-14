package WordBrainSolver;

import java.util.*;

public class ListCellHelp {

    public static String ToWord( ArrayList<Cell> word)
    {
        StringBuilder s = new StringBuilder();
        for( Cell c : word)
        {
            s.append(c.val);
        }
        return s.toString();
    }
	
}
