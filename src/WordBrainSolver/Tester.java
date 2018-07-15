package WordBrainSolver;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class Tester {
	
	public static void main( String[] arg)
	{
		Logger l = new Logger();
        Date start = new Date();
        DateFormat dtFmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSS");
        
        l.log(String.format("Started at %s", dtFmt.format(start)));
        // kinda long
        Solver slv = new Solver(l, new int[] {  });

        try
        {
	        List<String> potentials = slv.GetPermutations(new int[] {8,4,5,4,6,8,8,6},
	        		new WordCube("ernagsuldadeeadurinchploteitooablsudcpoeespamunpr"),  "", 0,
	        		"suitcase,heel,purse,loop,paddle,cupboard,mountain");
	        l.log("---------------------------------");
	        for( String s : potentials)
	        {
	            l.log(s);
	        }
        }catch(Exception e)
        {
        	l.log("error in analysis " + e.getMessage());
        }

        
        Date ended = new Date();
        long msDiff = ended.getTime() - start.getTime();
        l.log(String.format("Ended at %s after taking %s ms", dtFmt.format(ended), msDiff));
	}
	

}
