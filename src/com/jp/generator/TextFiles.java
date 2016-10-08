package com.jp.generator;

import com.jp.helper.ConstraintsHandler;
import com.jp.helper.FileHandler;

public class TextFiles {

    public TextFiles(int num_users, int num_tasks, int num_orders, int authorize, int BoDPercent, int SodPercent) {
        ConstraintsHandler c = new ConstraintsHandler(num_users, num_tasks, num_orders, authorize, BoDPercent, SodPercent);
        FileHandler file = new FileHandler(c);
        file.writeJavaDataFiles();
    }
}
