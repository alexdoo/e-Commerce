package ecommerce;
import java.util.Arrays;

public class BillingInfo
{
	private int ID;
	private char[] address;
	private char[] cardType;
	private long cardNumber;
	private char[] cardExpiryDate;
	private long phoneNumber;
	
	public BillingInfo() {
		//default initializer
		ID = 0;
		address = new char[40];
		cardType = new char[10];
		cardNumber = 0;
		cardExpiryDate = new char[10];
		phoneNumber = 0;
	}
	
	public BillingInfo(int IDArg, char[] addressArg, char[] cardTypeArg, long cardNumberArg, char[] cardExpiryDateArg, long phoneNumberArg){
		//full
		this.ID = IDArg;
		this.address = addressArg;
		this.cardType = cardTypeArg;
		this.cardNumber = cardNumberArg;
		this.cardExpiryDate = cardExpiryDateArg;
		this.phoneNumber = phoneNumberArg;
	}
	
	public int getID(){
		return ID;
	}
	
	public void setID(int IDArg) {
		ID = IDArg;
	}
	
	public char[] getAddress(){
		return address;
	}
	
	public void setAddress(String addressArg){
		address = addressArg.toCharArray();
	}
	
	public char[] getCardType(){
		return cardType;
	}
	
	public void setCardType(String cardTypeArg){
		cardType = cardTypeArg.toCharArray();
	}
	
	public long getCardNumber(){
		return cardNumber;
	}
	
	public void setCardNumber(long cardNumberArg){
		cardNumber = cardNumberArg;
	}
	
	public char[] getCardExpiryDate(){
		return cardExpiryDate;
	}
	
	public void setCardExpiryDate(String cardExpiryDateArg){
		cardExpiryDate = cardExpiryDateArg.toCharArray();
	}
	
	public long getPhoneNumber(){
		return phoneNumber;
	}
        
        public void setPhoneNumber(long input){
            phoneNumber = input;
        }
	
	public void setBillingInfoID(int phoneNumberArg){
		phoneNumber = phoneNumberArg;
	}

        @Override
	public String toString(){
		return cardNumber + ", " + phoneNumber + ", " + Arrays.toString(address) + ", " + Arrays.toString(cardType) + ", " + Arrays.toString(cardExpiryDate);
	}
}
