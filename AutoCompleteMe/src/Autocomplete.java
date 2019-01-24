//Autocomplete Shriya Kagolanu A period Paley
//Stanford Nifty Project
import java.io.*;
import java.util.*;

import org.apache.commons.io.IOUtils;

public class Autocomplete {

    private List<Term> termList;
    private Term[] termArray;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(List<Term> terms) {
        if (terms.isEmpty()) {
            throw new java.lang.NullPointerException();
        }
        Collections.sort(terms);
        termArray = new Term[terms.size()];
        terms.toArray(termArray);
      /*  for (int i=0; i<termArray.length; i++)
        {
            System.out.println(termArray[i].getQuery());
        }*/
        termList = terms;

    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {

        Integer firstIndex = 0;
        Integer lastIndex = 0;

        if (prefix == null) {
            throw new java.lang.NullPointerException();
        }
        Term comp = new Term(prefix, new Long(0));

        firstIndex = BinarySearchDeluxe.firstIndexOf(termArray, comp, Term.byPrefixOrder(prefix.length()));
        lastIndex = BinarySearchDeluxe.lastIndexOf(termArray, comp, Term.byPrefixOrder(prefix.length()));

        //System.out.println(" First Index = " + firstIndex + "Second Index = "+ lastIndex);

        if (firstIndex <0 || lastIndex<0) {
            System.out.println(" Did not find the query. ");
            return null;

        }

        Term[] matchQueries = new Term[lastIndex - firstIndex + 1];
        //from - the initial index of the range to be copied, inclusive
        //to - the final index of the range to be copied, exclusive. (This index may lie outside the array.)
        matchQueries = Arrays.copyOfRange(termArray, firstIndex, lastIndex+1);
       // System.out.println(matchQueries.length);

        //sort them by weight
        Arrays.sort(matchQueries, Term.byReverseWeightOrder());
        return matchQueries;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {

        return 0;

    }


    public static void main(String[] args){

        String filename = args[0];
        List<Term> termList = new ArrayList<Term>();

        try{
            FileInputStream inputStream = new FileInputStream(filename);
            String strFile = IOUtils.toString(inputStream);
            String[] lines = strFile.split(System.getProperty("line.separator"));
            for (int i = 0; i<lines.length; i++)
            {
                String[] words = lines[i].split("\t");
                Term n = new Term(words[1], Long.parseLong(words[0].trim()));
                termList.add(n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // read in queries from standard input and print out the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(termList);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String prefix = null;


            try {
                prefix = reader.readLine();
                if(prefix.isEmpty())
                    continue;
            } catch (IOException e) {
                e.printStackTrace();
            }

            Term[] results = autocomplete.allMatches(prefix);

           /* for (int i = 0; i < results.length; i++)
                System.out.println(results[i].getQuery());*/
            if (results != null) {
                for (int i = 0; i < Math.min(k, results.length); i++)
                    System.out.println(Term.toString(results[i]));
            }

            continue;
        }

    }


}
