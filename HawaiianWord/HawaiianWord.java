/* /* Shriya Kagolanu
 * 11th grade Paley
*  Stanford Nifty project Hawaiin Phonetic Generator
 * http://nifty.stanford.edu/2019/bingham-hawaiian-phonetic-generator/
 * Hawaiian words can be intimidating to attempt to pronounce.
 * Words like humuhumunukunukuapua'a look like someone may have fallen asleep on the keyboard.
 *
 */


import java.util.*;


public class HawaiianWord {

    private String inputWord;

    private HashSet<String> validChars;

    private HashMap<String, String> validConsonents;
    private HashMap<String, String> vowelGroups;
    private HashMap<String, String> validVowels;
    private HashMap<String, String> checkWs;


    public HawaiianWord() {
        validChars = new HashSet<String>(Arrays.asList("a", "e", "i", "o", "u", "p", "k", "h", "l", "m", "n", "w"));

        validConsonents = new HashMap<String, String>() {{
            put("p", "p");
            put("k", "k");
            put("l", "l");
            put("m", "m");
            put("n", "n");
            put("h", "h");
        }};

        checkWs = new HashMap<String, String>() {{
            put("w", "w");
            put("aw", "w");
            put("iw", "v");
            put("ew", "v");
            put("uw", "w");
            put("ow", "w");
        }};


        validVowels = new HashMap<String, String>() {{
            put("a", "ah");
            put("e", "eh");
            put("i", "ee");
            put("o", "oh");
            put("u", "oo");
        }};

        vowelGroups = new HashMap<String, String>() {{

            put("ai", "eye");
            put("ae", "eye");
            put("ao", "ow");
            put("au", "ow");
            put("ei", "ay");
            put("eu", "eh-oo");
            put("iu", "ew");
            put("oi", "oy");
            put("ou", "ow");
            put("ui", "ooey");
        }};


    }


    public String getHawaiianWord(String inputWord) {

        String outputWord = "";
        for (int i = 0; i < inputWord.length(); i++) {
            //System.out.println(" letter is " + inputWord.charAt(i) + "  i is " + i + " word length is " + inputWord.length() + "output word is " + outputWord);

            StringBuilder comWord1 = new StringBuilder().append(inputWord.charAt(i));

            if (Character.isLetter(inputWord.charAt(i))) {

                if (!validChars.contains(comWord1.toString())) {
                    return "Invalid Word";
                }
                //check for consonents
                else if (validConsonents.containsKey(comWord1.toString())) {
                    outputWord = outputWord + validConsonents.get(comWord1.toString());
                }
                //check for w
                else if (inputWord.charAt(i) == 'w') {
                    if (i >= 1) {
                        StringBuilder comWord = new StringBuilder().append(inputWord.charAt(i - 1));

                        comWord.append(inputWord.charAt(i));
                        if (checkWs.containsKey(comWord.toString())) {
                            outputWord = outputWord + checkWs.get(comWord.toString());
                        }

                    } else {
                        outputWord = outputWord + "w";
                    }

                }
                //check for vowel
                else if (validVowels.containsKey(comWord1.toString())) {
                    //check if is part of group
                    if (i + 1 <= (inputWord.length() - 1)) {

                        // System.out.println(" i+1" + (i + 1));

                        StringBuilder comWord = new StringBuilder().append(inputWord.charAt(i));
                        comWord.append(inputWord.charAt(i + 1));
                        if (vowelGroups.containsKey(comWord.toString())) {

                            outputWord = outputWord + vowelGroups.get(comWord.toString());
                            i++;
                        } else
                            outputWord = outputWord + validVowels.get(comWord1.toString());
                    } else
                        outputWord = outputWord + validVowels.get(comWord1.toString());

                    //all vowels except for last one end with "-"
                    if (i != inputWord.length() - 1) {
                        outputWord = outputWord + "-";
                    }


                }
            } else outputWord = outputWord + comWord1.toString();
        }
        //uppercase first letter
        String finalStr = outputWord.substring(0, 1).toUpperCase() + outputWord.substring(1);
        return finalStr;
    }
}
