/*
 * Created on 14 juin 2004
 *
 */
 
package com.papyrus.tools.importer;

import java.io.*;

import com.papyrus.common.PapyrusException;
import com.papyrus.common.Config;
import com.papyrus.common.PapyrusDatabasePool;
 
/**
 * @author did
 *
 * This class permits to import items from any csv file into
 * the database of the "eGestion Commerciale" system.
 * Param: -DclassName=xxxxx   => name of the Item class to import
 * Param: -DfileName=xxxx.csv => name of the file containing the data
 */
public class ImporterScript {
 
	public static void main(String[] args) {
		/* Get the properties */
    	String className = System.getProperty("className");
    	String fileName = System.getProperty("fileName");
 				
 		System.out.println("class = " + className);
 		System.out.println("file = " + fileName);
 		System.out.println("Performing....");
 				
 		try {		
			String line;
			
			/* init db */
			Config.initConfAndLogs();
			PapyrusDatabasePool.init();
			
			System.out.println("...Initialization OK...");
			
 			Class filterClass = Class.forName(className);
 			Item filter = (Item) filterClass.newInstance();
 			
			/* open a bufferized stream from the fileName */
			BufferedReader bufReader = new BufferedReader(new FileReader(fileName));

			/* parse the file */
			for (line = bufReader.readLine(); line != null; line = bufReader.readLine()) {
				Object obj = filter.parseLine(line);
				System.out.println("obj = " + obj);
			}
			
			/* save to db */
			filter.saveToDB();	
			
			System.out.println("....Done");
 		} catch (ClassNotFoundException e) {
 			System.out.println("ERROR : filter not found");
 			System.exit(-1);
 		} catch (IllegalAccessException e) {
			System.out.println("ERROR : filter not created");
			System.exit(-1); 
		} catch (InstantiationException e) {
			System.out.println("ERROR : filter not created");
			System.exit(-1); 
 		} catch (FileNotFoundException e) {
			System.out.println("ERROR : file not found");
			System.exit(-1); 	
 		} catch (PapyrusException e) {
 			System.out.println("ERROR : filter error (" + e.getMessage() + ")");
 			System.exit(-1);
		} catch (IOException e) {
			System.out.println("ERROR : IO exception (" + e.getMessage() + ")");
			System.exit(-1);
		} finally {
			System.exit(0);
		}
    }
}

