package headfirst.adapter.client;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

public class ToEnumerationAdaptor<T> implements Enumeration<T> {
	
	private Iterator<T> iterator;
	
	
	public ToEnumerationAdaptor() {
		this.iterator = new ArrayList<T>().iterator();
	}
	
	public ToEnumerationAdaptor(Iterator<T> iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasMoreElements() {
		return iterator.hasNext();
	}

	@Override
	public T nextElement() {
		return iterator.next();
	}
}
