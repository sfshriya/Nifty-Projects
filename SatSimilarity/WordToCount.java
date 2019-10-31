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


import java.util.HashMap;
import java.lang.Math;

public class WordToCount {
    private HashMap<String, Integer> WordToWordCounts;
    private String word;
    private Double vectMagnitude;


    public double getVectMagnitude()
    {
        return vectMagnitude;
    }

    public WordToCount()
    {
        WordToWordCounts  = new HashMap<String, Integer>();
        vectMagnitude= new Double(0);
    }
    public HashMap<String, Integer> getWordToWordCounts()
    {
        return WordToWordCounts;
    }
    public String getMainWord()
    {
        return word;
    }
    public void setMainWord(String word)
    {
         this.word  = word;
    }
    public void addWordToWordCounts(String word)
    {
        if(word.length()>0) {
            if (WordToWordCounts.containsKey(word)) {

                Integer count = WordToWordCounts.get(word);
                count++;
                WordToWordCounts.put(word, count);

            } else {
                WordToWordCounts.put(word, 1);
            }
        }
    }

    public void getVectorMagnitude()
    {
        int sumOfSquares = 0;
        for (HashMap.Entry<String, Integer> entry : WordToWordCounts.entrySet()) {
            vectMagnitude+= entry.getValue() * entry.getValue();
        }

    }

}
