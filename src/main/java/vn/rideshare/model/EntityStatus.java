package vn.rideshare.model;

public enum EntityStatus {
    UNKNOWN(0),
    PENDING(1),
    PREPARE(2),
    ACTIVE(3),
    INACTIVE(4),
    EXPIRED(5),
    DISABLE(6);

    private int value;
    EntityStatus(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
