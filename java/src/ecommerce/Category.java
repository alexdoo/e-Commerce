/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecommerce;

import java.util.Arrays;

public class Category {
    private int ID;
    private char[] categoryName;
    
    public Category(){
        ID = 0;
        categoryName = new char[20];
    }
    
    public Category(int newID, char[] newName){
        ID = newID;
        categoryName = newName;
    }
    
    public int getID(){
        return ID;
    }
    
    public void setID(int newID){
        ID = newID;
    }
    
    public char[] getCategoryName(){
        return categoryName;
    }
    
    public void setCategoryName(String newName){
        categoryName = newName.toCharArray();
    }
    
    @Override
    public String toString(){
        return ID + ", " + Arrays.toString(categoryName);
    }
}
