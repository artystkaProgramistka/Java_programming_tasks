package zad1;

public class Downloader extends Thread {

    private Container cont;
    private int sum;
    private int processedObjects;
    private Article a;

    Downloader(Container cont) {
        this.cont = cont;
        sum=0;
    }

    public void run() {
        a = cont.getArt();
        while(a != null) {
            //przetwarzam dane
            sum += a.weight;
            processedObjects++;
            if(processedObjects % 100 == 0)
                System.out.println("policzono wage "+processedObjects+" towarów");
            a = cont.getArt();
        }
    }
}
