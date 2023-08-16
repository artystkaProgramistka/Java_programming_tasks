package zad3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ProgLang {
	
	LinkedHashMap<String,ArrayList<String>> langsMap;
	LinkedHashMap<String,ArrayList<String>> progsMap;
	
	public ProgLang(String fname) {
		langsMap = new LinkedHashMap<>();
		progsMap = new LinkedHashMap<>();
		File file = new File(fname);
		try {
			Scanner sc = new Scanner(file);
			String line;
			String inputArr[];
			String lang;
			ArrayList<String> surnames;
			while(sc.hasNext()) {
				line = sc.nextLine();
				inputArr = line.split("\t");
				lang = inputArr[0];
				surnames = new ArrayList();
				for(int i=1; i<inputArr.length; i++) {
					surnames.add(inputArr[i]);
				}
				for(String surname: surnames) {
					if (progsMap.containsKey(surname)) {
						if(!progsMap.get(surname).contains(lang)) progsMap.get(surname).add(lang);
					} else {
						ArrayList<String> arr = new ArrayList<>();
						arr.add(lang);
						progsMap.put(surname, arr);
					}
					if (langsMap.containsKey(lang)) {
						if(!langsMap.get(lang).contains(surname)) langsMap.get(lang).add(surname);
					} else {
						ArrayList<String> arr = new ArrayList<>();
						arr.add(surname);
						langsMap.put(lang, arr);
					}
				}
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public LinkedHashMap<String,ArrayList<String>> getLangsMap() {
		return langsMap;
	}
	
	public LinkedHashMap<String,ArrayList<String>> getProgsMap() {
		return progsMap;
	}
	
	public static <T, V> LinkedHashMap<T, V> sorted(HashMap<T, V> dictionary,
														    Function<Pair<T, V>, Comparable> func) {
		ArrayList<Pair<T, V>> pairs = new ArrayList<>();
		for(T key: dictionary.keySet()) {
			pairs.add(new Pair<T, V>(key, dictionary.get(key)));
		}
		pairs.sort(Comparator.comparing(func));
		LinkedHashMap<T, V> result = new LinkedHashMap<T, V>();
		for(Pair<T, V> keyHolder: pairs) {
			result.put(keyHolder.getKey(), keyHolder.getValue());
		}
		return result;
	}

	public static <K, V> LinkedHashMap<K, V> filtered(LinkedHashMap<K, V> dictionary,
																    Function<Pair<K, V>, Boolean> func) {
		LinkedHashMap<K, V> result = new LinkedHashMap<K, V>();
		for(K key: dictionary.keySet()) {
			Pair<K, V> pair = new Pair<K, V>(key, dictionary.get(key));
			if (func.apply(pair)) {
				result.put(key, dictionary.get(key));
			}
		}
		return result;

	}

	public LinkedHashMap<String, ArrayList<String>> getLangsMapSortedByNumOfProgs() {
		return sorted(langsMap, (x)-> new ComparablePair(-x.getValue().size(), x.getKey()));
	}

	public LinkedHashMap<String, ArrayList<String>> getProgsMapSortedByNumOfLangs() {
		return sorted(progsMap, (x)-> new ComparablePair(-x.getValue().size(), x.getKey()));
	}
	public LinkedHashMap<String, ArrayList<String>> getProgsMapForNumOfLangsGreaterThan(Integer n) {
		return filtered(progsMap, (x) -> x.getValue().size() > n);
	}
}
