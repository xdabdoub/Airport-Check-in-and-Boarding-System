package me.yhamarsheh.airport.structure;

import me.yhamarsheh.airport.structure.nodes.CNode;

public class CursorArray<T> {

	private CNode<T>[] cArray;

	public CursorArray(int size) {
		this.cArray = new CNode[size];

		init(size);
	}

	private void init(int size) {
		for (int i = 0; i < size; i++) {
			if (i == size - 1)
				cArray[i] = new CNode<>(null, 0);
			else {
				cArray[i] = new CNode<>(null, i + 1);
			}
		}
	}

	public int malloc() {
		if (cArray[0].getNext() == 0)
			return 0; // Empty

		int i = cArray[0].getNext();
		cArray[0].setNext(cArray[i].getNext());

		return i;
	}

	public void free(int p) {
		cArray[p].setNext(cArray[0].getNext());
		cArray[0].setNext(p);
	}

	public void insert(int list, T data) {
		if (isNull(list)) // list not created
			return;

		int p = malloc();
		if (p == 0) {
			System.out.println("Not enough space");
			return;
		}

		cArray[p].setNext(cArray[list].getNext());
		cArray[list].setNext(p);
		cArray[p].setData(data);
	}

	public CNode<T> delete(T data, int list) {
		int p = findPrevious(data, list);

		if (p != -1) {
			int c = cArray[p].getNext();
			CNode<T> temp = cArray[c];
			cArray[p].setNext(temp.getNext());
			free(c);
		}

		return null;
	}

	public CNode<T> deleteAtHead(int list) {
		CNode<T> temp = cArray[cArray[list].getNext()];
		cArray[list].setNext(cArray[cArray[list].getNext()].getNext());

		return temp;
	}

	public CNode<T> getHead(int list) {
		// if empty????
		return cArray[cArray[list].getNext()];
	}

	public void clearList(int list) {
		cArray[list].setNext(0);
	}

	public int createList() {
		int l = malloc();
		if (l == 0)
			System.out.println("Error: Out of space!!!");
		else
			cArray[l] = new CNode<>(null, 0);
		return l;
	}

	public int find(T data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			l = cArray[l].getNext();
			if (cArray[l].getData().equals(data))
				return l;
		}
		return -1; // not found
	}

	public int findPrevious(T data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			if (cArray[cArray[l].getNext()].getData().equals(data))
				return l;
			l = cArray[l].getNext();
		}
		return -1; // not found
	}

	public boolean isNull(int l) {
		return cArray[l] == null;
	}

	public boolean isEmpty(int l) {
		return cArray[l].getNext() == 0;
	}

	public boolean isLast(int p) {
		return cArray[p].getNext() == 0;
	}

	public void traversList(int l) {
		System.out.print("list_" + l + "-->");
		while (!isNull(l) && !isEmpty(l)) {
			l = cArray[l].getNext();
			System.out.print(cArray[l] + "-->");
		}
		System.out.println("null");
	}

	public void insertAtLast(int list, T data) {
		if (isNull(list)) // list not created
			return;

		int p = malloc();
		if (p == 0) {
			System.out.println("Not enough space");
			return;
		}

		while (!isNull(list) && !isEmpty(list)) {
			list = cArray[list].getNext();
		}

		cArray[p].setNext(0);
		cArray[list].setNext(p);
		cArray[p].setData(data);

	}

	private void insertAtEnd(int list, T data) {
		int p = malloc();
		if (p == 0) {
			System.out.println("Not enough space");
			return;
		}
		cArray[p].setData(data);
		cArray[p].setNext(0);

		int current = list;
		while (cArray[current].getNext() != 0) {
			current = cArray[current].getNext();
		}
		cArray[current].setNext(p);
	}
}