package zad4;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {
    T value;

    private Maybe(T value) {
        this.value = value;
    }

    public static <T>Maybe<T> of(T v) {
        return new Maybe(v);
    }
    
    private static final Maybe<?> EMPTY = new Maybe<>(null);

    void ifPresent(Consumer cons) {
        if (this.value != null) {
        	cons.accept(this.value);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> Maybe<T> ofNullable(T value) {
        return value == null ? (Maybe<T>) EMPTY
                             : new Maybe<>(value);
    }
    
    public static<T> Maybe<T> empty() {
        @SuppressWarnings("unchecked")
        Maybe<T> t = (Maybe<T>) EMPTY;
        return t;
    }

    public <U> Maybe<U> map(Function<? super T, ? extends U> func) {
        Objects.requireNonNull(func);
        if (!isPresent()) {
            return empty();
        } else {
            return Maybe.ofNullable(func.apply(value));
        }
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public T orElse(T other) {
        return value != null ? value : other;
    }

    public Maybe<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : empty();
        }
    }

    @Override
    public String toString() {
        if (value == null) return "Maybe is empty";
        else return "Maybe has value "+value;
    }
}
