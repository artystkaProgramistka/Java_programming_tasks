package zad2;

public class StringTask implements Runnable {
	private String s;
	private int howMany;
	private String result;
	private TaskState taskState;
	private boolean isDone;
	private Thread task;

	public TaskState getState() { return taskState; }

	public String getResult() { return result; } // zwraca wynik konkatenacji

	public void start() { // uruchamia zadanie w odrębnym wątku
		task = new Thread(this);
		taskState = TaskState.RUNNING;
		task.start();
	}

	public void abort() { // przerywa wykonanie kodu działania i działanie wątku
		taskState = TaskState.ABORTED;
		isDone = true;
		task.interrupt();
	}

	public boolean isDone() { return isDone; }// zwraca true, jeśli wykonanie zadania się zakończyło normalnie lub przez przerwanie, false w przeciwnym razie

	enum TaskState {
		CREATED, // zadanie utworzone, ale nie zaczęło się jeszcze wykonywać
		RUNNING, // zadanie wykonuje się w odrebnym wątku
		ABORTED, // wykonanie zadania zostało przerwane
		READY; // zadanie zakończyło się pomyślnie i sa gotowe wyniki
	}
	
	public StringTask(String s, int howMany) {
		this.s = s;
		this.howMany = howMany;
		result = "";
		isDone = false;
		taskState = TaskState.CREATED;
	}

	@Override
	public void run() {
		for (int i=0; i < howMany; i++) {
			if(i/1000 == 0)
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				isDone = true;
				return;
			}
			// ToDo proces wykonuje się zbyt szybko i tylko dlatego na konsole nie zostają wypisane R, tylko po to jest powyższy fragment kodu...
			if (task.interrupted()) {
				isDone = true;
				return;
			}
			result  = result + s;
		}
		isDone = true;
		taskState = TaskState.READY;
	}


}
