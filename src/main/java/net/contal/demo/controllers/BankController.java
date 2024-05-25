package net.contal.demo.controllers;

import net.contal.demo.modal.CustomerAccount;
import net.contal.demo.services.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/banks")
public class BankController {
    final Logger logger = LoggerFactory.getLogger(BankController.class);
    final BankService dataService;

    public BankController(BankService dataService) {
        this.dataService = dataService;
    }


    /**
     *  TODO call properiate method in dataService to create an bank account , return generated bank account number
     * @param account {firstName:"" , lastName:"" }
     * @return bank account number
     */
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public long createBankAccount(@RequestBody CustomerAccount account){
        logger.info("{}" ,account.toString());
//TODO implement the rest
        String accountNumber = dataService.createAnAccount(account);
        return Long.parseLong(accountNumber);
    }

    /**
     *TODO call related Method from Service class to do the process
     * @param accountNumber BankAccount number
     * @param amount Amount as Transaction
     */
    @RequestMapping(method = RequestMethod.POST,value = "/transaction")
    public void addTransaction(@RequestParam("accountNumber") String accountNumber, @RequestParam("amount") Double amount){
        logger.info("Bank Account number is :{} , Transaction Amount {}",accountNumber,amount);
        //TODO implement the rest
        int intAccountNumber = Integer.parseInt(accountNumber);
        boolean result = dataService.addTransactions(intAccountNumber, amount);
        if (!result) {
            throw new RuntimeException("Transac could not be added");
        }
    }


    /**
     * TODO call related Method from Service class to do the process
     * @param accountNumber customer  bank account  number
     * @return balance
     */
    @RequestMapping(method = RequestMethod.POST,value = "/balance")
    public Double getBalance(@RequestParam("accountNumber") String accountNumber){
        logger.info("Bank Account number is :{}",accountNumber);
            //TODO implement the rest
        int intAccountNumber = Integer.parseInt(accountNumber);
        return dataService.getBalance(intAccountNumber);
    }


    /** self made
     * ADVANCED, RETRIEVE ALL TRANSACTIONS GIVEN ACCOUNT NUMBER
     *
     * @param accountNumber
     * @return all transactions
     */

    @RequestMapping(method = RequestMethod.POST, value = "/alltransaction")
    public Map<Date, Double> getDateBalance(@RequestParam("accountNumber") String accountNumber) {
        logger.info("{}", accountNumber);

        int intAccountNumber = Integer.parseInt(accountNumber);
        return dataService.getDateBalance(intAccountNumber);
    }

}
