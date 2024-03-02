/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package financeApplication;

/**
 *
 * @author anyah
 */
public class User {
    
    // Private member variables to store username, password, and an array of budget names
    private String username, password;
    private String[] budgetNames;
    
    /**
     * Constructor to initialize a User object with username, password, and an array of budget names
     * Require 3 parameters.
     * @param username
     * @param password
     * @param budgetNames 
     */
    public User(String username, String password, String[] budgetNames) {
        
        this.username = username;
        this.password = password;
        this.budgetNames = budgetNames;
        
    }

    //Getters
    
    // Getter method to retrieve the password associated with the user
    public String getPassword() {
        return password;
    }

    // Getter method to retrieve the username of the user
    public String getUsername() {
        return this.username;
    }

    // Getter method to retrieve the array of budget names associated with the user
    public String[] getBudgetNames() {
        return budgetNames;
    }

    //All setter methods do not return values.
    
    /**
     * Setter method to set the password for the user, one string parameter required.
     * Post: Does not return.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Setter method to set the username for the user, one string parameter required.
     * Post: Does not return.
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Setter method to set the array of budget names for the user
     * Does not return values.
     * @param budgetNames 
     */
    public void setBudgetNames(String[] budgetNames) {
        this.budgetNames = budgetNames;
    }
    
    
    /**
     * Pre: No parameters required.
     * Post:Return user's username and password.
     * This will be used to write into the files directly.
     * @return 
     */
    @Override
    public String toString() {
     
        String allBudget = "";
        
        // Concatenate all budget names into a single string with comma separation
        for (int i = 0; i < budgetNames.length; i++){
                            
            allBudget += budgetNames[i];
                            
            if (i != budgetNames.length - 1){
                allBudget += ",";
            }
                            
        }
         
        // If the user has no budgets, indicate it with a specific string
        if (budgetNames.length == 0){
            allBudget = "**no_budgets";
        }
        
        // Create a formatted string with user data (username, password, and budget names)
        String userData = this.username + "," + this.password + "\n" +allBudget;
        return userData; //Return the string.
    }
    
    
    
    
}
