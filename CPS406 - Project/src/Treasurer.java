import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Treasurer extends User {

    private HashMap<String, Double> incomeList;
    private HashMap<String, Double> expenseList;
    private HashMap<String, Double> expense;
    private Double profit;
    private String month;
    private String year;
    private ArrayList<String> paidMembers;

    public Treasurer(String name, String email, String month, String year){
        super(name, email);

        this.incomeList = new HashMap<String, Double>();
        this.expenseList = new HashMap<String, Double>();
        this.expense = new HashMap<String, Double>();
        this.profit = 0.0;
        this.month = month;
        this.year = year;

        this.paidMembers = new ArrayList<>();
    }

    public void addIncome(String info, Double value) {
        //Member payment and any other income should go here//
        this.incomeList.put(info, value);
        this.profit += value;
    }

    public void addExpense(String info, Double value) {
        //Coach's fee, hall expense, and other expense should go here//
        this.expenseList.put(info, value);
        this.expense.put(info, value);
        this.profit -= value;
    }



    public void payExpense(String info, Double value) {
        if(this.expense.containsKey(info)) {
            this.expense.remove(info);
        }
    }

    public void payExpense(String info) {
        if(this.expense.containsKey(info)) {
            this.expense.remove(info);
        }
    }

    public double getProfit() {
        return profit;
    }

    public String getLoggingList(String name) {
        if (!name.isEmpty()) {
            this.paidMembers.add(name);
        }

        String report = "Logging Infomation | Year: " + this.year + " | Month: " + this.month;
        report += "\n\n****** Member Payment Info ******";
        for(String info : this.incomeList.keySet()) {
            if (info.equals(name)) {
                String temp = String.format("\n%-20s$%.2f (Paid)",info, this.incomeList.get(info));
                report += temp;
            }
            else {
                String temp = String.format("\n%-20s$%.2f (Unpaid)",info, this.incomeList.get(info));
                report += temp;
            }

        }
        report += "\n\n****** Coach Fees ******";
        for(String info : this.expenseList.keySet()) {
            String temp = String.format("\n%-20s$%.2f",info, this.expenseList.get(info));
            report += temp;
        }

        report += "\n\nProfit: " + this.getProfit();
        return report;
    }

}