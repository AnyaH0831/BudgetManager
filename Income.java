/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package financeApplication;

import java.util.Objects;

/**
 *
 * @author anyah
 */
public class Income extends Transaction{
    //Sub class inherit from Transaction class.

    /**
     * Constructor
     * 
     * @param budgetName
     * @param name
     * @param type
     * @param frequency
     * @param amount 
     */
    public Income(String budgetName,String name, String type,String frequency, String amount) {
        this.budgetName = budgetName;
        this.name = name;
        this.type = type;
        this.frequency = frequency;
        this.amount = amount;
    }

    //Getters.

    /**
     * Pre: No parameters expected.
     * Post: Return the budget name in string data type.
     * @return 
     */
    @Override
    public String getBudgetName() {
        return budgetName;
    }

    /**
     * Pre: No parameters expected.
     * Post: Return the frequency in string data type.
     * @return 
     */
    @Override
    public String getFrequency() {
        return frequency;
    }
    

    /**
     * Pre: No parameters expected.
     * Post: Return the name in string data type.
     * @return 
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * Pre: No parameters expected.
     * Post: Return the amount in string data type.
     * @return 
     */
    @Override
    public String getAmount() {
        return amount;
    }

    /**
     * Pre: No parameters expected.
     * Post: Return the budget type in string data type.
     * @return 
     */
    @Override
    public String getType() {
        return type;
    }
    
    
    //Setters

    /**
     * Pre: Expects a string.
     * Post: Set the value of budgetName as the parameter value, does not return.
     * @param budgetName 
     */
    @Override
    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    /**
     * Pre: Expects a string.
     * Post: Set the value of frequency as the parameter value, does not return.
     * @param frequency 
     */
    @Override
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    /**
     * Pre: Expects a string.
     * Post: Set the value of income name as the parameter value, does not return.
     * @param name 
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Pre: Expects a string.
     * Post: Set the value of type as the parameter value, does not return.
     * @param type 
     */
    @Override
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Pre: Expects a string.
     * Post: Set the value of amount as the parameter value, does not return.
     * @param amount 
     */
    @Override
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    
    /**
     * Pre: Expects an object.
     * Post: Return boolean based on if two objects are equal.
     * This method is used when methods like removeAll() is used.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        
        //Check if the two objects are the same.
        if (this == obj) {
            return true;
        }
        //Check if the object is not an object of Expenses.
        if (!(obj instanceof Income)) {
            return false;
        }
        Income other = (Income) obj;
        // Implement the custom equality check based on attributes.
        //Check if all attributes are the same.
        return Objects.equals(this.budgetName, other.budgetName) &&
               Objects.equals(this.name, other.name) &&
               Objects.equals(this.type, other.type) &&
               Objects.equals(this.frequency,other.frequency) &&
               Objects.equals(this.amount,other.amount);
    }
    
    
    
    /**
     * Pre:Expects no parameters.
     * Post: Return the real income in double data type.
     * Calculate the real amount based on the frequency.
     * @return 
     */
    @Override
    public double calculateRealAmount(){
        //Convert the amount from string to double.
        double originalAmount = Double.parseDouble(amount);
        double realIncome = originalAmount;
        
        //Multiply the original amount based on the frequency.
        if (frequency.equals("One Time")){
            realIncome = originalAmount;
        }else if (frequency.equals("Semi-Annual")){
            realIncome = originalAmount*2;
        }else if (frequency.equals("Quarterly")){
            realIncome = originalAmount*4;
        }else if (frequency.equals("Monthly")){
            realIncome = originalAmount*12;
        }else if (frequency.equals("Weekly")){
            realIncome = originalAmount*52;
        }else if (frequency.equals("Bi-Weekly")){
            realIncome = originalAmount*26;
        }else if (frequency.equals("Daily")){
            realIncome = originalAmount*365;
        }
        
        return realIncome;  //Return value
        
    }
    
    /**
     * Pre: No parameters expected.
     * Post: Return the string of the data.
     * Used for writing into files.
     * @return 
     */
    @Override
    public String toString() {
        
        String incomeData = "";
        //Write in all data for this object, seperated with commas.
        incomeData += budgetName+","+"income"+","+name+","+type+","+frequency+","+amount;
         
        return incomeData;  //Return string.
    }

    /**
     * Provide this method for the consistent for equals().
     * Pre: No parameters.
     * Post: Return the hash code.
     * @return 
     */
    @Override
    public int hashCode() {
        
        return Objects.hash(budgetName,name,type,frequency,amount);
    }
    
    
    
    
    
}
