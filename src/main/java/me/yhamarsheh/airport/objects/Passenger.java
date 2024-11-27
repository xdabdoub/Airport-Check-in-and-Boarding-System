package me.yhamarsheh.airport.objects;

public class Passenger implements Comparable<Passenger> {

    private String id;
    private String name;
    private int flightId;
    private boolean vipStatus;

    public Passenger(String id, String name, int flightId, boolean vipStatus) {
        this.id = id;
        this.name = name;
        this.flightId = flightId;
        this.vipStatus = vipStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public boolean isVipMember() {
        return vipStatus;
    }

    public void setVipStatus(boolean vipStatus) {
        this.vipStatus = vipStatus;
    }

    @Override
    public int compareTo(Passenger o) {
        if (this.vipStatus == o.isVipMember())
            return this.getId().compareTo(o.getId());

        if (this.vipStatus && !o.isVipMember()) {
            return -1;
        } else if (!this.vipStatus && o.isVipMember()) {
            return 1;
        }

        return 0;
    }
}
