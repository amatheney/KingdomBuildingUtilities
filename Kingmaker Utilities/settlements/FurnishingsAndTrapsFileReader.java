package settlements;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class FurnishingsAndTrapsFileReader 
{
	/** Constructor. */
	FurnishingsAndTrapsFileReader(String aFileName, String aEncoding)
	{
	    fEncoding = aEncoding;
	    fFileName = aFileName;
	}
	  
	/** Write fixed content to the given file. */
	void write() throws IOException  
	{
		log("Writing to file named " + fFileName + ". Encoding: " + fEncoding);
		Writer out = new OutputStreamWriter(new FileOutputStream(fFileName), fEncoding);
		try 
		{
		  out.write(FIXED_TEXT);
		}
		finally 
		{
		  out.close();
		}
	}
	  
	/** Read the contents of the given file. */
	FurnishingsAndTraps[] read() throws IOException 
	{
		FurnishingsAndTraps[] returnList = new FurnishingsAndTraps[0];
		
		log("Reading from " + fFileName);
	    Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
	    try {
	      while (scanner.hasNextLine())
	      {
	    	String output = scanner.nextLine();
	    	FurnishingsAndTraps tempElement = new FurnishingsAndTraps(output);
	    	returnList = expand(tempElement, returnList);
	    	returnList[returnList.length-1]= tempElement;
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    //log("Text read in: " + text);
	    return returnList;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	private FurnishingsAndTraps[] expand(FurnishingsAndTraps tempElement, FurnishingsAndTraps[] oldArray)
	{
		FurnishingsAndTraps[] newArray = new FurnishingsAndTraps[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	public String[] readTextFile() throws IOException
	{
		String[] returnList = new String[0];
		
		log("Reading from " + fFileName);
	    Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
	    try {
	      while (scanner.hasNextLine())
	      {
	    	String output = scanner.nextLine();
	    	//String tempElement = new String(output, completeRooms);
	    	returnList = expandStringArray(output, returnList);
	    	returnList[returnList.length-1]= output;
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    //log("Text read in: " + text);
	    return returnList;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	private String[] expandStringArray(String tempElement, String[] oldArray)
	{
		String[] newArray = new String[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	  
	// PRIVATE 
	private final String fFileName;
	private final String fEncoding;
	private final String FIXED_TEXT = "But soft! what code in yonder program breaks?";
	  
	private void log(String aMessage)
	{
		System.out.println(aMessage);
	}
}
