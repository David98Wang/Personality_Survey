/**
 * File: Survey.java
 *
 * Project : Personality_Survey
 * Package : common
 * 
 * Created : Jan 6, 2015 6:02:20 PM
 * Created by: Jack Li
 */
package common;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A overall class storing everything needed for 1 survey.
 * @author Jack Li
 *
 */
public class Survey {
	/**
	 * The title of this survey
	 */
	private String title;
	/**
	 * The questions in this survey
	 */
	private LinkedList<Question> questions;
	/**
	 * The results in this survey
	 */
	private LinkedList<Result> results;
	/**
	 * The types of choices that exist
	 */
	private LinkedList<Type> types;
	
	/**
	 * Private empty constructor used for parsing
	 */
	private Survey(){
		questions = new LinkedList<>();
		results = new LinkedList<>();
		types = new LinkedList<>();
	}
	
	/**
	 * Creates an empty survey with the specified titles
	 * @param title
	 */
	public Survey(String title) {
		this.title = title;
		questions = new LinkedList<>();
		results = new LinkedList<>();
		types = new LinkedList<>();
	}
	
	/**
	 * Delimiter used to read one token (a word, number, etc) at a time while skipping comments for the scanner
	 */
	private static final Pattern TOKEN_DELIM = Pattern.compile("(?:(?:\\s+)|(?:#[^\\n]*)){1,}");
	/**
	 * Delimiter used to read one line at a time while skipping comments for the scanner
	 */
	private static final Pattern LINE_DELIM = Pattern.compile("(?:(?:\\s*\\n)|(?:#[^\\n]*\\n)){1,}");
	
	public static Survey parse(String str) {
		Survey res = new Survey();
		Scanner in = new Scanner(str);
		String token;	//the next token in input
		int num;	//temporary variable storing the number of the current item
		
		//read the title
		in.useDelimiter(LINE_DELIM);
		res.title = in.next();
		
		//read number of questions
		in.useDelimiter(TOKEN_DELIM);
		num = in.nextInt();
		
		//read questions
		for (int i = 0; i < num; ++i) {
			Question cur;
			//read question text
			in.useDelimiter(LINE_DELIM);
			token = in.nextLine();
			cur = new Question(token);
			
			//read number of choices
			in.useDelimiter(TOKEN_DELIM);
			int nChoices = in.nextInt();
			
			//read choices
			for (int j = 0; j < nChoices; ++j) {
				in.useDelimiter(TOKEN_DELIM);
				int type = in.nextInt();		//type of question
				double points = in.nextInt();	//number of points awarded to that type
				
				in.useDelimiter(LINE_DELIM);
				token = in.next();				//question text	
				cur.choices.add(new Choice(token,type,points));
			}
			
			
			res.questions.add(cur);
		}
		

		in.close();
		return res;
	}
}
