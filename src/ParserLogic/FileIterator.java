package ParserLogic;

import java.io.File;
import java.util.ArrayList;

public class FileIterator {
	public ArrayList<String> listFilesForFolder(final File folder) 
	{
	ArrayList<String> listofFiles = new ArrayList<String>();
	
	for (final File fileEntry : folder.listFiles()) 
	{
	        if (fileEntry.isDirectory()) 
	        {
	          System.out.println("Skipping Directories....");
	        } 
	        else
	        {
	           listofFiles.add(fileEntry.getName());
	        }
	    }
	return listofFiles;
	}


}
