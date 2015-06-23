package hh.learnj.designpattern.momento.step3;

import hh.learnj.designpattern.momento.step1.Memento;

import java.util.ArrayList;
import java.util.List;

/**
 * Create CareTaker class
 * 
 * @author hunnyhu
 *
 */
public class CareTaker {

	private List<Memento> mementoList = new ArrayList<Memento>();

	public void add(Memento state) {
		mementoList.add(state);
	}

	public Memento get(int index) {
		return mementoList.get(index);
	}
}
