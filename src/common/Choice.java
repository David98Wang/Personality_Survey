/**
 * File: Choice.java
 *
 * Project : Personality_Survey
 * Package : common
 * 
 * Created : Jan 6, 2015 5:58:38 PM
 * Created by: Jack Li
 */
package common;

import java.util.LinkedList;

/**
 * A data structure used to store a single choice.
 * 
 * @author Jack Li
 * 
 */
public class Choice {
	/**
	 * The text to be displayed for this choice
	 */
	String text;
	/**
	 * The effect of choosing this choice
	 */
	LinkedList<ValType> values;

	/**
	 * Default constructor
	 * 
	 * @param text
	 *            the text of this choice
	 */
	public Choice(String text) {
		this.text = text;
		this.values = new LinkedList<>();
	}

	/**
	 * An inner class for representing the type and value of choices 
	 * @author Jack Li
	 *
	 */
	static class ValType {
		int type;
		double value;

		public ValType(int type, double value) {
			this.type = type;
			this.value = value;
		};

		public ValType() {
			type = 0;
			value = 0;
		};
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.text);
		for (ValType v : values)
			sb.append(v.type + " " + v.value + " ");
		sb.append('\n');	//newline
		return sb.toString();
	}
}
