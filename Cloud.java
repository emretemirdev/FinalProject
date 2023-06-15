package org.atlas;

public class Cloud {
    private int id;

    public Cloud()
    {
        this.id = IDGenerator.generateID(); // ID üreticisinden ID alıyor.
    }
    public int getId() {
        return id;
    }
}