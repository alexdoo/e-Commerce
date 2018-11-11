package ecommerce;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLHandler {

    private Connection connect;
    private Statement statement;
    private ResultSet resultSet;

    // The constructor will automatically connect to database. 
    public SQLHandler() {
        connect = null;
        statement = null;
        resultSet = null;
    }
    
    public void openConnection(){
        try {
            // Registering the JDBC driver
//            Class<?> forName = Class.forName("com.mysql.jdbc.Driver");
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            // Connect to database
            connect = DriverManager.getConnection("jdbc:mysql://localhost:8080?autoReconnect=true&useSSL=false", "username", "password");
            // create statment to execute
            statement = connect.createStatement();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.out);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(SQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
        Used to execute a specific query.
    */
    public void modifyData(String query){
        try{
            statement.executeUpdate(query);
        } catch (SQLException ex){
        }
    }
    
    /*
        Must be called before any get?() with select query.
        Must call closeStatment() when done with the query; except when next() returns false
            - Basically, if you want to end a loop parsing the rows early, you have to call closeStatemnt()
        @Params String query = the SELECT query to run
    */
    public void getTable(String query){        
        try{
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    /*
        Moves to next row, returns if successful
        If there is no next row, returns false and closes ResultSet
    */
    public boolean next(){
        boolean result = false;
        try { 
            if (resultSet != null){
                result = resultSet.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        if (result == false){
            closeResultSet();
        }
        
        return result;
    }
    
    /*
        The get?() methods are meant to be called after getTable() and next().
        Takes the row/relation and returns the specific column/object
    */
    public Item getItem(){
        Item item = new Item();
        try{
            String res;
            item.setID(resultSet.getInt("Id"));
            item.setSellerID(resultSet.getInt("SellerId"));
            item.setCategoryID(resultSet.getInt("CategoryId"));
            item.setPrice(resultSet.getDouble("Price"));
            res = resultSet.getString("ItemName");
            item.setItemName(res);
            res = resultSet.getString("Description");
            item.setDescription(res);
                
        }catch (SQLException ex){
            ex.printStackTrace(System.out);
        }catch (NullPointerException ex){
                System.out.print("");
        }
        
        return item;
    }
    
    public Inventory getInventory(){
        Inventory inv = new Inventory();
        try{
            inv.setItemID(resultSet.getInt("ItemId"));
            inv.setSellerID(resultSet.getInt("SellerId"));
            inv.setQuantity(resultSet.getInt("Quantity"));
            inv.setPrice(resultSet.getInt("Price"));
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
        
        return inv;
    }
    
    public Category getCategory(){
        Category cat = new Category();
        try{
            cat.setID(resultSet.getInt("Id"));
            cat.setCategoryName(resultSet.getString("CategoryName"));
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
        
        return cat;
    }
    
    public Seller getSeller(){
        Seller seller = new Seller();
        try{
            seller.setID(resultSet.getInt("Id"));
            seller.setBillingInfoID(resultSet.getInt("BillingInfoId"));
            seller.setCompanyName(resultSet.getString("CompanyName"));
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
        
        return seller;
    }
    
    public User getUser(){
        User user = new User();
        try{
            user.setSellerID(resultSet.getInt("SellerId"));
            user.setCustomerID(resultSet.getInt("CustomerId"));
            user.setUsername(resultSet.getString("Username"));
            user.setPassword(resultSet.getString("Password"));
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
        
        return user;
    }
    
    public BillingInfo getBillingInfo(){
        BillingInfo bi = new BillingInfo();
        try{
            bi.setID(resultSet.getInt("Id"));
            bi.setCardNumber(resultSet.getLong("CardNumber"));
            bi.setPhoneNumber(resultSet.getLong("PhoneNumber"));
            bi.setAddress(resultSet.getString("Address"));
            bi.setCardType(resultSet.getString("CardType"));
            bi.setCardExpiryDate(resultSet.getString("CardExpiryDate"));
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
        
        return bi;
    }
    
    public Orders getOrders(){
        Orders order = new Orders();
        try{
            order.setID(resultSet.getInt("Id"));
            order.setCustomerID(resultSet.getInt("CustomerId"));
            order.setBillingInfoID(resultSet.getInt("BillingInfoId"));
            order.setOrderDate(resultSet.getDate("OrderDate"));
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
        
        return order;
    }
    
    public Ordered getOrdered(){
        Ordered order = new Ordered();
        try{
            order.setOrderID(resultSet.getInt("OrderId"));
            order.setItemID(resultSet.getInt("ItemId"));
            order.setSellerID(resultSet.getInt("SellerId"));
            order.setQuantity(resultSet.getInt("Quantity"));
            order.setPrice(resultSet.getDouble("Price"));
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
        
        return order;
    }
    
    public Customers getCustomer(){
        Customers cm = new Customers();
        try{
            cm.setID(resultSet.getInt("Id"));
            cm.setBillingInfoID(resultSet.getInt("BillingInfoId"));
            cm.setLastName(resultSet.getString("LastName"));
            cm.setFirstName(resultSet.getString("FirstName"));
            cm.setEmail(resultSet.getString("Email"));
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
        return cm;
    }
    
    public Reviews getReviews(){
        Reviews rw = new Reviews();
        try{
            rw.setItemID(resultSet.getInt("ItemId"));
            rw.setCustomerID(resultSet.getInt("CustomerId"));
            rw.setSellerID(resultSet.getInt("SellerId"));
            rw.setRating(resultSet.getInt("Rating"));
            rw.setReview(resultSet.getString("Review"));
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
        return rw;
    }
    
    public Shipment getShipment(){
        Shipment ship = new Shipment();
        try{
            ship.setCourierID(resultSet.getInt("CourierId"));
            ship.setOrderID(resultSet.getInt("OrderId"));
            ship.setSurcharge(resultSet.getDouble("Surcharge"));
            ship.setAddress(resultSet.getString("Address"));
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
        return ship;
    }
    
    public ShoppingCart getShoppingCart(){
        ShoppingCart sc = new ShoppingCart();
        try{
            sc.setCustomerID(resultSet.getInt("CustomerId"));
            sc.setItemID(resultSet.getInt("ItemId"));
            sc.setSellerID(resultSet.getInt("SellerId"));
            sc.setQuantity(resultSet.getInt("Quantity"));
            sc.setPrice(resultSet.getDouble("Price"));
            sc.setTotalPrice(resultSet.getDouble("TotalPrice"));
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
        return sc;
    }
    
    /*
        Used for closing Statement individually.
    */
    public void closeStatement(){
        try {
            if (statement != null){
                statement.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    /*
        Used for closing ResultSet individually.
    */
    public void closeResultSet(){
        try {
            if (resultSet != null){
                resultSet.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    /*
        Must be called after done with Connection.
        Used for closing Connection individually.
    */
    public void closeConnection(){
        try {
            if (connect != null){
                connect.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    /*
        Used for closing/cleaning SQL enviornment
    */
    public void shutdown() {
        try {
            if (resultSet != null){
                resultSet.close();
            }
            if (statement != null){
                statement.close();
            }
            if (connect != null){
                connect.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    /*
        inputs query to db (adding a row to it's respective table)
        insertData() are overriding methods for all table objects.
    */
    public void insertData(BillingInfo bi){
        String insert = "INSERT INTO BillingInfo (CardNumber, PhoneNumber, "
                + "Address, CardType, CardExpiryDate)  VALUES(";
        insert += bi.toString() + ")";
        try{
            statement.executeUpdate(insert);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    public void insertData(Reviews rw){
        String insert = "INSERT INTO Reviews (ItemId, CustomerId, SellerId, "
                + "Rating, Review) VALUES (";
        insert += rw.toString() + ")";
        try{
            statement.executeUpdate(insert);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    public void insertData(Shipment sp){
        String insert = "INSERT INTO Shipment (CourierId, OrderId, Surcharge, "
                + "ShippingType, Address) VALUES (";
        insert += sp.toString() + ")";
        try{
            statement.executeUpdate(insert);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    public void insertData(ShoppingCart sc){
        String insert = "INSERT INTO ShoppingCart (CustomerId, ItemId, SellerID,"
                + " Quantity, Price, TotalPrice) VALUES (";
        insert += sc.toString() + ")";
        try{
            statement.executeUpdate(insert);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    public void insertData(Orders order){
        String insert = "INSERT INTO Orders (CustomerId, BillingInfoId, "
                + "OrderDate) VALUES (";
        insert += order.toString() + ")";
        try{
            statement.executeUpdate(insert);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    public void insertData(Ordered order){
        String insert = "INSERT INTO Orderd (OrderID, ItemID) VALUES (";
        insert += order.toString() + ")";
        try{
            statement.executeUpdate(insert);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    public void insertData(Customers customer){
        String insert = "INSERT INTO Customers (BillingInfoId, LastName, "
                + "FirstName, Email) VALUES (";
        insert += customer.toString() + ")";
        try{
            statement.executeUpdate(insert);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    public void insertData(Item item){
        String insert = "INSERT INTO Item (Id, SellerId, CategoryId, Price, "
                + "ItemName, Description) VALUES (";
        insert += item.toString() + ")";
        try{
            statement.executeUpdate(insert);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    public void insertData(Seller seller){
        String insert = "INSERT INTO Seller (BillingInfoId, CompanyName) VALUES (";
        insert += seller.toString() + ")";
        try{
            statement.executeUpdate(insert);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    public void insertData(Inventory inv){
        String insert = "INSERT INTO Inventory (ItemId, SellerId, Quantity, Price) VALUES (";
        insert += inv.toString() + ")";
        try{
            statement.executeUpdate(insert);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    public void insertData(Category cat){
        String insert = "INSERT INTO Category (CategoryName) VALUES (";
        insert += cat.toString() + ")";
        try{
            statement.executeUpdate(insert);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    public void insertData(User user){
        String insert = "INSERT INTO User (SellerId, CustomerId, Username, Password) VALUES (";
        insert += user.toString() + ")";
        try{
            statement.executeUpdate(insert);
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }    
}
