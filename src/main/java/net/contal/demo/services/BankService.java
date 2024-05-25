package net.contal.demo.services;

import net.contal.demo.AccountNumberUtil;
import net.contal.demo.DbUtils;
import net.contal.demo.modal.BankTransaction;
import net.contal.demo.modal.CustomerAccount;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO complete this service class
 * TODO use BankServiceTest class
 */
@Service
@Transactional
public class BankService {

    //USE this class to access database , you can call openASession to access database
    private final DbUtils dbUtils;
    @Autowired
    public BankService(DbUtils dbUtils) {
        this.dbUtils = dbUtils;
    }


    /**
     * TODO implement the rest , populate require fields for CustomAccount (Generate Back account by using AccountNumberUtil )
     * Save customAccount to database
     * return AccountNumber
     * @param customerAccount populate this (firstName , lastName ) already provided
     * @return accountNumber
     */
    public String createAnAccount(CustomerAccount customerAccount){
            // TODO implement the rest
        int accountNumber = AccountNumberUtil.generateAccountNumber();
        customerAccount.setAccountNumber(accountNumber);

        try (Session session = dbUtils.openASession()) {
            session.saveOrUpdate(customerAccount);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //TODO return bank account number
        return String.valueOf(accountNumber);
    }


    /**
     * TODO implement this functions
     * @param accountNumber target account number
     * @param amount amount to register as transaction
     * @return boolean , if added as transaction
     */
    public boolean addTransactions(int accountNumber , Double amount){

        /**
         *TODO
         * Find and account by using accountNumber (Only write  the query in hql String  )
         * create Transaction for account with provided  amount
         * return true if added , return false if account dont exist , or amount is null
         */

        /** TODO write Query to get account by number un comment section below , catch query   */
        if (amount == null) {
            return false;
        }

        try (Session session = dbUtils.openASession()) {
            String hql = "FROM CustomerAccount WHERE accountNumber = :accountNumber";
            CustomerAccount account = session.createQuery(hql, CustomerAccount.class)
                    .setParameter("accountNumber", accountNumber)
                    .uniqueResult();

            if (account == null) {
                return false;
            }


            // Creating new transaction
            BankTransaction transaction = new BankTransaction();
            transaction.setCustomerAccount(account);
            transaction.setTransactionAmount(amount);
            transaction.setTransactionDate(new Date());

            session.save(transaction);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * TODO implement this functions
     * @param accountNumber target account
     * @return account balance
     */
    public double getBalance(int accountNumber){

        /**
         *TODO
         *  find the account by this account Number
         *  sum total of transactions belong to account
         *  return sum of amount
         *
         */

        String hql = "FROM CustomerAccount WHERE accountNumber = :accountNumber";
        CustomerAccount account = this.dbUtils.openASession()
                .createQuery(hql,CustomerAccount.class)
                .setParameter("accountNumber",accountNumber)
                .uniqueResult();

        return account.getAccountBalance();
    }


    /**
     * TODO implement this functions
     * ADVANCE TASK
     * @param accountNumber accountNumber
     * @return HashMap [key: date , value: double]
     */
    public Map<Date,Double> getDateBalance(int accountNumber){
        /**
         *TODO
         * get all bank Transactions for this account number
         * Create map , Each Entry should hold a Date as a key and value as balance on key date from start of account
         * Example data [01/01/1992 , 2000$] balance 2000$ that date 01/01/1992
         */

        String hql = "FROM CustomerAccount WHERE accountNumber = :accountNumber";
        CustomerAccount account;

        try (Session session = dbUtils.openASession()) {
            account = session.createQuery(hql, CustomerAccount.class)
                    .setParameter("accountNumber", accountNumber)
                    .uniqueResult();

            if (account == null) {
                throw new RuntimeException("Account not found for account number: " + accountNumber);
            }

            String transactionHql = "FROM BankTransaction WHERE customerAccount.id = :accountId ORDER BY transactionDate";
            List<BankTransaction> transactions = session.createQuery(transactionHql, BankTransaction.class)
                    .setParameter("accountId", account.getId())
                    .getResultList();

            Map<Date, Double> dateBalanceMap = new HashMap<>();
            double runningBalance = 0.0;

            for (BankTransaction transaction : transactions) {
                runningBalance += transaction.getTransactionAmount();
                dateBalanceMap.put(transaction.getTransactionDate(), runningBalance);
            }

            session.getTransaction().commit();
            return dateBalanceMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving transactions", e);
        }
    }


}
