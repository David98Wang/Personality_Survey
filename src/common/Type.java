/**
 * File: Type.java
 *
 * Project : Personality_Survey
 * Package : common
 * 
 * Created : Jan 6, 2015 6:01:25 PM
 * Created by: Jack Li
 */
package common;

/**
 * A data structure to store information required for the types of responses
 * @author Jack Li
 *
 */
public class Type {
	/**
	 * Text for this response
	 */
	String text;
	/**
	 * An integer representation for this type
	 */
	int index;
	public Type(String text, int index) {
		this.text = text;
		this.index = index;
	}
	
	public String toString() {
		return this.text;
	}
}
