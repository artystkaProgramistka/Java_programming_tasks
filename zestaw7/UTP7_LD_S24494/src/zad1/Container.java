package zad1;

public class Container {
    Article art = null;
    boolean newArt = false;

    //  - wywołuje Dodawacz
    synchronized void add(Article o) {
        while (newArt == true) {
            try {
                wait();
            } catch(InterruptedException exc) {}
        }
        art= o;
        newArt = true;
        notifyAll();
    }

    // wywoluje Pobieracz
    synchronized Article getArt() {
        while (newArt == false) {
            try {
                wait();
            } catch(InterruptedException exc) {}
        }
        newArt = false;
        notifyAll();
        return art;
    }
}
