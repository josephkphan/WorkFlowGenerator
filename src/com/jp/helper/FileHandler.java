package com.jp.helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by jphan on 10/8/16.
 */
public class FileHandler {
    private ConstraintsHandler c;

    public FileHandler(ConstraintsHandler c) {
        this.c = c;
    }

    public void writeCplexModFile(){
        write_file();
    }

    public void writeJavaDataSets(){
        write_avg_file();
        write_bc_file();
        write_sc_file();
        write_ua_file();
        write_uc_file();
    }

    private void write_file(){
        // The name of the file to open
        String fileName = "data.txt";
        String numberAsString;
        try {
            // Assume default encoding.
            FileWriter fileWriter =
                    new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);
            //**********************sizes************************//

            //**********************avg************************//
            bufferedWriter.write("u = " +Integer.toString(c.num_users-1) + ";" );
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("t = " + Integer.toString(c.num_tasks-1) + ";");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            // Note that write() does not automatically
            // append a newline character.

            bufferedWriter.write("taskDemands = ");
            bufferedWriter.newLine();
            bufferedWriter.write("[ ");
            bufferedWriter.newLine();
            for (int j=0; j<c.num_tasks; j++){
                numberAsString = Integer.toString(c.task_orders[j]);
                bufferedWriter.write(numberAsString);
                if(j!=c.num_tasks-1)
                    bufferedWriter.write(", ");
            }
            bufferedWriter.write("]; ");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            //**********************BoD************************//
            bufferedWriter.write("binding = ");
            bufferedWriter.newLine();
            bufferedWriter.write("[ ");
            bufferedWriter.newLine();
            for(int i=0; i<c.num_tasks; i++){
                bufferedWriter.write("[ ");
                for(int j=0; j<c.num_tasks; j++){
                    bufferedWriter.write(Integer.toString(c.bod[i][j]));
                    if(j!=c.num_tasks-1)
                        bufferedWriter.write(", ");

                }
                bufferedWriter.write("] ");
                if(i!=c.num_tasks-1)
                    bufferedWriter.write(", ");
                bufferedWriter.newLine();
            }
            bufferedWriter.write("]; ");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            //**********************SoD************************//
            bufferedWriter.write("separation = ");
            bufferedWriter.newLine();
            bufferedWriter.write("[ ");
            bufferedWriter.newLine();
            for(int i=0; i<c.num_tasks; i++){
                bufferedWriter.write("[ ");
                for(int j=0; j<c.num_tasks; j++){
                    bufferedWriter.write(Integer.toString(c.sod[i][j]));
                    if(j!=c.num_tasks-1)
                        bufferedWriter.write(", ");

                }
                bufferedWriter.write("] ");
                if(i!=c.num_tasks-1)
                    bufferedWriter.write(", ");
                bufferedWriter.newLine();
            }
            bufferedWriter.write("]; ");
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            //**********************authorize************************//
            bufferedWriter.write("authorizations = ");
            bufferedWriter.newLine();
            bufferedWriter.write("[ ");
            bufferedWriter.newLine();
            for(int i=0; i<c.num_users; i++){
                bufferedWriter.write("[ ");
                for (int j=0; j<c.num_tasks; j++){
                    numberAsString = Integer.toString(c.authorizations[i][j]);
                    bufferedWriter.write(numberAsString);
                    if(j!=c.num_tasks-1)
                        bufferedWriter.write(", ");
                }
                bufferedWriter.write("]");
                if(i!=c.num_users-1)
                    bufferedWriter.write(", ");
                bufferedWriter.newLine();
            }
            bufferedWriter.write("];");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            //**********************uc************************//
            bufferedWriter.write("userCapability = ");
            bufferedWriter.newLine();
            bufferedWriter.write("[");
            bufferedWriter.newLine();
            for(int i=0; i<c.num_users; i++){
                bufferedWriter.write("[");
                for (int j=0; j<c.num_tasks; j++){
                    numberAsString = Integer.toString(c.user_capability[i][j]);
                    bufferedWriter.write(numberAsString);
                    if(j!=c.num_tasks-1)
                        bufferedWriter.write(", ");
                }
                bufferedWriter.write("] ");
                if(i!=c.num_users-1)
                    bufferedWriter.write(", ");
                bufferedWriter.newLine();
            }
            bufferedWriter.write("];");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '"
                            + fileName + "'");
        }


    }

    private void write_avg_file(){
        // The name of the file to open
        String fileName = "avg.txt";
        String numberAsString;
        try {
            // Assume default encoding.
            java.io.FileWriter fileWriter =
                    new java.io.FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.

            for (int j=0; j<c.num_tasks; j++){
                numberAsString = Integer.toString(c.task_orders[j]);
                bufferedWriter.write(numberAsString);
                if(j!=c.num_tasks-1)
                    bufferedWriter.write(" ");
            }

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '"
                            + fileName + "'");
        }
    }
    private void write_ua_file(){
        // The name of the file to open
        String fileName = "ua.txt";
        String numberAsString;
        try {
            // Assume default encoding.
            java.io.FileWriter fileWriter =
                    new java.io.FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            for(int i=0; i<c.num_users; i++){
                for (int j=0; j<c.num_tasks; j++){
                    if(c.authorizations[i][j]==0)
                        continue;
                    numberAsString = Integer.toString(j);
                    bufferedWriter.write(numberAsString);
                    if(j!=c.num_tasks-1)
                        bufferedWriter.write(" ");
                }
                bufferedWriter.newLine();
            }

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '"
                            + fileName + "'");
        }
    }

    private void write_uc_file(){
        // The name of the file to open
        String fileName = "uc.txt";
        String numberAsString;
        try {
            // Assume default encoding.
            java.io.FileWriter fileWriter =
                    new java.io.FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            for(int i=0; i<c.num_users; i++){
                for (int j=0; j<c.num_tasks; j++){
                    if(c.user_capability[i][j]==0)
                        continue;
                    numberAsString = Integer.toString(c.user_capability[i][j]);
                    bufferedWriter.write(numberAsString);
                    if(j!=c.num_tasks-1)
                        bufferedWriter.write(" ");
                }
                bufferedWriter.newLine();
            }

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '"
                            + fileName + "'");
        }
    }

    private void write_bc_file(){
        // The name of the file to open
        String fileName = "bc.txt";
        String numberAsString;
        try {
            // Assume default encoding.
            java.io.FileWriter fileWriter =
                    new java.io.FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            for(int i=0; i<c.num_tasks; i++){
                Set<Integer> temp = c.binding_constraints.get(i);
                Iterator it = temp.iterator();
                Object[] tmp = temp.toArray();
                for(int j=0; j<tmp.length;j++){
                    bufferedWriter.write(tmp[j].toString());
                    if(j!=tmp.length-1)
                        bufferedWriter.write(" ");
                }
                bufferedWriter.newLine();
            }

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '"
                            + fileName + "'");
        }
    }
    private void write_sc_file(){
        // The name of the file to open
        String fileName = "sc.txt";
        String numberAsString;
        try {
            // Assume default encoding.
            java.io.FileWriter fileWriter =
                    new java.io.FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            for(int i=0; i<c.num_tasks; i++){
                Set<Integer> temp = c.separating_constraints.get(i);
                Iterator it = temp.iterator();
                Object[] tmp = temp.toArray();
                for(int j=0; j<tmp.length;j++){
                    bufferedWriter.write(tmp[j].toString());
                    if(j!=tmp.length-1)
                        bufferedWriter.write(" ");
                }
                bufferedWriter.newLine();
            }

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '"
                            + fileName + "'");
        }
    }
}
