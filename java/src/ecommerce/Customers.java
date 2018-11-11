package ecommerce;
import java.util.Arrays;

public class Customers
{
	private int ID;
	private char[] lastName;
	private char[] firstName;
	private char[] email;
	private int billingInfoID;
	
	public Customers() {
		//default initializer
		ID = 0;
		lastName = new char[20];
		firstName = new char[20];
		email = new char[40];
		billingInfoID = 0;
	}
	
	public Customers(int IDArg, char[] lastNameArg, char[] firstNameArg, char [] emailArg, int billingInfoIDArg){
		//full
		this.ID = IDArg;
		this.lastName = lastNameArg;
		this.firstName = firstNameArg;
		this.email = emailArg;
		this.billingInfoID = billingInfoIDArg;
	}
	
	public int getID(){
		return ID;
	}
	
	public void setID(int IDArg) {
		ID = IDArg;
	}
	
	public char[] getLastName(){
		return lastName;
	}
	
	public void setLastName(String lastNameArg){
		lastName = lastNameArg.toCharArray();
	}
	
	public char[] getFirstName(){
		return firstName;
	}
	
	public void setFirstName(String firstNameArg){
		firstName = firstNameArg.toCharArray();
	}
	
	public char [] getEmail(){
		return email;
	}
	
	public void setEmail(String emailArg){
		email = emailArg.toCharArray();
	}
	
	public int getBillingInfoID(){
		return billingInfoID;
	}
	
	public void setBillingInfoID(int billingInfoIDArg){
		billingInfoID = billingInfoIDArg;
	}

        @Override
	public String toString(){
		return billingInfoID + ", '" + Arrays.toString(lastName) + "', '" + Arrays.toString(firstName) + "', '" + Arrays.toString(email) + "'";
	}
}
