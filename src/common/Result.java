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
	 * A public inner class for representing the requirements for this result to be displayed.
	 * <br>
	 * <pre>
	 * Requirements can 1 of 2 types:
	 * 1. a non-zero type, followed by minimum and maximum requirements
	 * 2. a zero type, and a target, which is only displayed if the target is a maximum
	 */
	public static class Requirement {
		int type;
		double min, max;
		int target;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.text);
		sb.append('\n');	//new line
		for (Requirement r : reqs) {
			sb.append(r.type);
			sb.append('\n');	//new line
			if (r.type == 0) {
				sb.append(r.target);
			} else {
				sb.append(r.min + " " + r.max);
			}
			sb.append('\n');	//new line
		}
		sb.append('\n');	//new line
		return sb.toString();
	}
}
