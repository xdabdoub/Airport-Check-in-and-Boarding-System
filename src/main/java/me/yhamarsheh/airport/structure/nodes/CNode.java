package me.yhamarsheh.airport.structure.nodes;


public class CNode<T> {

    private int next;
    private T data;

    public CNode(T data, int next) {
        this.data = data;
        this.next = next;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
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
    	return "[" + data + ", " + next + "]";
    }

}