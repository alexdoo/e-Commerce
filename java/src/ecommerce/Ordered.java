package ecommerce;

public class Ordered {
    private int orderId;
    private int itemId;
    
    public Ordered(){
        orderId = 0;
        itemId = 0;
    }
    
    public Ordered(int orderId, int itemId){
        this.orderId = orderId;
        this.itemId = itemId;
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
    
    @Override
    public String toString(){
        return orderId + ", " + itemId;
    }
}
