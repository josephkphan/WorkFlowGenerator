package com.jp.generator;
import com.jp.helper.ConstraintsHandler;
import com.jp.helper.FileHandler;

/**
 * Created by jphan on 10/7/16.
 */
public class CplexText {

    private ConstraintsHandler c;
    FileHandler file;

    public CplexText(int num_users, int num_tasks, int num_orders, int authorize, int BoDPercent, int SodPercent) {
        c = new ConstraintsHandler(num_users, num_tasks, num_orders, authorize, BoDPercent, SodPercent);
        file = new FileHandler(c);
        file.writeCplexModFile();
    }
}
