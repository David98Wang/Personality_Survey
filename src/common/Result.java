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

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A data structure representing the result of the survey
 * @author Jack Li
 *
 */
public class Result implements Comparable<Result>{
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
	 * 3. a -1 type, returning true if a < b
	 * </pre>
	 */
	public static class Requirement implements Comparable<Requirement>{
		int type;
		double min, max;	//TODO fix name
		int target;
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.type);
			sb.append('\n');	//new line
			if (this.type == 0) {
				sb.append(this.target);
			} else {
				sb.append(this.min + " " + this.max);
			}
			return sb.toString();
		}
		@Override
		public int compareTo(Requirement o) {
			if (this.type != o.type)
				return this.type - o.type;
			else {
				if (this.min != o.min)
					return (int) (this.min - o.min);
				else
					return (int) (this.max - o.max);
			}
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.text);
		sb.append('\n');	//new line
		for (Requirement r : reqs) {
			sb.append(r);
			sb.append('\n');	//new line
		}
		sb.append('\n');	//new line
		return sb.toString();
	}

	/**
	 * Checks it this all the criteria for displaying this result is met
	 * @return true if this result should be displayed, false otherwise
	 */
	public boolean check(ArrayList<Type> scores) {
		System.out.println("Checking " + this.text + ": ");
		for (Requirement r : reqs) {
			double points;
			switch(r.type) {
			case -1:
				//get the points for each type
				double a = scores.get((int)r.min-1).points;
				double b = scores.get((int)r.max-1).points;
				if (a > b) return false;
				System.out.println(String.format("%s:%f < %s:%f",scores.get((int)r.min-1).text,scores.get((int)r.min-1).points,scores.get((int)r.max-1).text,scores.get((int)r.max-1).points));
				break;
			case 0:
				//get the points of the type that should be the maximum
				points = scores.get(r.target-1).points;
				for (Type s : scores) {
					if (s.index != r.target && s.points > points)
						return false;
				}
				System.out.println(String.format("%s:%f is max", scores.get(r.target-1).text,scores.get(r.target-1).points));
				break;
			default:
				points = scores.get(r.type-1).points;
				if (points < r.min || points > r.max)
					return false;
				System.out.println(String.format("%f < %s:%f < %f", r.min, scores.get(r.type-1).text, scores.get(r.type-1).points, r.max));
			}
		}
		return true;
	}
	
	@Override
	public int compareTo(Result o) {
		if (this.reqs.size() != o.reqs.size())
			return this.reqs.size() - o.reqs.size();
		else
			for (int i = 0; i < reqs.size(); ++i) {
				if (this.reqs.get(i).compareTo(o.reqs.get(i))!=0)
					return this.reqs.get(i).compareTo(o.reqs.get(i));
			}
		return 0;
	}

	/**
	 * @return the text of this result
	 */
	public String getText() {
		return this.text;
	}
}
