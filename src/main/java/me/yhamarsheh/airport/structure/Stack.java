package me.yhamarsheh.airport.structure;


public class Stack<T> {

	private CursorArray<T> cArray;
	private final int LIST;

    public Stack(int size) {
    	cArray = new CursorArray<>(size + 2);
    	LIST = cArray.createList();
	}

	public void push(T data) {
		cArray.insert(LIST, data);
	}

	public T pop() {
		return cArray.deleteAtHead(LIST).getData();
	}

	public T peek() {
		return cArray.getHead(LIST).getData();
	}

	public boolean isEmpty() {
		return cArray.isEmpty(LIST);
	}

	public void clear() {
		cArray.clearList(LIST);
	}

}