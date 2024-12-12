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

        this.vipQueue = new Queue<>(30);
        this.regularQueue = new Queue<>(30);
        this.undoOperations = new Stack<>(30);
        this.redoOperations = new Stack<>(30);

        this.boardedPassengers = new LinkedList<>();
        this.cancelledPassengers = new LinkedList<>();
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

    public boolean removePassenger(Passenger passenger) {
        if (passenger == null) return false;

        if (passenger.isVipMember()) {
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
            return vipQueue.deleteItem(passenger);
        } else {
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
            return regularQueue.deleteItem(passenger);
        }
    }

    public void boardPassenger(Passenger passenger) {
        if (passenger == null) return;
        if (passenger.isVipMember()) vipQueue.deleteItem(passenger);
        else regularQueue.deleteItem(passenger);

        boardedPassengers.insert(passenger);
        undoOperations.push(new Operation("Boarding", " | " + passenger.getName() + " | " + getId() +
                " | Boarded " + passenger.getName() + " on Flight " + getId()) {
            @Override
            public void undo() {
                boardedPassengers.delete(passenger);
            }

            @Override
            public void redo() {
                boardedPassengers.insert(passenger);
            }
        });
    }

    public void cancelPassenger(Passenger passenger) {
        if (passenger == null) return;

        cancelledPassengers.insert(passenger);
        boolean foundInQueues = removePassenger(passenger);
        boolean foundInBoarded = boardedPassengers.delete(passenger);

        undoOperations.push(new Operation("Cancel", " | " + passenger.getName() + " | " +
                getId() + " | Cancelled " + passenger.getName() + " from Flight " + getId()) {
            @Override
            public void undo() {
                if (foundInBoarded) boardedPassengers.insert(passenger);
                if (foundInQueues) addPassenger(passenger);
            }

            @Override
            public void redo() {
               if (foundInBoarded) boardedPassengers.delete(passenger);
               if (foundInQueues) removePassenger(passenger);
            }
        });
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
