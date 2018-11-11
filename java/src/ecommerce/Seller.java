/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecommerce;

import java.util.Arrays;

/**
 *
 * @author Alex
 */
public class Seller {
    private int ID;
    private int billingInfoID;
    private char[] companyName;
    
    public Seller(){
        ID = 0;
        billingInfoID = 0;
        companyName = new char[20];
    }
    
    public Seller(int newID, int newBilling, char[] newName){
        ID = newID;
        billingInfoID = newBilling;
        companyName = newName;
    }
    
    public int getID(){
        return ID;
    }
    
    public void setID(int newID){
        ID = newID;
    }
    
    public int getBillingInfoID(){
        return billingInfoID;
    }
    
    public void setBillingInfoID(int newID){
        billingInfoID = newID;
    }
    public char[] getCompanyName(){
        return companyName;
    }
    
    public void setCompanyName(String newName){
        companyName = newName.toCharArray();
    }
    
    @Override
    public String toString(){
        return ID + ", " + billingInfoID + ", " + Arrays.toString(companyName);
    }
}
