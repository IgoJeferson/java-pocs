package com.github.igojeferson.protobuf;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        String email = "igojeferson@gmail.com";
        int id = new Random().nextInt();
        String name = "Igo Jeferson";

        Person person1 = Person.newBuilder()
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

        AddressBook addressBook = AddressBook.newBuilder()
                .addPeople(person1)
                .build();

        addressBook.getPeopleList().forEach(System.out::println);

    }
}
