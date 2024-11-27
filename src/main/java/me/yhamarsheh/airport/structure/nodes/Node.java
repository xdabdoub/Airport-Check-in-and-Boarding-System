package me.yhamarsheh.airport.structure.nodes;

public class Node<T extends Comparable<T>> implements Comparable<T>{

    private T data;
    private Node<T> next;
    public Node(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public int compareTo(T o) {
        return this.data.compareTo(o);
    }


}
