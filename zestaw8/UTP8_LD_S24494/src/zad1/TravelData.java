package zad1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

public class TravelData {

    private final List<Record> data;
    private Properties translations;

    public TravelData(File dataDir) {
        data = new ArrayList<>();

        Arrays.stream(Objects.requireNonNull(dataDir.listFiles())).forEach(file -> {
            try {
                Files.lines(Paths.get(file.getPath())).forEach(line -> {
                    String[] lineData = line.split("\t");
                    try {
                        data.add(new Record(lineData));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try (InputStream input = new FileInputStream("translations.properties")) {
            translations = new Properties();
            translations.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Properties getDictionary() {
        return translations;
    }

    List<String> getOffersDescriptionsList(String locale, String dateFormat) {
        List<String> descList = new ArrayList<>();

        Locale destLocale = Locale.forLanguageTag(locale.replace("_", "-"));

        data.forEach(d -> {
            StringBuilder sb = new StringBuilder();
            for (String s : d.toArrayStr(destLocale, dateFormat, translations, false)) {
                sb.append(s).append(" ");
            }
            descList.add(sb.toString());
        });

        return descList;
    }


    List<Record> getData() {
        return data;
    }
}
