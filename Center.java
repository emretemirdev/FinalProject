package org.atlas;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
//KAYNAKÇA:
// HashMap vs HashSet : https://www.geeksforgeeks.org/difference-between-hashmap-and-hashset/ , https://www.baeldung.com/java-hashmap-vs-hashtable-vs-hashset , https://www.javatpoint.com/difference-between-hashmap-and-hashset-in-java
//Java Map : https://www.w3schools.com/java/java_hashmap.asp , https://www.geeksforgeeks.org/map-interface-java-examples/ , https://www.javatpoint.com/java-map , https://www.baeldung.com/java-map
//Java Set : https://www.w3schools.com/java/java_hashset.asp , https://www.geeksforgeeks.org/set-in-java/ , https://www.javatpoint.com/java-set , https://www.baeldung.com/java-set
//List : https://www.w3schools.com/java/java_arraylist.asp , https://www.geeksforgeeks.org/arraylist-in-java/ , https://www.javatpoint.com/java-arraylist , https://www.baeldung.com/java-arraylist

//bu projede list yapısını kullanmadım çünkü list yapısı, bir nesnenin bir sonraki nesneyi gösterdiği bir bağlantı listesidir.
//yani sıralı bir yapıdır. mesajlaşma nesneleri arasında sıralı bir ilişki olmadığı için list yapısını kullanmadım.
//


//Bu projeyi yaparken Map yapısını kullandım. Map yapısı, bir anahtar-değer çifti saklamama yarıyor.
//Bu sayede her mesajlaşma nesnesi için benzersiz bir kimlik numarası atanabilir
// ve bu kimlik numarası kullanılarak nesnelerin depolanması ve erişimi kolaylaştırılabilir.
//anahtarlar mesajlaşma nesnelerinin kimlikleri iken değerler nesnelerin kendisidir.

// Ayrıca, Map yapısı, bir anahtarın değerine hızlı bir şekilde erişim sağlar.= HashMap
//HashMap o(1) karmaşıklığına sahiptir. Bu, bir anahtarın değerine hızlı bir şekilde erişimeme yarar.
//Bu nedenle, bir mesajlaşma nesnesinin kimliğine göre erişim sağlamak için HashMap kullanıyorum.


//Kayıt mesajı gönderirken hashSet kullandım,  yeni Oluşturduğum iletişimcilerin kayıt mesajlarını eski ileticilerin almasını istemediğim için
//mesajın gönderildiği mesajlaşma nesnesinin kimliği olan senderID hariç, tüm alıcıların kimliklerini içeren bir Set oluşturdum.
// Bu yüzden communicationObjects.keySet() ifadesi kullandım ve tüm mesajlaşma nesnelerinin kimliklerini içeren bir Set döndürdüm.

//HashSet'te veri saklama : e.g. {1,2,3,4,5}
//HashMap'te veri saklama : e.g. {a -> 1, b -> 2, c -> 2, d -> 1}



class Center {
    private Map<Integer, Object> communicationObjects; //Mesajlaşma nesnelerinin depolandığı bir "Map" nesnesidir. Bu nesne, nesnelerin "ID" numaralarına göre erişilebilir.
    public Center() {
        this.communicationObjects = new HashMap<>();
    }
    public void register(Object communicationObject, int id) //Bu yöntem, yeni bir mesajlaşma nesnesi kaydetmek için kullanılır.
    // Bu metoda verilen "communicationObject" parametresi, kaydedilecek
    // mesajlaşma nesnesidir. "id" parametresi ise bu nesnenin benzersiz tanımlayıcısıdır.

    {
        communicationObjects.put(id, communicationObject);// Bu yöntem , yeni bir mesajlaşma nesnesinin sisteme katıldığına dair bir mesaj yayınlar.
        String message = "Selamlar bende aranıza yeni katıldım İşte Bilgilerim: " + communicationObject.getClass().getSimpleName() + "(ID=" + id + ")";
        broadcastMessage(id, message);
    }

