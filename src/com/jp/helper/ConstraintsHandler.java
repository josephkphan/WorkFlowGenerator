package com.jp.helper;

import java.util.*;


public class ConstraintsHandler {
    public int num_users;
    public int num_tasks;
    public int num_orders;
    public int[][] authorizations;
    public int[][] task_probability;
    public int[][] user_capability;
    public int[] task_orders;
    public HashMap<Integer, Set<Integer>> binding_constraints = new HashMap<Integer, Set<Integer>>();
    public HashMap<Integer, Set<Integer>> separating_constraints = new HashMap<Integer, Set<Integer>>();
    public int[][] bod, sod;
    public boolean[] boolBoD, boolSoD;
    public int numBoD, numSoD;
    public int counter;
    private Printer p;

    public ConstraintsHandler(int num_users, int num_tasks, int num_orders, int authorize, int BoDPercent, int SodPercent) {
        p = new Printer(this);
        initialize(num_users, num_tasks, num_orders);
        createAllConstraints(BoDPercent, SodPercent, authorize);
        createSoDBoD();
        removeRedundantBinding();
    }

    private void createAllConstraints(int BoDPercent, int SoDPercent, int authorize) {
        create_task_orders();
        create_binding_constraints(BoDPercent);
        create_separating_constraints(SoDPercent);
        create_authorizations_matrix(authorize);
        create_user_capability_matrix();
        //create_probability_matrix();

    }

    public void removeRedundantBinding() {
        boolSoD = new boolean[num_tasks];
        boolBoD = new boolean[num_tasks];
        for (int i = 0; i < num_tasks; i++) {
            boolBoD[i] = boolSoD[i] = true;
        }
        for (int i = 0; i < num_tasks; i++) {
            for (int j = 0; j < num_tasks; j++) {
                if (bod[i][j] == 1 && j > i)
                    boolBoD[j] = false;
                if (sod[i][j] == 1 && j > i)
                    boolSoD[j] = false;
            }
        }
        for (int i = 0; i < num_tasks; i++)
            if (separating_constraints.containsKey(i) && separating_constraints.get(i).size() == 1)
                boolSoD[i] = false;
        for (int i = 0; i < num_tasks; i++)
            if (binding_constraints.containsKey(i) && binding_constraints.get(i).size() == 1)
                boolBoD[i] = false;

        numBoD = numSoD = 0;

        for (int i = 0; i < num_tasks; i++) {
            if (boolBoD[i])
                numBoD++;
            if (boolSoD[i])
                numSoD++;
        }
    }

    //Precondition: users, tasks, orders > 0
    private void initialize(int users, int tasks, int orders) {
        num_users = users;
        num_tasks = tasks;
        num_orders = orders;
        task_probability = new int[num_tasks][num_tasks];
        authorizations = new int[num_users][num_tasks];
        user_capability = new int[num_users][num_tasks];
        task_orders = new int[num_tasks];
        Set<Integer> temp;
        for (int i = 0; i < num_tasks; i++) {
            temp = new HashSet<Integer>();
            temp.add(i);
            separating_constraints.put(i, temp);
            temp = new HashSet<Integer>();
            temp.add(i);
            binding_constraints.put(i, temp);
        }
        System.out.println("users:" + num_users + "\ntasks: " + num_tasks + "\norders: " + num_orders);
    }

    private void create_authorizations_matrix(int percent) {
        double dx = percent * .01 * num_users;
        int factor = (int) dx;
        int rand, repetitions;
        int[] check = new int[num_tasks];
        Random random = new Random();
        for (int i = 0; i < num_tasks; i++) {
            if (check[i] == 1)
                continue;
            Set<Integer> temp = binding_constraints.get(i);
            Iterator it = temp.iterator();
            Object[] tmp = temp.toArray();
            //repetitions = factor / tmp.length + 1;
            repetitions = factor;
            for (int k = 0; k < repetitions; k++) {
                rand = random.nextInt(num_users);
                if (authorizations[rand][(Integer) tmp[0]] == 1) {
                    k--;
                    continue;
                }
                for (int j = 0; j < tmp.length; j++) {
                    authorizations[rand][(Integer) tmp[j]] = 1;
                    check[(Integer) tmp[j]] = 1;
                }
            }
        }
        p.array(authorizations, "Authorizations Matrix");
    }

    private void create_probability_matrix() {
        Random random = new Random();
        int rand;
        int row_min;
        int row_max = 100;
        for (int i = 0; i < num_tasks; i++) {
            if (i == num_users - 1)    //for last row. can't have probability 1
                row_max = 99;
            row_min = 0;

            for (int j = 0; j < num_tasks; j++) {
                if (row_min == row_max)
                    break;    //probability already reached 0

                if (i == j) {
                    task_probability[i][j] = 0;
                } else if (i == 0 && j == num_tasks - 1) {
                    task_probability[i][j] = 100 - row_min;
                } else {
                    rand = random.nextInt(row_max - row_min);
                    row_min += rand;
                    task_probability[i][j] = rand;
                }
            }

        }
        p.array(task_probability, "Probability Matrix");

    }

