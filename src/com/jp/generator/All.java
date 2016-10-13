package com.jp.generator;


import com.jp.helper.ConstraintsHandler;
import com.jp.helper.FileHandler;
import com.jp.helper.fileEditor;

public class All {
    public All(int num_users, int num_tasks, int num_orders, int authorize, int BoDPercent, int SodPercent) {
        ConstraintsHandler c = new ConstraintsHandler(num_users, num_tasks, num_orders, authorize, BoDPercent, SodPercent);
        FileHandler file = new FileHandler(c);

        //creates original files
        file.writeCplexModFile();
        file.writeJavaDataFiles();
        //file.writeExcelFile("data");

        //removingBoD Constraints
        fileEditor fe = new fileEditor(c);
        for(int i=9; i>=0; i--) {
            //UC
            fe.reduceCapability(i);
            file.write_uc_file("java_uc_" + i);
            file.writeCplexUC("opl_uc_" + i);

            // Bod
            fe.reduceBoD();
            file.writeCplexBC("opl_bc_" + i);
            file.write_bc_file("java_bc_"+i);

            //Sod
            fe.reduceSoD();
            file.write_sc_file("java_sc_" + i);
            file.writeCplexSC("opl_sc_"+i);

            //ua
            fe.redunceAuthorizations();
            file.write_ua_file("java_ua_" + i);
            file.writeCplexUA("opl_ua_"+i);
        }


    }
}
