package vn.rideshare.model;

public enum EntityStatus {
    ACTIVE(1),
    INACTIVE(0),
    UNKNOWN(2),

    PENDING(4),
    EXPIRED(3);

    private int value;
    EntityStatus(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
