/* Stanford Nifty project Hawaiin Phonetic Generator
*  Shriya Kagolanu
* 11th grade Paley
*  Stanford Nifty project Hawaiin Phonetic Generator
 * http://nifty.stanford.edu/2019/bingham-hawaiian-phonetic-generator/
 * Hawaiian words can be intimidating to attempt to pronounce.
 * Words like humuhumunukunukuapua'a look like someone may have fallen asleep on the keyboard.
 *
 */


import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to the Hawaiian Word Pronunciation.");

        Scanner console = new Scanner(System.in);


        HawaiianWord hawaiianWord = new HawaiianWord();

        String newPhrase = "";


        for (; ; ) {
            System.out.println("");
            System.out.print("Enter a hawaiian word to pronounce ==> ");
            String phrase = console.nextLine();
            if (phrase.length() == 0)
                break;


            String[] words = phrase.split(" ");
            for (String word : words) {

                //System.out.println("Input phrase "+ phrase);

                String changedword = hawaiianWord.getHawaiianWord(word.toLowerCase());

                if (changedword == "Invalid Word") {
                    newPhrase = changedword;
                    break;

                } else {
                    newPhrase = newPhrase + " " + changedword;
                }

            }

            System.out.print(phrase + " is pronounced " + newPhrase);

        }
    }
}
