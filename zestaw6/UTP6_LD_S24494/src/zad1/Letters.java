package zad1;

import java.util.ArrayList;

public class Letters extends Thread {
	ArrayList<Thread> threads = new ArrayList<Thread>();
	
	public Letters(String s){
		for(char c: s.toCharArray()){
			Runnable myThread= () -> {
				while(true)
					try{
						System.out.print(c);
						Thread.sleep(1000);
					} catch (InterruptedException e){ break; }
			};
			threads.add(new Thread(myThread, "Thread " + c));
		}
	}
	public ArrayList<Thread> getThreads(){ return threads; }
	
}