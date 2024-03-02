/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package financeApplication;

import java.util.ArrayList;


/**
 *
 * @author anyah
 */
public class Budget {
    
    // Private member variables to store username, name, incomes, and expenses
    private String username,name;
    private ArrayList<Income> incomes;
    private ArrayList<Expenses> expenses;

    /** Constructor to initialize Budget object with username, name, incomes, and expenses
     * Pre: Expects two strings and two arrayLists.
     * @param username
     * @param name
     * @param incomes
     * @param expenses 
     */
    public Budget(String username,String name, ArrayList<Income> incomes, ArrayList<Expenses> expenses) {
        this.username = username;
        this.name = name;
        this.incomes = incomes;
        this.expenses = expenses;
    }
    
    /**
     * Pre:No parameters expected.
     * Post: Calculate total income and return it as a double.
     * @return totalIncome - the total income calculated from all Income objects
     */
    public double calculateIncome(){
        
        double totalIncome = 0;
        // Iterate through the incomes ArrayList and calculate the total income
        for (int i=0; i<incomes.size(); i++){
            totalIncome += incomes.get(i).calculateRealAmount();
        }
        
        return totalIncome;  //Return the value.
    }
    
    /**
     * Pre:No parameters expected.
     * Post: Calculate total expense and return it as a double.
     * @return totalExpense - the total expense calculated from all Expenses objects
     */
    public double calculateExpenses(){
        
        double totalExpense = 0;
        // Iterate through the expenses ArrayList and calculate the total expense
        for (int i=0; i<expenses.size(); i++){
            totalExpense += expenses.get(i).calculateRealAmount();
        }
        
        return totalExpense;  //Return the expense.
    }
    
    //Getters

    /** 
     * Pre: No parameters expected.
     * Post: Getter method to retrieve the name of the Budget, return the name as a string.
     * 
     * @return 
     */
    public String getName() {
        return name;
    }

    /** 
     * Pre: Pre: No parameters expected.
     * Post: Getter method to retrieve the username associated with the Budget, return username as string.
     * 
     * @return 
     */
    public String getUsername() {
        return username;
    }

    /** 
     * Pre: Pre: No parameters expected.
     * Post: Getter method to retrieve the ArrayList of Income objects, return incomes as arrayList of Income objects.
     * 
     * @return 
     */
    public ArrayList<Income> getIncomes() {
        return incomes;
    }

    /** 
     * Pre: Pre: No parameters expected.
     * Post: Getter method to retrieve the ArrayList of Expenses objects, return expeses as arrayList of Expenses objects.
     * 
     * @return 
     */
    public ArrayList<Expenses> getExpenses() {
        return expenses;
    }

    
    //Setters
    
    
    /** 
     * Pre: Expects an arrayList of Income Objects.
     * Post: Setter method to set the ArrayList of Income objects, does not return.
     * 
     * @param incomes 
     */
    public void setIncomes(ArrayList<Income> incomes) {
        this.incomes = incomes;
    }

    /** 
     * Pre: Expects an arrayList of expenses objects.
     * Post: Setter method to set the ArrayList of Expenses objects, does not return.
     * @param expenses 
     */
    public void setExpenses(ArrayList<Expenses> expenses) {
        this.expenses = expenses;
    }

    /**
     * Pre: Expects a string parameter.
     * Post: Setter method to set the name of the budget. Does not return.
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Pre: Expects a string parameter.
     * Post: Setter method to set the username the budget belongs to.
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    
    
    /**
     * Override the toString() method to return the name of the Budget
     * Pre: No parameters expected.
     * Post: Return the name of the budget.
     * @return 
     */
    @Override
    public String toString() {
        String info = "";  //Initialize a string.
        info += this.name;  //Add the name to string.
        return info;  //Return value.
    }
    
    
     
}
