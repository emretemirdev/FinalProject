package org.atlas;

public class Cat {
    private int id;

    public Cat() {
        this.id = IDGenerator.generateID();
    }

    public int getId() {
        return id;
    }
}