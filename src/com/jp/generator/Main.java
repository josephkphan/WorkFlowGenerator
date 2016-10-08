package com.jp.generator;

public class Main {

    public static void main(String[] args) {
        int numUsers, numTasks, numOrders, authorize, bodPercent, sodPercent;
        String generatorType;

        numUsers = numTasks = 10;
        numOrders = 10;
        authorize = 5;
        bodPercent = sodPercent = 50;
        generatorType = "EXCEL";

        /* Checking Variables */
        if ((bodPercent + sodPercent) >= 100)
            System.out.println("Please put in valid bod/sod values ( bod + sod <= 100)");
        if (authorize > numUsers)
            System.out.println("Please put in valid authorize value (authorize <= numUser)");

        /* Creating Data Set */
        switch (generatorType) {

            case "CPLEX":
                new CplexText(numUsers, numTasks, numOrders, authorize, bodPercent, sodPercent);
                break;
            case "JAVA":
                new TextFiles(numUsers, numTasks, numOrders, authorize, bodPercent, sodPercent);
                break;
            case "EXCEL":
                new Excel(numUsers, numTasks, numOrders, authorize, bodPercent, sodPercent);
                break;
            default:
                System.out.println("Put in a valid Generator");
                break;
        }


    }
}
