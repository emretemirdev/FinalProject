package org.atlas;

public class IDGenerator {
    private static int id = 0; //başlangıç değeri için 0 verildi.
    public static int generateID()
    {
        return id++;
    }
}

