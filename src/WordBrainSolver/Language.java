package WordBrainSolver;

import java.util.*;
import java.util.List;
import java.io.*;
import java.nio.file.Paths;

public class Language {

	public static String WordListLocation =  "/users/rob_herrick/development/wordBrainSolver/wordlists/";
	
    private List<String> presolved_words;
    private Map<Integer, List<String>> words;

    public static void main(String[] args)
    {
    	Language l = new Language(new int[]{4,5}, "");
    	if( l.SubstringFound("aaa",4))
    	{
    		System.out.println("found");
    	}
    	else{
    		System.out.println("not found");
   	
    	}
    	
    }
    
    public Language( int[] validLengths, String PresolvedWords)
     {
        this.presolved_words = new ArrayList<String>();
        //this.words = new Map<Integer, List<String>>();
        this.words = new HashMap<Integer, List<String>>();
        basecase(validLengths);

        // presolved words come in as csv
        if (PresolvedWords.length() > 0)
        {
            String[] prewords = PresolvedWords.split(",");
            for (int i = 0; i < prewords.length; i++)
            {
                this.presolved_words.add(prewords[i]);
                if (this.words.containsKey(prewords[i].length()))
                {
                    if (!this.words.get(prewords[i].length()).contains(prewords[i]))
                    {
                        System.out.println(String.format("WARNING: %s not found in dictionary", prewords[i]));
                        this.words.get(prewords[i].length()).add(prewords[i]);
                    }
                }
                else
                {
                    System.out.println(String.format("WARNING: '%s' has an invalid length", prewords[i]));
                }
            }
        }
    }
    
    private void basecase(int[] validLengths)
    {
        boolean givereport = false;

        for (int i = 0; i < validLengths.length; i++)
        {
            if (!words.containsKey(validLengths[i]))
            {
                words.put(validLengths[i], new ArrayList<String>());
                givereport = true;
                LoadAWordSize(validLengths[i]);
            }
        }

        if (givereport)
        {
            for (Map.Entry<Integer, List<String>> kv : words.entrySet())
            {
            	System.out.println(String.format("There are %s words in the English language with length=%s", kv.getValue().size(), kv.getKey()));
            }
            
        }

    }
    
    private void LoadAWordSize( int i)
    {
        String fileName = String.format(Language.WordListLocation + "%sletterwords.txt", i);

        File f = new File(fileName);
        if(!f.exists() || f.isDirectory()) { 
        	System.out.println(String.format("File %s does not exist!", fileName));
        	return;
        }
        
        try{
	        Scanner in = new Scanner(Paths.get(fileName));
			while( in.hasNextLine()){
				String s = in.nextLine().toLowerCase();
	            String[] elv = s.split(" ");
	            for (int x = 0; x < elv.length; x++)
	            {
	            	if( elv[x].length() != i)
	            	{
	            		System.out.println(String.format("String %s has an inconsistent length of %s where its supposed to be %s",elv[x],elv[x].length(),i ));
	            	}
	            	else
	            	{
	            		words.get(i).add(elv[x].trim());
	            	}
	            }
			}
			in.close();
        }
		catch( Exception e)
		{
			System.out.println(e);
		}
    }
    
    public boolean Contains(String candidate, int dimptr)
    {
        if (presolved_words.size() > 0 && dimptr < presolved_words.size())
        {
            if (presolved_words.get(dimptr).toString().equals(candidate))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        return words.get(candidate.length()).contains(candidate);
    }
   
    public boolean SubstringFound(String fragment, int targetlen)
    {
        /*
        if (presolved_words.Count > 0)
        {
            if (presolved_words.ElementAt(0).ToString().Substring(0, fragment.Length) == fragment)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        */

        //String substringfound = (from frag in words[targetlen] where frag.Substring(0, fragment.Length) == fragment select frag).FirstOrDefault();
    	
    	for( String frag : words.get(targetlen))
    	{
    		if (frag.substring(0, fragment.length()).equals(fragment))
    		{
    			return true;
    		}
    	}
        return false;
    }
    
    public boolean hasWords()
    {
        return (this.words.size() > 0);
    }
}
