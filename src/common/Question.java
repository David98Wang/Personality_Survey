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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * A data structure used to store a single question.
 * @author Jack Li
 *
 */
public class Question {
	/**
	 * The text to be displayed for this question
	 */
	String text;
	/**
	 * The choices for this question
	 */
	ArrayList<Choice> choices;
	
	public Question(String text) {
		this.text = text;
		choices = new ArrayList<>();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.text);
		sb.append('\n');	//newline
		sb.append(choices.size());
		sb.append('\n');	//newline
		for (Choice c : choices)
			sb.append(c);
		sb.append('\n');	//newline
		return sb.toString();
	}

	/**
	 * Shuffles the choice in the questions
	 */
	public void shuffle() {
		Collections.shuffle(choices);
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the choices
	 */
	public ArrayList<Choice> getChoices() {
		return choices;
	}

	/**
	 * @param choices the choices to set
	 */
	public void setChoices(ArrayList<Choice> choices) {
		this.choices = choices;
	}
	
		
}
