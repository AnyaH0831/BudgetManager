/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package financeApplication;


//The imports.

import java.io.BufferedReader;  //Read and write in files.
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;   //For using arrayLists.

import java.util.HashMap;   //For sorting the income and expense objects into seperate budget objects.
import java.util.HashSet;
import java.util.Map;

import java.util.Random;  //Import random class for quick sort.

import javax.swing.DefaultListModel;  //Modify the Jlists.
import javax.swing.JOptionPane;  //For pop-ups.


/**
 *
 * @author anyah
 */
public class FinanceApplication extends javax.swing.JFrame {

    //Declare global object arrayLists for the users, budgets, income, and expenses.
    
    /**
     * userList is an User object arrayList for all users of this application.
     * It stores login details and the budget names.
     */
    public static ArrayList<User>userList = new ArrayList <>();  
    
    /**
     * budgetList is a Budget object arrayList of the current user.
     * Thus this list will only be filled in after the login.
     */
    public static ArrayList<Budget>budgetList = new ArrayList <>();
    
    /**
     * incomeList and expenseList serves similar purposes.
     * Both are object lists that record the income and expenses
     * from the current user.
     * Similar to budgetList, it will only be filled in after the login.
     */
    public static ArrayList<Income>incomeList = new ArrayList <>();
    public static ArrayList<Expenses>expenseList = new ArrayList <>();

    //Global variables.
    public static String username;   //The username.
    public static String globalBudgetName;  //The current budget name.
    public static int userIndex;  //The user's index in the userList.
    public static int budgetIndex;  //The current budget index in the budgetList.
    
    
    /**
     * 
     * @param userInput
     * @return 
     * Pre: Expects a string variable.
     * Post: Return boolean based on if the string can be converted to a double.
     */
    public static boolean isDouble(String userInput){
        try{
            Double input = Double.parseDouble(userInput);  //Try if the string is parseable.
            return true;
        }catch (NumberFormatException ex){  
            return false;
        }
    }
    
    
    /**
     * Pre: Expects one string array and two strings.
     * Post: Swap the position of the two strings. Does not return.
     * In the program, this method is specifically for swapping the positions in userList.
     * When using this in the program, an array parallel to userList will be made.
     * Then it will be used in quickSort, and when it uses swap(),
     * The userList will swap the two object's position along with the array.
     * @param stringArray
     * @param a
     * @param b 
     */
    public static void swap(String[] stringArray, int a, int b){
        String temp = stringArray[a];  //Set up a temporary variable and set it as the value of the first string.
        stringArray[a] = stringArray[b];  //Let the first integer position be the second string.
        stringArray[b] = temp;   //Set the second position as the first string.
        
        //Set temporary variables for the username, password,budgetName for the first object.
        String tempName = userList.get(a).getUsername();
        String tempPW = userList.get(a).getPassword();
        String[] tempBudgetNames = userList.get(a).getBudgetNames();
        
        //Set the data of the second object to the first object.
        userList.get(a).setUsername(userList.get(b).getUsername());
        userList.get(a).setPassword(userList.get(b).getPassword());
        userList.get(a).setBudgetNames(userList.get(b).getBudgetNames());
        
        //Set the data of the temporary values to the first object.
        userList.get(b).setUsername(tempName);
        userList.get(b).setPassword(tempPW);
        userList.get(b).setBudgetNames(tempBudgetNames);
    }
    
    /**
     * Pre: Expects a string array and two integers and one string.
     * Post: Change the position of the pivot,
     * This compares the strings in the alphabetical order.
     * where the string greater than the pivot goes to the right
     * and the string less than the pivot goes to the left.
     * 
     * Swap the positions of the string at the right and left pointers
     * then when the pointer meet, swap the string at that position with the pivot.
     * Partition for ascending order.
     * Return the leftPointer position. 
     * @param strings
     * @param lowIndex
     * @param highIndex
     * @param pivot
     * @return 
     */
    public static int partition(String[] strings, int lowIndex, int highIndex, String pivot){
        
        int leftPointer = lowIndex;   //Let the left pointer be the low index.
        int rightPointer = highIndex - 1;  //Let the right pointer be one less than the high index.
        
        while (leftPointer < rightPointer){  //When the left pointer position is less than right pointer.
            
            //When the value of where the left pointer is pointing is less than or equal to the pivot.
            //And the left pointer position is less than right pointer.
            while (strings[leftPointer].compareTo(pivot) <= 0 && leftPointer < rightPointer){
                leftPointer ++;  //Add one to left pointer.
            }
            
            //When the value of where the right pointer is pointing is greater than or equal to the pivot.
            //And the left pointer position is less than right pointer.
            while (strings[rightPointer].compareTo(pivot) >= 0 && leftPointer < rightPointer){
                rightPointer --;  //Move one position to the left.
            }
            
            //Swap the position of the two pointers.
            swap(strings,leftPointer,rightPointer);
        }
        
        //Check if the string the left pointer is pointing is greater than the high index value.
        if (strings[leftPointer].compareTo(strings[highIndex]) > 0){
            //Swap the position of the left pointer and the high index.
            swap(strings,leftPointer,highIndex);
            
        }else{  
            leftPointer = highIndex;  //Let the left pointer position be the high index.
        }
        
        return leftPointer;   //Return the left pointer.
    }
    
    /**
     * Pre: Expects a string array and two integers.
     * Post: Sort the array in ascending order; does not return anything.
     * Sort the strings in alphabetical orders.
     * @param strings
     * @param lowIndex
     * @param highIndex 
     */
    public static void quickSortAscend(String[] strings, int lowIndex, int highIndex){
        
        //If the low index is greater or equal to the high index.
        if (lowIndex >= highIndex){
            return;  //Return to end the method.
        }
        
        //Randomize the pivot index.
        int pivotIndex = new Random().nextInt(highIndex-lowIndex) + lowIndex;
        String pivot = strings[pivotIndex];  //Set the pivot.
        
        swap(strings,pivotIndex,highIndex); //Swap the randomized pivot with the high index value.
        
        //Use the partition method to do the partition and get the left pointer position.
        int leftPointer = partition(strings,lowIndex,highIndex,pivot);
        
        //Do recursion with the strings before the left pointer.
        quickSortAscend(strings,lowIndex,leftPointer-1);
        //Do recursion with the strings after the left pointer.
        quickSortAscend(strings,leftPointer + 1,highIndex);
        
    }
    
    /**
     * Overload the other quickSortAscend() method.
     * Increase code clarity.
     * Pre: Expects a string array.
     * Post: Call the other quickSortAscend() method.
     * @param strings 
     */
    public static void quickSortAscend(String[] strings){
        //Call quickSortAscend() method.
        quickSortAscend(strings,0,strings.length-1);
    }
    
    
    
    /**
     * Pre: Expects a sorted string array and a string to find.
     * Post: Use the binary search way to search the string in the array.
     * Return the position of the string in the array.
     * Or return -1 if not found. 
     * @param reference
     * @param stringToFind
     * @return 
     */
    public static int binarySearch(String[] reference, String stringToFind){
        
        //Set the position as low and high; which is 0 and the last position.
        int low = 0;
        int high = reference.length-1;
        
        //When low is less than or equal to high.
        while (low <= high){
            
            //Find the middle position.
            int middlePos = (low+high)/2;
            //Find the actual value of the middle position.
            String middleString = reference[middlePos];
            
            //If the string we want to find is the same as the middle string.
            if (stringToFind.toLowerCase().equals(middleString.toLowerCase())){
                return middlePos;  //Return postion.
            }
            
            //If the string to find is less than the middle string.
            if (stringToFind.toLowerCase().compareTo(middleString.toLowerCase()) < 0){
                high = middlePos - 1;  //Set the high value be one less than middle.
            }else{  //The number to find is bigger than the middle string.
                low = middlePos + 1;  //Set the low value one more than middle.
            }
        }
        
        return -1;  //If didn't find the string, return -1.
    }
    
    /**
     * Pre: No parameters expected.
     * Post:Convert userList arrayList into array with the usernames.
     * Used to sort and search in the previous methods.
     * @return 
     */
    public static String[] convertToArray(){
        //Initialize the size of the userArray.
        
        String[] userArray = new String[userList.size()];
        
        //Go through every user.
        for (int idex = 0; idex<userList.size();idex++){
                //Set the values in the array.
            userArray[idex] = userList.get(idex).getUsername();
        }
        
        return userArray;  //Return the array.
         
    }
    
    /**
     * Pre: Expects two string arrays.
     * Post: Add or concatenate two arrays together and return the array.
     * @param array1
     * @param array2
     * @return 
     */
    public static String[] concatenateArrays(String[] array1, String[] array2){
        //Initialize new array with the length of the sum of the two arrays to concatenate.
        String[] result = new String[array1.length+array2.length];
        
        //Copy the first array to the result array.
        System.arraycopy(array1,0,result,0,array1.length);
        //Copy the second array to the result array start from position after first array.
        System.arraycopy(array2,0,result,array1.length,array2.length);
        
        return result;      //Return the array.
    }
    
    
    /**
     * Pre: Expects four strings and one double. These are the informations for transaction details.
     * Post: Create the message for the pop-up window then create a pop-up. Does not return anything.
     * @param description
     * @param transactionType
     * @param frequency
     * @param amount
     * @param annualAmount 
     */
    public static void showDetailPopUp(String description, String transactionType, String category, String frequency,String amount,double annualAmount){
        
        //Initialize the message string.
        String message = "Description: " + description + "\n";
        
        //Fill in the data into the message string.
        message += "Transaction Type: " + transactionType + "\n";
        message += "Category: " + category + "\n";
        message += "Frequency: " + frequency + "\n";
        message += "Amount: $" + amount + "\n";
        message += "Annual Amount: $" + String.format("%.2f",annualAmount) + "\n";
        
        //Create the pop-up.
        JOptionPane.showMessageDialog(null,message,"Transaction Details", JOptionPane.INFORMATION_MESSAGE);
    }

    
    /**
     * Pre: Does not require parameters.
     * Post: Does not return anything. Only write into the files for current user.
     * Write all the transaction details.
     */
    public static void writeInFile(){

        //Write into the file.
        try {

         //Open the user file for write.
            BufferedWriter writer = new BufferedWriter(new FileWriter(username+".txt"));
                    
            for (int i = 0; i<incomeList.size();i++){  //Go through all incomes in the arraylist.
                //Write into the file, using the toString() method.
                writer.write(incomeList.get(i).toString()+"\n");

            }

            writer.close();  //Close file.

        } catch (IOException e) {
            e.printStackTrace();    //Write if there's an error.    
        }
            
        //Write into the file again for expenses.
        try {

            //Open the user file for append.
            BufferedWriter writer = new BufferedWriter(new FileWriter(username+".txt",true));
                    
            for (int i = 0; i<expenseList.size();i++){  //Go through all expenses.
               //Write into the file, using the toString() method.
                writer.write(expenseList.get(i).toString()+"\n");

            }

            writer.close();  //Close file.

        } catch (IOException e) {
            e.printStackTrace();    //Write if there's an error.    
        }
        
        
        
    }
    
    
    /**
     * Pre: Expects an array and a string to find in the array.
     * Post: Return the index of the position where the string is found.
     * Return -1 if not found. Return integer data type.
     * Used for unsorted arrays.
     * @param unSortedArray
     * @param stringToFind
     * @return 
     */
    public static int linearSearch(String[] unSortedArray, String stringToFind){
        
        //Go through all elements.
        for (int k=0; k<unSortedArray.length; k++) {
            //Check if the element equals to the target string.
            if (unSortedArray[k].equals(stringToFind)) {
                return k;   //Return the index.
            }

        }
        //Not found, return -1.
        return -1;
    }
    
