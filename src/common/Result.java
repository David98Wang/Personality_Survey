/**
 * File: Result.java
 *
 * Project : Personality_Survey
 * Package : common
 * 
 * Created : Jan 6, 2015 6:00:50 PM
 * Created by: Jack Li
 */
package common;

import java.util.LinkedList;

/**
 * A data structure representing the result of the survey
 * @author Jack Li
 *
 */
public class Result {
	/**
	 * Text to display for this result
	 */
	String text;
	/**
	 * Requirements for this result to be displayed
	 */
	LinkedList<Requirement> reqs;
	
	/**
	 * Default constructor
	 * @param text the text to display for this result
	 */
	public Result(String text) {
		this.text = text;
		reqs = new LinkedList<>();
	}
	
	/**
	 * A public inner class for representing the requirements for this result to be displayed
	 */
	public class Requirement {
		int type;
		double min, max;
	}
}
