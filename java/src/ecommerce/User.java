/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecommerce;

import java.util.Arrays;

public class User {
    private int sellerID;
    private int customerID;
    private char[] username;
    private char[] password;
    
    public User(){
        sellerID = 0;
        customerID = 0;
        username = new char[20];
        password = new char[20];
    }
    
    public User(int newSeller, int newCust, char[] newUser, char[] newPass){
        sellerID = newSeller;
        customerID = newCust;
        username = newUser;
        password = newPass;
    }
    
    public int getSellerID(){
        return sellerID;
    }
    
    public void setSellerID(int newSeller){
        sellerID = newSeller;
    }
    
    public int getCustomerID(){
        return customerID;
    }
    
    public void setCustomerID(int newCus){
        customerID = newCus;
    }
    
    public char[] getUsername(){
        return username;
    }
    
    public void setUsername(String newUser){
        username = newUser.toCharArray();
    }
    
    public char[] getPassword(){
        return password;
    }
    
    public void setPassword(String newPass){
        password = newPass.toCharArray();
    }
    
    @Override
    public String toString(){
        return sellerID + ", " + customerID + ", " + Arrays.toString(username) + ", " + Arrays.toString(password);
    }
}
