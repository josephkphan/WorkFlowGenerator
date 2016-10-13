package com.jp.main;

import com.jp.generator.All;
import com.jp.generator.CplexText;
import com.jp.generator.Excel;
import com.jp.generator.TextFiles;

public class Main {

    public static void main(String[] args) {
        int numUsers, numTasks, numOrders, authorize, bodPercent, sodPercent;
        String generatorType;

        numUsers = 100;
       // numTasks = 2 * numUsers;
        numTasks = 200;
        numOrders = 10;
        authorize = 10;
        bodPercent = sodPercent = 10;
        generatorType = "ALL";

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
            case "ALL":
                new All(numUsers, numTasks, numOrders, authorize, bodPercent, sodPercent);
                break;
            default:
                System.out.println("Put in a valid Generator");
                break;
        }


    }
}
