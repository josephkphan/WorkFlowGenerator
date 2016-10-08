package com.jp.helper;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class FileHandler {
    private ConstraintsHandler c;

    public FileHandler(ConstraintsHandler c) {
        this.c = c;
    }

    public void writeCplexModFile() {
        write_file();
    }

    public void writeJavaDataFiles() {
        write_avg_file();
        write_bc_file();
        write_sc_file();
        write_ua_file();
        write_uc_file();
    }

    public void writeExcelFile() {
        try {
            createExcelFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write_file() {
        String fileName = "data.txt";
        String numberAsString;
        try {
            FileWriter fileWriter =
                    new FileWriter(fileName);

            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);
            //**********************sizes************************//

            bufferedWriter.write("u = " + Integer.toString(c.num_users - 1) + ";");
            bufferedWriter.newLine();
            bufferedWriter.write("t = " + Integer.toString(c.num_tasks - 1) + ";");
            bufferedWriter.newLine();
            bufferedWriter.write("b = " + Integer.toString(c.numBoD - 1) + ";");
            bufferedWriter.newLine();
            bufferedWriter.write("s = " + Integer.toString(c.numSoD - 1) + ";");
            bufferedWriter.newLine();

            //**********************avg************************//
            bufferedWriter.write("taskDemands = ");
            bufferedWriter.newLine();
            bufferedWriter.write("[ ");
            for (int j = 0; j < c.num_tasks; j++) {
                numberAsString = Integer.toString(c.task_orders[j]);
                bufferedWriter.write(numberAsString);
                if (j != c.num_tasks - 1)
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
            for (int i = 0; i < c.num_tasks; i++) {
                if (!c.boolBoD[i])
                    continue;
                bufferedWriter.write("[ ");
                for (int j = 0; j < c.num_tasks; j++) {
                    bufferedWriter.write(Integer.toString(c.bod[i][j]));
                    if (j != c.num_tasks - 1)
                        bufferedWriter.write(", ");

                }
                bufferedWriter.write("] ");
                if (i != c.num_tasks - 1)
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
            for (int i = 0; i < c.num_tasks; i++) {
                if (!c.boolSoD[i])
                    continue;
                bufferedWriter.write("[ ");
                for (int j = 0; j < c.num_tasks; j++) {
                    bufferedWriter.write(Integer.toString(c.sod[i][j]));
                    if (j != c.num_tasks - 1)
                        bufferedWriter.write(", ");
                }
                bufferedWriter.write("] ");
                if (i != c.num_tasks - 1)
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
            for (int i = 0; i < c.num_users; i++) {
                bufferedWriter.write("[ ");
                for (int j = 0; j < c.num_tasks; j++) {
                    numberAsString = Integer.toString(c.authorizations[i][j]);
                    bufferedWriter.write(numberAsString);
                    if (j != c.num_tasks - 1)
                        bufferedWriter.write(", ");
                }
                bufferedWriter.write("]");
                if (i != c.num_users - 1)
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
            for (int i = 0; i < c.num_users; i++) {
                bufferedWriter.write("[");
                for (int j = 0; j < c.num_tasks; j++) {
                    numberAsString = Integer.toString(c.user_capability[i][j]);
                    bufferedWriter.write(numberAsString);
                    if (j != c.num_tasks - 1)
                        bufferedWriter.write(", ");
                }
                bufferedWriter.write("] ");
                if (i != c.num_users - 1)
                    bufferedWriter.write(", ");
                bufferedWriter.newLine();
            }
            bufferedWriter.write("];");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        }
    }

    private void write_avg_file() {
        String fileName = "avg.txt";
        String numberAsString;
        try {
            java.io.FileWriter fileWriter =
                    new java.io.FileWriter(fileName);
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);
            for (int j = 0; j < c.num_tasks; j++) {
                numberAsString = Integer.toString(c.task_orders[j]);
                bufferedWriter.write(numberAsString);
                if (j != c.num_tasks - 1)
                    bufferedWriter.write(" ");
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        }
    }

    private void write_ua_file() {
        String fileName = "ua.txt";
        String numberAsString;
        try {
            java.io.FileWriter fileWriter =
                    new java.io.FileWriter(fileName);
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);
            for (int i = 0; i < c.num_users; i++) {
                for (int j = 0; j < c.num_tasks; j++) {
                    if (c.authorizations[i][j] == 0)
                        continue;
                    numberAsString = Integer.toString(j);
                    bufferedWriter.write(numberAsString);
                    if (j != c.num_tasks - 1)
                        bufferedWriter.write(" ");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        }
    }

    private void write_uc_file() {
        String fileName = "uc.txt";
        String numberAsString;
        try {
            java.io.FileWriter fileWriter =
                    new java.io.FileWriter(fileName);
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);
            for (int i = 0; i < c.num_users; i++) {
                for (int j = 0; j < c.num_tasks; j++) {
                    if (c.user_capability[i][j] == 0)
                        continue;
                    numberAsString = Integer.toString(c.user_capability[i][j]);
                    bufferedWriter.write(numberAsString);
                    if (j != c.num_tasks - 1)
                        bufferedWriter.write(" ");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        }
    }

    private void write_bc_file() {
        String fileName = "bc.txt";
        try {
            java.io.FileWriter fileWriter =
                    new java.io.FileWriter(fileName);
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);
            for (int i = 0; i < c.num_tasks; i++) {
                Set<Integer> temp = c.binding_constraints.get(i);
                Object[] tmp = temp.toArray();
                for (int j = 0; j < tmp.length; j++) {
                    bufferedWriter.write(tmp[j].toString());
                    if (j != tmp.length - 1)
                        bufferedWriter.write(" ");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        }
    }

    private void write_sc_file() {
        String fileName = "sc.txt";
        try {
            java.io.FileWriter fileWriter =
                    new java.io.FileWriter(fileName);
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);
            for (int i = 0; i < c.num_tasks; i++) {
                Set<Integer> temp = c.separating_constraints.get(i);
                Object[] tmp = temp.toArray();
                for (int j = 0; j < tmp.length; j++) {
                    bufferedWriter.write(tmp[j].toString());
                    if (j != tmp.length - 1)
                        bufferedWriter.write(" ");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        }
    }

    private void initSheet(HSSFSheet sheet, String s) {
        for (int i = 0; i <= c.num_users; i++) {
            sheet.createRow(i);
            for (int j = 0; j <= c.num_tasks; j++) {
                sheet.getRow(i).createCell(j);
                if (i == 0 && j == 0) {
                    sheet.getRow(i).getCell(j).setCellValue(s);
                } else if (i == 0) {
                    sheet.getRow(i).getCell(j).setCellValue("task" + j);
                } else if (j == 0) {
                    if (s == "sod" || s == "bod") {
                        sheet.getRow(i).getCell(j).setCellValue("task" + i);
                    } else {
                        sheet.getRow(i).getCell(j).setCellValue("user" + i);
                    }
                } else {
                    sheet.getRow(i).getCell(j).setCellValue("0");
                }
            }
        }
    }

    private void createExcelFile() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheetua = workbook.createSheet("ua");
        HSSFSheet sheetuc = workbook.createSheet("uc");
        HSSFSheet sheetavg = workbook.createSheet("avg");
        HSSFSheet sheetbod = workbook.createSheet("bod");
        HSSFSheet sheetsod = workbook.createSheet("sod");
        HSSFSheet sheetdeets = workbook.createSheet("deets");

        initSheet(sheetua, "ua");
        initSheet(sheetuc, "uc");
        initSheet(sheetbod, "bod");
        initSheet(sheetsod, "sod");

        //// TODO: 10/8/16 FIX BOD / SOD
        //BoD
        for (int i = 0; i < c.num_tasks; i++) {
            for (int j = 0; j < c.binding_constraints.get(i).size(); j++) {
                sheetbod.getRow(i + 1).getCell(j + 1).setCellValue("1");
            }
        }

        //SoD
        for (int i = 0; i < c.num_tasks; i++) {
            for (int j = 0; j < c.separating_constraints.get(i).size(); j++) {
                sheetsod.getRow(i + 1).getCell(j + 1).setCellValue("1");
            }
        }
        //ua
        for (int i = 0; i < c.num_users; i++) {
            for (int j = 0; j < c.num_tasks; j++) {
                sheetua.getRow(i + 1).getCell(j + 1).setCellValue(c.authorizations[i][j]);
            }
        }
        //user capability
        for (int i = 0; i < c.num_users; i++) {
            for (int j = 0; j < c.num_tasks; j++) {
                sheetuc.getRow(i + 1).getCell(j + 1).setCellValue(c.user_capability[i][j]);
            }
        }

        //init avg
        sheetavg.createRow(0);
        sheetavg.createRow(1);
        for (int i = 0; i < c.num_tasks; i++) {
            sheetavg.getRow(0).createCell(i);
            sheetavg.getRow(0).getCell(i).setCellValue("task" + i);
            sheetavg.getRow(1).createCell(i);
            sheetavg.getRow(1).getCell(i).setCellValue((Integer.toString(c.num_orders)));

        }

        //init sheet deets
        sheetdeets.createRow(0);
        sheetdeets.createRow(1);
        sheetdeets.getRow(0).createCell(0);
        sheetdeets.getRow(1).createCell(0);
        sheetdeets.getRow(0).createCell(1);
        sheetdeets.getRow(1).createCell(1);
        sheetdeets.getRow(0).getCell(0).setCellValue("numUsers");
        sheetdeets.getRow(0).getCell(1).setCellValue("numTasks");
        sheetdeets.getRow(1).getCell(0).setCellValue(Integer.toString(c.num_users));
        sheetdeets.getRow(1).getCell(1).setCellValue(Integer.toString(c.num_tasks));

        try {
            workbook.write(new FileOutputStream("data.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        workbook.close();
    }


}
