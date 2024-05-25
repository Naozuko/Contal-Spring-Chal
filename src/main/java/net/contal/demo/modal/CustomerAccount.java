package net.contal.demo.modal;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CUSTOMER_ACCOUNT")
public class CustomerAccount {
    @Id
    @GeneratedValue
    private long id;
    @OneToMany
    private List<BankTransaction> transactions;

    //TODO implement extra properties and create  setter and getter for each
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "ACCOUNT_NUMBER")
    private int accountNumber;

    @Column(name = "ACCOUNT_BALANCE")
    private double accountBalance;
    //Set getter and setters
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public List<BankTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<BankTransaction> transactions) {
        this.transactions = transactions;
    }
}
