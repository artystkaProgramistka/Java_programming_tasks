package zad1;

import java.util.ArrayList;
import java.util.Comparator;

public class MyComparator implements Comparator<ArrayList<String>> {
	@Override
	public int compare(ArrayList<String> arr1, ArrayList<String> arr2)
	{
		if (arr1.size() == arr2.size())
				if(arr1.get(0).charAt(0) <= arr2.get(0).charAt(0)) return 1;
			else return -1;
		else if (arr1.size() < arr2.size())
			return 1;
		else
			return -1;
	}
}