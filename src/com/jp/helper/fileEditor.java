package com.jp.helper;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;


public class fileEditor {
    ConstraintsHandler c;

    public fileEditor(ConstraintsHandler c) {
        this.c = c;
    }

    public void reduceBoD(){
        int  deleteIndex, deleteTask;
        Random random  = new Random();
        List<Integer> tmp = new ArrayList<Integer>();
        int counter = 0;
        int onePercent = c.num_tasks / 100;
        boolean found = false;
        while(counter < onePercent){
            found = false;
            tmp.clear();
            for (int i=0; i<c.num_tasks; i++) {
                if (c.binding_constraints.get(i).size() > 1) {
                    tmp.addAll(c.binding_constraints.get(i));
                    while (true) {
                        deleteIndex = random.nextInt(tmp.size());
                        if (tmp.get(deleteIndex) != i) {
                            deleteTask = tmp.get(deleteIndex);
                            for(int j : tmp){
                                if(j != deleteTask) {
                                    c.binding_constraints.get(j).remove(deleteTask);
                                    c.binding_constraints.get(deleteTask).remove(j);
                                    found = true;
                                }
                            }
                            tmp.clear();
                            counter++;
                            break;
                        }
                    }
                    if(counter >= onePercent)
                        break;

                }

            }
            if(!found) {
                System.out.println("No More Constraint");
                break;
            }
        }

    }
    public void reduceSoD(){
        int  deleteIndex, deleteTask;
        Random random  = new Random();
        List<Integer> tmp = new ArrayList<Integer>();
        int counter = 0;
        int onePercent = c.num_tasks / 100;
        boolean found = false;
        while(counter < onePercent){
            found = false;
            tmp.clear();
            for (int i=0; i<c.num_tasks; i++) {
                if (c.separating_constraints.get(i).size() > 1) {
                    tmp.addAll(c.separating_constraints.get(i));
                    while (true) {
                        deleteIndex = random.nextInt(tmp.size());
                        if (tmp.get(deleteIndex) != i) {
                            deleteTask = tmp.get(deleteIndex);
                            for(int j : tmp){
                                if(j != deleteTask) {
                                    c.separating_constraints.get(j).remove(deleteTask);
                                    c.separating_constraints.get(deleteTask).remove(j);
                                    found = true;
                                }
                            }
                            tmp.clear();
                            counter++;
                            break;
                        }
                    }
                    if(counter >= onePercent)
                        break;

                }

            }
            if(!found) {
                System.out.println("No More Constraint");

                break;
            }
        }

    }

    public void reduceCapability(){


        for (int i=0; i< c.num_users; i++){
            for (int j = 0; j<c.num_tasks; j++) {
                if(c.user_capability[i][j] > 0){
                    c.user_capability[i][j] -= c.num_tasks/2;
                }
            }
        }

    }

    public void redunceAuthorizations(){
        int onePercent = c.num_tasks / 100;
        Random random = new Random();
        int randUser;
        for (int i =0; i< c.num_tasks; i++){
            for(int j = 0; j < onePercent; j++)
            while(true){
                randUser = random.nextInt(c.num_users);
                if(c.authorizations[randUser][i] == 1){
                    c.authorizations[randUser][i] = 0;
                    break;
                }
            }
        }
    }
}
