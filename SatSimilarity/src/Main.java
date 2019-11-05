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


public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to SAT Similarity program.");
        System.out.println("Program command line should have <filename> <testword> <wordchoice1> <wordchoice2> <wordchoice2> <wordchoice3>");

        if (args.length <5)
        {
            System.out.println("Welcome to SAT Similarity program.");
            System.out.println("Program command line should have <filename> <testword> <wordchoice1> <wordchoice2> <wordchoice2> <wordchoice3>");

            return;
        }

        String filename = args[0];
        System.out.println("Processing file " + filename);



        String word = args[1];
        String[] choiceWords = new String[4];
        choiceWords[0] = args[2];
        choiceWords[1] = args[3];
        choiceWords[2] = args[4];
        choiceWords[3] = args[5];

        SATSimilarity satSim = new SATSimilarity(filename);
        satSim.processFile();

        satSim.most_similar_word(word, choiceWords);
    }
}
