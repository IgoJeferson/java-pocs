package io.cucumber.shouty;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private ArrayList<String> messagesHeard = new ArrayList<>();
    private Integer currentDistance;

    public void moveTo(Integer distance) {
        this.currentDistance = distance;
    }

    public void shout(String message) {
    }

    public void addMessagesHeard(String message){
        if ( currentDistance <= 15)
            messagesHeard.add(message);
    }

    public List<String> getMessagesHeard() {
        return messagesHeard;
    }
}
