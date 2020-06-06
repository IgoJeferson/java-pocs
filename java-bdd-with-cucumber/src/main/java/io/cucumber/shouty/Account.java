package io.cucumber.shouty;

public class Account {
    private int balance;
    private boolean validCard;

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return this.balance;
    }

    public boolean isValidCard() {
        return validCard;
    }

    public void setValidCard(boolean validCard) {
        this.validCard = validCard;
    }

}
