package io.cucumber.shouty;

public class AtmMachine {

    public AtmMachine(int balance) {
        this.balance = balance;
    }

    private int balance;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void withDraw(Account account, int requestedAmount) {

        if ( account.getBalance() < requestedAmount )
            throw new IllegalStateException("Not enough balance");

        this.balance = this.balance - requestedAmount;
        account.setBalance( account.getBalance() - requestedAmount );

    }
}
