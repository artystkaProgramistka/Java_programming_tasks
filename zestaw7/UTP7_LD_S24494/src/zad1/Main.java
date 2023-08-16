/**
 *
 *  @author Łęczycka Dominika S24494
 *
 */

package zad1;


public class Main {

  public static void main(String[] args) {
      Container k = new Container();
      Thread A = new Adder(k);
      Thread B = new Downloader(k);
      A.start();
      B.start();
  }
}
