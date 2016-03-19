package hh.learnj.enhance.thread.lock;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheData {
	
	private Object cacheData = null;
	private volatile boolean cacheValid = false;
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	public void processCacheData() {
		readWriteLock.readLock().lock();
		if (!cacheValid) {
			readWriteLock.readLock().unlock();
			readWriteLock.writeLock().lock();
			if (!cacheValid) {
				cacheData = new Random().nextInt();
				cacheValid = true;
			}
			readWriteLock.readLock().lock();
			readWriteLock.writeLock().unlock();
		}
		useData(cacheData);
		readWriteLock.readLock().unlock();
	}
	
	public void useData(Object data) {
		System.out.println(data);
	}

	public static void main(String[] args) {

	}

}
