package me.yhamarsheh.airport.structure;

import java.util.Iterator;

public class Queue<T> implements Iterable<T> {

	private final Stack<T> stack; // BACK - FRONT (TOP)
	private final Stack<T> temp;

	private int size;
	public Queue(int size) {
		this.stack = new Stack<>(size);
		this.temp = new Stack<>(size);
	}

	public void enqueue(T data) {

		while (!stack.isEmpty()) {
			temp.push((T) stack.pop());
		}

		temp.push(data);

		while (!temp.isEmpty()) {
			stack.push((T) temp.pop());
		}
//		stack.push(data);
	}

	public T dequeue() {
/*		Stack<T> temp = new Stack<>(SIZE);

		while (!stack.isEmpty()) {
			temp.push((T) stack.pop());
		}

		T data = (T) temp.pop();

		while (!temp.isEmpty()) {
			stack.push((T) temp.pop());
		}

		return data;*/

		return (T) stack.pop();
	}

	public void deleteItem(T data) {
		while (!stack.isEmpty()) {
			T item = stack.pop();
			if (!item.equals(data)) temp.push((T) stack.pop());
		}

		temp.push(data);

		while (!temp.isEmpty()) {
			stack.push((T) temp.pop());
		}
	}

	public T getFront() {
		return (T) stack.peek();
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}

	public void clear() {
		stack.clear();
	}

	public void print() {
		System.out.println("Back->");
		while (!stack.isEmpty()) {
			temp.push((T) stack.pop());
		}

		while (!temp.isEmpty()) {
			System.out.println("\t" + stack.peek());
			stack.push((T) temp.pop());
		}
	}


	@Override
	public Iterator<T> iterator() {
		return new QueueIterator();
	}

	private class QueueIterator implements Iterator<T> {

		public QueueIterator() {
			while (!stack.isEmpty()) {
				temp.push(stack.pop());
			}
		}

		@Override
		public boolean hasNext() {
			return !isEmpty();
		}

		@Override
		public T next() {
			T data = stack.peek();
			stack.push(temp.pop());
			return data;
		}
	}
}
