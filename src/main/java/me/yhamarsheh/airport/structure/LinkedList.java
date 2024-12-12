package me.yhamarsheh.airport.structure;

import me.yhamarsheh.airport.objects.Flight;
import me.yhamarsheh.airport.structure.nodes.Node;

import java.util.Iterator;

public class LinkedList<T extends Comparable<T>> implements Iterable<T> {

    private Node<T> head;

    public LinkedList() {
        this.head = null;
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getFirst() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    public void insert(T obj) {
        Node<T> newNode = new Node<>(obj);
        if (head == null) {
            head = newNode; // Case 0: To insert a node at the beginning of a
            // linked list
            return;
        }

        Node<T> current = head;
        Node<T> prev = null;
        for (prev = null, current = head; (current != null)
                && (newNode.getData().compareTo(current.getData()) < 0); prev = current, current = (Node<T>) current.getNext())
            ;

        if (current == head) { // Case 1: Add at head
            newNode.setNext(head);
            head = newNode;
            return;
        }

        if (current == null) { // Case 3: Add at the end
            newNode.setNext(current);
            prev.setNext(newNode);
            return;
        }

        newNode.setNext(current);
        prev.setNext(newNode);
    }

    public void insertAtHead(T data) {
        Node<T> newNode = new Node<>(data);

        newNode.setNext(head);
        head = newNode;
    }

    public boolean delete(T obj) {
        if (head == null) return false;
        if (head.getData().compareTo(obj) == 0) {
            head = (Node<T>) head.getNext();
            return true;
        }

        Node<T> curr = head;
        Node<T> prev = null;
        while (curr != null) {
            if (curr.getData().compareTo(obj) == 0) {
                prev.setNext(curr.getNext());
                return true;
            }

            curr = (Node<T>) curr.getNext();
            prev = curr;
        }

        return false;
    }

    public T next(T obj) {
        if (head == null) return null;
        for (Node<T> curr = head; curr != null; curr = curr.getNext()) {
            if (curr.getData().compareTo(obj) == 0) {
                if (curr.getNext() == null) return null;
                return curr.getNext().getData();
            }
        }

        return null;
    }

    public T previous(T obj) {
        if (head == null) return null;

        Node<T> curr = head;
        Node<T> prev = null;

        while (curr != null) {
            if (curr.getData().compareTo(obj) == 0) {
                if (prev == null) return null;
                return prev.getData();
            }
            prev = curr;
            curr = curr.getNext();
        }

        return null;
    }



    public void removeDuplicates() {
        Node<T> current = head;
        while (current != null && current.getNext() != null) {
            if (current.getData().compareTo(current.getNext().getData()) == 0)
                current.setNext(current.getNext().getNext());
            else current = (Node<T>) current.getNext();
        }
    }

    public Node<T> getAtFromLast(int i) {
        Node<T> current = head;
        while (current != null) {
            Node<T> check = current;
            for (int j = 0; j < i; j++) {
                check = (Node<T>) check.getNext();
                if (check == null && i != j) continue;
            }

            if (check == null) {
                return current;
            }

            current = (Node<T>) current.getNext();
        }

        return null;
    }

    public Node<T> find(T obj) {
        Node<T> curr = head;
        while (curr != null) {
            if (curr.getData().compareTo(obj) == 0) // if (curr.getData().equals(data))
                return curr;
            curr = (Node<T>) curr.getNext();
        }

        return null;
    }

    public Node<T> findRecursively(Node<T> current, T obj) {
        if (current == null) return null;
        if (current.getData().compareTo(obj) > 0) return null;
        if (current.getData().compareTo(obj) == 0) return current;

        return findRecursively((Node<T>) current.getNext(), obj);
    }

    public Node<T> getAtRecursively(int index) {
        if (index > size() || index < 0) throw new IndexOutOfBoundsException();

        return getAtRecursively(head, 0, index);
    }

    private Node<T> getAtRecursively(Node<T> current, int i, int index) {
        if (current == null) return null;
        if (i > index) return null;
        if (i == index) return current;

        return getAtRecursively((Node<T>) current.getNext(), ++i, index);
    }

    public int getFrequencySorted(T data) {
        return getFrequencySorted(data, head);
    }

    private int getFrequencySorted(T data, Node<T> current) {
        if (current == null) return 0;
        if (current.getData().compareTo(data) > 0) return 0;
        if (current.getData().compareTo(data) != 0) return 0;

        return 1 + getFrequencySorted(data, (Node<T>) current.getNext());
    }

    public int getFrequencyNotSorted(T data) {
        return getFrequencyNotSorted(data, head);
    }

    private int getFrequencyNotSorted(T data, Node<T> current) {
        if (current == null) return 0;
        return ((current.getData().compareTo(data) != 0) ? 0 : 1) + getFrequencyNotSorted(data, (Node<T>) current.getNext());
    }


    public void print() {
        Node<T> current = head;
        System.out.print("Head -> ");
        while (current != null) {
            System.out.print(current.toString());
            current = (Node<T>) current.getNext();
        }

        System.out.println("Null");
    }

    public void clear() {
        head = null;
    }

    public int size() {
        int length = 0;
        Node<T> curr = head;
        while (curr != null) {
            length++;
            curr = (Node<T>) curr.getNext();
        }
        return length;
    }

    public int lengthRecursively() {
        return lengthRecursively(head);
    }

    private int lengthRecursively(Node<T> current) {
        if (current == null) return 0;

        return 1 + lengthRecursively((Node<T>) current.getNext());
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        private Node<T> current = head;

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return current.getNext() != null;
        }

        @Override
        public T next() {
            T data = current.getData();
            current = current.getNext();
            return data;
        }

    }

}