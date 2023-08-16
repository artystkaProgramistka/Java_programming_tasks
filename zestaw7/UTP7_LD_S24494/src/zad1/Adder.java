package zad1;

import java.io.*;

public class Adder extends Thread {
    Container  cont;
    int addedObjects;
    double sum;

    Adder(Container cont)  {
        this.cont = cont;
        addedObjects =0;
        sum=0;
    }

    public void run() {
        File file = new File("../Towary.txt");
        String st;
        Article art;
        String splitted[];
        try {
            BufferedReader br= new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null) {
                splitted = st.split(" ");
                art = new Article(Integer.parseInt(splitted[0]), Double.parseDouble(splitted[1]));
                //tworze obiekty, jesli to ostatni to posyłam null
                cont.add(art);
                addedObjects++;
                sum += art.weight;
                if(addedObjects % 100 == 0)
                    System.out.println("utworzono "+addedObjects+" obiektów");

            } cont.add(null);
            System.out.println("Podaję sumaryczną wagę wszystkich towarów: "+(long)sum);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
