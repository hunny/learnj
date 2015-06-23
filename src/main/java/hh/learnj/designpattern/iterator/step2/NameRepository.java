package hh.learnj.designpattern.iterator.step2;

import hh.learnj.designpattern.iterator.step1.Container;
import hh.learnj.designpattern.iterator.step1.Iterator;

public class NameRepository implements Container {
	
	public String names[] = { "Robert", "John", "Julie", "Lora" };

	@Override
	public Iterator getIterator() {
		return new NameIterator();
	}

	private class NameIterator implements Iterator {
		private int index = 0;
		@Override
		public boolean hasNext() {
			if (index < names.length) {
				return true;
			}
			return false;
		}
		@Override
		public Object next() {
			if (this.hasNext()) {
				return names[index++];
			}
			return null;
		}
	}
}