    /**
     * 
     * @param name
     * @return 
     * Pre: Expects a string.
     * Post: Check if there's any commas in the string and return a boolean.
     */
    public static boolean ifCommas(String name){
        
        //Split the string into seperate characters.
        String[] splitName = name.split("");
        
        //Check through every character in the list.
        for (int charact = 0; charact<splitName.length; charact++){
            
            //If there's a comma.
            if (splitName[charact].equals(",")){
                return true;  //Return true.
            }
        }
        
        return false;
    }
    
    /**
     * 
     * One double data type parameter.
     * Return double data type, which is the same number as the parameter 
     * but rounded to 2 decimal places.
     *
     * @param num
     * @return 
     */      
    public static double roundMethod(double num){  //Method to round a number to 2 decimal places.
        
        double numRound;
        numRound = (Math.round(num*100.0))/100.0;  
        /**
         * Multiply the number by 100
         * Use math method round
         * Divide it by 100.
         */
        
        return numRound;  //Return value.
    }
    
   
    /**
     * Pre: Expects a string.
     * Post: Check if the number has to use the E notation and return boolean.
     * @param numInput
     * @return 
     */
    public static boolean ifTooLong(String numInput){
        
        //Convert to double.
        double num = roundMethod(Double.parseDouble(numInput));
        
        //Check if the number is too big or small.
        if (num>=Math.pow(10,7) || num <= Math.pow(10,-3)){
            return true;
        }else{
            return false;
        }
     
    }
    
