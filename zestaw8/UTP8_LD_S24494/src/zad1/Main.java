/**
 *
 *  @author Łęczycka Dominika S24494
 *
 */
package zad1;

import java.io.*;
import java.util.*;

public class Main {

  public static void main(String[] args) {
    File dataDir = new File("data");
    TravelData travelData = new TravelData(dataDir);
    String dateFormat = "yyyy-MM-dd";
    for (String locale : Arrays.asList("pl_PL", "en_GB")) {
      List<String> odlist = travelData.getOffersDescriptionsList(locale, dateFormat);
      for (String od : odlist) System.out.println(od);
    }
    // --- część bazodanowa
    String url = "jdbc:mysql://localhost:3306/database?createDatabaseIfNotExist=true&server.initialize-user=true&server.allow_upgrade=true&server.initialize-insecure=true&server.user=inmemory_user&server.password=inmemory_password";/*<-- tu należy wpisać URL bazy danych */
    Database db = new Database(url, travelData);
    db.create();
    db.showGui();
  }

}
