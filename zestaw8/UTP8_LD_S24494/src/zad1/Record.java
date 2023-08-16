package zad1;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class Record {
    private final Locale countryCode;
    private final String countryName;
    private final Date startDate; //YYYY-MM-DD
    private final Date endDate; //YYYY-MM-DD
    private final String location;
    private final Double price;
    private final String currency;

    Record(Locale countryCode, String countryName, Date startDate, Date endDate, String location, Double price, String currency) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.price = price;
        this.currency = currency;
    }

    public Record(String[] lineData) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int i = 0;

        Locale locale = Locale.forLanguageTag(lineData[i++].replace("_", "-"));
        NumberFormat numberFormat = NumberFormat.getInstance(locale);

        this.countryCode = locale;
        this.countryName = lineData[i++];
        this.startDate = simpleDateFormat.parse(lineData[i++]);
        this.endDate = simpleDateFormat.parse(lineData[i++]);
        this.location = lineData[i++];
        this.price = numberFormat.parse(lineData[i++]).doubleValue();
        this.currency = lineData[i];
    }


    Locale getCountryCode() {
        return countryCode;
    }

    String getCountryName() {
        return countryName;
    }

    Date getStartDate() {
        return startDate;
    }

    Date getEndDate() {
        return endDate;
    }

    String getLocation() {
        return location;
    }

    Double getPrice() {
        return price;
    }

    String getCurrency() {
        return currency;
    }

    ArrayList<String> toArrayStr(Locale destLocale, String dateFormat, Properties translations, Boolean includeCountryCode) {
        ArrayList<String> ret = new ArrayList<String>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        NumberFormat numberFormat = NumberFormat.getInstance(destLocale);

        String country = translateCountry(destLocale, getCountryName());
        if (includeCountryCode) {
            ret.add(countryCode.toString());
        }
        ret.add(country);
        ret.add(simpleDateFormat.format(getStartDate()));
        ret.add(simpleDateFormat.format(getEndDate()));
        ret.add(translateLocation(translations, destLocale, getLocation()));
        ret.add(numberFormat.format(getPrice()));
        ret.add(getCurrency());

        return ret;
    }

    public String translateCountry(Locale outLocale, String countryName) {
        for (Locale l : Locale.getAvailableLocales()) {
            if (l.getDisplayCountry(countryCode).equals(countryName)) {
                return l.getDisplayCountry(outLocale);
            }
        }
        System.out.println("Unable to translate: " + countryName);
        return countryName;
    }

    public String translateLocation(Properties translations, Locale outLocale, String word) {
        return translations.getProperty(countryCode.getLanguage() + "-" + outLocale.getLanguage() + "." + word, word);
    }
}