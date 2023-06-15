package org.atlas;

public class Main {
    public static void main(String[] args) {
        Center center = new Center();


        Cat cat = new Cat();
        Car car = new Car();
        Cup cup = new Cup();
        Cloud cloud = new Cloud();

        // İletişim nesneleri kaydı
        center.register(cat, cat.getId());
        center.register(car, car.getId());
        center.register(cup, cup.getId());
        center.register(cloud, cloud.getId());

        center.sendMessage("0:2:Merhaba arkadaşlar!"); // Kedi, Arabaya bir mesaj gönderiyor.
        center.sendMessage("1:all:Merhaba nasılsın burnun kapıya kısılsın!"); // Kedi, tüm iletişim nesnelerine bir yayın mesajı gönderiyor.
        center.sendMessage("3:1:Selam!"); // Bardak, Kedi'ye bir mesaj gönderiyor.
        center.sendMessage("4:all:Herkese merhaba!"); // Bulut, tüm iletişim nesnelerine bir mesaj gönderiyor.
    }
}