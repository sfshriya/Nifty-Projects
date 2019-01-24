//Shriya Kagolanu 
//Paley A Period
//Anagram Solver - find all anagram word combinations under max words limit for a given phrase 
// max words 3 limitation has to return combinations of 3 or 2 or 1
//Algorithm: Mainly use recursion 
//Step 1: Find all the combinations of word lengths for a given length of the phrase
//and given max words restriction
//Step 2: Recursively find combinations of the words that have letters in the given phrase
//When you add all the words in the combo, the sorted combo should match the sorted phrase to match
//Do letter by letter comparision for each word in the recursion loop and terminate early to improve efficiency
//Main contains a main program to give dictionary file and phrase and max words
//AnagramSolver loads the dictionary and runs the steps in the algorithm

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Welcome to the anagram solver.");
		System.out.println();
		// ask for the dictionary file
		Scanner console = new Scanner(System.in);
		System.out.print("What is the name of the dicitonary file (return to default) ?");
		String dictionaryFile = console.nextLine();
		
		//load dictionary
		AnagramSolver anagramSolver= new AnagramSolver (dictionaryFile);
		anagramSolver.anagramStart();
		
		for (;;) {
			System.out.println();
			System.out.print("phrase to scramble (return to quit)? ");
			String phrase = console.nextLine();
			if (phrase.length() == 0)
				break;
			System.out.println("Max words to include (0 for no max)? ");
			Integer maxWords = console.nextInt();
			console.nextLine();
			anagramSolver.findAnagrams(phrase, maxWords);
		}
		

	}

}
