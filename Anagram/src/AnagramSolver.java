//Shriya Kagolanu 
//Paley A Period
//Anagram Solver - find all anagram word combinations under max words limit for a given phrase 
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

public class AnagramSolver {
	private String inputString;
	private String dictionaryFile;
	private Integer maxWords;
	private Integer maxCount;
	private List<Integer> letterCounts;
	// size to list words that have the size
	private HashMap<Integer, List<String>> sizeToWordListMap;
	// unique words set
	private HashSet<String> uniqueWordMap;
	private List<List<Integer>> resultWordLengthSets;
	private List<Integer> sizeData;
	private List<String> wordData;
	private Set<List<String>> resultAnagramSets;

	public AnagramSolver(String dictionaryFile) {
		this.dictionaryFile = dictionaryFile;
		letterCounts = new ArrayList<Integer>();
		uniqueWordMap = new HashSet<String>();
		resultWordLengthSets = new ArrayList<List<Integer>>();
		resultAnagramSets = new HashSet<List<String>>();
		sizeToWordListMap = new HashMap<Integer, List<String>>();
	}

	public void anagramStart() {
		// load dictionary into memory
		processDictionary();
		Collections.sort(letterCounts);
	}
//load dictionary 
	private void processDictionary() {
		letterCounts.add(0);
		if (dictionaryFile.isEmpty()) {
			dictionaryFile = "dict3.txt";
		}
		try (FileInputStream inputStream = new FileInputStream(dictionaryFile)) {
			String strFile = IOUtils.toString(inputStream);
			String[] words = strFile.split("\\W+");

			for (String word : words) {
				if (!uniqueWordMap.contains(word)) {
					uniqueWordMap.add(word);
					if (!letterCounts.contains(word.length())) {
						letterCounts.add(word.length());
						List<String> l1 = new ArrayList<String>();
						l1.add(word);
						sizeToWordListMap.put(word.length(), l1);
					} else {
						List<String> l1 = sizeToWordListMap.get(word.length());
						l1.add(word);

					}
				}
			}

			// sort all the lists of words
			sizeToWordListMap.forEach((l, w) -> Collections.sort(w));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void findAnagrams(String inputPhrase, Integer noOfWords) {
		// reset DataStructures
		inputString = sortString(inputPhrase.replaceAll("\\s+", ""));
		inputString = inputString.replaceAll("[^A-Za-z]+", "");
		this.maxWords = noOfWords;
		this.maxCount = inputString.length();
		sizeData = new ArrayList<Integer>();
		wordData = new ArrayList<String>();
		resultWordLengthSets.clear();
		resultAnagramSets.clear();
		// generate permutations of letter sizes for input phrase
		generateCombinations(0, sizeData);
		findandPrintGrams();
	}

	// generate permutations of word lengths to make up the given phrase
	public void generateCombinations(Integer colCount, List<Integer> data) {
		if (colCount == 1)
			sizeData.clear();
		
		for (Integer row = 1; row < letterCounts.size(); row++) {
			if (colCount <= maxWords && (sizeData.stream().mapToInt(Integer::intValue).sum() + letterCounts.get(row)) == maxCount) {
				List<Integer> L1 = new ArrayList<Integer>();
				sizeData.forEach((i) -> L1.add(i));
				L1.add(letterCounts.get(row));
				if (!resultWordLengthSets.contains(L1))
				{
					resultWordLengthSets.add(L1);
				}
			}
			if (colCount < maxWords) {
				sizeData.add(letterCounts.get(row));
				generateCombinations((colCount + 1), sizeData);
			}
		}

		if (!sizeData.isEmpty())
			sizeData.remove(sizeData.size() - 1);

	}

	private static boolean containsWord(String s, String r) {
		for (char c : s.toCharArray()) {
			if (r.indexOf(c) == -1)
				return false;
		}
		return true;
	}

	private void findandPrintGrams() {
		resultWordLengthSets.forEach((length_set) -> {
			// create entry for each word
			generateWordCombinations(0, wordData, sizeToWordListMap.get(length_set.get(0)), length_set);

		});
		if (resultAnagramSets.size() >0)
		resultAnagramSets.forEach((word_set) -> System.out.println(word_set));
		else System.out.println("No anagrams found");
	}

	private static String sortString(String inputString) {
		char tempArray[] = inputString.toCharArray();
		Arrays.sort(tempArray);
		return new String(tempArray);
	}

	//generate anagram combos
	public void generateWordCombinations(Integer colCount, List<String> wordData, List<String> wordList,
			List<Integer> numberList) {
		if (colCount == 0)
			wordData.clear();

		for (Integer row = 0; row < wordList.size(); row++) {
			if (!containsWord(wordList.get(row), inputString)) {
				continue;
			}

			if (colCount + 1 == numberList.size()) {
				StringBuilder sb = new StringBuilder("");
				wordData.forEach((i) -> {
					sb.append(i);
				});
				sb.append(wordList.get(row));

				if (sortString(sb.toString()).equals(inputString)) {
					List<String> L1 = new ArrayList<String>();
					wordData.forEach((i) -> L1.add(i));
					L1.add(wordList.get(row));
					if (!resultAnagramSets.contains(L1))
						resultAnagramSets.add(L1);

				}
			} else if (colCount + 1 <= numberList.size() - 1) {
				wordData.add(wordList.get(row));
				List<String> nextWordList = sizeToWordListMap.get(numberList.get(colCount + 1));
				generateWordCombinations((colCount + 1), wordData, nextWordList, numberList);

			}
		}
		if (!wordData.isEmpty())
			wordData.remove(wordData.size() - 1);
	}
}
