//Autocomplete Shriya Kagolanu A period Paley
//Stanford Nifty Project
import java.util.*;
import static java.lang.Math.toIntExact;


public class Term implements Comparable<Term> {

    private Long weight;
    private String query;

    //gets
    public Long getWeight ()
    {
        return this.weight;
    }

    public String getQuery ()
    {
        return this.query;
    }
    // Initializes a term with the given query string and weight.
    public Term(String query, Long weight){

        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder(){
        return new Comparator<Term>() {
            @Override
            public int compare(Term a, Term other) {
                // code here
                return Long.compare(other.getWeight(),a.getWeight());
            }
        };
    }

    // Compares the two terms in lexicographic order but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(final int  r){

        return new Comparator<Term>() {
            @Override
            public int compare(Term a, Term b) {
                if(a.getQuery().length()>=r && b.getQuery().length()>=r)
                {
                    //System.out.println(a.getQuery() + " " + b.getQuery());
                    return (a.getQuery().substring(0,r).compareTo(b.getQuery().substring(0,r)));
                }
                return -1;
            }
        };

    }

    // Compares the two terms in lexicographic order by query.
    @Override
    public int compareTo(Term that){

        return this.getQuery().compareTo(that.getQuery());

    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public static String toString(Term t)
    {
        String s= t.getWeight() + "\t" + t.getQuery();
        return s;
    }

}