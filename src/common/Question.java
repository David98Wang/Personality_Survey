/**
 * File: Question.java
 *
 * Project : Personality_Survey
 * Package : common
 * 
 * Created : Jan 6, 2015 5:58:02 PM
 * Created by: Jack Li
 */
package common;

import java.util.LinkedList;

/**
 * A data structure used to store a single question.
 * @author Jack Li
 *
 */
public class Question {
	String text;
	LinkedList<Choice> choices;
	
	public Question(String text) {
		this.text = text;
		choices = new LinkedList<>();
	}
	
}