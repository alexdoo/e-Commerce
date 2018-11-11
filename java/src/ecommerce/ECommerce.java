package ecommerce;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Ecommerce extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        User user = new User();
        Seller seller = new Seller();
        Item item = new Item();
        Inventory inventory = new Inventory();
        Category category = new Category();
        Reviews review = new Reviews();
        Customers customer = new Customers();
        ArrayList<ShoppingCart> cart = new ArrayList<>();
        BillingInfo billing = new BillingInfo();
        Orders order = new Orders();
        ArrayList<Ordered> ordered = new ArrayList<>();
        Shipment shipping = new Shipment();
        
        SQLHandler sql = new SQLHandler();
        // Read SQL script and create relations
        String parse = new String();
        String query = new String();
        File file = new File("src/SQLScript.sql");
            // parses queries and executes them in db (assumes script is written properly)
            // may replace if preloaded database is used.
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                parse = scan.nextLine();
                query += parse.replaceAll("\t+", " ");
                if (parse.contains(";")){
                    query = query.replace(";", "");
                    sql.modifyData(query);
                    query = "";
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("SQL Script file not found.");
        }
        
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        /*
            Where Customer--ShoppingCart--Item interacts
        */
        
        // if user is a customer
//        if (user.getCustomerID() > 0){
//            
//            // retreive user db info
//            sql.getTable("SELECT * FROM User WHERE Username = " + new String(user.getUsername()));
//            if (sql.next()){
//                user = sql.getUser();
//                sql.closeResultSet();
//            }
//            
//            // retreive customer db info
//            sql.getTable("SELECT * FROM Customer WHERE Id = " + user.getCustomerID());
//            if (sql.next()){
//                customer = sql.getCustomer();
//                sql.closeResultSet();
//            }
//            
//            // retreive shopping cart db info
//            sql.getTable("SELECT * FROM ShoppingCart WHERE CustomerId = " + customer.getID());
//            while (sql.next()){
//                cart.add(sql.getShoppingCart());
//            }
//            
//            // retreive billing info db
//            sql.getTable("SELECT * FROM BillingInfo WHERE Id = " + customer.getBillingInfoID());
//            if (sql.next()){
//                billing = sql.getBillingInfo();
//                sql.closeResultSet();
//            }
//        }
//        else {  // user is a seller
//            // retreive user info db
//            sql.getTable("SELECT * FROM User WHERE Username = " + new String(user.getUsername()));
//            if (sql.next()){
//                user = sql.getUser();
//                sql.closeResultSet();
//            }
//            
//            // retreive seller info db
//            sql.getTable("SELECT * FROM Seller WHERE Id = " + user.getSellerID());
//            if (sql.next()){
//                seller = sql.getSeller();
//                sql.closeResultSet();
//            }
//            
//            // retreive billing info db
//            sql.getTable("SELECT * FROM BillingInfo WHERE Id = " + seller.getID());
//            if (sql.next()){
//                billing = sql.getBillingInfo();
//            }
//        }
        
        
        // if (Customer adds an item to shoppingcart){
            cart.add((new ShoppingCart(customer.getID(), item.getID(), item.getSellerID(), 1, item.getPrice(), item.getPrice())));
            sql.insertData(cart.get(cart.size()-1));
        // }
            
        // if (Customer deletes an item to shoppingcart){
            sql.modifyData("DELETE FROM ShoppingCart WHERE CustomerId = " + customer.getID() 
                    + "AND ItemId=" + item.getID());
            for(int i=0;i<cart.size();i++){
                if (item.getID() == cart.get(i).getItemID()){
                    cart.remove(i);
                    break;
                }
            }
        // }
        
        // if (Customer changes item quantity in shopping cart){
            int quantity = 0;
            // quantity = result from input;
            for (int i=0;i<cart.size();i++){
                if (item.getID() == cart.get(i).getItemID()){
                    cart.get(i).setQuantity(quantity);
                    cart.get(i).setTotalPrice(i*cart.get(i).getPrice());
                    sql.modifyData("UPDATE ShoppingCart SET Quantity = " + quantity + ", TotalPrice = "
                            + cart.get(i).getTotalPrice() + " WHERE CustomerId = " + customer.getID()
                            + "AND ItemId = " + item.getID());
                    break;
                }
            }
        // }
        
        // if (Customer checks out cart){
            // add to Orders db and instance
            order.setOrderDate(new Date());
            order.setCustomerID(customer.getID());
            order.setBillingInfoID(billing.getID());
            sql.insertData(order);
            
            // add to Ordered db and variable, while deleting shopping cart db and instance along with decreasing Inventory quantity
            while (!cart.isEmpty()){
                ordered.add(new Ordered(order.getID(), cart.get(0).getItemID()));
                sql.insertData(ordered.get(ordered.size()-1));
                
                sql.modifyData("DELETE FROM ShoppingCart WHERE CustomerId = " + cart.get(0).getCustomerID() 
                        + " AND ItemId = " + cart.get(0).getItemID());
                sql.modifyData("UPDATE Inventory SET Quantity = Quantity - " + cart.get(0).getQuantity() 
                        + " WHERE ItemId = " + cart.get(0).getItemID() + " AND SellerId = " 
                        + cart.get(0).getSellerID());
                cart.remove(0);
            }
            
            // get shipping info from ui
                sql.insertData(shipping);
        // }
            
        // if (a seller puts items up for sale){
            // item and inventory will be filled with input from ui...
            
            // add to Item db
            sql.insertData(item);
            // now add item to Inventory db
            sql.insertData(inventory);
        // }
        
        // if (seller changes price of item){
           sql.modifyData("UPDATE Inventory SET Price = " + inventory.getPrice() 
                   + "WHERE ItemId = " + inventory.getItemID() + "AND SellerId = "
                   + inventory.getSellerID());
           
           sql.modifyData("UPDATE Item SET Price " + inventory.getPrice() +
                   " WHERE Id = " + inventory.getItemID() 
                   + "AND SellerId = " + inventory.getSellerID());
           
           // check if item is already in a shopping cart; if true update price
            sql.getTable("SELECT * FROM ShoppingCart WHERE ItemId = " + inventory.getItemID()
                    + " AND SellerId = " + inventory.getSellerID());
            while (sql.next()){
                ShoppingCart sc = sql.getShoppingCart();
                // maybe close resultset
                // sql.closeResultSet();
                // maybe close statement
                sql.modifyData("UPDATE ShoppingCart SET Price = " + inventory.getPrice()
                        + " WHERE ItemId = " + sc.getItemID() + " AND CustomerId = "
                        + sc.getCustomerID());
                
                // possible alert for customer price has changed
            }
        // }
        
        // if (seller changes quantity of an item) {
            sql.modifyData("Update Inventory SET Quantity = " + inventory.getQuantity()
                    + "WHERE ItemId = " + inventory.getItemID() + " AND SellerId = "
                    + inventory.getSellerID());
        // }
        
        // if (customer leaves a review){
            // obtain review info from ui
            
            // add review to db
            sql.insertData(review);
        // }
        
