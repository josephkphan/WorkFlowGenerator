package com.jp.helper;

import java.util.Iterator;
import java.util.Set;


public class Printer {
    ConstraintsHandler c;

    public Printer(ConstraintsHandler c) {
        this.c = c;
    }

    /******************************Debugging Printing Methods ***********************/
    public void separating_constraints() {
        System.out.println("separating Constraints: ");
        for (int i = 0; i < c.num_tasks; i++)
            if (c.separating_constraints.containsKey(i))
                set(c.separating_constraints.get(i), "Task " + i + ": ");
        System.out.println("\n");
    }

    public void binding_constraints() {
        System.out.println("Binding Constraints: ");
        for (int i = 0; i < c.num_tasks; i++)
            if (c.binding_constraints.containsKey(i))
                set(c.binding_constraints.get(i), "Task " + i + ": ");
        System.out.println("\n");
    }

    public void array(int[] array, String s) {
        System.out.println("\n" + s);
        for (int i = 0; i < c.num_tasks; i++)
            System.out.print(array[i] + " ");
        System.out.println("");
    }

    public void array(int[][] array, String s) {
        System.out.println("\n" + s);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++)
                System.out.print(array[i][j] + " ");
            System.out.println("");
        }
    }

    public void set(Set<Integer> S, String s) {
        System.out.println("\n" + s);
        Iterator it = S.iterator();
        while (it.hasNext()) {
            // Get element
            Object element = it.next();
            System.out.print(element + " ");
        }
    }
}
