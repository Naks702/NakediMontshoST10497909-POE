package com.mycompany.finalpoe;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
public class LoginsnReg {
   public static final Map<String, String> userCredentials = new HashMap<>();
    public String username, password, cellPhoneNumber;
    
    public void startLoginProcess(int loginOptions) {
        Message message = new Message();
        boolean isContinue = true;
        
        while (isContinue) {
            String[] menuOptions = {"Register", "Login", "Exit"};
            loginOptions = JOptionPane.showOptionDialog(null, "Choose an option", "User Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, menuOptions, menuOptions[0]);
            
            // Register
            if (loginOptions == 0) {
                this.username = JOptionPane.showInputDialog("Enter Your Username: ");
                if (username == null || !checkUserName(username)) continue;
                
                this.password = JOptionPane.showInputDialog("Enter Password:");
                if (password == null || !checkPassword(password)) continue;
                
                this.cellPhoneNumber = JOptionPane.showInputDialog("Enter Cellphone:");
                if (cellPhoneNumber == null || !checkCellPhoneNumber(cellPhoneNumber)) continue;
                
                userCredentials.put(username, password);
                JOptionPane.showMessageDialog(null, "Registration successful!");
            }
            // Login
            else if (loginOptions == 1) {
                String user = JOptionPane.showInputDialog("Enter Your Username: ");
                if (user == null) continue;
                
                String pass = JOptionPane.showInputDialog("Enter Your Password: ");
                if (pass == null) continue;
                
                if (loginUser(user, pass)) {
                    JOptionPane.showMessageDialog(null, "Login Successful! Welcome " + user);
                    message.numericMenu();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid details. Login unsuccessful");
                }
            }
            // Exit
            else if (loginOptions == 2 || loginOptions == JOptionPane.CLOSED_OPTION) {
                isContinue = false;
            }
        }
    }
    
    public boolean checkUserName(String username) {
        if (username.contains("_") && username.length() <= 5) {
            JOptionPane.showMessageDialog(null, "Username has been captured successfully");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Ensure your username contains '_' and is not more than 5 characters.");
            return false;
        }
    }
   public  boolean checkPassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$";
        if (password.matches(pattern)) {
           JOptionPane.showMessageDialog(null,"Password successfully captured");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "\"Password is not correctly formatted. Please ensure:\\n\"\n" +
"                    + \"- At least one uppercase letter\\n\"\n" +
"                    + \"- At least one lowercase letter\\n\"\n" +
"                    + \"- At least one digit\n\"\n" +
"                    + \"- At least one special character (@#$%^&+=!)\\n\"\n" +
"                    + \"- Between 8 and 20 characters");
          
            return false;
        }
    }

    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        String numberPattern = "^\\+27[0-9]{9}$";
        if (cellPhoneNumber.matches(numberPattern)) {
            System.out.println("Cellphone successfully captured");
            return true;
        } else {
            System.out.println("Incorrect cellphone number. It should start with +27 and be 12 digits long.");
            return false;
        }
    }

  public String registerUser(String username, String password, String cellPhoneNumber) {
        if (checkUserName(username) && checkPassword(password) && checkCellPhoneNumber(cellPhoneNumber)) {
            userCredentials.put(username, password);
            return "The conditions have been met. User has been registered successfully.";
        } else {
            return "Registration unsuccessful due to invalid details.";
        }
    }


    public boolean loginUser(String username, String password) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Successful login";
        } else {
            return "Failed login";
        }
    }

    
}
