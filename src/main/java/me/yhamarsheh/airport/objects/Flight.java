package me.yhamarsheh.airport.objects;

import me.yhamarsheh.airport.structure.LinkedList;
import me.yhamarsheh.airport.structure.Queue;
import me.yhamarsheh.airport.structure.Stack;

public class Flight implements Comparable<Flight> {

    private int id;
    private String destination;
    private boolean activeStatus;
    private Queue<Passenger> vipQueue;
    private Queue<Passenger> regularQueue;
    private Stack<Operation> undoOperations;
    private Stack<Operation> redoOperations;
    private LinkedList<Passenger> boardedPassengers;
    private LinkedList<Passenger> cancelledPassengers;

    public Flight(int id, String destination, boolean activeStatus) {
        this.id = id;
        this.destination = destination;
        this.activeStatus = activeStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Queue<Passenger> getVipQueue() {
        return vipQueue;
    }

    public Queue<Passenger> getRegularQueue() {
        return regularQueue;
    }

    public Stack<Operation> getUndoOperations() {
        return undoOperations;
    }

    public Stack<Operation> getRedoOperations() {
        return redoOperations;
    }

    public LinkedList<Passenger> getBoardedPassengers() {
        return boardedPassengers;
    }

    public LinkedList<Passenger> getCancelledPassengers() {
        return cancelledPassengers;
    }

    public void addPassenger(Passenger passenger) {
        if (passenger == null) return;

        if (passenger.isVipMember()) {
            vipQueue.enqueue(passenger);
            undoOperations.push(new Operation("Add VIP Passenger", passenger.getName() + " | " + getId() + " | Added VIP passenger " + passenger.getName() + " to Flight " + getId()) {
                @Override
                public void undo() {
                    removePassenger(passenger);
                }

                @Override
                public void redo() {
                    vipQueue.enqueue(passenger);
                }
            });
        } else {
            regularQueue.enqueue(passenger);
            undoOperations.push(new Operation("Add Regular Passenger", passenger.getName() + " | " + getId() + " | Added regular passenger " + passenger.getName() + " to Flight " + getId()) {
                @Override
                public void undo() {
                    removePassenger(passenger);
                }

                @Override
                public void redo() {
                    regularQueue.enqueue(passenger);
                }
            });
        }
    }

    public void removePassenger(Passenger passenger) {
        if (passenger == null) return;

        if (passenger.isVipMember()) {
            vipQueue.deleteItem(passenger);
            undoOperations.push(new Operation("Remove VIP Passenger", passenger.getName() + " | " + getId() + " | Removed VIP passenger " + passenger.getName() + " from Flight " + getId()) {

                @Override
                public void undo() {
                    vipQueue.enqueue(passenger);
                }

                @Override
                public void redo() {
                    vipQueue.deleteItem(passenger);
                }
            });
        } else {
            regularQueue.deleteItem(passenger);
            undoOperations.push(new Operation("Remove Regular Passenger", passenger.getName() + " | " + getId() + " | Removed regular passenger " + passenger.getName() + " from Flight " + getId()) {

                @Override
                public void undo() {
                    regularQueue.enqueue(passenger);
                }

                @Override
                public void redo() {
                    regularQueue.deleteItem(passenger);
                }
            });
        }
    }

    @Override
    public int compareTo(Flight o) {
        return Integer.compare(this.getId(), o.getId());
    }

    @Override
    public String toString() {
        return "Flight [id=" + id + ", destination=" + destination + ", activeStatus=" + activeStatus + "]";
    }
}
