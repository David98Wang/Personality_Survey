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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

import common.Result.Requirement;

/**
 * A overall class storing everything needed for 1 survey.
 * 
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
	 * Url of website for more information for this survey
	 */
	private String website;

	/**
	 * Private empty constructor used for parsing
	 */
	private Survey() {
		questions = new LinkedList<>();
		results = new LinkedList<>();
		types = new LinkedList<>();
	}

	/**
	 * Creates an empty survey with the specified titles
	 * 
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
	private static final Pattern TOKEN_DELIM = Pattern
			.compile("(?:(?:\\s+)|(?:#[^\\n]*)){1,}");
	/**
	 * Delimiter used to read one line at a time while skipping comments for the scanner
	 */
	private static final Pattern LINE_DELIM = Pattern
			.compile("(?:(?:\\s*\\n)|(?:#[^\\n]*\\n)){1,}");

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.title);
		sb.append('\n');	// newline
		sb.append(questions.size());
		sb.append('\n');	// newline
		for (Question q : questions)
			sb.append(q);
		sb.append(results.size());
		sb.append('\n');	// newline
		for (Result r : results)
			sb.append(r);
		sb.append(types.size());
		sb.append('\n');	// newline
		for (Type t : types)
			sb.append(t + "\n");
		sb.append(this.website);
		sb.append('\n');	// newline
		return sb.toString();
	}

	public static Survey parse(String str) {
		// TODO fix this
		Survey res = new Survey();
		Scanner in = new Scanner(str);
		String token = null;	// the next token in input
		int num;	// temporary variable storing the number of the current item

		// read the title
		in.useDelimiter(LINE_DELIM);
		res.title = in.next();

		// read number of questions
		in.useDelimiter(TOKEN_DELIM);
		num = in.nextInt();

		// read questions
		for (int i = 0; i < num; ++i) {
			Question cur;
			// read question text
			in.skip("[^\\n]*\\n");	// skip rest of line
			in.useDelimiter(LINE_DELIM);
			token = in.next();
			cur = new Question(token);

			// read number of choices
			in.useDelimiter(TOKEN_DELIM);
			int nChoices = in.nextInt();

			// read choices
			for (int j = 0; j < nChoices; ++j) {
				Choice curChoice;				// a variable for the current choice
				in.skip("[^\\n]*\\n");	// skip rest of line
				in.useDelimiter(LINE_DELIM);
				token = in.next();				// question text

				curChoice = new Choice(token);

				in.useDelimiter(TOKEN_DELIM);

				int type;						// buffer variable for reading type
				double points;					// number of points awarded to that type
				while (in.hasNextInt()) {
					token = in.next();
					if (!in.hasNextDouble())
						break;
					type = Integer.parseInt(token);
					points = in.nextDouble();
					curChoice.values.add(new Choice.ValType(type, points));
				}

				cur.choices.add(curChoice);
			}
			System.out.println("read\n" + cur);
			// add question to queue
			res.questions.add(cur);
		}

		// read number of results
		in.useDelimiter(TOKEN_DELIM);
		if (token == null)
			token = in.next();
		num = Integer.parseInt(token);
		// read results
		for (int i = 0; i < num; ++i) {
			Result cur;
			// read results text
			in.skip("[^\\n]*\\n");	// skip rest of line
			in.useDelimiter(LINE_DELIM);
			token = in.next();
			cur = new Result(token);

			// use token deliminator to read tokens instead of lines
			in.useDelimiter(TOKEN_DELIM);

			// read results
			// in.hasNext("[\\d]+\\s*\\n(?:[\\d\\.]+|Infinity)\\s+(?:[\\d\\.]+|Infinity)?");
			while (in.hasNextInt()) {
				Requirement r = new Requirement();	// a temp variable to construct requirements
				token = in.next();
				if (!in.hasNextDouble())
					break;
				r.type = Integer.parseInt(token);

				if (r.type != 0) {
					r.min = in.nextDouble();
					r.max = in.nextDouble();
				} else {
					r.target = in.nextInt();
				}
				cur.reqs.add(r);

			}
			// add question to queue
			res.results.add(cur);
		}

		// read types
		if (token == null)
			token = in.next();
		num = Integer.parseInt(token);
		// read types
		for (int i = 1; i <= num; ++i) {
			in.skip("[^\\n]*\\n"); // skip rest of line
			in.useDelimiter(LINE_DELIM);
			token = in.next().trim();
			Type cur = new Type(token, i);
			res.types.add(cur);
		}
		in.skip("\\s*"); // skip any remaining whitespace
		token = in.next();
		res.website = token;
		in.close();
		return res;
	}
}
