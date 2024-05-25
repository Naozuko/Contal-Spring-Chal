package net.contal.demo;

import java.util.Random;

public abstract class AccountNumberUtil {


    /**
     * TODO implement this function
     * this function should generate random integer number and return
     * @return random integer
     */
    public static int generateAccountNumber(){
        //TODO help use Random  class part of java SDK
        Random random = new Random();

        return random.nextInt(90000000) + 10000000; // 8 digit between 1... to 9...
    }

}
