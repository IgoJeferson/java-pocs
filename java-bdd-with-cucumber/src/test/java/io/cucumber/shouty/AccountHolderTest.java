package io.cucumber.shouty;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountHolderTest {

    private Account account;
    private AtmMachine atmMachine = new AtmMachine(100);
    private int requestedAmount;
    private int atmMachineOldBalance;

    @Given("the account balance is $100")
    public void the_account_balance_is_$100() {
        account = new Account();
        account.setBalance(100);
    }

    @Given("the card is valid")
    public void the_card_is_valid() {
        account.setValidCard(true);
    }

    @When("the Account Holder requests $20")
    public void the_Account_Holder_requests_$20() {
        atmMachineOldBalance = this.atmMachine.getBalance();
        requestedAmount = 20;

        atmMachine.withDraw(account, requestedAmount);
    }

    @Then("the machine contains enough money")
    public void the_machine_contains_enough_money() {
        assertTrue(requestedAmount < atmMachine.getBalance());
    }

    @Then("the ATM should dispense $20")
    public void the_ATM_should_dispense_$20() {
        assertTrue( atmMachineOldBalance - atmMachine.getBalance() == requestedAmount );
    }

    @Then("the account balance should be $80")
    public void the_account_balance_should_be_$80() {
        assertEquals(80, account.getBalance());
    }

}
