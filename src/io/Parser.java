/**
 * File: Parser.java
 *
 * Project : Personality_Survey
 * Package : gui
 * 
 * Created : Jan 6, 2015 9:08:32 PM
 * Created by: Jack Li
 */
package io;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;

import common.Survey;
import common.Util;

/**
 * Class for handling file I/O and parsing the survey
 * 
 * @author Jack Li
 *
 */
public class Parser {
	public static final FileFilter fileFilter = new FileFilter() {
		/** 
		 * A file filter for txt files
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith(".txt");
		}
	};
	
	/**
	 * Reads all the contents of a file and returns it in a single string.
	 * @param file the file to be read
	 * @return the contents of a file, in string format
	 * @throws IOException if an I/O Exception occurs
	 */
	public static String slurp(File file) throws IOException {
		char[] buffer = new char[(int)file.length()];
		Reader in = new FileReader(file);
		StringBuilder sb = new StringBuilder((int) file.length());
		while(in.read(buffer)>0)
			sb.append(buffer);
		in.close();
		return sb.toString();
	}
	
	/**
	 * Reads a survey from a file, with the same format as specified in the manual
	 * @param file the file to be read
	 * @return a Survey as represented by the contents of the file
	 * @throws IOException if an I/O Exception occurs
	 */
	public static Survey readSurvey(File file) throws IOException {
		String src = slurp(file);
		Survey s = Survey.parse(src);
		if (s==null)
			Util.showError("Format error for file at " + file.getAbsolutePath() + " !");
		return s;
	}
	
	/**
	 * Reads all surveys in the given directory, and returns them as an array.
	 * @param dir the directory
	 * @return an array containing all surveys in a directory
	 * @throws IOException if an I/O exception occurs
	 * @throws IllegalArgumentException	if a file is given as the input instead of a directory
	 */
	public static Survey[] readAll(File dir) throws IOException, IllegalArgumentException{
		if (dir.isFile())
			throw new IllegalArgumentException("File given as argument instead of directory!");
		
		//get all the .txt files
		File[] files = dir.listFiles(fileFilter);
		if (files == null)
			throw new IOException("Error reading files from " + dir.getAbsolutePath());
		Survey[] surveys = new Survey[files.length];
		for (int i = 0; i < files.length; ++i) {
			System.out.println("Reading " + files[i].getAbsolutePath());
			try {
			surveys[i] = readSurvey(files[i]);
			} catch(Exception e) {
				System.err.println("Error occurred while reading the maze@" + files[i].getAbsolutePath() + ":");
				e.printStackTrace();
			}
		}
		return surveys;
	}
}
