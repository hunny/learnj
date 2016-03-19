package hh.learnj.enhance.thread.lock;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

	public static void main(String[] args) {
		final DemoFile file = new DemoFile();
		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int m = 0; m < 5; m++) {
						file.read();
					}
				}
			}).start();
		}
		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int m = 0; m < 5; m++) {
						file.write(new Random().nextInt(10000));
					}
				}
			}).start();
		}
	}

}

class DemoFile {

	private Object data = null;

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public void read() {
		lock.readLock().lock();
		System.out.println(Thread.currentThread().getName()
				+ " be ready to read data.");
		try {
			Thread.sleep((long)new Random().nextInt(1000));
			System.out.println(Thread.currentThread().getName()
					+ " have read data :" + data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();
		}
	}

	public void write(Object data) {
		lock.writeLock().lock();
		System.out.println(Thread.currentThread().getName()
				+ " be ready to write data.");
		try {
			Thread.sleep((long)new Random().nextInt(1000));
			this.data = data;
			System.out.println(Thread.currentThread().getName()
					+ " have write data :" + data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}

}
