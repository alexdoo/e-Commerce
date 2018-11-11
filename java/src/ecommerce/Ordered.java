package ecommerce;

public class Ordered {
    private int orderId;
    private int itemId;
    private int sellerId;
    private int quantity;
    private double price;
    
    public Ordered(){
        orderId = 0;
        itemId = 0;
        sellerId = 0;
        quantity = 0;
        price = 0;
    }
    
    public Ordered(int orderId, int itemId, int sell, int qty, double pc){
        this.orderId = orderId;
        this.itemId = itemId;
        sellerId = sell;
        quantity = qty;
        price = pc;
    }
    
    public int getOrderID(){
        return orderId;
    }
    
    public void setOrderID(int orderId){
        this.orderId = orderId;
    }
    
    public int getItemID(){
        return itemId;
    }
    
    public void setItemID(int itemId){
        this.itemId = itemId;
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
    
    public void setQuantity(int qty){
        quantity = qty;
    }
    
    public double getPrice(){
        return price;
    }
    
    public void setPrice(double pc){
        price = pc;
    }
    
    @Override
    public String toString(){
        return orderId + ", " + itemId;
    }
}
