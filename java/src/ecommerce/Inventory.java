/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecommerce;

public class Inventory {
    private int itemID;
    private int sellerID;
    private int quantity;
    private double price;
    
    public Inventory(){
        itemID = 0;
        sellerID = 0;
        quantity = 0;
        price = 0;
    }
    
    public Inventory(int newItem, int newSeller, int newQuantity, double newPrice){
        itemID = newItem;
        sellerID = newSeller;
        quantity = newQuantity;
        price = newPrice;
    }
    
    public int getItemID(){
        return itemID;
    }
    
    public void setItemID(int newItem){
        itemID = newItem;
    }
    
    public int getSellerID(){
        return sellerID;
    }
    
    public void setSellerID(int newSeller){
        sellerID = newSeller;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    public void setQuantity(int newQuantity){
        quantity = newQuantity;
    }
    
    public double getPrice(){
        return price;
    }
    
    public void setPrice(double newPrice){
        price = newPrice;
    }
    
    @Override
    public String toString(){
        return itemID + ", " + sellerID + ", " + quantity + ", " + price;
    }
}
