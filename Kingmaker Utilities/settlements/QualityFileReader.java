package settlements;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;


public class QualityFileReader 
{
	/** Constructor. */
	QualityFileReader(String aFileName, string  aEncoding)
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
	Quality[] read() 
	{
		Quality[] returnList = new Quality[0];
		
		log("Reading from " + fFileName);
	    Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
	    try {
	      while (scanner.hasNextLine())
	      {
	    	String output = scanner.nextLine();
	    	//Console.Out.WriteLine("Now trying to parse the quality: " + output);
	    	Quality tempElement = new Quality(output);
	    	returnList = RoomUtilities.expand(returnList);
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

