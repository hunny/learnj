package hh.learnj.enhance.thread.collects.blockingqueue;

import java.util.ArrayList;
import java.util.List;


class SomeEvent {
	private long time;

	public SomeEvent(long time) {
		super();
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
}

interface SomeListener {
	
	public void actionEvent(SomeEvent event);
	
}

class Child implements Runnable {
	
	private List<SomeListener> list = new ArrayList<SomeListener>();
	public void addListener(SomeListener elem) {
		this.list.add(elem);
	}
	public void some() {
		for (SomeListener w : list) {
			w.actionEvent(new SomeEvent(System.currentTimeMillis()));
		}
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.some();
	}
	
}

class Parent implements SomeListener {

	@Override
	public void actionEvent(SomeEvent event) {
		System.out.println("Parent event.");
	}
	
}

class GrandParent implements SomeListener {
	
	@Override
	public void actionEvent(SomeEvent event) {
		System.out.println("Grand parent event.");
	}
	
}

public class TestObserver {

	public static void main(String[] args) {
		Child c = new Child();
		c.addListener(new Parent());
		c.addListener(new GrandParent());
		new Thread(c).start();
	}

}