    /**
     * Creates new form MySignup
     */
    public FinanceApplication() {
        initComponents();

        
        //Read from the all user file.
        try {
          
            //Open the file for read.
            BufferedReader reader = new BufferedReader(new FileReader("UserLogin.txt"));
            String line;  //Initalize a string.

            int countLine = 0;  //Counter to count the number of lines.
            
            String[] lineArray = new String[2];   //Array for username and password.
            String[] userBudgetArray;  //Array for budget names.
            
            //While the line does not equal to null.
            while ((line=reader.readLine()) != null && line.equals("") == false){

                
                if (countLine%2 == 0){  //If it's start at the first line and everyother line in the file.
                    //Split the string if there's a comma.
                    lineArray = line.split(",",2);
                }else{
                    userBudgetArray = line.split(",");  //Split string if there's a comma.
                    
                    //If the first element of the array is no budgets.
                    if (userBudgetArray[0].equals("**no_budgets")){
                        String[] clearUserBudget = {};  //Clear the budget list.
                        
                        //Create the user object and add to the arraylist.
                        userList.add(new User(lineArray[0],lineArray[1],clearUserBudget));
                      
                    }else{
                        //Create the user object and add to the arraylist.
                        userList.add(new User(lineArray[0],lineArray[1],userBudgetArray));
                    }
                    
                    lineArray = new String[2];  //Re-initialize the lineArray.
                }
                
                countLine++;  //Add one to counter.

            }
            
            reader.close();  //Close file.
            
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        lblUsernameLog = new javax.swing.JLabel();
        txtUsernameLog = new javax.swing.JTextField();
        lblLogin1 = new javax.swing.JLabel();
        lblPasswordLog = new javax.swing.JLabel();
        lblSignUp = new javax.swing.JLabel();
        lblNoAccount1 = new javax.swing.JLabel();
        lblIncorrect = new javax.swing.JLabel();
        lblImage1 = new javax.swing.JLabel();
        btnLoginExit = new javax.swing.JButton();
        txtPasswordLog = new javax.swing.JPasswordField();
        tabbedPane = new javax.swing.JTabbedPane();
        panelSignUp = new javax.swing.JPanel();
        btnSignup = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblCreate = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        lblNoAccount = new javax.swing.JLabel();
        lblErrorName = new javax.swing.JLabel();
        lblErrorPW = new javax.swing.JLabel();
        lblImage2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        panelHome = new javax.swing.JPanel();
        lblCreate1 = new javax.swing.JLabel();
        btnAddBudget = new javax.swing.JButton();
        btnSelectBudget = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        budgetJlist = new javax.swing.JList<>();
        lblYourBudgets = new javax.swing.JLabel();
        lblGreeting = new javax.swing.JLabel();
        lblSelectBudgetError = new javax.swing.JLabel();
        lblProfile = new javax.swing.JLabel();
        txtProfile = new javax.swing.JTextField();
        btnAppExit = new javax.swing.JButton();
        btnDeleteBudget = new javax.swing.JButton();
        panelAddBudget = new javax.swing.JPanel();
        lblCreate2 = new javax.swing.JLabel();
        lblPromptName = new javax.swing.JLabel();
        txtNameBudget = new javax.swing.JTextField();
        btnAddBudget2 = new javax.swing.JButton();
        lblPig = new javax.swing.JLabel();
        btnGoBack = new javax.swing.JButton();
        lblBudgetError = new javax.swing.JLabel();
        lblYear = new javax.swing.JLabel();
        comboBoxYear = new javax.swing.JComboBox<>();
        panelBudget = new javax.swing.JPanel();
        btnBackHome = new javax.swing.JButton();
        txtBudgetTitle = new javax.swing.JTextField();
        btnAddIncome = new javax.swing.JButton();
        addExpense = new javax.swing.JButton();
        panelSummary = new javax.swing.JPanel();
        lblIncomeSub = new javax.swing.JLabel();
        lblExpenseSub = new javax.swing.JLabel();
        lblBalanceSub = new javax.swing.JLabel();
        lblIncomeValue = new javax.swing.JLabel();
        lblExpensesValue = new javax.swing.JLabel();
        lblBalanceValue = new javax.swing.JLabel();
        panelIncome = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        incomeJList = new javax.swing.JList<>();
        lblIncomeTitle = new javax.swing.JLabel();
        btnIncomeDelete = new javax.swing.JButton();
        btnIncomeCheck = new javax.swing.JButton();
        lblCheckError = new javax.swing.JLabel();
        panelExpense = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        expenseJList = new javax.swing.JList<>();
        btnExpenseDelete = new javax.swing.JButton();
        btnExpenseCheck = new javax.swing.JButton();
        lblExpenseTitle = new javax.swing.JLabel();
        lblCheckError2 = new javax.swing.JLabel();
        btnIncome = new javax.swing.JButton();
        btnExpense = new javax.swing.JButton();
        panelAddIncome = new javax.swing.JPanel();
        lblAddIncome = new javax.swing.JLabel();
        comboBoxIncome = new javax.swing.JComboBox<>();
        lblTypeIncome = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        lblFrequency = new javax.swing.JLabel();
        comboBoxIncomeFrequency = new javax.swing.JComboBox<>();
        lblIncomeAmount = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        lblIncomePhoto = new javax.swing.JLabel();
        btnAddIncome2 = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblIncomeError = new javax.swing.JLabel();
        lblIncomeAmountError = new javax.swing.JLabel();
        panelAddExpense = new javax.swing.JPanel();
        lblAddIExpense = new javax.swing.JLabel();
        comboBoxExpense = new javax.swing.JComboBox<>();
        lblTypeExpense = new javax.swing.JLabel();
        lblDescription1 = new javax.swing.JLabel();
        txtDescription1 = new javax.swing.JTextField();
        lblFrequency1 = new javax.swing.JLabel();
        comboBoxExpenseFrequency = new javax.swing.JComboBox<>();
        lblExpenseAmount = new javax.swing.JLabel();
        txtAmount1 = new javax.swing.JTextField();
        lblMoneyImage = new javax.swing.JLabel();
        btnAddExpense = new javax.swing.JButton();
        btnCancel1 = new javax.swing.JButton();
        lblExpenseError = new javax.swing.JLabel();
        lblExpenseAmountError = new javax.swing.JLabel();

        panelLogin.setBackground(new java.awt.Color(34, 34, 34));
        panelLogin.setForeground(new java.awt.Color(34, 34, 34));
        panelLogin.setToolTipText("");
        panelLogin.setMaximumSize(new java.awt.Dimension(800, 500));
        panelLogin.setMinimumSize(new java.awt.Dimension(800, 500));
        panelLogin.setPreferredSize(new java.awt.Dimension(800, 500));

        btnLogin.setBackground(new java.awt.Color(0, 0, 51));
        btnLogin.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        lblUsernameLog.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        lblUsernameLog.setForeground(new java.awt.Color(255, 255, 255));
        lblUsernameLog.setText("Username:");

        txtUsernameLog.setBackground(new java.awt.Color(34, 34, 34));
        txtUsernameLog.setForeground(new java.awt.Color(255, 255, 255));

        lblLogin1.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        lblLogin1.setForeground(new java.awt.Color(255, 255, 255));
        lblLogin1.setText("Login to Your Account");

        lblPasswordLog.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        lblPasswordLog.setForeground(new java.awt.Color(255, 255, 255));
        lblPasswordLog.setText("Password:");

        lblSignUp.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        lblSignUp.setForeground(new java.awt.Color(0, 153, 255));
        lblSignUp.setText("Sign Up");
        lblSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSignUpMouseClicked(evt);
            }
        });

        lblNoAccount1.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        lblNoAccount1.setForeground(new java.awt.Color(255, 255, 255));
        lblNoAccount1.setText("Don't have an account?");

        lblIncorrect.setForeground(new java.awt.Color(255, 0, 51));

        lblImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/blueBg.jpg"))); // NOI18N

        btnLoginExit.setBackground(new java.awt.Color(0, 0, 102));
        btnLoginExit.setForeground(new java.awt.Color(255, 255, 255));
        btnLoginExit.setText("Exit");
        btnLoginExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addContainerGap(77, Short.MAX_VALUE)
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLoginLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(lblUsernameLog, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLoginLayout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLoginLayout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(lblNoAccount1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(lblSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLoginLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(lblIncorrect, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblLogin1)
                            .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtUsernameLog, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelLoginLayout.createSequentialGroup()
                                    .addGap(40, 40, 40)
                                    .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblPasswordLog, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPasswordLog, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(64, 64, 64))
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnLoginExit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(lblImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(lblLogin1)
                .addGap(32, 32, 32)
                .addComponent(lblUsernameLog)
                .addGap(20, 20, 20)
                .addComponent(txtUsernameLog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(lblPasswordLog)
                .addGap(7, 7, 7)
                .addComponent(txtPasswordLog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIncorrect, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogin)
                .addGap(14, 14, 14)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNoAccount1)
                    .addComponent(lblSignUp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLoginExit)
                .addGap(106, 106, 106))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("University Finance Manager");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelSignUp.setBackground(new java.awt.Color(34, 34, 34));
        panelSignUp.setForeground(new java.awt.Color(34, 34, 34));
        panelSignUp.setMaximumSize(new java.awt.Dimension(800, 500));
        panelSignUp.setMinimumSize(new java.awt.Dimension(800, 500));
        panelSignUp.setPreferredSize(new java.awt.Dimension(800, 500));

        btnSignup.setBackground(new java.awt.Color(0, 0, 51));
        btnSignup.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        btnSignup.setForeground(new java.awt.Color(255, 255, 255));
        btnSignup.setText("Sign Up");
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });

        lblUsername.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("Username:");

        txtUsername.setBackground(new java.awt.Color(34, 34, 34));
        txtUsername.setForeground(new java.awt.Color(255, 255, 255));

        lblCreate.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        lblCreate.setForeground(java.awt.Color.white);
        lblCreate.setText("Create Account");

        lblPassword.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword.setText("Password:");

        lblLogin.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(51, 153, 255));
        lblLogin.setText("Login");
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoginMouseClicked(evt);
            }
        });

        lblNoAccount.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        lblNoAccount.setForeground(new java.awt.Color(255, 255, 255));
        lblNoAccount.setText("Already have an account?");

        lblErrorName.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 11)); // NOI18N
        lblErrorName.setForeground(new java.awt.Color(255, 51, 51));

        lblErrorPW.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 11)); // NOI18N
        lblErrorPW.setForeground(new java.awt.Color(255, 0, 51));

        lblImage2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/blueBg.jpg"))); // NOI18N

        javax.swing.GroupLayout panelSignUpLayout = new javax.swing.GroupLayout(panelSignUp);
        panelSignUp.setLayout(panelSignUpLayout);
        panelSignUpLayout.setHorizontalGroup(
            panelSignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSignUpLayout.createSequentialGroup()
                .addGroup(panelSignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSignUpLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(panelSignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblErrorPW, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelSignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelSignUpLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(lblErrorName, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panelSignUpLayout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(lblCreate))
                    .addGroup(panelSignUpLayout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(btnSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSignUpLayout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(lblNoAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(lblImage2, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelSignUpLayout.setVerticalGroup(
            panelSignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSignUpLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(lblCreate)
                .addGap(34, 34, 34)
                .addComponent(lblUsername)
                .addGap(18, 18, 18)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorName, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPassword)
                .addGap(18, 18, 18)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorPW, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSignup)
                .addGap(18, 18, 18)
                .addGroup(panelSignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoAccount)
                    .addComponent(lblLogin))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSignUpLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblImage2))
        );

        tabbedPane.addTab("tab2", panelSignUp);

        panelHome.setBackground(new java.awt.Color(34, 34, 34));
        panelHome.setForeground(new java.awt.Color(34, 34, 34));

        lblCreate1.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        lblCreate1.setForeground(java.awt.Color.white);
        lblCreate1.setText("Home");
        lblCreate1.setToolTipText("");

        btnAddBudget.setBackground(new java.awt.Color(0, 0, 102));
        btnAddBudget.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        btnAddBudget.setForeground(new java.awt.Color(255, 255, 255));
        btnAddBudget.setText("+ New Budget");
        btnAddBudget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBudgetActionPerformed(evt);
            }
        });

        btnSelectBudget.setBackground(new java.awt.Color(0, 0, 102));
        btnSelectBudget.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        btnSelectBudget.setForeground(new java.awt.Color(255, 255, 255));
        btnSelectBudget.setText("Select Budget");
        btnSelectBudget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectBudgetActionPerformed(evt);
            }
        });

        budgetJlist.setBackground(new java.awt.Color(102, 102, 102));
        budgetJlist.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(budgetJlist);

        lblYourBudgets.setBackground(new java.awt.Color(255, 255, 255));
        lblYourBudgets.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        lblYourBudgets.setForeground(new java.awt.Color(255, 255, 255));
        lblYourBudgets.setText("Your Budgets:");

        lblGreeting.setBackground(new java.awt.Color(255, 255, 255));
        lblGreeting.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        lblGreeting.setForeground(new java.awt.Color(255, 255, 255));
        lblGreeting.setText(" ");

        lblSelectBudgetError.setBackground(new java.awt.Color(255, 0, 51));
        lblSelectBudgetError.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSelectBudgetError.setForeground(new java.awt.Color(255, 0, 0));
        lblSelectBudgetError.setText(" ");

        lblProfile.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        lblProfile.setForeground(new java.awt.Color(255, 255, 255));
        lblProfile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblProfile.setText(" ");

        txtProfile.setEditable(false);
        txtProfile.setBackground(new java.awt.Color(153, 153, 255));
        txtProfile.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        txtProfile.setForeground(new java.awt.Color(0, 0, 51));
        txtProfile.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProfile.setText(" ");

        btnAppExit.setBackground(new java.awt.Color(0, 0, 102));
        btnAppExit.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        btnAppExit.setForeground(new java.awt.Color(255, 255, 255));
        btnAppExit.setText("Exit");
        btnAppExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAppExitActionPerformed(evt);
            }
        });

        btnDeleteBudget.setBackground(new java.awt.Color(0, 0, 102));
        btnDeleteBudget.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        btnDeleteBudget.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteBudget.setText("Delete");
        btnDeleteBudget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBudgetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelHomeLayout = new javax.swing.GroupLayout(panelHome);
        panelHome.setLayout(panelHomeLayout);
        panelHomeLayout.setHorizontalGroup(
            panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHomeLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHomeLayout.createSequentialGroup()
                        .addComponent(btnAppExit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHomeLayout.createSequentialGroup()
                        .addGroup(panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelHomeLayout.createSequentialGroup()
                                .addComponent(lblYourBudgets, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSelectBudgetError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                            .addComponent(lblGreeting, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSelectBudget, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddBudget, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDeleteBudget, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(76, 76, 76))
                    .addGroup(panelHomeLayout.createSequentialGroup()
                        .addComponent(lblCreate1)
                        .addGap(43, 625, Short.MAX_VALUE))))
        );
        panelHomeLayout.setVerticalGroup(
            panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHomeLayout.createSequentialGroup()
                .addGroup(panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHomeLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btnAppExit)
                        .addGap(18, 18, 18)
                        .addComponent(lblCreate1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHomeLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProfile)
                            .addComponent(txtProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(62, 62, 62)))
                .addGroup(panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHomeLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnAddBudget))
                    .addComponent(lblGreeting))
                .addGap(34, 34, 34)
                .addGroup(panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYourBudgets)
                    .addComponent(lblSelectBudgetError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelHomeLayout.createSequentialGroup()
                        .addComponent(btnDeleteBudget)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSelectBudget)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        tabbedPane.addTab("tab3", panelHome);

        panelAddBudget.setBackground(new java.awt.Color(34, 34, 34));
        panelAddBudget.setForeground(new java.awt.Color(34, 34, 34));

        lblCreate2.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        lblCreate2.setForeground(java.awt.Color.white);
        lblCreate2.setText("New Budget");
        lblCreate2.setToolTipText("");

        lblPromptName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPromptName.setForeground(new java.awt.Color(255, 255, 255));
        lblPromptName.setText("What is the name of the budget?");

        txtNameBudget.setBackground(new java.awt.Color(34, 34, 34));
        txtNameBudget.setForeground(new java.awt.Color(255, 255, 255));

        btnAddBudget2.setBackground(new java.awt.Color(0, 0, 102));
        btnAddBudget2.setForeground(new java.awt.Color(255, 255, 255));
        btnAddBudget2.setText("Add Budget");
        btnAddBudget2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBudget2ActionPerformed(evt);
            }
        });

        lblPig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/budgetPig.png"))); // NOI18N

        btnGoBack.setBackground(new java.awt.Color(0, 0, 102));
        btnGoBack.setForeground(new java.awt.Color(255, 255, 255));
        btnGoBack.setText("Back to Home");
        btnGoBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoBackActionPerformed(evt);
            }
        });

        lblBudgetError.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBudgetError.setForeground(new java.awt.Color(255, 0, 0));

        lblYear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblYear.setForeground(new java.awt.Color(255, 255, 255));
        lblYear.setText("Please select the year of the budget:");

        comboBoxYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));

        javax.swing.GroupLayout panelAddBudgetLayout = new javax.swing.GroupLayout(panelAddBudget);
        panelAddBudget.setLayout(panelAddBudgetLayout);
        panelAddBudgetLayout.setHorizontalGroup(
            panelAddBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddBudgetLayout.createSequentialGroup()
                .addContainerGap(79, Short.MAX_VALUE)
                .addComponent(lblPig, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(panelAddBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnAddBudget2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblPromptName, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNameBudget, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                        .addComponent(lblCreate2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblBudgetError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblYear, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comboBoxYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
            .addGroup(panelAddBudgetLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnGoBack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAddBudgetLayout.setVerticalGroup(
            panelAddBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddBudgetLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnGoBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCreate2)
                .addGroup(panelAddBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddBudgetLayout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(lblPromptName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNameBudget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblYear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboBoxYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBudgetError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelAddBudgetLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(lblPig)))
                .addGap(15, 15, 15)
                .addComponent(btnAddBudget2)
                .addGap(45, 45, 45))
        );

        tabbedPane.addTab("tab4", panelAddBudget);

        panelBudget.setBackground(new java.awt.Color(34, 34, 34));
        panelBudget.setForeground(new java.awt.Color(34, 34, 34));
        panelBudget.setPreferredSize(new java.awt.Dimension(800, 500));

        btnBackHome.setText("Back to Home");
        btnBackHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackHomeActionPerformed(evt);
            }
        });

        txtBudgetTitle.setEditable(false);
        txtBudgetTitle.setBackground(new java.awt.Color(34, 34, 34));
        txtBudgetTitle.setFont(new java.awt.Font("Tempus Sans ITC", 0, 30)); // NOI18N
        txtBudgetTitle.setForeground(new java.awt.Color(255, 255, 255));
        txtBudgetTitle.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnAddIncome.setBackground(new java.awt.Color(0, 0, 102));
        btnAddIncome.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        btnAddIncome.setForeground(new java.awt.Color(255, 255, 255));
        btnAddIncome.setText("+ Add Income");
        btnAddIncome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddIncomeActionPerformed(evt);
            }
        });

        addExpense.setBackground(new java.awt.Color(0, 0, 102));
        addExpense.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        addExpense.setForeground(new java.awt.Color(255, 255, 255));
        addExpense.setText("+ Add Expense");
        addExpense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addExpenseActionPerformed(evt);
            }
        });

        panelSummary.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        lblIncomeSub.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        lblIncomeSub.setText("Income ($)");

        lblExpenseSub.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        lblExpenseSub.setText("Expenses ($)");

        lblBalanceSub.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        lblBalanceSub.setText("Balance ($)");

        lblIncomeValue.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        lblIncomeValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIncomeValue.setText(" ");

        lblExpensesValue.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        lblExpensesValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblExpensesValue.setText(" ");

        lblBalanceValue.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        lblBalanceValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBalanceValue.setText(" ");

        javax.swing.GroupLayout panelSummaryLayout = new javax.swing.GroupLayout(panelSummary);
        panelSummary.setLayout(panelSummaryLayout);
        panelSummaryLayout.setHorizontalGroup(
            panelSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSummaryLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSummaryLayout.createSequentialGroup()
                        .addGroup(panelSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblExpenseSub, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBalanceSub, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBalanceValue, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblExpensesValue, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelSummaryLayout.createSequentialGroup()
                        .addComponent(lblIncomeSub, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblIncomeValue, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))))
        );
        panelSummaryLayout.setVerticalGroup(
            panelSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSummaryLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIncomeSub)
                    .addComponent(lblIncomeValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpenseSub)
                    .addComponent(lblExpensesValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBalanceSub)
                    .addComponent(lblBalanceValue))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        panelIncome.setBackground(new java.awt.Color(51, 51, 51));
        panelIncome.setPreferredSize(new java.awt.Dimension(800, 260));

        jScrollPane2.setForeground(new java.awt.Color(153, 153, 153));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(800, 260));

        incomeJList.setBackground(new java.awt.Color(34, 34, 34));
        incomeJList.setForeground(new java.awt.Color(255, 255, 255));
        incomeJList.setPreferredSize(new java.awt.Dimension(800, 260));
        jScrollPane2.setViewportView(incomeJList);

        lblIncomeTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblIncomeTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblIncomeTitle.setText("INCOME:");

        btnIncomeDelete.setText("Delete");
        btnIncomeDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncomeDeleteActionPerformed(evt);
            }
        });

        btnIncomeCheck.setText("Check");
        btnIncomeCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncomeCheckActionPerformed(evt);
            }
        });

        lblCheckError.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCheckError.setForeground(new java.awt.Color(255, 0, 0));
        lblCheckError.setText(" ");

        javax.swing.GroupLayout panelIncomeLayout = new javax.swing.GroupLayout(panelIncome);
        panelIncome.setLayout(panelIncomeLayout);
        panelIncomeLayout.setHorizontalGroup(
            panelIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIncomeLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblIncomeTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCheckError, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIncomeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIncomeCheck)
                    .addComponent(btnIncomeDelete))
                .addGap(23, 23, 23))
            .addGroup(panelIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelIncomeLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(124, Short.MAX_VALUE)))
        );
        panelIncomeLayout.setVerticalGroup(
            panelIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIncomeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIncomeTitle)
                    .addComponent(lblCheckError))
                .addGap(12, 12, 12)
                .addComponent(btnIncomeDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnIncomeCheck)
                .addContainerGap(144, Short.MAX_VALUE))
            .addGroup(panelIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIncomeLayout.createSequentialGroup()
                    .addContainerGap(38, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        panelExpense.setBackground(new java.awt.Color(51, 51, 51));
        panelExpense.setPreferredSize(new java.awt.Dimension(800, 260));

        jScrollPane1.setForeground(new java.awt.Color(102, 102, 102));

        expenseJList.setBackground(new java.awt.Color(34, 34, 34));
        expenseJList.setForeground(new java.awt.Color(255, 255, 255));
        expenseJList.setPreferredSize(new java.awt.Dimension(800, 260));
        jScrollPane1.setViewportView(expenseJList);

        btnExpenseDelete.setText("Delete");
        btnExpenseDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpenseDeleteActionPerformed(evt);
            }
        });

        btnExpenseCheck.setText("Check");
        btnExpenseCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpenseCheckActionPerformed(evt);
            }
        });

        lblExpenseTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblExpenseTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblExpenseTitle.setText("EXPENSES:");

        lblCheckError2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCheckError2.setForeground(new java.awt.Color(255, 0, 0));
        lblCheckError2.setText(" ");

        javax.swing.GroupLayout panelExpenseLayout = new javax.swing.GroupLayout(panelExpense);
        panelExpense.setLayout(panelExpenseLayout);
        panelExpenseLayout.setHorizontalGroup(
            panelExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExpenseLayout.createSequentialGroup()
                .addGroup(panelExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelExpenseLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lblExpenseTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCheckError2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelExpenseLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(panelExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExpenseDelete, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnExpenseCheck, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(21, 21, 21))
        );
        panelExpenseLayout.setVerticalGroup(
            panelExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelExpenseLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(panelExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpenseTitle)
                    .addComponent(lblCheckError2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelExpenseLayout.createSequentialGroup()
                        .addComponent(btnExpenseDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExpenseCheck))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnIncome.setText("Income");
        btnIncome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncomeActionPerformed(evt);
            }
        });

        btnExpense.setText("Expense");
        btnExpense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpenseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBudgetLayout = new javax.swing.GroupLayout(panelBudget);
        panelBudget.setLayout(panelBudgetLayout);
        panelBudgetLayout.setHorizontalGroup(
            panelBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelIncome, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
            .addGroup(panelBudgetLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(panelSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBudgetLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addExpense)
                            .addComponent(btnAddIncome))
                        .addGap(102, 102, 102))
                    .addGroup(panelBudgetLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(btnIncome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExpense)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(panelBudgetLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btnBackHome, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(txtBudgetTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelExpense, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE))
        );
        panelBudgetLayout.setVerticalGroup(
            panelBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBudgetLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBudgetTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBackHome))
                .addGroup(panelBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBudgetLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnAddIncome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addExpense))
                    .addGroup(panelBudgetLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(panelBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnIncome)
                                .addComponent(btnExpense))
                            .addComponent(panelSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(panelIncome, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))
            .addGroup(panelBudgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBudgetLayout.createSequentialGroup()
                    .addGap(0, 232, Short.MAX_VALUE)
                    .addComponent(panelExpense, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tabbedPane.addTab("tab5", panelBudget);

        panelAddIncome.setPreferredSize(new java.awt.Dimension(800, 500));

        lblAddIncome.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        lblAddIncome.setForeground(new java.awt.Color(0, 0, 102));
        lblAddIncome.setText("Add Income");
        lblAddIncome.setToolTipText("");

        comboBoxIncome.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        comboBoxIncome.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Savings", "Family Contribution", "Work Income", "Student Loans", "Scholarship/Bursary/Grants", "Other" }));

        lblTypeIncome.setText("What type of income is this?");

        lblDescription.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        lblDescription.setText("Description:");

        lblFrequency.setText("What is the frequency?");

        comboBoxIncomeFrequency.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "One Time", "Semi-Annual", "Quarterly", "Monthly", "Weekly", "Bi-Weekly", "Daily" }));

        lblIncomeAmount.setText("Amount ($):");

        lblIncomePhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Money.png"))); // NOI18N
        lblIncomePhoto.setText("jLabel7");

        btnAddIncome2.setText("Add");
        btnAddIncome2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddIncome2ActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblIncomeError.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIncomeError.setForeground(new java.awt.Color(255, 0, 0));

        lblIncomeAmountError.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIncomeAmountError.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout panelAddIncomeLayout = new javax.swing.GroupLayout(panelAddIncome);
        panelAddIncome.setLayout(panelAddIncomeLayout);
        panelAddIncomeLayout.setHorizontalGroup(
            panelAddIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddIncomeLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(panelAddIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddIncomeLayout.createSequentialGroup()
                        .addComponent(lblIncomeAmountError, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelAddIncomeLayout.createSequentialGroup()
                        .addGroup(panelAddIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboBoxIncomeFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboBoxIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAddIncome)
                            .addComponent(lblTypeIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescription)
                            .addComponent(lblIncomeAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFrequency, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                            .addComponent(txtAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                            .addComponent(lblIncomeError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panelAddIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAddIncomeLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                                .addComponent(lblIncomePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72))
                            .addGroup(panelAddIncomeLayout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(btnAddIncome2)
                                .addGap(62, 62, 62)
                                .addComponent(btnCancel)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        panelAddIncomeLayout.setVerticalGroup(
            panelAddIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddIncomeLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblAddIncome)
                .addGroup(panelAddIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddIncomeLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lblTypeIncome)
                        .addGap(29, 29, 29)
                        .addComponent(comboBoxIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(lblDescription)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblIncomeError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFrequency)
                        .addGap(18, 18, 18)
                        .addComponent(comboBoxIncomeFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(lblIncomeAmount))
                    .addGroup(panelAddIncomeLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblIncomePhoto)))
                .addGap(18, 18, 18)
                .addGroup(panelAddIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddIncome2)
                    .addComponent(btnCancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIncomeAmountError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        tabbedPane.addTab("tab6", panelAddIncome);

        lblAddIExpense.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        lblAddIExpense.setForeground(new java.awt.Color(0, 0, 102));
        lblAddIExpense.setText("Add Expenses");
        lblAddIExpense.setToolTipText("");

        comboBoxExpense.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        comboBoxExpense.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Education", "Living", "Transportation", "Personal", "Other" }));

        lblTypeExpense.setText("What type of income is this?");

        lblDescription1.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
        lblDescription1.setText("Description:");

        lblFrequency1.setText("What is the frequency?");

        comboBoxExpenseFrequency.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "One Time", "Semi-Annual", "Quarterly", "Monthly", "Weekly", "Bi-Weekly", "Daily" }));

        lblExpenseAmount.setText("Amount ($):");

        lblMoneyImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/looseMoney.png"))); // NOI18N

        btnAddExpense.setText("Add");
        btnAddExpense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddExpenseActionPerformed(evt);
            }
        });

        btnCancel1.setText("Cancel");
        btnCancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancel1ActionPerformed(evt);
            }
        });

        lblExpenseError.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblExpenseError.setForeground(new java.awt.Color(255, 0, 0));
        lblExpenseError.setText(" ");

        lblExpenseAmountError.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblExpenseAmountError.setForeground(new java.awt.Color(255, 0, 0));
        lblExpenseAmountError.setText(" ");

        javax.swing.GroupLayout panelAddExpenseLayout = new javax.swing.GroupLayout(panelAddExpense);
        panelAddExpense.setLayout(panelAddExpenseLayout);
        panelAddExpenseLayout.setHorizontalGroup(
            panelAddExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddExpenseLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(panelAddExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddExpenseLayout.createSequentialGroup()
                        .addComponent(lblExpenseAmountError, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelAddExpenseLayout.createSequentialGroup()
                        .addGroup(panelAddExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboBoxExpenseFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboBoxExpense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAddIExpense)
                            .addComponent(lblTypeExpense, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDescription1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescription1)
                            .addComponent(lblExpenseAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFrequency1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                            .addComponent(txtAmount1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                            .addComponent(lblExpenseError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panelAddExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAddExpenseLayout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(btnAddExpense)
                                .addGap(62, 62, 62)
                                .addComponent(btnCancel1)
                                .addContainerGap(140, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddExpenseLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblMoneyImage, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89))))))
        );
        panelAddExpenseLayout.setVerticalGroup(
            panelAddExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddExpenseLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblAddIExpense)
                .addGroup(panelAddExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddExpenseLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lblTypeExpense)
                        .addGap(29, 29, 29)
                        .addComponent(comboBoxExpense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(lblDescription1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescription1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblExpenseError)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblFrequency1)
                        .addGap(18, 18, 18)
                        .addComponent(comboBoxExpenseFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddExpenseLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMoneyImage)
                        .addGap(2, 2, 2)))
                .addComponent(lblExpenseAmount)
                .addGap(18, 18, 18)
                .addGroup(panelAddExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddExpense)
                    .addComponent(btnCancel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblExpenseAmountError)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        tabbedPane.addTab("tab7", panelAddExpense);

        getContentPane().add(tabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, -1, 540));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //Initialize the models for modifying the Jlists.
    
    DefaultListModel mod = new DefaultListModel();
    DefaultListModel modIncome = new DefaultListModel();
    DefaultListModel modExpense = new DefaultListModel();
    
    
    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignupActionPerformed
        // TODO add your handling code here:
        
        /**
         * This section is when the user click the sign-up button.
         * It will check if it's a valid sign-up.
         * If valid, new user data will be written in files and a new file for the user will be created.
         */
        
        //Reset error messages fields.
        lblErrorName.setText("");
        lblErrorName.setText("");
        
        //Get usernames and passwords in string format.
        String usernameInput = txtUsername.getText();
        char[] passwordInput = txtPassword.getPassword();
        String password = new String(passwordInput);
        
        //Check if input is valid.
        
        //Check if the username is less than 4 characters.
        if (usernameInput.length() < 4){
            lblErrorName.setText("*Your username should include atleast 4 characters.");  //Error message.
            
            //Check if the username is longer than 15 characters.
        }else if (usernameInput.length() > 15){
            lblErrorName.setText("*Your username should be less than 15 characters.");   //Error message.
            
            //Check if the password length is less than 4 characters.
        }else if (password.length() < 4){
            lblErrorPW.setText("*Your password should include atleast 4 characters.");   //Error message.
            
            //Check if the password length is longer than 4 characters.
        }else if (password.length() > 15){
            lblErrorName.setText("*Your password should be less than 15 characters.");   //Error message.
            
            //Check if the username is the same as the all user file name.
        }else if (usernameInput.toLowerCase().equals("userlogin")){
            lblErrorName.setText("*Your username can not be \"UserLogin\" in any case.");   //Error message.
            
            //Check if the username includes commas.
        }else if (ifCommas(usernameInput) == true){
            lblErrorName.setText("*Your username can not include commas.");   //Error message.
            
            //Check if the password includes commas.
        }else if (ifCommas(password) == true){
            lblErrorPW.setText("*Your password can not include commas.");   //Error message.
        }else{  //The format of the username and password is correct.
            
            String[] userArray = convertToArray();  //Convert the userList to array.
        
            //Search the username in the array.
            int index = binarySearch(userArray,usernameInput);
            
            //Check if it's an already existing user.
            if (index != -1){
                //Error message.
                lblErrorName.setText("*The username have been taken.");
            }else{  //New user.
                
                String[] tempBudgetArray = {};

                //Create new user object and add it to the arrayList.
                userList.add(new User(usernameInput,password,tempBudgetArray));

                //Convert the updated arraylist to array.
                String[] userArrayFinal = convertToArray();
  
                //Sort the arrayList.
                quickSortAscend(userArrayFinal);

                
                //Write into the all user file.
                try {

                    //Open the UserLogin.txt file.
                    BufferedWriter writer = new BufferedWriter(new FileWriter("UserLogin.txt"));
                    
                    for (int i = 0; i<userList.size();i++){  //Go through all users.
                        //Write into the file, using the toString() method.
                        
                        writer.write(userList.get(i).toString()+"\n");
                     
                    }

                    writer.close();  //Close file.

                } catch (IOException e) {
                    e.printStackTrace();    //Write if there's an error.    
                }
                
                //Write into the file.
                try {
                    //Create a new user file.
                    BufferedWriter writer = new BufferedWriter(new FileWriter(usernameInput.toLowerCase()+".txt"));
                    
                    writer.close();  //Close file.

                } catch (IOException e) {
                    e.printStackTrace();    //Write if there's an error.    
                }
                
                //Pop-up for successful sign-up.
                JOptionPane.showMessageDialog(null,"Signup Successful\nPlease login using the login form.");

                tabbedPane.setSelectedIndex(0);  //Change view.
                
                txtUsername.setText("");  //Reset the text fields.
                txtPassword.setText("");
                
            }
        }
    }//GEN-LAST:event_btnSignupActionPerformed

    private void lblLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginMouseClicked
        // TODO add your handling code here:
        
        /**
         * When the login text on the sign-up form is being clicked.
         * View transferred to the login page.
         */
        tabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_lblLoginMouseClicked

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
 
        /**
         * This section is when the user click the button to login.
         * It will check if the inputs are valid.
         * If yes, the user is allowed to be into the rest of the application.
         */
        
        //Reset text field for the error messages.
        lblIncorrect.setText("");
        
        //Get user input.
        String usernameInput = txtUsernameLog.getText();
        char[] passwordInput = txtPasswordLog.getPassword();
        String password = new String(passwordInput);
        
        //Convert userList to array then use the binarySearch() method to search the username.
        String[] userArray = convertToArray();
        int index = binarySearch(userArray,usernameInput);
        
        //If username not found.
        if (index == -1){   //Error message.
            lblIncorrect.setText("*The username/password is incorrect. Please try again.");
        }else{  //User found.
        
            //Get the password for this user.
            String userPassword = userList.get(index).getPassword();
            
            //Check if the input password is the same as the real password recorded.
            if (password.equals(userPassword)){
                username = usernameInput;  //Set global username as the username input.
                
                //Set the userIndex(global) in all of the users of this app.
                userIndex = binarySearch(userArray,username);
                
                
                //Read from the user file.
                try {

                    //Open the file.
                    BufferedReader reader = new BufferedReader(new FileReader(username+".txt"));
                    String line;  //Initalize a string.

                    String[] lineArray;  //Initialize an array.
                    
                    //While the line does not equal to null.
                    while ((line=reader.readLine()) != null && line.equals("") == false){

                        //Split the string.
                        lineArray = line.split(",");

                        //Get all the data from the user.
                        String budgetName = lineArray[0];
                        String transactionType = lineArray[1];
                        String transactionName = lineArray[2];
                        String moneyType = lineArray[3];
                        String transactionFrequency = lineArray[4];
                        String transactionAmount = lineArray[5];
                        
                        //Check if the transaction type is income.
                        if (transactionType.equals("income")){
                            //Create new income objects and add it to the incomeList.
                            incomeList.add(new Income(budgetName,transactionName,moneyType,transactionFrequency,transactionAmount));
                        }else{
                            //Create new expense objects and add it to the expenseList.
                            expenseList.add(new Expenses(budgetName,transactionName,moneyType,transactionFrequency,transactionAmount));
                        }
                        
                        

                    }

                    reader.close();  //Close file.

                } catch (IOException e) {
                    e.printStackTrace();
                }
        
                //Initialize a budgetMap.
                //This will be used to sort the incomes into the specific budgets.
                Map<String, Budget> budgetMap = new HashMap<>();
                
                //Go through all income objects in the incomeList.
                for (Income income:incomeList){
                    //Get the budget name from the income object.
                    //Which is the budget that the income belongs to.
                    String budgetName = income.getBudgetName();
                    
                    //Check if the budgetMap already have the budgetName.
                    if (!budgetMap.containsKey(budgetName)){  //Does not include.
                        //Create a new budget object.
                        Budget budget = new Budget(username,budgetName,new ArrayList<>(),new ArrayList<>());
                        //Put the new budget name and the object to the map.
                        budgetMap.put(budgetName, budget);
                    }
                    
                    //Add the income object to the budget object.
                    budgetMap.get(budgetName).getIncomes().add(income);
                }
                
                //Go through all expenses object in the list.
                for (Expenses expense:expenseList){
                    //Get the budget name that the expense belongs to.
                    String budgetName = expense.getBudgetName();
                    
                    //Check if the budget name already exists in the map.
                    if (!budgetMap.containsKey(budgetName)){
                        //Create new budget object.
                        Budget budget = new Budget(username,budgetName,new ArrayList<>(),new ArrayList<>());
                        budgetMap.put(budgetName, budget);  //Put new object to map.
                    }
                    
                    //Add the expense object to the budget object.
                    budgetMap.get(budgetName).getExpenses().add(expense);
                }
                
                //For every budget in map.
                for (Budget budget:budgetMap.values()){
                    //Add the budget objects to the arraylist.
                    budgetList.add(budget);
                }
                
                //Create a string array list of budget names from budgetList.
                //This section is for the budgets that does not have any income or expenses.
                //Because if the budget does not have any transactions, it would not be recorded in the user file.
                ArrayList<String> tempBudgetList = new ArrayList<>();
                
                //Get all budget names into the temporary array list.
                for (int i = 0; i<budgetList.size();i++){
                    tempBudgetList.add(budgetList.get(i).getName());
                    
                }
   
                //Create a string arrayList.
                ArrayList<String>tempCurrentUser = new ArrayList<>();
                //Get all budget names stored in current user.
                String[] tempAllBudget = userList.get(userIndex).getBudgetNames();
                
                //Get all budget stored in the user object into the new arraylist.
                for (int i = 0; i<tempAllBudget.length;i++){
                    tempCurrentUser.add(tempAllBudget[i]);
                    
                }
                //Initialize a budgetSet.
                HashSet<String> budgetSet = new HashSet<>(tempBudgetList);

                //Go through all strings in the arrayList.
                for (String name:tempAllBudget){
                    if (!budgetSet.contains(name)){  //If the budget does not contain this budget name
                        //Add a new budget object to budgetList.
                        budgetList.add(new Budget(username,name,new ArrayList<>(),new ArrayList<>()));
                    }
                }
                
                //Pop-up for successful log-in.
                JOptionPane.showMessageDialog(null,"Login Successful");

                //Set the greeting.
                lblGreeting.setText("Hello "+username + "!");
                
                //Set profile.
                lblProfile.setText(username);
                //Get first character of the username for the avatar.
                char firstCharact = username.charAt(0);
                String firstCharacter = firstCharact + "";
                
                txtProfile.setText(firstCharacter.toUpperCase());
                
                //Set mod as budgetJlist's model.
                budgetJlist.setModel(mod);

                //Output all budgetNames in the Jlist.
                for (int i = 0; i<budgetList.size();i++){
                     //Add Item.
                    mod.addElement(budgetList.get(i).getName());
                }

                //Change panel.
                tabbedPane.setSelectedIndex(2);
                
            }else{ //Invalid inputs.
                lblIncorrect.setText("*The username/password is incorrect. Please try again.");  //Error messages.
            }
            

        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void lblSignUpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSignUpMouseClicked
        // TODO add your handling code here:
        /**
         * If the user click the sign up text on the login page.
         * The user now will be transferred to the sign up form.
         */
        tabbedPane.setSelectedIndex(1);  //Change panel.
        
    }//GEN-LAST:event_lblSignUpMouseClicked

    private void btnAddBudgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBudgetActionPerformed
        // TODO add your handling code here:
        
        /**
         * If the user click the new budget button on home page.
         * User will be transferred to the add budget panel.
         * And if there were any error messages, it will be reset.
         */
        lblSelectBudgetError.setText("");
        //Reset text fields.
        lblBudgetError.setText("");
        txtNameBudget.setText("");
        
        tabbedPane.setSelectedIndex(3);

    }//GEN-LAST:event_btnAddBudgetActionPerformed

    private void btnGoBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoBackActionPerformed
        // TODO add your handling code here:
        
        //From add budget back to home.
        tabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_btnGoBackActionPerformed

    private void btnAddBudget2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBudget2ActionPerformed
        // TODO add your handling code here:
        
        /**
         * This section is when the user want to add a budget 
         * on the add budget panel.
         */
        
        //Reset text fields.
        lblBudgetError.setText("");
        
        //Get user inputs.
        String budgetName = txtNameBudget.getText();
        String budgetYear = comboBoxYear.getSelectedItem().toString();
         
        //Generate the full budget name.
        String fullBudgetName = budgetName + " - " + budgetYear;
        
        //Check for valid input.
        
        //Check if the budgetName is empty.
        if (budgetName == null || budgetName.equals("")){
            lblBudgetError.setText("*Please enter budget name.");  //Error message.
            
            //Check if the budgetName is the same as "**no_budgets", because this string is being used in the file for actual no budget users.
        }else if (budgetName.equals("**no_budgets")){
            lblBudgetError.setText("*The budget name can't be \"**no_budgets\".");  //Error message.
            
            //Check if the budget for this user already existed.
        }else if (linearSearch(userList.get(userIndex).getBudgetNames(),fullBudgetName) != -1){
            lblBudgetError.setText("*The budget already exists.");  //Error message.
            
            //Check if commas are included,because data in files are seperated using commas.
        }else if (ifCommas(budgetName) == true){
            lblBudgetError.setText("*The budget can not include commas.");  //Error message.
            
            //Check if the name is too long.
        }else if (budgetName.length() > 18){
            lblBudgetError.setText("*The budget can not be longder than 18 characters.");   //Error message.
        }else{
            
            //Set the global name for the current budget the user is looking at.
            globalBudgetName = budgetName + " - " + budgetYear;
            
            //Set the title of the budget page.
            txtBudgetTitle.setText(globalBudgetName);
            
            //Since new budget has no transactions, so all values being set to 0.0
            lblIncomeValue.setText("0.0");
            lblExpensesValue.setText("0.0");
            lblBalanceValue.setText("0.0");
            
            //Temporary lists.
            ArrayList<Income> tempIncome = new ArrayList<>();
            ArrayList<Expenses> tempExpense = new ArrayList<>();
            
            //Create the new budget objects.
            budgetList.add(new Budget(username,globalBudgetName,tempIncome,tempExpense));
            
            //Set the global budget Index.
            budgetIndex = budgetList.size()-1;

            //Get the all budget array for current user.
            String[] oldBudgetArray = userList.get(userIndex).getBudgetNames();
               
            //Create an array with the new budget name.
            String[] newBudgetName = {globalBudgetName};
            
            //Concatenate the two arrays.
            String[] newBudgetArray = concatenateArrays(oldBudgetArray,newBudgetName);
            
            //Set the budget names array with the new budget array.
            userList.get(userIndex).setBudgetNames(newBudgetArray);
            
            //Set JList model for budgetJlist.
            budgetJlist.setModel(mod);

                //Add new Item.
            mod.addElement(globalBudgetName);
            
            //Write into the file to record the new budget.
            try {
                    //Open the UserLogin.txt file.
                BufferedWriter writer = new BufferedWriter(new FileWriter("UserLogin.txt"));
                    
                for (int i = 0; i<userList.size();i++){  //Go through all users.
                    //Write into the file, using the toString() method.
                    writer.write(userList.get(i).toString()+"\n");

                }

                writer.close();  //Close file.

            } catch (IOException e) {
                e.printStackTrace();    //Write if there's an error.    
            }
            
            tabbedPane.setSelectedIndex(4);  //Change panel to the budget page.
            
            //Reset combo box and fields.
            txtNameBudget.setText("");
            comboBoxYear.setSelectedIndex(0);
            
        }
        
        
    }//GEN-LAST:event_btnAddBudget2ActionPerformed

    private void btnAddIncomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddIncomeActionPerformed
        // TODO add your handling code here:
        
        /**
         * When the user clicked the add income button on the budget page.
         * Reset all fields and change panel.
         */        
        lblIncomeError.setText("");
        lblIncomeAmountError.setText("");
        
        //Reset fields and boxes for the potential next use.
        txtDescription.setText("");
            
        txtAmount.setText("");
        comboBoxIncome.setSelectedIndex(0);
        comboBoxIncomeFrequency.setSelectedIndex(0);
        
        
        tabbedPane.setSelectedIndex(5);
        
    }//GEN-LAST:event_btnAddIncomeActionPerformed

    private void addExpenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExpenseActionPerformed
        // TODO add your handling code here:
        
        /**
         * When the user clicked the add expense button on the budget page.
         * Reset all fields and change panel.
         */    
        lblExpenseError.setText("");
        lblExpenseAmountError.setText("");
        
        //Reset fields and boxes for the potential next use.
        txtDescription1.setText("");
            
        txtAmount1.setText("");
        comboBoxExpense.setSelectedIndex(0);
        comboBoxExpenseFrequency.setSelectedIndex(0);
        
        
        tabbedPane.setSelectedIndex(6);
    }//GEN-LAST:event_addExpenseActionPerformed

    private void btnIncomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncomeActionPerformed
        // TODO add your handling code here:
        /**
         * When the user click "Income" button wanting to see the list of incomes.
         * It reset error message fields and set the income panel visible.
         */        
        lblCheckError.setText("");
        lblCheckError2.setText("");
        
        txtDescription.setText("");
        txtAmount.setText("");
    
        panelExpense.setVisible(false);
        panelIncome.setVisible(true);
    }//GEN-LAST:event_btnIncomeActionPerformed

    private void btnExpenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpenseActionPerformed
        // TODO add your handling code here:
        
        /**
         * When the user click "Expense" button wanting to see the list of expenses.
         * It reset error message and input fields and set the expense panel visible.
         */    
        lblCheckError.setText("");
        lblCheckError2.setText("");
        
        txtDescription1.setText("");
        txtAmount1.setText("");
        
        panelIncome.setVisible(false);
        panelExpense.setVisible(true);
    }//GEN-LAST:event_btnExpenseActionPerformed

    private void btnAddIncome2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddIncome2ActionPerformed
        // TODO add your handling code here:
        
        /**
         * When the user want to add a new income after filling in the informations.
         * It will check for valid inputs and add a new income if valid.
         */
        
        //Reset the fields.
        lblIncomeError.setText("");
        lblIncomeAmountError.setText("");
        
        //Get user's input.
        String incomeType = comboBoxIncome.getSelectedItem().toString();
        String incomeName = txtDescription.getText();
        String incomeFrequency = comboBoxIncomeFrequency.getSelectedItem().toString();
        String incomeAmountInput = txtAmount.getText();
        
        //Check for valid inputs.
        
        //Check if the income name is empty.
        if (incomeName.equals("")){
            lblIncomeError.setText("*Please enter the income description.");  //Error message.
            
        //Check if commas are included in the name.
        }else if (ifCommas(incomeName) == true){
            lblIncomeError.setText("*The income description can not include commas.");  //Error message.
            
        //Check if the amount is a number.
        }else if (isDouble(incomeAmountInput) == false){
            lblIncomeAmountError.setText("*Please enter a valid amount.");  //Error message.
            
        //Check if the amount is negative.
        }else if (Double.parseDouble(incomeAmountInput) < 0){
            lblIncomeAmountError.setText("*Negative inputs are not allowed.");  //Error message.
            
        //Check if the input is too big or small.
        } else if(ifTooLong(incomeAmountInput) == true){
            lblIncomeAmountError.setText("*Your number is too big or too small; it must be less than 10^7 and larger than 10^-3.");  //Error message.    
       
        }else{  //Valid input.
            
            //Convert the user-inputted amount into double and round it.
            double incomeAmountNum = roundMethod(Double.parseDouble(incomeAmountInput));
            String incomeAmount = Double.toString(incomeAmountNum);  //Convert it back to string.
            
            //Add new income object to incomeList.
            incomeList.add(new Income(globalBudgetName,incomeName,incomeType,incomeFrequency,incomeAmount));
            
            //Get the incomes for this budget.
            ArrayList<Income>budgetIncome = budgetList.get(budgetIndex).getIncomes();
            
            //Add new income to this new arrayList.
            budgetIncome.add(new Income(globalBudgetName,incomeName,incomeType,incomeFrequency,incomeAmount));
            
            //Set the income arraylist to the new values.
            budgetList.get(budgetIndex).setIncomes(budgetIncome);
            
            //Set the Jlist model for income Jlist.
            incomeJList.setModel(modIncome);

                //Add income.
            modIncome.addElement(incomeName);
         
            //Update the expenses and income.
            double totalIncome = roundMethod(budgetList.get(budgetIndex).calculateIncome());
            double totalExpense = roundMethod(budgetList.get(budgetIndex).calculateExpenses());
            //Round the balance.
            double currentBalance = roundMethod(totalIncome - totalExpense);
            
            //Update the text fields with the new values.
            lblIncomeValue.setText(String.format("%.2f",totalIncome));
            lblBalanceValue.setText(String.format("%.2f",currentBalance));
            
            //Write into the user file with the updated data.
            writeInFile();
            
            //Change panel back to budget page.
            tabbedPane.setSelectedIndex(4);

            //Reset fields and boxes for the potential next use.
            txtDescription.setText("");
            
            txtAmount.setText("");
            comboBoxIncome.setSelectedIndex(0);
            comboBoxIncomeFrequency.setSelectedIndex(0);
            
        }
        
        
    }//GEN-LAST:event_btnAddIncome2ActionPerformed

    private void btnAddExpenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddExpenseActionPerformed
        // TODO add your handling code here:
        
        /**
         * This section is when the user want to add expense after fill in the informations.
         */
 
        //Reset the text fields.
        lblExpenseError.setText("");
        lblExpenseAmountError.setText("");
        
        //Get user inputs.
        String expenseType = comboBoxExpense.getSelectedItem().toString();
        String expenseName = txtDescription1.getText();
        String expenseFrequency = comboBoxExpenseFrequency.getSelectedItem().toString();
        String expenseAmountInput = txtAmount1.getText();
        
        //Check for valid inputs.
        
        //Check if the name is empty.
        if (expenseName.equals("")){
            lblExpenseError.setText("*Please enter the expense description."); //Error message.
         
        //Check if the amount is double.
        }else if (isDouble(expenseAmountInput) == false){
            lblExpenseAmountError.setText("*Please enter a valid amount.");  //Error message.
            
        //Check if the amount is negative.
        }else if (Double.parseDouble(expenseAmountInput) < 0){
            lblIncomeAmountError.setText("*Negative inputs are not allowed.");  //Error message.
 
        //Check if the input is too big or small.
        } else if(ifTooLong(expenseAmountInput) == true){
            lblExpenseAmountError.setText("*Your number is too big or too small; it must be less than 10^7 and larger than 10^-3.");  //Error message.
        
        //Check if the name included commas.
        }else if (ifCommas(expenseName) == true){
            lblExpenseError.setText("*The expense name can not include commas.");  //Error message.
            
        }else{
            
            //Convert amount to double to round it.
            double expenseAmountNum = roundMethod(Double.parseDouble(expenseAmountInput));
            String expenseAmount = Double.toString(expenseAmountNum);  //Convert back to string.
            
            //Add new expense object to list.
            expenseList.add(new Expenses(globalBudgetName,expenseName,expenseType,expenseFrequency,expenseAmount));
            
            //get the expense object arraylist for this budget.
            ArrayList<Expenses>budgetExpense = budgetList.get(budgetIndex).getExpenses();
            
            //Add new expense object to the new arrayList.
            budgetExpense.add(new Expenses(globalBudgetName,expenseName,expenseType,expenseFrequency,expenseAmount));
            
            //Set the expense arraylist of the budget to the new values.
            budgetList.get(budgetIndex).setExpenses(budgetExpense);
            
            //Set the Jlist model.
            expenseJList.setModel(modExpense);
            
                //Add expense to jList.
            modExpense.addElement(expenseName);
            
            
            //Update the expenses and income.
            double totalIncome = roundMethod(budgetList.get(budgetIndex).calculateIncome());
            double totalExpense = roundMethod(budgetList.get(budgetIndex).calculateExpenses());
            
            //Round the balance.
            double currentBalance = roundMethod(totalIncome - totalExpense);
            
            //Set the new values.
            lblExpensesValue.setText(String.format("%.2f",totalExpense));
            lblBalanceValue.setText(String.format("%.2f",currentBalance));
            
            //Write into the user file.
            writeInFile();
            
            
            //Reset fields and boxes for potential next use.
            tabbedPane.setSelectedIndex(4);
            
            comboBoxExpense.setSelectedIndex(0);
            txtDescription1.setText("");
            comboBoxExpenseFrequency.setSelectedItem(0);
            txtAmount1.setText("");
            
        }
        
        
    }//GEN-LAST:event_btnAddExpenseActionPerformed

    private void btnSelectBudgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectBudgetActionPerformed
        // TODO add your handling code here:
        
        /**
         * This section is when the user selected an already existing budget.
         */
       
        lblSelectBudgetError.setText("");  //Reset error message field.
        
        //Get the index of the selected item; it should be the same index as in the budget list.
        int selectedBudgetIndex = budgetJlist.getSelectedIndex();
        
        if (selectedBudgetIndex != -1){  //If user selected something.
            String selectedBudget = budgetJlist.getSelectedValue();  //Get the actual value of the item.
            
            //Set the current budget index and name.
            budgetIndex = selectedBudgetIndex;
            globalBudgetName = selectedBudget;
            
            //Set the title of budget page.
            txtBudgetTitle.setText(globalBudgetName);
            
            //Set Jlist models for income and expenses.
            incomeJList.setModel(modIncome);
            expenseJList.setModel(modExpense);

            //Get the income list for the budget.
            ArrayList<Income> userIncomeList = budgetList.get(budgetIndex).getIncomes();
            
            //Output every income name in the arrayList on Jlist.
            for (int i = 0; i<userIncomeList.size();i++){
                //Add Item.
                modIncome.addElement(userIncomeList.get(i).getName());
     
            }
            
            //Get the expense list for the budget.
            ArrayList<Expenses> userExpenseList = budgetList.get(budgetIndex).getExpenses();
            
            //Output every expense name in the arrayList on Jlist.
            for (int i = 0; i<userExpenseList.size();i++){
                //Add Item.
                modExpense.addElement(userExpenseList.get(i).getName());
            }
            
            //Calculate the incomes and expenses.
            double totalIncome = roundMethod(budgetList.get(budgetIndex).calculateIncome());
            double totalExpense = roundMethod(budgetList.get(budgetIndex).calculateExpenses());
     
            //Calculate balance.
            double currentBalance = roundMethod(totalIncome - totalExpense);
            
            //Output the values.
            lblIncomeValue.setText(String.format("%.2f",totalIncome));
            lblExpensesValue.setText(String.format("%.2f",totalExpense));
            lblBalanceValue.setText(String.format("%.2f",currentBalance));
            
            tabbedPane.setSelectedIndex(4);  //Change panel to budget page.
            
        }else{  //Did not select budget.
            lblSelectBudgetError.setText("Please select a budget.");  //Error message.
        }
        
        
    }//GEN-LAST:event_btnSelectBudgetActionPerformed

    private void btnBackHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackHomeActionPerformed
        // TODO add your handling code here:
        
        /**
         * when the user click the back to home button,
         * the Jlist models for expenses and income will be cleared.
         * Change panel screen.
         */
        
        tabbedPane.setSelectedIndex(2);
        
        modIncome.clear();  //Clear model.
        modExpense.clear();
    }//GEN-LAST:event_btnBackHomeActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        
        tabbedPane.setSelectedIndex(4);  //Back to budget.
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancel1ActionPerformed
        // TODO add your handling code here:
        
        tabbedPane.setSelectedIndex(4);  //Back to budget.
    }//GEN-LAST:event_btnCancel1ActionPerformed

    private void btnExpenseCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpenseCheckActionPerformed
        // TODO add your handling code here:
        
        /**
         * This section is when the user click the check button for expenses.
         * It will have a pop-up for the information of the expenses.
         */
        
        lblCheckError2.setText("");  //Clear field.
        //Get the Jlist index being selected
        int selectedExpenseIndex = expenseJList.getSelectedIndex();
        
        //Check if any item is being selected.
        if (selectedExpenseIndex != -1){
            
            //Get the actual value being selected.
            String selectedExpense = expenseJList.getSelectedValue();
            
            //Get the current expense.
            Expenses currentExpense = budgetList.get(budgetIndex).getExpenses().get(selectedExpenseIndex);
            
            //Show pop-up of the data.
            showDetailPopUp(selectedExpense, "Expenses", currentExpense.getType(),currentExpense.getFrequency(),currentExpense.getAmount(),currentExpense.calculateRealAmount());
       
        }else{  //No item selected.
            lblCheckError2.setText("*Please select an expense.");  //Error message.
        }
        
    }//GEN-LAST:event_btnExpenseCheckActionPerformed

    private void btnIncomeCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncomeCheckActionPerformed
        // TODO add your handling code here:
        
        /**
         * This section is when the user click the check button for incomes.
         * It will have a pop-up for the information of the incomes.
         */
        
        lblCheckError.setText("");  //Reset text field.
        //Get the Jlist index being selected.
        int selectedIncomeIndex = incomeJList.getSelectedIndex();
        
        //Check if any item is being selected.
        if (selectedIncomeIndex != -1){
            
            //Get the actual value being selected.
            String selectedIncome = incomeJList.getSelectedValue();
            
             //Get the current income object.
            Income currentIncome = budgetList.get(budgetIndex).getIncomes().get(selectedIncomeIndex);
            
            //Show pop-up of the data.
            showDetailPopUp(selectedIncome, "Income", currentIncome.getType(),currentIncome.getFrequency(),currentIncome.getAmount(),currentIncome.calculateRealAmount());
        }else{  //No item selected.
            lblCheckError.setText("*Please select an income.");   //Error message.
        }
        
    }//GEN-LAST:event_btnIncomeCheckActionPerformed

    private void btnExpenseDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpenseDeleteActionPerformed
        // TODO add your handling code here:
        
        /**
         * When the user want to delete an expense.
         */
        
        lblCheckError2.setText("");  //Reset text field.
        
        //Get the Jlist index being selected.
        int selectedExpenseIndex = expenseJList.getSelectedIndex();
        
        //Check if the user have selected an item.
        if (selectedExpenseIndex != -1){
            expenseJList.setModel(modExpense);
            
            modExpense.remove(selectedExpenseIndex);
            
            //Get the actual value of the selected expense.
            String selectedExpense = expenseJList.getSelectedValue();
            
            //Get the current expense object.
            Expenses currentExpense = budgetList.get(budgetIndex).getExpenses().get(selectedExpenseIndex);
            
            //Remove the object from the budget's expenseList.
            budgetList.get(budgetIndex).getExpenses().remove(selectedExpenseIndex);
  
            //Rrmove the expense object from the expenseList.
            expenseList.remove(currentExpense);
            
            //Calculate updated income and expenses.
            double totalIncome = roundMethod(budgetList.get(budgetIndex).calculateIncome());
            double totalExpense = roundMethod(budgetList.get(budgetIndex).calculateExpenses());
            
            //Update balance.
            double currentBalance = roundMethod(totalIncome - totalExpense);
            
            //Update the output values.
            lblIncomeValue.setText(String.format("%.2f",totalIncome));
            lblExpensesValue.setText(String.format("%.2f",totalExpense));
            lblBalanceValue.setText(String.format("%.2f",currentBalance));
            
            
            //Write into the user file.
            writeInFile();
            
            
        }else{  //User didn't select an item.
            //Error message.
            lblCheckError2.setText("*Please select an expense.");
        }
        
    }//GEN-LAST:event_btnExpenseDeleteActionPerformed

    private void btnIncomeDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncomeDeleteActionPerformed
        // TODO add your handling code here:
        
        /**
         * When the user want to delete an income.
         */
        
        //Get the index of the income in Jlist.
        int selectedIncomeIndex = incomeJList.getSelectedIndex();
        lblCheckError.setText("");  //Reset text fields.
        
        //Check if the user selected an item.
        if (selectedIncomeIndex != -1){
            incomeJList.setModel(modIncome);
            
            modIncome.remove(selectedIncomeIndex);
            
            //Get the actual selected value.
            String selectedIncome = incomeJList.getSelectedValue();
            
            //Get the current income object.
            Income currentIncome = budgetList.get(budgetIndex).getIncomes().get(selectedIncomeIndex);
            
            //Remove the current income from the budget's incomelist.
            budgetList.get(budgetIndex).getIncomes().remove(selectedIncomeIndex);
  
            //Remove the income from the incomeList.
            incomeList.remove(currentIncome);
            
            //Re-calculate the income and expenses.
            double totalIncome = roundMethod(budgetList.get(budgetIndex).calculateIncome());
            double totalExpense = roundMethod(budgetList.get(budgetIndex).calculateExpenses());
            //Calculate new balance.
            double currentBalance = roundMethod(totalIncome - totalExpense);
            
            //Update the values.
            lblIncomeValue.setText(String.format("%.2f",totalIncome));
            lblExpensesValue.setText(String.format("%.2f",totalExpense));
            lblBalanceValue.setText(String.format("%.2f",currentBalance));
            
            //Write into the user file.
            writeInFile();
            
            
        }else{  //No item selected.
            //Error message.
            lblCheckError.setText("*Please select an income.");
        }
        
    }//GEN-LAST:event_btnIncomeDeleteActionPerformed

    private void btnAppExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAppExitActionPerformed
        // TODO add your handling code here:
        
        System.exit(0); //Exit the application.
    }//GEN-LAST:event_btnAppExitActionPerformed

    private void btnLoginExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginExitActionPerformed
        // TODO add your handling code here:
        
        System.exit(0); //Exit the application.
    }//GEN-LAST:event_btnLoginExitActionPerformed

    private void btnDeleteBudgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBudgetActionPerformed
        // TODO add your handling code here:
        
        /**
         * When the user want to delete a budget.
         */
        
        lblSelectBudgetError.setText("");  //Reset text field.
        
        //Get the selected budget index of Jlist.
        int selectedBudgetIndex = budgetJlist.getSelectedIndex();

        //Check if any item is selected.
        if (selectedBudgetIndex != -1){

            int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete the budget?","Confirmation", JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION){
            
                //Get the actual selected value.
                String selectedBudget = budgetJlist.getSelectedValue();

                //Get all budget names for the current user.
                String[] oldArray = userList.get(userIndex).getBudgetNames();

                //Search the user's Budget Index in all of its budgets.
                int userBudgetIndex = linearSearch(oldArray,selectedBudget);

                //Initialize a new array one position smaller than the original array of budget names.
                String[] newUserBudget = new String[oldArray.length-1];

                // Copy the elements before the target budget name
                System.arraycopy(oldArray, 0, newUserBudget, 0, userBudgetIndex);

                // Copy the elements after the target budget name.
                System.arraycopy(oldArray, userBudgetIndex + 1, newUserBudget, userBudgetIndex, oldArray.length - userBudgetIndex - 1);

                //Reset the budget array of the user.
                userList.get(userIndex).setBudgetNames(newUserBudget);

                budgetJlist.setModel(mod);

                //Remove the budget on the Jlist.
                mod.remove(selectedBudgetIndex);

                //Get two arrayLists with the Income and Expenses objects of the budget.
                ArrayList<Income> currentBudgetIncome = budgetList.get(selectedBudgetIndex).getIncomes();
                ArrayList<Expenses> currentBudgetExpense = budgetList.get(selectedBudgetIndex).getExpenses();

                //Use removeAll to remove every object in the new created arrayLists from the incomeList and expenseList.
                incomeList.removeAll(currentBudgetIncome);
                expenseList.removeAll(currentBudgetExpense);

                //Remove budget from budgetList.
                budgetList.remove(selectedBudgetIndex);

                //Write into the user file.
                writeInFile();

                //Write into the all user file.
                try {

                    //Open the UserLogin.txt file.
                    BufferedWriter writer = new BufferedWriter(new FileWriter("UserLogin.txt"));

                    for (int i = 0; i<userList.size();i++){  //Go through all items.
                        //Write into the file, using the toString() method.
                        writer.write(userList.get(i).toString()+"\n");

                    }

                    writer.close();  //Close file.

                } catch (IOException e) {
                    e.printStackTrace();    //Write if there's an error.
                }

            }
            
        }else{  //No item selected
            //Error message.
            lblSelectBudgetError.setText("*Please select a budget.");
        }

    }//GEN-LAST:event_btnDeleteBudgetActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FinanceApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FinanceApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FinanceApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FinanceApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FinanceApplication().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addExpense;
    private javax.swing.JButton btnAddBudget;
    private javax.swing.JButton btnAddBudget2;
    private javax.swing.JButton btnAddExpense;
    private javax.swing.JButton btnAddIncome;
    private javax.swing.JButton btnAddIncome2;
    private javax.swing.JButton btnAppExit;
    private javax.swing.JButton btnBackHome;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCancel1;
    private javax.swing.JButton btnDeleteBudget;
    private javax.swing.JButton btnExpense;
    private javax.swing.JButton btnExpenseCheck;
    private javax.swing.JButton btnExpenseDelete;
    private javax.swing.JButton btnGoBack;
    private javax.swing.JButton btnIncome;
    private javax.swing.JButton btnIncomeCheck;
    private javax.swing.JButton btnIncomeDelete;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnLoginExit;
    private javax.swing.JButton btnSelectBudget;
    private javax.swing.JButton btnSignup;
    private javax.swing.JList<String> budgetJlist;
    private javax.swing.JComboBox<String> comboBoxExpense;
    private javax.swing.JComboBox<String> comboBoxExpenseFrequency;
    private javax.swing.JComboBox<String> comboBoxIncome;
    private javax.swing.JComboBox<String> comboBoxIncomeFrequency;
    private javax.swing.JComboBox<String> comboBoxYear;
    private javax.swing.JList<String> expenseJList;
    private javax.swing.JList<String> incomeJList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAddIExpense;
    private javax.swing.JLabel lblAddIncome;
    private javax.swing.JLabel lblBalanceSub;
    private javax.swing.JLabel lblBalanceValue;
    private javax.swing.JLabel lblBudgetError;
    private javax.swing.JLabel lblCheckError;
    private javax.swing.JLabel lblCheckError2;
    private javax.swing.JLabel lblCreate;
    private javax.swing.JLabel lblCreate1;
    private javax.swing.JLabel lblCreate2;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblDescription1;
    private javax.swing.JLabel lblErrorName;
    private javax.swing.JLabel lblErrorPW;
    private javax.swing.JLabel lblExpenseAmount;
    private javax.swing.JLabel lblExpenseAmountError;
    private javax.swing.JLabel lblExpenseError;
    private javax.swing.JLabel lblExpenseSub;
    private javax.swing.JLabel lblExpenseTitle;
    private javax.swing.JLabel lblExpensesValue;
    private javax.swing.JLabel lblFrequency;
    private javax.swing.JLabel lblFrequency1;
    private javax.swing.JLabel lblGreeting;
    private javax.swing.JLabel lblImage1;
    private javax.swing.JLabel lblImage2;
    private javax.swing.JLabel lblIncomeAmount;
    private javax.swing.JLabel lblIncomeAmountError;
    private javax.swing.JLabel lblIncomeError;
    private javax.swing.JLabel lblIncomePhoto;
    private javax.swing.JLabel lblIncomeSub;
    private javax.swing.JLabel lblIncomeTitle;
    private javax.swing.JLabel lblIncomeValue;
    private javax.swing.JLabel lblIncorrect;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblLogin1;
    private javax.swing.JLabel lblMoneyImage;
    private javax.swing.JLabel lblNoAccount;
    private javax.swing.JLabel lblNoAccount1;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPasswordLog;
    private javax.swing.JLabel lblPig;
    private javax.swing.JLabel lblProfile;
    private javax.swing.JLabel lblPromptName;
    private javax.swing.JLabel lblSelectBudgetError;
    private javax.swing.JLabel lblSignUp;
    private javax.swing.JLabel lblTypeExpense;
    private javax.swing.JLabel lblTypeIncome;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblUsernameLog;
    private javax.swing.JLabel lblYear;
    private javax.swing.JLabel lblYourBudgets;
    private javax.swing.JPanel panelAddBudget;
    private javax.swing.JPanel panelAddExpense;
    private javax.swing.JPanel panelAddIncome;
    private javax.swing.JPanel panelBudget;
    private javax.swing.JPanel panelExpense;
    private javax.swing.JPanel panelHome;
    private javax.swing.JPanel panelIncome;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelSignUp;
    private javax.swing.JPanel panelSummary;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtAmount1;
    private javax.swing.JTextField txtBudgetTitle;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtDescription1;
    private javax.swing.JTextField txtNameBudget;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPasswordLog;
    private javax.swing.JTextField txtProfile;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtUsernameLog;
    // End of variables declaration//GEN-END:variables
}
