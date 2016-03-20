package hh.learnj.enhance.thread;

public class JoinThreadTest {
	public static void main(String[] args) throws InterruptedException {
		Join j1 = new Join("新线程");
		j1.start();
		for (int i = 0; i < 100; i++) {
			if (i == 20) {
				Join j2 = new Join("被Join的线程" + i);
				j2.start();
				j2.join();
			}
			System.out.println(Thread.currentThread().getName() + " " + i);
		}
	}
}

class Join extends Thread {
	String name;

	public Join(String name) {
		this.name = name;
	}

	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(name + "=" + getName() + " " + i);
		}
	}
}
