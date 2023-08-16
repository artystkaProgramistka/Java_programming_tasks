/**
 *
 *  @author Łęczycka Dominika S24494
 *
 */

package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;

public class Anagrams {
	private File file;
	private int[][] ascii;
	ArrayList<String> allWords;
	public ArrayList<ArrayList<String>> anagrams;
	int uniqueAnagramCount;

	public Anagrams(String path) {
		File file = new File(path);
		allWords = new ArrayList();
		ascii = new int[1][123];
		anagrams = new ArrayList();
		uniqueAnagramCount = 1;
		try {
			Scanner in = new Scanner(file);
			while (in.hasNext())
				allWords.add(in.next());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int[] tempAscii;
		boolean twinFound;
		ArrayList tmpAnagramList;
		for (int i = 0; i < allWords.size(); i++) {
			twinFound = false;
			tempAscii = new int[123];
			for (int j = 0; j < allWords.get(i).length(); j++) {
				tempAscii[allWords.get(i).charAt(j)]++;
			}
			if (i == 0) {
				ascii[0] = tempAscii;
				tmpAnagramList = new ArrayList();
				tmpAnagramList.add(allWords.get(i));
				// indeksy ascii w i anagrams sobie odpowiadają
				anagrams.add(0, tmpAnagramList);
			} else {
				for (int k = 0; k < ascii.length; k++) {
					for (int l = 0; l < 123; l++) {
						if (ascii[k][l] != tempAscii[l]) {
							// twin not here
							break;
						}
						if (l == 122 && ascii[k][l] == tempAscii[l])
							twinFound = true;
					}
					if (twinFound) {
						anagrams.get(k).add(allWords.get(i));

						break;
					}
				}
				if (!twinFound) {
					// nowy set liter
					// dodaję nowy set do ascii
					// przepisuję ascii
					int[][] ascii1 = new int[ascii.length + 1][123];
					for (int k = 0; k < ascii.length; k++) {
						for (int l = 0; l < 123; l++) {
							ascii1[k][l] = ascii[k][l];
						}
					}
					ascii1[ascii.length] = tempAscii;
					// podmieniam
					ascii = ascii1;

					// dodaję nową tablicę do anagrams
					tmpAnagramList = new ArrayList();
					tmpAnagramList.add(allWords.get(i));
					if (uniqueAnagramCount >= anagrams.size())
						anagrams.add(tmpAnagramList);
					else
						anagrams.add(uniqueAnagramCount, tmpAnagramList);
					uniqueAnagramCount++;
					
					assert uniqueAnagramCount == ascii.length;
				}
			}
		}
	}

	public ArrayList<ArrayList<String>> getSortedByAnQty() {
		Collections.sort(this.anagrams, new MyComparator());
		return this.anagrams;
	}
	
	public String getAnagramsFor(String word) {
		ArrayList<String> result = getAnagramsFor1(word);
		for(String anagram: result) {
			if(anagram.equals(word)) {
				result.remove(word);
				break;
			}
		}
		return word + ": " + result;
	}
	
	private ArrayList<String> getAnagramsFor1(String word) {
		int index = getIndexOfArrayOfAnagramsFor(word);
		if(index == -1) return null;
		else return anagrams.get(index);
	}

	private int getIndexOfArrayOfAnagramsFor(String word) {
		//sprawdzaim, czy słowo znajduje się w anagrams (a anagrams przechowuje wszystkie slowa z allWords.txt), a jeśli tak, to gdzie
		boolean twinFound;
		for(int i=0; i<anagrams.size(); i++) {
			for(int j=0; j<anagrams.get(i).size(); j++) {
				twinFound=true;
				for(int k=0; k<anagrams.get(i).get(j).length() && k<word.length(); k++) {
					if (word.charAt(k) != anagrams.get(i).get(j).charAt(k)) {
						twinFound=false;
						break;
					}
					if(twinFound) return i;
				} 
			}
		}
		return -1;
	}
}