//        // if (creating a new account){
//            // fill in billing
//            sql.insertData(billing);
//            
//            //if (seller) {
//                // fill in seller
//                sql.insertData(seller);
//                sql.closeConnection();
//                sql.openConnection();
//                sql.getTable("SELECT * FROM Seller WHERE BillingInfoId = " 
//                        + billing.getID());
//                if (sql.next()){
//                    seller = sql.getSeller();
//                    sql.closeResultSet();
//                }
//                sql.closeConnection();
//                sql.openConnection();
//                user.setSellerID(seller.getID());
//                // user.setUsername();
//                // user.setPassword();
//            // }
//            // else if customer {
//                // fill in customer
//                sql.insertData(customer);
//                sql.closeConnection();
//                sql.openConnection();
//                sql.getTable("SELECT * FROM Customers WHERE BillingInfoId = " 
//                        + billing.getID());
//                if (sql.next()){
//                    customer = sql.getCustomer();
//                    sql.closeResultSet();
//                }
//                sql.closeConnection();
//                sql.openConnection();
//                user.setCustomerID(customer.getID());
//                // user.setUsername();
//                // user.setPassword();
//            // }
//        // }
//        
//        // FOR THE CUSTOMER ITEM TABLE
//        sql.closeConnection();
//        sql.openConnection();
//        sql.getTable("SELECT * FROM Items");
//        while (sql.next()){
//            item = sql.getItem();
//            CustomerTable ct = new CustomerTable();
//            ct.setProductName(item.getItemName());
//            ct.setProductPrice(item.getPrice());
//            ct.setProductDescription(item.getDescription());
//            ct.setItemID(item.getID());
//            ct.setSellerID(item.getSellerID());
//        }
//        ct.forEach(x -> {
//            sql.getTable("SELECT * FROM Seller WHERE Id = " + x.getSellerID());
//            if (sql.next()){
//                seller = sql.getSeller();
//                x.setCompanyName(seller.getCompanyName());
//            }
//            sql.getTable("SELECT * FROM Inventory WHERE ItemId = " + x.getItemID() + " AND SellerId = " + x.getSellerID());
//            inventory = sql.getInventory();
//            if (inventory.getQuantity() > 0){
//                x.setInStock(true);
//            }
//            else{
//                x.setInStock(false);
//            }
//        })
//        //
                
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){     
        
        launch(args);
    }
    
}
