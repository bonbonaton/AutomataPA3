
/**
 * 
 * Name: Bonnie Cobarruvias & Anthony Nguyen
 * Date: 22 May 2014
 * File: RegExp.java
 *
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


public class RegExp {

	static int n; // used for keeping count numerous times throughout code
	static File reFile; // file that is to be read in from
	static File writeToMeFile; // File we are writing to
	static FileWriter fw; // file writer for file
	static BufferedWriter bw; // buffered writer for file

	static String[] acceptarr; // the array of accept states
	private static ArrayList<State> states; // array list that holds all of the states

	// main method
	public static void main(String[] args) throws FileNotFoundException {
		Scanner input; // scans reFile file
		input = new Scanner(System.in);

		// says hello to user and gets their input
		System.out.println("Welcome to Bonnie and Anthony's Regular Expression converter!");

		if (args.length < 2) { // user didn't specify files
			System.out
					.println("Oops! A file wasn't initially specified for either reading or writing." +
							" Please enter the file name you'd like to read through the program: ");
			reFile = new File(input.next());
			System.out.println("Please enter the filename you want to write to:");
			writeToMeFile = new File(input.next());
		}

		else if (args.length == 2) { // user did specify files; need to make
										// sure it exists
			reFile = new File(args[0]);
			writeToMeFile = new File(args[1]);

			if (!reFile.exists()) {
				System.out
						.println("There was an error opening this file. Please"
								+ " specify file name again: ");
				reFile = new File(input.next());
			}

		}
		System.out.println("Now working... ");

		@SuppressWarnings("unused")
		RegExp map = new RegExp(reFile); // call NFA method on file
		input.close();
	}
	
	public RegExp(File file) throws FileNotFoundException{
		Scanner regExpInput = new Scanner(file);
		NFADescription newNFA = new NFADescription();
		states = new ArrayList<State>(); // creates states list
		
		String chars = regExpInput.next(); // reads in the alphabet
		char[] alphabet = chars.toCharArray(); // puts alphabet into array
		newNFA.setAlphabet(alphabet);
		
		/** this print line makes sure the alphabet is read in correctly. **/
		/*for(int i = 0; i < alphabet.length; i++){
			System.out.println(alphabet[i]);
		}*/
		String regex = regExpInput.next();
		// System.out.println(regex); // checks that the reg exp string is read in correctly
		RegExpToNFA(regex); // This method sendings the string of the Reg Exp to be converted into an NFA
		
	}
	
	/**
	 * This is the method that is going to do the actual conversion
	 * of a regular expression to an NFA. It takes in a string version of
	 * the regular expression, does work to it, then produces an NFA.
	 */
	public void RegExpToNFA(String regexp){
		System.out.println("Inside RegExpToNFA method.");
		//General procedure: Split RegExp to multiple NFAs and then start combining them
		//Cases: Single letter, concatenation, 
		
	}
	
	/**
	 * This is the method that is going to convert the NFA
	 * into a DFA. Instead of converting it inside of one giant method like in NFA.java, I think
	 * it should be it's own method this time. What do you think? It'll take in the NFA created by
	 * the above method. Does that sound okay?
	 */
	public void NFAToDFA(NFADescription nfa){
		
	}
	
	/*************************************** Old Stuff from PA 1 and 2 Below ******************************************************/
	
	/**
	 * Class that describes the NFA.
	 *
	 */
	public class NFADescription {
		private LinkedList<State> states;
		private String start;
		private State startState;
		private char[] alphab;
		private String[] accepts;

		public NFADescription() {
			states = new LinkedList<State>();
			start = "";
		}

		/**
		 * Sets accepts
		 * @param n Accept array
		 */
		public void setAccepts(String[] n) {
			accepts = n;
		}

		/**
		 * Sets start state name.
		 * @param n Name of the start state.
		 */
		public void setStart(String n) {
			startState = findState(this, n);
			start = n;
		}

		/**
		 * Sets the alphabet
		 * @param alphaIn Alphabet to be set to
		 */
		public void setAlphabet(char[] alphaIn) {
			alphab = alphaIn;
		}

		/**
		 * addState method.
		 * @param state
		 */
		public void addState(State state) {
			states.offer(state);
		}

		/**
		 * Debugging method that returns a string with the NFA specifics.
		 * @return NFA specifics in string form.
		 */
		public String print() {
			String result;
			String eol = System.getProperty("line.separator");
			result = "Start: " + start + "\n";
			String stringAlphabet = new String(alphab);
			result = result + "alphabet: " + stringAlphabet + eol;
			Iterator<State> listIterator = states.listIterator();
			while (listIterator.hasNext()) {
				Iterator<Path> pathIterator = listIterator.next().pathList
						.listIterator();
				while (pathIterator.hasNext()) {

					result = result + pathIterator.next().pathDescription()
							+ eol;
			}
			}
			result = result + "Accept states: ";
			for (int i = 0; i < accepts.length; i++) {
				result = result + accepts[i] + " ";
			}
			return result;
		}
	} // end of NFADescription Class
	
	/* State class; also creates a state */
	public class State {
		String name;
		LinkedList<Path> pathList;
		LinkedList<State> stateList;
		boolean isAcceptState;
		boolean isVisited;

		public State(int n) {
			name = "" + n;
			pathList = new LinkedList<Path>();
			stateList = new LinkedList<State>();
			isAcceptState = false;
			isVisited = false;

		}

		public State(String n) { // states have the following fields
			name = n;
			pathList = new LinkedList<Path>();
			stateList = new LinkedList<State>();
			isAcceptState = false;
			isVisited = false;
		}

		public void visit() {
			isVisited = true;
		}

		public void setAsAccept() {
			isAcceptState = true;
		}

		public String getName() {
			return name; // return name in this node
		}

		// These functions are mostly for DFAs

		// Add a state to the state list
		public void addState(State state) {
			stateList.offer(state);
		}

		// Returns the composite states of this state
		public LinkedList<State> getStates() {
			return stateList;
		}

		public boolean equals(State state) {
			if (this.name.compareTo(state.name) == 0)
				return true;

			else
				return false;
		}
	} // end of class State

	/* Path class; also creates a path between two states */
	public class Path {
		private State source;
		private State dest;
		private char transition;

		public Path(State s, State d, char c) { // creates an individual path
												// with the following fields
			source = s;
			dest = d;
			transition = c;
		}

		public String pathDescription() {
			String result;
			result = source.getName() + " " + "'" + transition + "'" + " "
					+ dest.getName();
			return result;
		}
	} // end of class Path
	
	// turns a string into a state
		public static State findState(NFADescription NFA, String sourceName) {

			State state = null;
			for (int i = 0; i < NFA.states.size(); i++) {
				if (NFA.states.get(i).getName().compareTo(sourceName) == 0) {
					state = NFA.states.get(i); // found the state!
					break;
				}
			}

			return state;
		}
}
