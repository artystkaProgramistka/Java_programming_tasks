package zad1;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class XList<T> extends LinkedList<T> {
    public XList(Collection<T> list){ this.addAll(list); }

    @SafeVarargs//Ostrzezenie - jak sie tego pozbyc
    public XList(T...args){ Collections.addAll(this, args); }

    @SafeVarargs//Ostrzezenie - jak sie tego pozbyc
    public static<T> XList<T> of(T...args){ return new XList<>(args); }

    public static<T> XList<T> of(Collection<T> list){ return new XList<>(list); }

    public static XList<String> charsOf(String string){
        return new XList<>(splitIntoListOfStrings(string, ""));
    }
    // Change the name
    private static List<String> splitIntoListOfStrings(String string, String sep) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, string.split(sep));
        return list;
    }

    public static XList<String> tokensOf(String string) {
        return new XList<>(splitIntoListOfStrings(string, " "));
    }

    public static XList<String> tokensOf(String string, String sep) {
        return new XList<>(splitIntoListOfStrings(string, sep));
    }

    public XList<T> union(Collection<T> collection) {
        ArrayList<T> list = new ArrayList<>();
        list.addAll(this);
        list.addAll(collection);
        return new XList<>(list);
    }
    
    public XList<T> union(T[] arr) {
        return union(Arrays.asList(arr));
    }

    public XList<T> diff(Collection<T> collection) {
        XList list = new XList<>();
        for (T element: this) if(!collection.contains(element)) list.add(element);
        return list;
    }

    public XList<T> unique() {
        Set<T> set = new HashSet<>();
        XList result = new XList();
        for (T element: this) 
        	if (!set.contains(element)) { 
        		result.add(element);
        		set.add(element);
        	}
        return result;
    }

    private void reverse()
    {
        if (this.size() <= 1) return;
        T value = this.remove(0);
        reverse();
        this.add(value);
    }

    public <T>XList<List<T>> combine1(XList<List<T>> list, XList<T> currentData) {
    	XList<List<T>> result = new XList<>();
        if (list.isEmpty()) {
        	XList<T> copy = new XList<T>(currentData);
        	copy.reverse();
        	result.add(copy);
            return result;
        }
        List<T> currentList = list.removeLast();
        for (T element: currentList) {
            currentData.add(element);
            result.addAll(combine1(list, currentData));
            currentData.removeLast();
        }
        list.add(currentList);
        return result;
    }

    public <T>XList<List<T>> combine() {
        XList<T> data = new XList<>();
        return combine1((XList<List<T>>)this, data);
    }

    public <U> XList<U> collect(Function<T, U> f) {
        XList<U> result = new XList<>();
        for (T element: this) result.add( f.apply(element) );
        return result;
    }

    public String join(String sep) {
        String result = this.get(0).toString();
        for (int i=1; i<this.size(); i++) {
            result += (sep + this.get(i).toString());
        }
        return result;
    }

    public String join() {
        return this.join("");
    }

    public void forEachWithIndex(BiConsumer<T, Integer> konsumer) {
        for(int i=0; i<this.size(); i++) konsumer.accept(this.get(i), i);
    }

}