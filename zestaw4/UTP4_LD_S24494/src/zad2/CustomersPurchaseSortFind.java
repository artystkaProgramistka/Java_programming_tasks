/**
 * @author Łęczycka Dominika S24494
 */

package zad2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CustomersPurchaseSortFind {
    List<Purchase> orders;
    Map<String, List<Purchase>> transactions;

    public CustomersPurchaseSortFind() {
        orders = new ArrayList<>();
        transactions = new HashMap<String, List<Purchase>>();
    }

    public void showSortedBy(String string) {
        if (string == "Nazwiska") {
            System.out.println(string);
            ArrayList<Pair<String, Purchase>> pairs_to_sort = new ArrayList<Pair<String, Purchase>>();
            for (Purchase order : orders) {
                String name = order.client.name;
                pairs_to_sort.add(new Pair<String, Purchase>(name, order));
            }
            pairs_to_sort.sort(Comparator.comparing(p -> p.getKey()));
            for (Pair<String, Purchase> p : pairs_to_sort) {
                System.out.println(p.getValue().toString());
            }
            System.out.println();
        } else if (string == "Koszty") {
            System.out.println(string);
            ArrayList<Pair<Float, Purchase>> pairs_to_sort = new ArrayList<Pair<Float, Purchase>>();
            for (Purchase order : orders) {
                Float value = order.article.price * order.amount;
                pairs_to_sort.add(new Pair<Float, Purchase>(value, order));
            }
            pairs_to_sort.sort(Comparator.comparing(p -> -p.getKey()));
            for (Pair<Float, Purchase> p : pairs_to_sort) {
                String cost_str = " (koszt: ";
                cost_str += p.getKey().toString();
                cost_str += ")";
                System.out.println(p.getValue().toString() + cost_str);
            }
            System.out.println();
        }
    }

    public void readFile(String fname) {
        File file = new File(fname);
        try {

            Scanner sc = new Scanner(file);
            String line;
            String inputArr[];
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                inputArr = line.split(";");
                String id = inputArr[0];
                Client client = new Client(id, inputArr[1]);
                Article article = new Article(inputArr[2], Float.parseFloat(inputArr[3]));
                Purchase purchase = new Purchase(client, article, Float.parseFloat(inputArr[4]));
                orders.add(purchase);
                if (transactions.containsKey(id)) transactions.get(id).add(purchase);
                else {
                    List<Purchase> list = new ArrayList<>();
                    list.add(purchase);
                    transactions.put(id, list);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showPurchaseFor(String id) {
        System.out.println("Klient " + id);
        for (Purchase purchase : transactions.get(id)) {
            System.out.println(purchase);
        }
        System.out.println();
    }
}
