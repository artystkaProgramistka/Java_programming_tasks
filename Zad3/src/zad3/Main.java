/**
 * @author Łęczycka Dominika S24494
 */

package zad3;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

/*<-- niezbędne import */
public class Main {
    public static void main(String[] args) throws IOException {
        /*<--
         *  definicja operacji w postaci lambda-wyrażeń:
         *  - flines - zwraca listę wierszy z pliku tekstowego
         */
        // ExtendedFunction<String, Integer> flines = (filePath -> {
        //     Integer numberOfLines = 0;
        //     File file = new File(filePath);
        //     Scanner scanner = new Scanner(file);
        //     while (scanner.hasNextLine()) {
        //         numberOfLines++;
        //     }
        //     return numberOfLines;
        // });
        ExtendedFunction<String, List<String>> flines = (filePath -> {
            return Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        });
        //  - join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)
        Function<List<String>, String> join = (listOfStrings ->
                listOfStrings.stream().collect(Collectors.joining())
        );
        //  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
        Function<String, List<Integer>> collectInts = (string -> {
            List<Integer> result = new ArrayList<>();
            String toBecomeInt = "";
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) >= '0' && string.charAt(i) <= '9') {
                    toBecomeInt += string.charAt(i);
                } else {
                    if (!toBecomeInt.isEmpty()) {
                        result.add(Integer.parseInt(toBecomeInt));
                        toBecomeInt = "";
                    }
                }
            }
            if (!toBecomeInt.isEmpty())
                result.add(Integer.parseInt(toBecomeInt));
            return result;
        });
        //  - sum - zwraca sumę elmentów listy liczb całkowitych
        Function<List<Integer>, Integer> sum = (ints -> ints.stream().reduce(0, Integer::sum));


        String fname = System.getProperty("user.home") + "/LamComFile.txt";
        InputConverter<String> fileConv = new InputConverter<>(fname);
        List<String> lines = fileConv.convertBy(flines);
        String text = fileConv.convertBy(flines, join);
        List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
        Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

        System.out.println(lines);
        System.out.println(text);
        System.out.println(ints);
        System.out.println(sumints);

        List<String> arglist = Arrays.asList(args);
        InputConverter<List<String>> slistConv = new InputConverter<>(arglist);
        sumints = slistConv.convertBy(join, collectInts, sum);
        System.out.println(sumints);

        // Zadania badawcze:
        // Operacja flines zawiera odczyt pliku, zatem może powstac wyjątek IOException
        // Wymagane jest, aby tę operację zdefiniowac jako lambda-wyrażenie
        // Ale z lambda wyrażeń nie możemy przekazywac obsługi wyjatków do otaczającego bloku
        // I wobec tego musimy pisać w definicji flines try { } catch { }
        // Jak spowodować, aby nie było to konieczne i w przypadku powstania wyjątku IOException
        // zadziałała klauzula throws metody main
    }
}