    private void broadcastMessage(int senderID, String message) // Bu yöntem, bir mesajı tüm mesajlaşma nesnelerine göndermek için kullanılır.
    // "senderID" parametresi, mesajı gönderen mesajlaşma nesnesinin kimliğidir. "message" parametresi ise gönderilecek mesajdır.
    {
        Set<Integer> receiverIDs = new HashSet<>(communicationObjects.keySet()); //communicationObjects.keySet() ifadesi,
        // tüm mesajlaşma nesnelerinin kimliklerini içeren bir "Set" döndürür.
        // Bu kimlikler, daha sonra mesajın gönderildiği mesajlaşma nesnesinin kimliği olan "senderID" hariç,
        // tüm alıcılar için bir "receiverIDs" kümesine kopyalanır.

        receiverIDs.remove(senderID); //Bu ifade, "broadcastMessage" yönteminde mesajı alacak alıcıların kimliklerini belirlemek için kullanılan
        // "receiverIDs" kümesinden, mesajın gönderildiği mesajlaşma nesnesinin kimliği olan "senderID" hariç, bir öğe kaldırır.
       // Yani, "receiverIDs" kümesinde yalnızca mesajın gönderilmediği tüm mesajlaşma nesnelerinin kimlikleri kalır.
        // Bu, mesajın gönderilmediği mesajlaşma nesnelerine mesaj göndermek istemediğimiz için gereklidir.

        for (int receiverID : receiverIDs) //for-each döngüsü,
            // "receiverIDs" kümesindeki tüm alıcıların kimliklerini alır. "receiverIDs" kümesindeki her bir eleman için bir kez çalışır.
        {
            saveMessage(senderID, receiverID, message);
        }
    }
    public void sendMessage(String message) {
        String[] parts = message.split(":"); //message parametresini : karakterine göre parçalıyoruz.
        // Bu şekilde mesajı gönderenin kimliği, alıcının kimliği ve mesaj içeriği parçalara ayrılır.
        int srcID = Integer.parseInt(parts[0]); //Gönderenin kimliğini parts dizisinin
        // ilk elemanından alıyoruz. Ancak, bu değer bir String olduğundan, parseInt() metodu kullanarak int türüne dönüştürüyoruz.

        String destID = parts[1]; //Alıcının kimliğini parts dizisinin ikinci elemanından alıyoruz.
        String content = parts[2];  //Mesaj içeriğini parts dizisinin üçüncü elemanından alıyoruz.

        if (destID.equals("all")) // Eğer alıcı "all" ise, tüm alıcılara mesaj göndeririz.
        {
            for (int receiverID : communicationObjects.keySet()) //for-each döngüsü,
                // "communicationObjects.keySet()" ifadesi, tüm mesajlaşma nesnelerinin kimliklerini içeren bir "Set" döndürür.
                // "communicationObjects.keySet()" ifadesi, "broadcastMessage" yöntemindekiyle aynıdır.
                // Bu döngü, "communicationObjects.keySet()" ifadesinden dönen tüm kimlikler için bir kez çalışır.
                //keySet() yöntemi, bir Map nesnesinin tüm anahtarlarını içeren bir Set döndürür.
                //
            {
                if (receiverID != srcID) //Eğer alıcı gönderenle aynı kişi değilse
                {
                    saveMessage(srcID, receiverID, content); //Mesajı kaydetmek için "saveMessage" yöntemini çağırırız.
                }
            }
        } else //Eğer alıcı "all" değilse, alıcıların kimliklerini "," karakterine göre parçalıyoruz.
        {
            String[] receiverIDs = destID.split(","); //split ifadesi ile alıcıların kimliklerini "," karakterine göre parçalıyoruz.
            for (String receiverID : receiverIDs) //Her bir alıcı kimliği için döngü oluşturuyoruz.
            {
                int id = Integer.parseInt(receiverID); //Alıcının kimliğini String olarak aldık, ancak saveMessage()
                // metodunun ikinci parametresi için int türüne ihtiyacımız var.
                // Bu nedenle, parseInt() metodu kullanarak int türüne dönüştürüyoruz.

                if (communicationObjects.containsKey(id) && id != srcID) //Eğer communicationObjects listesi belirtilen
                    // alıcı kimliğini içeriyorsa ve alıcı gönderenle aynı kişi değilse:
                {
                    saveMessage(srcID, id, content); //aveMessage() metodunu çağırarak mesajı kaydediyoruz.
                    // Bu, gönderenin kimliği, alıcının kimliği ve mesaj içeriğini kullanarak
                    // kaydedilecek olan dosyaya yazılan metni oluşturur.
                }
            }
        }
    }

    private void saveMessage(int srcID, int destID, String content) {
        String senderClassName = communicationObjects.get(srcID).getClass().getSimpleName();//Bu ifade, "communicationObjects" nesnesinden,
        // "srcID" parametresi ile belirtilen mesajlaşma nesnesini alır.
        // "getClass()" yöntemi, bir nesnenin sınıfını döndürür. "getSimpleName()" yöntemi, bir sınıfın adını döndürür.
        String receiverClassName = communicationObjects.get(destID).getClass().getSimpleName();
        String senderMessage = senderClassName + "(ID=" + srcID + ")'dan " + receiverClassName + "(ID=" + destID + ")'e gönderilen mesaj: " + content;
        //Bu ifade, mesajın gönderildiği mesajlaşma nesnesinin kimliği olan "srcID" ve mesajın alındığı mesajlaşma nesnesinin kimliği olan "destID"
        // ile birlikte, mesajın içeriğini içeren bir mesaj oluşturur.

        String receiverMessage = receiverClassName + "(ID=" + destID + ")'e " + senderClassName + "(ID=" + srcID + ") tarafından gönderilen mesaj: " + content;

        try {
            FileWriter senderFileWriter = new FileWriter(senderClassName + ".txt", true);
            //Göndericinin sınıf adına göre bir dosya oluşturuyoruz.
            // Dosya adı sınıf adı + .txt şeklinde. İkinci parametre olarak true veriyoruz,
            // böylece dosyaya yazdığımız her şeyi dosyanın sonuna ekleriz. Eğer False olsaydı,
            // dosyayı her açtığımızda dosyanın içeriği silinir ve yeniden yazılırdı.

            FileWriter receiverFileWriter = new FileWriter(receiverClassName + ".txt", true);
            senderFileWriter.write(senderMessage + "\n"); //Göndericinin kaydedilecek olan dosyaya yazdığı mesajı dosyaya yazıyoruz. "\n" ifadesi,
            // bir alt satıra geçmemizi sağlar.
            receiverFileWriter.write(receiverMessage + "\n"); //Alıcının kaydedilecek olan dosyaya yazdığı mesajı dosyaya yazıyoruz. "\n" ifadesi,
            // bir alt satıra geçmemizi sağlar.


            senderFileWriter.close();//Dosyayı kapatıyoruz.
            receiverFileWriter.close();//Dosyayı kapatıyoruz.
        } catch (IOException e)//Eğer dosya işlemlerinde bir hata olursa bu blok çalışır.
        {
            System.out.println("Dosya yazma hatası! Lütfen dosyaların mevcut olduğundan emin olun veya Emre Temir ile iletişime geçin.");
            e.printStackTrace();//Hata mesajını yazdırır.
        }
    }
}
