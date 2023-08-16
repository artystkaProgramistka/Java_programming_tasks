public interface Filter<V> { 

	//metoda odpowiedzialna za wybór elementów listy, które spełniają określony warunek 

	boolean test(V v); 

} 

 

public interface Transformer<T,S> { 

	// metoda ma wykonać operację na przekazanej wartości i zwrócić jej wynik 

	T transform(S s); 

} 

 

static <T, S> List<T> create(List<S> src, Filter<S> f, 

	Transformer<T, S> t) { 

	List<T> target = new ArrayList<>(); 

	for (S e : src) 

		if (f.test(e)) target.add(t.transform(e)); 

	return target; 

} 

 

/* Dzięki zastosowaniu lambda-wyrażeń powyższy przykład wywołania metody create()  
upraszczamy praktycznie do jednego wiersza: */

 

List<Integer> target = create(src, n -> n < 10, n -> n*n ); 

 

// Tu zapis: n -> n < 10 jest lambda-wyrażeniem, które możemy traktować jako anonimową funkcję o parametrze n, która zwraca wynik wyrażenia n<10. 