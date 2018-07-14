package WordBrainSolver;
import java.util.*;
import java.io.*;
import java.text.*;

public class Logger {

	private PrintWriter sw;
	
	
	public static void main( String[] arg)
	{
		Logger l = new Logger();
		l.log("this is a message");
		l.close();
	}
	
    public Logger()
    {
    	try
    	{
    		SimpleDateFormat dt1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
    		sw = new PrintWriter(String.format("SolverLog_%s.log", dt1.format(new Date())));
    	}
    	catch(Exception e)
    	{
    		System.out.println("could not open logfile");
    	}
    	
    }

    public void close()
    {
        sw.close();
    }

    public void log(String msg)
    {
    	System.out.println(msg);
        sw.println(msg);
    }
	
}
