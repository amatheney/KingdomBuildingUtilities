package settlements;

import java.io.*;
import java.util.Scanner;

public class RoomFileReader 
{
	 /** Constructor. */
	RoomFileReader(String aFileName, String aEncoding)
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
	Room[] read() throws IOException 
	{
		Room[] returnList = new Room[0];
		
		log("Reading from " + fFileName);
	    //StringBuilder text = new StringBuilder();
	    //String NL = System.getProperty("line.separator");
	    Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
	    try {
	      while (scanner.hasNextLine())
	      {
	    	String output = scanner.nextLine();
	        //text.append(scanner.nextLine() + NL);
	    	//log(output);
	    	Room tempElement = new Room(output);
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
	private Room[] expand(Room tempElement, Room[] oldArray)
	{
		Room[] newArray = new Room[oldArray.length+1];
		
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
