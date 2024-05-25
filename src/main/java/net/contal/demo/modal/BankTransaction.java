package net.contal.demo.modal;

import javax.persistence.*;
import java.util.Date;

//TODO complete this class
@Entity
@Table(name = "BANK_TRANSACTION")
public class BankTransaction {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private CustomerAccount customerAccount;
    //TODO implement extra properties and create  setter and getter for each
    @Column(name = "TRANSACTION_AMOUNT")
    private double transactionAmount;
    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
    }
}
