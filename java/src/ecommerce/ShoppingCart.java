package ecommerce;
import java.util.Arrays;

public class ShoppingCart
{
	private int customerID;
	private int itemID;
        private int sellerId;
	private int quantity;
	private double price;
	private double totalPrice;
	
	public ShoppingCart() {
		//default initializer
		customerID = 0;
		itemID = 0;
                sellerId = 0;
		quantity = 0;
		price = 0;
		totalPrice = 0;
	}
	
	public ShoppingCart(int IDArg, int itemIDArg, int sellerId, int quantityArg, double priceArg, double totalPriceArg){
		//full
		this.customerID = IDArg;
		this.itemID = itemIDArg;
                this.sellerId = sellerId;
		this.quantity = quantityArg;
		this.price = priceArg;
		this.totalPrice = totalPriceArg;
	}
	
	public int getCustomerID(){
		return customerID;
	}
	
	public void setCustomerID(int IDArg) {
		customerID = IDArg;
	}
	
	public int getItemID(){
		return itemID;
	}
	
	public void setItemID(int itemIDArg){
		itemID = itemIDArg;
	}
        
        public int getSellerID(){
            return sellerId;
        }
        
        public void setSellerID(int sellerId){
            this.sellerId = sellerId;
        }
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setQuantity(int quantityArg){
		quantity = quantityArg;
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setPrice(double priceArg){
		price = priceArg;
	}
	
	public double getTotalPrice(){
		return totalPrice;
	}
	
	public void setTotalPrice(double totalPriceArg){
		totalPrice = totalPriceArg;
	}

	public String toString(){
		return customerID + ", " + itemID + ", " + sellerId + ", "+ quantity + ", " + price + ", " + totalPrice;
	}
}
