package org.atlas;

public class Car {
    private int id;

    public Car() {
        this.id = IDGenerator.generateID();
    }

    public int getId() {
        return id;
    }
}
