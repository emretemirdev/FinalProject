package org.atlas;

public class Cup {
    private int id;

    public Cup() {
        this.id = IDGenerator.generateID();
    }

    public int getId() {
        return id;
    }
}