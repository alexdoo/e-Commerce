/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecommerce;

import java.util.Arrays;

public class Item {
    private int ID;
    private int sellerID;
    private int categoryID;
    private double price;
    private char[] itemName;
    private char[] description;

    public Item(){
        ID = 0;
        sellerID = 0;
        categoryID = 0;
        price = 0;
        itemName = new char[20];
        description = new char[255];
    }
    
    public Item(int newID, int newSeller, int newCategory, double newPrice, char[] newDesc, char[] newName){
        ID = newID;
        sellerID = newSeller;
        categoryID = newCategory;
        price = newPrice;
        itemName = newName;
        description = newDesc;
    }
    
    public int getID(){
        return ID;
    }
    
    public void setID(int newID){
        ID = newID;
    }
    
    public int getSellerID(){
        return sellerID;
    }
    
    public void setSellerID(int newSeller){
        sellerID = newSeller;
    }
    
    public int getCategoryID(){
        return categoryID;
    }
    
    public void setCategoryID(int newCat){
        categoryID = newCat;
    }
    
    public double getPrice(){
        return price;
    }
    
    public void setPrice(double newPrice){
        price = newPrice;
    }
    
    public char[] getDescription(){
        return description;
    }
    
    public void setDescription(String newDesc){
        description = newDesc.toCharArray();
    }
    
    public char[] getItemName(){
        return itemName;
    }
    
    public void setItemName(String newName){
        itemName = newName.toCharArray();
    }
    
    @Override
    public String toString(){
        return ID + ", " + sellerID + ", " + categoryID + ", " + price + ", " + Arrays.toString(itemName) + ", " + Arrays.toString(description);
    }
}
