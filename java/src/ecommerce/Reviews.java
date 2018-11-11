package ecommerce;
import java.util.Arrays;

public class Reviews
{
	private int customerID;
	private int itemID;
	private int sellerID;
	private char [] review;
	private int rating;
	
	public Reviews() {
		//default initializer
                customerID = 0;
                itemID = 0;
                sellerID = 0;
                review = new char[255];
                rating = 0;
	}
	
	public Reviews(int customerIDArg, int itemIDArg, int sellerIDArg, char [] reviewArg, int ratingArg){
		this.customerID = customerIDArg;
		this.itemID = itemIDArg;
		this.sellerID = sellerIDArg;
		this.review = reviewArg;
		this.rating = ratingArg;
	}
	
	public int getCustomerID(){
		return customerID;
	}
	
	public void setCustomerID(int customerIDArg) {
		this.customerID = customerIDArg;
	}
	
	public int getItemID(){
		return itemID;
	}
	
	public void setItemID(int setItemIDArg){
		itemID = setItemIDArg;
	}
	
	public int getSellerID(){
		return sellerID;
	}
	
	public void setSellerID(int sellerIDArg){
		sellerID = sellerIDArg;
	}
	
	public char [] getReview(){
		return review;
	}
	
	public void setReview(String reviewArg){
		review = reviewArg.toCharArray();
	}
	
	public int getRating(){
		return rating;
	}
	
	public void setRating(int ratingArg){
		rating = ratingArg;
	}

        @Override
	public String toString(){
		return itemID + ", " + customerID + ", " + sellerID + ", " + rating + ", " + Arrays.toString(review);
	}
}
