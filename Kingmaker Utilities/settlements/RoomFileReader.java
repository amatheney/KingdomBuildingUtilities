package settlements;

import java.io.*;
import java.util.Scanner;


public class RoomFileReader 
{
	 /** Constructor. */
	RoomFileReader(String aFileName, string  aEncoding)
	{
	    fEncoding = aEncoding;
	    fFileName = aFileName;
	}
	  
	/** Write fixed content to the given file. */
	void write()  
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
	Room[] read() 
	{
		Room[] returnList = new Room[0];
		
		log("Reading from " + fFileName);
	    //StringBuilder text = new string Builder();
	    //String NL = System.getProperty("line.separator");
	    Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
	    try {
	      while (scanner.hasNextLine())
	      {
	    	String output = scanner.nextLine();
	        //text.append(scanner.nextLine() + NL);
	    	//log(output);
	    	Room tempElement = new Room(output);
	    	returnList = RoomUtilities.expand(tempElement, returnList);
	    	returnList[returnList.Length-1]= tempElement;
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    //log("Text read in: " + text);
	    return returnList;
	}
	  
	// PRIVATE 
	privateString fFileName;
	privateString fEncoding;
	privateString FIXED_TEXT = "But soft! what code in yonder program breaks?";
	  
	private void log(String aMessage)
	{
		Console.Out.WriteLine(aMessage);
	}
}
