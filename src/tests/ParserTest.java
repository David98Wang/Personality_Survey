/**
 * File: ParserTest.java
 *
 * Project : Personality_Survey
 * Package : tests
 * 
 * Created : Jan 6, 2015 9:06:53 PM
 * Created by: Jack Li
 */
package tests;

import io.Parser;

import java.io.File;
import java.io.IOException;

import common.Survey;

/**
 * Reads and prints all text files in the resources folder
 * @author Jack Li
 *
 */
public class ParserTest {

	public static void main(String[] args) throws IllegalArgumentException, IOException {
		Survey[] surveys = Parser.readAll(new File("./assets"));
		for (Survey s : surveys)
			System.out.println(s);
	}

}
