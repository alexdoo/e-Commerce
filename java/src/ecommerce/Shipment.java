package ecommerce;
import java.util.Arrays;

public class Shipment
{
	private int courierID;
	private int orderID;
	private char[] shippingType;
	private char[] address;
	private double surcharge;
	
	public Shipment() {
		//default initializer
		courierID = 0;
		orderID = 0;
		shippingType = new char[20];
		address = new char[40];
		surcharge = 0;
	}
	
	public Shipment(int courierIDArg, int orderIDArg, char[] shippingTypeArg, char[] addressArg, double surchargeArg){
		//full
		this.courierID = courierIDArg;
		this.orderID = orderIDArg;
		this.shippingType = shippingTypeArg;
		this.address = addressArg;
		this.surcharge = surchargeArg;
	}
	
	public int getCourierID(){
		return courierID;
	}
	
	public void setCourierID(int courierIDArg) {
		courierID = courierIDArg;
	}
	
	public int getOrderID(){
		return orderID;
	}
	
	public void setOrderID(int orderIDArg){
		orderID = orderIDArg;
	}
	
	public char[] getShippingType(){
		return shippingType;
	}
	
	public void setShippingType(String shippingTypeArg){
		shippingType = shippingTypeArg.toCharArray();
	}
	
	public char[] getAddress(){
		return address;
	}
	
	public void setAddress(String addressArg){
		address = addressArg.toCharArray();
	}
	
	public double getSurcharge(){
		return surcharge;
	}
	
	public void setSurcharge(double surchargeArg){
		surcharge = surchargeArg;
	}

        @Override
	public String toString(){
		return courierID + ", " + orderID + ", " + surcharge + ", " + Arrays.toString(shippingType) + ", " + Arrays.toString(address);
	}
}
