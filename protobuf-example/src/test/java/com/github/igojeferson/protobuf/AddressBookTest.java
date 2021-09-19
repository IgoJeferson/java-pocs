package com.github.igojeferson.protobuf;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressBookTest {

    private String filePath = "src/main/resources/textfiles/myfile.txt";

    private static Person person;

    private static String email = "igojeferson@gmail.com";
    private static int id = new Random().nextInt();
    private static String name = "Igo Jeferson";

    @BeforeAll
    static void setup(){
        person = Person.newBuilder()
                .setId(id)
                .setName(name)
                .setEmail(email)
                .setGender(Person.Gender.MASCULINE)
                .addPhones(Person.PhoneNumber.newBuilder()
                        .setNumber("+310688996655")
                        .setType(Person.PhoneType.WORK)
                        .build()
                )
                .build();
    }

    @Test
    void testSerialization() throws IOException {

        AddressBook addressBook = AddressBook.newBuilder()
                .addPeople(person)
                .build();

        FileOutputStream fos = new FileOutputStream(filePath);
        addressBook.writeTo(fos);

        AddressBook deserialized
                = AddressBook.newBuilder()
                .mergeFrom(new FileInputStream(filePath)).build();

        assertEquals(deserialized.getPeople(0).getEmail(), email);
        assertEquals(deserialized.getPeople(0).getId(), id);
        assertEquals(deserialized.getPeople(0).getName(), name);
        assertEquals(person.getPhonesList().stream().findFirst().get().getNumber(), "+310688996655");

    }

}
