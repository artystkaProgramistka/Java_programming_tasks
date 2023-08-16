package zad3;

import java.util.function.Function;

public class InputConverter<T> {
    T inputValue;

    public InputConverter(T inputValue) {
        this.inputValue = inputValue;
    }

    public <U> U convertBy(Function...args) {
        Object result = args[0].apply(this.inputValue);
        for(int i=1; i<args.length; i++) {
            result = args[i].apply(result);
        }
        return (U)result;
    }
}