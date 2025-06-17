
package com.mycompany.finalpoe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Message {
 
//    public Message(String messageId, String messageContent, String sender, String recipient){
//      this.messageId =messageId;
//       this.messageContent = messageContent;
//       this.sender = sender;
//       this.recipient = recipient;
//    }

    private int numOfMsg = 0;
    public String messageId;
    public String messageContent;
   public String sender;
    public String recipient;
  
   
    public static final int MAX_MESSAGES = 5;
    public static final String FILE_NAME = "messages.json";
    //paralle array for part 3
    public final String[] messageIds = new String[MAX_MESSAGES];
    public final String[] recipients = new String[MAX_MESSAGES];
   public final String[] hashes = new String[MAX_MESSAGES];
    public final String[] contents = new String[MAX_MESSAGES];
    public final String[] senders = new String[MAX_MESSAGES];
    public final String[] sentflags = new String[MAX_MESSAGES];
   public final String[] readflags = new String[MAX_MESSAGES];
    public final String[] discardedflags = new String[MAX_MESSAGES];


 

    public void numericMenu() {
     
        System.out.println("Welcome to QuickChat" );
        boolean isContinue = true;

        while (isContinue) {
            String input = JOptionPane.showInputDialog(null,
                    "Select Option:\n1. Send Message\n2. View Recently sent Message\n3.View Advance Reports \n4.Quit");

            if (input == null || input.equals("4")) {
                isContinue = false;
                JOptionPane.showMessageDialog(null, "Logging out... Thank you!");
                continue;
            }

            switch (input) {
                case "1":
                    String countInput = JOptionPane.showInputDialog(null, "How many messages to send?");
                    if (countInput == null) break;

                    int toSend = Integer.parseInt(countInput);
                    if (numOfMsg + toSend > MAX_MESSAGES) {
                        JOptionPane.showMessageDialog(null, "You can only send up to 5 messages total.");
                        break;
                    }

                    for (int i = 0; i < toSend; i++) {
                        recipient = JOptionPane.showInputDialog("Enter recipient number (+27 format):");
                        if (recipient == null || !recipient.matches("^\\+27[0-9]{9}$")) {
                            JOptionPane.showMessageDialog(null, "Invalid number. Format: +27XXXXXXXXX");
                            i--;
                            continue;
                        }

                        messageContent = JOptionPane.showInputDialog("Enter your message (max 250 chars):");
                        if (validMessageLength(messageContent) ) {
                            JOptionPane.showMessageDialog(null, "Message too long.");
                            i--;
                            continue;
                        }

                        String messageId = createMessageId();
                        String hash = createMessageHash(messageContent, messageId, numOfMsg + 1);

                        

                         handleMessageOption(messageId, hash);
                   
                    }
                    break;
              //Sent message reports
                case "2":
                   excutiveReport();
                    JOptionPane.showMessageDialog(null, "Messages sent: " +numOfMsg );
                    break;
                case "3":
               excutiveReport();
                    System.out.println("check here");
                 break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid input");
            }
        }
    }

    public String createMessageId() {
        return String.format("%010d", new Random().nextInt(1_000_000_000));
    }

    public String createMessageHash(String message, String messageId, int count) {
        String prefix = messageId.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;
        return String.format("%s:%d:%s%s", prefix, count, first.toUpperCase(), last.toUpperCase());
    }

    public boolean validMessageLength(String messageContent){
        if(messageContent.length() <=250 && messageContent ==null  ){
            return true;
        }
        return false;
    }
    
    //Come after selecting option 1 to send messages
    public void handleMessageOption(String messageId, String hash) {
        String option = JOptionPane.showInputDialog("1: Send\n2: Store\n3: Discard");
        if (option == null) return;

        switch (option) {
            case "1":
                sender = JOptionPane.showInputDialog ("Enter name of the Sender");
                if(sender == null)break;
                storeMessages("sent", messageContent);
                   
                    messageIds[numOfMsg] = messageId;
                    hashes[numOfMsg] = hash;
                   recipients[numOfMsg] = recipient;
                    contents[numOfMsg] = messageContent;
                    senders[numOfMsg] = sender;
                    sentflags[numOfMsg] = "True";
                    readflags[numOfMsg] ="True";    
                    discardedflags [numOfMsg] ="False";
 
                    
                  numOfMsg++;
               
              for (int i = 0; i <numOfMsg; i++ ){
                  JOptionPane.showMessageDialog(null, "Messages " +(i +1) +":"+contents[i] +"\n"
                          +"Recipient" +recipients[i] );   
              }
//          System.out.println("***********Sent Messages*********");
                JOptionPane.showMessageDialog(null, "Message sent.\n"
                        
                        +"Message details \n"
                        +"hash: "+hash + "\n" 
                        +"Message Id:" +messageId +"'\n"
                        + "Sender: " +sender+"\n"
                        +"Recipient: "+recipient + "\n"
                         +"Contents:" + messageContent + "\n"
                        
                   
                        );
          
                break;

            case "2":
//                storedMessages.add(messageContent);
                storeMessages("stored", messageContent);
                JOptionPane.showMessageDialog(null, "Message stored.");
                
                break;
            case "3":
//                discardedMessages.add(messageContent);
                JOptionPane.showMessageDialog(null, "Message discarded \n" 
                        +"Delivery Status: " +discardedflags );
                break;
            case "4": 
               excutiveReport();
                
               break;
            default:
                
                JOptionPane.showMessageDialog(null, "Invalid option");
        }
    }

    public void storeMessages(String type, String message) {
        Map<String, List<String>> data = loadMessages();
        data.computeIfAbsent(type, k -> new ArrayList<>()).add(message);
        try (Writer writer = new FileWriter(FILE_NAME)) {
            new Gson().toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<String>> loadMessages() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
            Map<String, List<String>> data = new Gson().fromJson(reader, type);
            return data != null ? data : new HashMap<>();
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
    
    //part 3
  
 
   //we added additional menu to access compressive reports for various message details
      public void excutiveReport(){
          String inputOptions = JOptionPane.showInputDialog("1:Search by Message by ID :\n2:Display Longest Messages:"
                  + " \n3:Search by Recipient: \n4:Delete by Hash \n5 Get Full report \n6: Quit \n7: Json file" );
          switch(inputOptions) {
              case "1":  
                  searchMessagesById();
                  break;
              case "2":
                    longestMessage();
                break;
                case "3":
                  searchByRecipient();
                    break;
                case "4":
                deleteByHash();
                    break;
                case "5":
                    getFullReport();
               
                    break;
                case "6": 
                     JOptionPane.showMessageDialog(null, "Goodbye. Have Nice day");
                    break;
                    //reading into json
                case "7":
                    loadMessages();
                    break;
                default: 
                    JOptionPane.showMessageDialog(null, "invalid Option");
          }
                     
      }
   
  
    public void senderRecipientReport(){
        String fullMessage = String.format("Recipient: %s\nSender: %s",
                //message content to be replaced by the sender
                                recipient, sender);
//                        JOptionPane.showMessageDialog(null, fullMessage);
}
  public void sentMessages(){
        Iterable<String> sentMessages = null;
     for(String msg : sentMessages){
         if(msg !=null)
             JOptionPane.showMessageDialog(null, msg);
     }

      }
    public void longestMessage(){
      Map<String, List<String>> data = loadMessages();
        List<String> sent = data.get("sent");

        if (sent == null || sent.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages found.");
            return;
        }
        String longest = "";
        for (String msg : sent) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }

        JOptionPane.showMessageDialog(null, "Longest Sent Message:\n\n" + longest);
    }
    public  void searchMessagesById(){
        String id = JOptionPane.showInputDialog("enter message ID to search the message");
        Map<String, List<String>> data = loadMessages();
        List<String> sent = data.get("sent");

    if (sent == null || sent.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No sent messages to search.");
        return;
    }

    for (String msg : sent) {
        if (msg.contains(id)) {
            JOptionPane.showMessageDialog(null, "Message Found:\n\n" + msg);
            return;
        }
    }

    JOptionPane.showMessageDialog(null, "No message found with ID: " + id);
}


    public void searchByRecipient() {
   String searchRecipient  = JOptionPane.showInputDialog(null, "Search Message by entering Recipient");
   StringBuilder results = new StringBuilder();
   
    //checking if the recipient is in the array by 
        for(int i =0; i <numOfMsg; i++){
            if(recipients[i] !=null && recipients[i].equals(searchRecipient)){
                results.append("Recipient:").append(recipients[i]).append("\n");
                results.append("Message:").append(contents[i]).append("\n");
                JOptionPane.showMessageDialog(null, results.length() >0 ? results.toString() : "No Message for that recipient");
            }
        }
    }

   public void deleteByHash() {
String hashToDelete  = JOptionPane.showInputDialog(null, "Enter hash to delete the message");
if(hashToDelete == null || hashToDelete.isEmpty()){
    JOptionPane.showInputDialog("'No hash found");
    return;
 }
boolean isFound = false;
for (int i =0; i <numOfMsg; i++){
    //this search through array items and check if entered hash matches the ones in the record (=searched = hashToDelete) 
    if(hashes[i] !=null && hashes[i].equals(hashToDelete)){
           for(int j = i; j<numOfMsg; j++ ){
               
               //shift remain messages
                     messageIds[j] = messageIds[j+ 1];
                    hashes[j] = hashes[j+ 1];
                   recipients[j] = recipients[j+ 1];
                    contents[j] = contents[j+ 1];
                    senders[j] = senders[j+ 1];
                    sentflags[j] = sentflags[j + 1];
                readflags[j] = readflags[j + 1];
                discardedflags[j] = discardedflags[j + 1];
                  
                  //delete
                  int lastIndex = numOfMsg-1;
                     messageIds[lastIndex] = null; 
                     hashes[lastIndex] = null;
                     recipients[lastIndex] = null;
                     contents[lastIndex] = null;
                     senders[lastIndex] = null;
                       sentflags[lastIndex] = null;
                readflags[lastIndex] = null;
                discardedflags[lastIndex] = null;
                     numOfMsg--;
                    isFound = true;
                    JOptionPane.showMessageDialog(null, "Message deleted");
                    break;
           }
    }
    if(!isFound)
        JOptionPane.showMessageDialog(null, "No messages deleted for that hash");
}
}
   public void getFullReport() {
         StringBuilder report = new StringBuilder("************All Messages sent**************\n\n");
    
    if (numOfMsg == 0) {
        JOptionPane.showMessageDialog(null, "No messages to display.", "Message Report", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    
    for (int i = 0; i < numOfMsg; i++) {
        report.append("=== Message ").append(i + 1).append(" ===\n");
        report.append("Message ID: ").append(messageIds[i]).append("\n");
        report.append("Hash: ").append(hashes[i]).append("\n");
        report.append("Recipient: ").append(recipients[i]).append("\n");
        report.append("Sender: ").append(senders[i]).append("\n");
    
        report.append("\n"); // Add spacing between messages
    }
    
    // Display the report in a JOptionPane with scrollable text area
    JOptionPane.showMessageDialog(null, report.toString(), "Message Report", JOptionPane.INFORMATION_MESSAGE);
}
     
                    
}



    


