/* Shriya Kagolanu
 * 11th grade Paley
 *  Stanford Nifty Semantic Similarity between words
 *  http://nifty.stanford.edu/2017/guerzhoy-SAT-synonyms/
 *  using cosine similarity  (u.v)/(|u||v|)
 *  For this assignment, you will build an intelligent system that can learn to answer questions like this one. In
 *  order to do that, the system will approximate the semantic similarity of any pair of words. The semantic
 *  similarity between two words is the measure of the closeness of their meanings. For example, the semantic
 *  similarity between “car” and “vehicle” is high, while that between “car” and “flower” is low.
 */


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import org.apache.commons.io.IOUtils;


public class SATSimilarity {


    private ArrayList<String> sentences;
    private HashMap<String, Integer> wordToSentenceCount;
    private HashMap<String, WordToCount> semantic_descriptors;

    private String filename;

    public  SATSimilarity(String filename)
    {
        this.filename = filename;
        wordToSentenceCount = new HashMap<String, Integer>();
        semantic_descriptors = new HashMap<String, WordToCount>();

    }

    public void processFile(){
        try{
            FileInputStream inputStream = new FileInputStream(filename);
            String strFile = IOUtils.toString(inputStream);
            String[] lines = strFile.split("[\\.?!]");
            for  (int i = 0; i<lines.length; i++)
            {

                String[] words = lines[i].split(" ");
                for  (String word: words) {
                    word = word.toLowerCase();
                    word = word.replaceAll("[,'*:]", "");
                    if (wordToSentenceCount.containsKey(word)){
                        wordToSentenceCount.put(word, (wordToSentenceCount.get(word)+1));
                        WordToCount wordMap = semantic_descriptors.get(word);
                        for  (String word1: words){
                            wordMap.addWordToWordCounts(word1.toLowerCase());
                        }
                    }
                    else {
                        wordToSentenceCount.put(word,1);
                        WordToCount wordCountMap = new WordToCount();
                        semantic_descriptors.put(word.toLowerCase(), wordCountMap);
                        for  (String word1: words){
                            wordCountMap.addWordToWordCounts(word1.toLowerCase());
                        }
                    }
                }
            }
            //System.out.println("man" + " Count" + wordToSentenceCount.get("man"));

           // System.out.println("man" + " desc " + (semantic_descriptors.get("man").getWordToWordCounts().toString()));
            for (HashMap.Entry<String, WordToCount> entry : semantic_descriptors.entrySet())
            {
                entry.getValue().getVectorMagnitude();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void most_similar_word(String word, String[] choices)
    {
        int targetWordCount =  wordToSentenceCount.get(word);
        WordToCount targetWordToCount = semantic_descriptors.get(word);
        HashMap<String, Double> choiceList = new HashMap<String, Double>();
        double prodOfVecMagnitudes = 0;

        for (String choiceWord: choices)
        {
            int choiceWordCount =  wordToSentenceCount.get(choiceWord);

            WordToCount wordchoiceCount = semantic_descriptors.get(choiceWord);

            prodOfVecMagnitudes=wordchoiceCount.getVectMagnitude()*targetWordToCount.getVectMagnitude();

            Double similarity = (targetWordCount*choiceWordCount)/Math.sqrt(prodOfVecMagnitudes);
            choiceList.put(choiceWord, similarity);
            System.out.println(" similarty between "+ word + " " + choiceWord+ " is " + similarity);

        }

        Double maxSimilarity = new Double(0);
        String similarWord = "";

        for (HashMap.Entry<String, Double> entry : choiceList.entrySet())
        {
            if ( maxSimilarity < entry.getValue())
            {
                maxSimilarity = entry.getValue();
                similarWord = entry.getKey();
            }
        }

        System.out.println(word + " is similar to " + similarWord);

    }

}
