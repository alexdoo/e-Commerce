package ecommerce;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Orders
{
	private int ID;
	private int customerID;
	private Date orderDate;
	private int billingInfoID;
	
	public Orders() {
		//default initializer
		ID = 0;
		customerID = 0;
		orderDate = new Date();
		billingInfoID = 0;
	}
	
	public Orders(int IDArg, int customerIDArg, Date orderDateArg, int billingInfoIDArg){
		//full
		this.ID = IDArg;
		this.customerID = customerIDArg;
		this.orderDate = orderDateArg;
		this.billingInfoID = billingInfoIDArg;
	}
	
	public int getID(){
		return ID;
	}
	
	public void setID(int IDArg) {
		ID = IDArg;
	}
	
	public int getCustomerID(){
		return customerID;
	}
	
	public void setCustomerID(int customerIDArg){
		customerID = customerIDArg;
	}
	
	public Date getOrderDate(){
		return orderDate;
	}
	
	public void setOrderDate(Date orderDateArg){
		orderDate = orderDateArg;
	}
	
	public int getBillingInfoID(){
		return billingInfoID;
	}
	
	public void setBillingInfoID(int billingInfoIDArg){
		billingInfoID = billingInfoIDArg;
	}

        @Override
	public String toString(){
            SimpleDateFormat datefort = new SimpleDateFormat("MM/dd/yyyy");
            return customerID + ", " + billingInfoID + ", " + datefort.format(orderDate);
	}
}