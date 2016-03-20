package hh.learnj.enhance.thread;

public class DaemonThreadTest {
	
	public static void main(String[] args) throws InterruptedException {
		Daemon d1 = new Daemon();
		d1.setDaemon(true);// 将此线程设置成后台线程
		d1.start();// 启动后台线程
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
		}// 程序执行好此处，前台线程 main结束，后台线程也随之结束

	}
}

class Daemon extends Thread {
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println("Daemon:" + getName() + " " + i);
		}
	}
}