    private void create_user_capability_matrix() {
        Random random = new Random();

        for (int i = 0; i < num_users; i++) {
            for (int j = 0; j < num_tasks; j++) {
                if (authorizations[i][j] == 0)
                    continue;
                user_capability[i][j] = num_tasks*5;
            }
        }
        p.array(user_capability, "User Capability Matrix");
    }

    private void create_task_orders() {
        for (int i = 0; i < num_tasks; i++) {
            task_orders[i] = num_orders;
        }
        p.array(task_orders, "Task Orders Matrix");

    }

    private void create_binding_constraints(int percent) {
        double dx = percent * .01 * num_tasks;
        int num_constraints = (int) dx;
        Random random = new Random();
        int task1, task2;
        System.out.println("NUM CONSTRAST:!!!!" + num_constraints);
        Set<Integer> temp1, temp2, empty_set;
        for (int i = 0; i < num_constraints; i++) {
            task1 = task2 = 0;
            while (task1 == task2) {
                task1 = random.nextInt(num_tasks);
                task2 = random.nextInt(num_tasks);
            }
            //System.out.print("\n tasks: "+ task1 +" " + task2 + "\n");

            if (!binding_constraints.containsKey(task1)) {
                empty_set = new HashSet<Integer>();
                empty_set.add(task1);
                binding_constraints.put(task1, empty_set);
            }

            if (!binding_constraints.containsKey(task2)) {
                empty_set = new HashSet<Integer>();
                empty_set.add(task2);
                binding_constraints.put(task2, empty_set);
            }
            temp1 = binding_constraints.get(task1);
            temp2 = binding_constraints.get(task2);

            temp1.addAll(temp2);
            update_binding_constraints(temp1);
            temp2.addAll(temp1);
            update_binding_constraints(temp2);
        }
        p.binding_constraints();
    }

    private void update_binding_constraints(Set<Integer> set) {
        Iterator it = set.iterator();
        Set<Integer> temp;
        while (it.hasNext()) {
            Object element = it.next();
            temp = binding_constraints.get((Integer) element);
            temp.addAll(set);
        }
    }

    private void create_separating_constraints(int c) {
        double dx = c * .01 * num_tasks;
        int num_constraints = (int) dx;
        Random random = new Random();
        int task1, task2;
        Set<Integer> temp1, temp2, empty_set;
        for (counter = 0; counter < num_constraints; counter++) {
            task1 = task2 = 0;
            while (task1 == task2) {
                task1 = random.nextInt(num_tasks);
                task2 = random.nextInt(num_tasks);
            }
            //System.out.print("\n tasks: "+ task1 +" " + task2 + "\n");
            if (check_is_there2(task1, task2))
                continue;

            if (!separating_constraints.containsKey(task1)) {
                empty_set = new HashSet<Integer>();
                empty_set.add(task1);
                separating_constraints.put(task1, empty_set);
            }

            if (!separating_constraints.containsKey(task2)) {
                empty_set = new HashSet<Integer>();
                empty_set.add(task2);
                separating_constraints.put(task2, empty_set);
            }
            temp1 = separating_constraints.get(task1);
            temp2 = separating_constraints.get(task2);

            temp1.addAll(temp2);
            update_separating_constraints(temp1);
            temp2.addAll(temp1);
            update_separating_constraints(temp2);

        }
        p.separating_constraints();
    }

    private void update_separating_constraints(Set<Integer> set) {
        Iterator it = set.iterator();
        Set<Integer> temp;
        while (it.hasNext()) {
            Object element = it.next();
            temp = separating_constraints.get((Integer) element);

            temp.addAll(set);
        }
    }

    private boolean check_is_there2(int value1, int value2) {
        Set<Integer> temp = new HashSet<Integer>();
        if (separating_constraints.containsKey(value1))
            temp.addAll(separating_constraints.get(value1));
        if (separating_constraints.containsKey(value2))
            temp.addAll(separating_constraints.get(value2));
        temp.add(value1);
        temp.add(value2);
        Iterator sep = temp.iterator();
        Iterator sep2 = temp.iterator();
        //sep2.next();
        Set<Integer> Bind;
        while (sep.hasNext()) {
            Object s1 = sep.next();
            while (sep2.hasNext()) {
                Object s2 = sep2.next();
                if (!binding_constraints.containsKey((Integer) s2))
                    continue;
                Bind = binding_constraints.get((Integer) s2);
                Iterator bind = Bind.iterator();
                while (bind.hasNext()) {
                    Object b = bind.next();
                    if ((Integer) s1 == (Integer) b && (Integer) s1 != (Integer) s2) {
                        counter--;
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public void createSoDBoD() {
        sod = new int[num_tasks][num_tasks];
        bod = new int[num_tasks][num_tasks];
        for (int i = 0; i < num_tasks; i++) {
            for (int j = 0; j < num_tasks; j++) {
                sod[i][j] = 0;
                bod[i][j] = 0;
            }
        }
        for (int i = 0; i < num_tasks; i++) {
            for (int j : binding_constraints.get(i)) {
                bod[i][j] = 1;
            }
            for (int j : separating_constraints.get(i)) {
                sod[i][j] = 1;
            }
        }
    }

}
