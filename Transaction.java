/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package financeApplication;

/**
 *
 * @author anyah
 */
public abstract class Transaction {
    //Abstract class for Income and Expenses Class.
    
    // Protected member variables accessible to child classes
    protected String budgetName,name,type,frequency,amount;
    

    //Abstract getters method to be implemented in child classes.
    public abstract String getAmount();

    public abstract String getBudgetName();

    public abstract String getFrequency();

    public abstract String getName();

    public abstract String getType();

    //Abstratc setter methods to be implemented in child classes.
    public abstract void setAmount(String amount);

    public abstract void setBudgetName(String budgetName);

    public abstract void setFrequency(String frequency);

    public abstract void setName(String name);

    public abstract void setType(String type);

    //Abstratc toString() method.
    @Override
    public abstract String toString();
    
    
    // Abstract method to be implemented in child classes
    public abstract double calculateRealAmount();
    
}
