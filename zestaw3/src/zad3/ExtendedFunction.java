package zad3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Function;

@FunctionalInterface
public interface ExtendedFunction<T, R> extends Function<T, R> {
    R checkedApply(T arg) throws IOException;

    default R apply(T arg) {
        try {
            return checkedApply(arg);
        } catch (IOException exc) {
            System.out.println(exc);
        } return null;
    }
}