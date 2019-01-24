//Autocomplete Shriya Kagolanu A period Paley
//Stanford Nifty Project
import java.util.Comparator;
import java.io.*;
import java.util.*;

public class BinarySearchDeluxe {

    // Returns the index of the first key in a[] that equals the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator)
    {
        //System.out.println( " First index keys" + a.toString());

        //System.out.println( " First index Key = " + key.toString());


        int low = 0;
        int high = a.length - 1;
        int mid;
        int lastIndex;

        while (low <= high) {
            mid = (low + high) / 2;

            if (comparator.compare(a[mid],key) < 0) {
                low = mid + 1;
            } else if (comparator.compare(a[mid],key) > 0) {
                high = mid - 1;
            } else {

                lastIndex= mid;

                for (int i = mid-1; i >=low; i--) {

                    if (comparator.compare(a[i],key) == 0) {
                        //System.out.println("\nfound low" + (i));
                        lastIndex = i;
                    }
                    else break;
                }
                return lastIndex;
            }
        }

        return-1;
    }

    // Returns the index of the last key in a[] that equals the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator)
    {

        //System.out.println( " Second index Key = " + key.toString());
        int low = 0;
        int high = a.length - 1;
        int mid;
        int lastIndex = 0;

        while (low <= high) {
            mid = (low + high) / 2;

            if (comparator.compare(a[mid],key) < 0) {
                low = mid + 1;
            } else if (comparator.compare(a[mid],key) > 0) {
                high = mid - 1;
            } else {
                lastIndex= mid;
                for (int i = mid; i <= high; i++) {
                    if (comparator.compare(a[i],key) == 0)
                    {
                      //  System.out.println("found high"+ i);
                        lastIndex = i;
                    }
                    else break;
                }
                return lastIndex;
            }
        }

        return-1;
    }

}
