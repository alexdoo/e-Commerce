/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecommerce;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author Alex
 */
public class ECommerce {

    static SQLHandler sql = new SQLHandler();
    static User user = new User();
    static Seller seller = new Seller();
    static Customers customer = new Customers();
    static BillingInfo billing = new BillingInfo();

    public static void main(String[] args) {
        Item item = new Item();
        Inventory inventory = new Inventory();
        Category category = new Category();
        Reviews review = new Reviews();
        Customers customer = new Customers();
        ArrayList<ShoppingCart> cart = new ArrayList<>();
        ArrayList<Orders> order = new ArrayList<>();
        ArrayList<Ordered> ordered = new ArrayList<>();
        Shipment shipping = new Shipment();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        // Read SQL script and create relations
        String parse = new String();
        String query = new String();

        sql.openConnection();
        File file = new File("src/SQLScript.sql");
        // parses queries and executes them in db (assumes script is written properly)
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                parse = scan.nextLine();
                query += parse.replaceAll("\t+", " ");
                if (parse.contains(";")) {
                    query = query.replace(";", "");
                    sql.modifyData(query);
                    query = "";
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("SQL Script file not found.");
        }
        sql.closeConnection();

        Scanner sc = new Scanner(System.in);
        String result = new String();
        while (true) {
            System.out.print("Are you an existing user? ");
            result = sc.nextLine();
            if (result.compareToIgnoreCase("yes") == 0) {
                while (user.getCustomerID() == 0 && user.getSellerID() == 0) {
                    login();
                    sql.closeConnection();
                    sql.openConnection();
                    sql.getTable("SELECT * FROM User WHERE Username = '" + new String(user.getUsername())
                            + "' AND Password = '" + new String(user.getPassword()) + "'");
                    if (sql.next()) {
                        user = sql.getUser();
                    }
                    sql.closeConnection();
                    sql.openConnection();

                    if (user.getCustomerID() > 0) {
                        // if user is a customer
                        String usage;
                        usage = "Command Usage: \n\t"
                                + "help : prints this usage\n\t"
                                + "shop : lists items that are available\n\t"
                                + "account : shows customer's account info\n\t"
                                + "edit billinginfo [attribute(no spaces)] [new value]: edits [attribute] of billing information\n\t"
                                + "show cart : shows shopping cart\n\t"
                                + "add cart [item id] [seller id] [quantity]: adds [quantity] item(s) to shopping cart\n\t"
                                + "edit cart [index] [quantity] : edits shopping cart's quantity for a item at [index]\n\t"
                                + "checkout : orders content in shopping cart\n\t"
                                + "show orders : shows order\n\t"
                                + "logout : exits application";
                        System.out.println(usage);

                        while (true) {
                            System.out.print("Enter a command: ");
                            result = sc.nextLine();
                            String cmd[];
                            cmd = result.split(" ");
                            if (cmd[0].compareToIgnoreCase("help") == 0) {
                                System.out.println(usage);
                            } else if (cmd[0].compareToIgnoreCase("shop") == 0) {
                                sql.closeConnection();
                                sql.openConnection();
                                sql.getTable("SELECT * FROM Item");
                                System.out.println("----------------------SHOP----------------------");
                                System.out.printf("%-10s |\t %-20s |\t %-10s |\t %12s |\t %s\n",
                                        "Item ID", "Item Name", "Seller ID", "Price", "Item Description");
                                System.out.println("------------------------------------------------");
                                while (sql.next()) {
                                    item = sql.getItem();
                                    System.out.printf("%-10d  \t %-20s  \t %-10d  \t $%10.2f  \t " + new String(item.getDescription()),
                                            item.getID(), new String(item.getItemName()), item.getSellerID(), item.getPrice());
                                }
                                System.out.println("------------------------------------------------");
                                sql.closeConnection();
                                sql.openConnection();
                            } else if (cmd[0].compareToIgnoreCase("account") == 0) {
                                System.out.println("Address: " + new String(billing.getAddress()));
                                System.out.println("Card Type: " + new String(billing.getCardType()));
                                System.out.println("Card Number: (Ending with) " + billing.getCardNumber() % 100);
                                System.out.println("Card Expiry Date: " + new String(billing.getCardExpiryDate()));
                                System.out.println("Phone number: " + billing.getPhoneNumber());
                            } else if (cmd[0].compareToIgnoreCase("edit") == 0) {
                                if (cmd[1].compareToIgnoreCase("cart") == 0) {
                                    int index = Integer.parseInt(cmd[2]);
                                    int update = Integer.parseInt(cmd[3]);
                                    if (update == 0) {
                                        cart.remove(index);
                                    } else {
                                        cart.get(index).setQuantity(update);
                                    }
                                } else if (cmd[1].compareToIgnoreCase("billinginfo") == 0) {
                                    if (cmd[2].compareToIgnoreCase("cardnumber") == 0) {
                                        sql.modifyData("UPDATE BillingInfo SET CardNumber = " + Long.parseLong(cmd[3]) + "WHERE Id = " + billing.getID());
                                        billing.setCardNumber(Long.parseLong(cmd[3]));
                                    } else if (cmd[2].compareToIgnoreCase("cardtype") == 0) {
                                        sql.modifyData("UPDATE BillingInfo SET CartType = " + cmd[3] + "WHERE Id = " + billing.getID());
                                        billing.setCardType(cmd[3]);
                                    } else if (cmd[2].compareToIgnoreCase("cardexpriydate") == 0) {
                                        sql.modifyData("UPDATE BillingInfo SET CardExpiryDate = " + cmd[3] + "WHERE Id = " + billing.getID());
                                        billing.setCardExpiryDate(cmd[3]);
                                    } else if (cmd[2].compareToIgnoreCase("address") == 0) {
                                        sql.modifyData("UPDATE BillingInfo SET Address = " + cmd[3] + "WHERE Id = " + billing.getID());
                                        for (int i = 3; i < cmd.length; i++) {
                                            billing.setAddress(cmd[i]);
                                        }
                                    } else if (cmd[2].compareToIgnoreCase("phonenumber") == 0) {
                                        sql.modifyData("UPDATE BillingInfo SET PhoneNumber = " + Long.parseLong(cmd[3]) + "WHERE Id = " + billing.getID());
                                        billing.setPhoneNumber(Long.parseLong(cmd[3]));
                                    } else {
                                        System.out.println("Invalid command.");
                                        System.out.println(usage);
                                    }
                                } else {
                                    System.out.println("Invalid command.");
                                    System.out.println(usage);
                                }
                            } else if (cmd[0].compareToIgnoreCase("show") == 0) {
                                if (cmd[1].compareToIgnoreCase("cart") == 0) {
                                    System.out.println("------------------------Cart-----------------------");
                                    System.out.printf("%-20s |\t %-20s |\t %-20s |\t %-20s |\t %-10s |\t %-12s |\t %-12s",
                                            "Item Name", "Company Name", "Item ID", "Seller ID", "Quantity", "Price", "Total Price");
                                    for (int i = 0; i < cart.size(); i++) {
                                        sql.closeConnection();
                                        sql.openConnection();
                                        sql.getTable("SELECT * FROM Item WHERE ItemId = " + cart.get(i).getItemID()
                                                + " AND SellerId = " + cart.get(i).getSellerID());
                                        if (sql.next()) {
                                            item = sql.getItem();
                                        }
                                        sql.closeConnection();
                                        sql.openConnection();
                                        sql.getTable("SELECT * FROM Seller WHERE Id = " + cart.get(i).getSellerID());
                                        if (sql.next()) {
                                            seller = sql.getSeller();
                                        }
                                        sql.closeConnection();
                                        sql.openConnection();

                                        System.out.printf("%-20s  \t %-20s  \t %-20d  \t %-20d  \t %10d  \t %10.2f  \t %10.2f",
                                                new String(item.getItemName()), new String(seller.getCompanyName()), cart.get(i).getItemID(), cart.get(i).getSellerID(),
                                                cart.get(i).getQuantity(), cart.get(i).getPrice(), cart.get(i).getTotalPrice());
                                    }

                                    System.out.println("-----------------------------------------------------");
                                } else if (cmd[1].compareToIgnoreCase("orders") == 0) {
                                    System.out.println("-----------------------Orders-----------------------");
                                    System.out.printf("%-10s |\t %-10s |\t %-20s |\t %-10s |\t %10s |\t %10.2f |\t %9s\n",
                                            "Order ID", "Item ID", "Item Name", "Seller ID", "Quantity", "Price", "Ordered Date");

                                    sql.closeConnection();
                                    sql.openConnection();
                                    sql.getTable("SELECT * FROM Orders WHERE CustomerId = " + customer.getID());
                                    while (sql.next()) {
                                        order.add(sql.getOrders());
                                    }
                                    sql.closeConnection();
                                    sql.openConnection();
                                    for (int i = 0; i < order.size(); i++) {
                                        sql.getTable("SELECT * FROM Ordered WHERE OrderId = " + order.get(i).getID());
                                        while (sql.next()) {
                                            ordered.add(sql.getOrdered());
                                        }
                                        sql.closeConnection();
                                        sql.openConnection();
                                    }
                                    for (int i = 0; i < order.size(); i++) {
                                        int index = 0;
                                        sql.getTable("SELECT * FROM Item WHERE Id = " + ordered.get(i).getItemID() + " AND " + ordered.get(i).getSellerID());
                                        item = sql.getItem();
                                        sql.closeConnection();
                                        sql.openConnection();
                                        for (int j = 0; j < order.size(); j++) {
                                            if (order.get(j).getID() == ordered.get(i).getOrderID()) {
                                                index = j;
                                                break;
                                            }
                                        }
                                        System.out.printf("%-10d  \t %-10d  \t %-20s  \t %-10d  \t %-10d  \t %10.2f  \t %10.2f  \t %9s",
                                                ordered.get(i).getOrderID(), ordered.get(i).getItemID(), item.getItemName(), ordered.get(i).getSellerID(), ordered.get(i).getQuantity(),
                                                ordered.get(i).getPrice(), ordered.get(i).getPrice() * ordered.get(i).getQuantity(), df.format(order.get(index).getOrderDate()));
                                    }
                                } else {
                                    System.out.println("Invalid command.");
                                    System.out.println(usage);
                                }
                            } else if (cmd[0].compareToIgnoreCase("add") == 0) {
                                Item itm = new Item();
                                itm.setID(Integer.parseInt(cmd[2]));
                                itm.setSellerID(Integer.parseInt(cmd[3]));
                                sql.closeConnection();
                                sql.openConnection();
                                sql.getTable("SELECT * FROM Item WHERE Id = " + itm.getID() + " AND SellerId = " + itm.getSellerID());
                                if (sql.next()) {
                                    item = sql.getItem();
                                }
                                sql.closeConnection();
                                sql.openConnection();
                                cart.add(new ShoppingCart(customer.getID(), itm.getID(), itm.getSellerID(), Integer.parseInt(cmd[4]), itm.getPrice(), itm.getPrice() * Integer.parseInt(cmd[4])));
                                sql.insertData(cart.get(cart.size() - 1));
                            } else if (cmd[0].compareToIgnoreCase("checkout") == 0) {
                                Orders or;
                                Ordered od;
                                or = new Orders();
                                or.setCustomerID(customer.getID());
                                or.setBillingInfoID(billing.getID());
                                or.setOrderDate(new Date());
                                sql.insertData(or);
                                sql.closeConnection();
                                sql.openConnection();
                                sql.getTable("SELECT * FROM Orders WHERE CustomerId = " + customer.getID());
                                while (sql.next()) {
                                    or = sql.getOrders();
                                }
                                sql.closeConnection();
                                sql.openConnection();
                                for (int i = 0; i < cart.size(); i++) {
                                    sql.modifyData("UPDATE Inventory SET Quantity = Quantity - " + cart.get(i).getQuantity()
                                            + " WHERE ItemId = " + cart.get(i).getItemID() + " AND SellerId = " + cart.get(i).getSellerID());

                                    od = new Ordered(or.getID(), cart.get(i).getItemID(), cart.get(i).getSellerID(),
                                            cart.get(i).getQuantity(), cart.get(i).getPrice());
                                    sql.insertData(od);
                                }
                            } else if (cmd[0].compareToIgnoreCase("logout") == 0) {
                                break;
                            } else {
                                System.out.println("Invalid command.");
                                System.out.println(usage);
                            }
                        }
                        break;
                    } else if (user.getSellerID() > 0) {
                        // if user is a seller

                        break;
                    } else {
                        // invalid username or password
                        System.out.println("Invalid username or password. \nCreate a new account?");
                        result = sc.nextLine();
                        if (result.compareToIgnoreCase("yes") == 0) {
                            createAccount();
                        }
                    }
                }
            } else if (result.compareToIgnoreCase("no") == 0) {
                createAccount();
            } else {
                System.out.println("Invalid Option");
            }
            System.out.println("");
        }
    }

    public static void setupCustomer() {

        sql.getTable("SELECT * FROM Customers WHERE Id = " + user.getCustomerID());
        if (sql.next()) {
            customer = sql.getCustomer();
        }
        sql.closeConnection();
        sql.openConnection();
    }

    public static void login() {
        String result = new String();
        Scanner sc = new Scanner(System.in);
        System.out.print("\nUsername: ");
        user.setUsername(sc.nextLine());
        System.out.print("\nPassword: ");
        user.setPassword(sc.nextLine());
    }

    public static void createAccount() {
        String res = new String();
        Scanner sc = new Scanner(System.in);

        System.out.print("\nAre you a seller or customer? ");
        res = sc.nextLine();
        while (true) {
            if (res.compareToIgnoreCase("seller") == 0) {
                while (true) {
                    System.out.print("\nUsername: ");
                    user.setUsername(sc.nextLine());
                    sql.closeConnection();
                    sql.openConnection();
                    sql.getTable("SELECT * FROM User WHERE Username = " + new String(user.getUsername()));
                    if (sql.next()) {
                        continue;
                    }
                    sql.closeConnection();
                    sql.openConnection();
                    break;
                }

                System.out.print("\nPassword: ");
                user.setPassword(sc.nextLine());
                System.out.print("\nCompany Name: ");
                seller.setCompanyName(sc.nextLine());
                System.out.print("\nAddress: ");
                billing.setAddress(sc.nextLine());
                System.out.print("\nCard Type: ");
                billing.setCardType(sc.nextLine());
                System.out.print("\nCard Number: (no dashes, only numbers) ");
                billing.setCardNumber(sc.nextLong());
                System.out.print("\nCard Expiry Date: (MM/YY) ");
                billing.setCardExpiryDate(sc.nextLine());
                System.out.print("\nPhone Number: (only numbers)");
                billing.setPhoneNumber(sc.nextLong());

                sql.insertData(billing);
                sql.closeConnection();
                sql.openConnection();
                sql.getTable("SELECT * FROM BillingInfo WHERE CardNumber = "
                        + billing.getCardNumber() + "AND Address = '" + new String(billing.getAddress()) + "'");
                if (sql.next()) {
                    billing = sql.getBillingInfo();
                    seller.setBillingInfoID(billing.getID());
                    sql.closeResultSet();
                }
                sql.insertData(seller);
                sql.closeConnection();
                sql.openConnection();
                sql.getTable("SELECT * FROM Seller WHERE BillingInfoId = "
                        + seller.getBillingInfoID());
                if (sql.next()) {
                    seller = sql.getSeller();
                    user.setSellerID(seller.getID());
                    sql.closeResultSet();
                }
                sql.insertData(user);
                sql.closeConnection();
                sql.openConnection();

                return;
            } else if (res.compareToIgnoreCase("customer") == 0) {
                while (true) {
                    System.out.print("\nUsername: ");
                    user.setUsername(sc.nextLine());
                    sql.getTable("SELECT * FROM User WHERE Username = " + new String(user.getUsername()));
                    if (sql.next()) {
                        continue;
                    }
                    break;
                }
                System.out.print("\nPassword: ");
                user.setPassword(sc.nextLine());
                System.out.print("\nFirst Name: ");
                customer.setFirstName(sc.nextLine());
                System.out.print("\nLast Name: ");
                customer.setLastName(sc.nextLine());
                System.out.print("\nEmail: ");
                customer.setEmail(sc.nextLine());
                System.out.print("Address: ");
                billing.setAddress(sc.nextLine());
                System.out.print("\nCard Type: ");
                billing.setCardType(sc.nextLine());
                System.out.print("\nCard Number: (no dashes, only numbers)");
                billing.setCardNumber(sc.nextLong());
                System.out.print("\nCard Expiry Date: (MM/YY)");
                billing.setCardExpiryDate(sc.nextLine());
                System.out.print("\nPhone Number: (only numbers)");
                billing.setPhoneNumber(sc.nextLong());

                sql.insertData(billing);
                sql.getTable("SELECT * FROM BillingInfo WHERE CardNumber = "
                        + billing.getCardNumber() + "AND Address = '" + new String(billing.getAddress()) + "'");
                if (sql.next()) {
                    billing = sql.getBillingInfo();
                    customer.setBillingInfoID(billing.getID());
                    sql.closeResultSet();
                }
                sql.insertData(customer);
                sql.closeConnection();
                sql.openConnection();
                sql.getTable("SELECT * FROM Customers WHERE BillingInfoId = "
                        + billing.getID());
                if (sql.next()) {
                    customer = sql.getCustomer();
                    user.setCustomerID(customer.getID());
                    sql.closeResultSet();
                }
                sql.insertData(user);
                sql.closeConnection();
                sql.openConnection();

                return;
            }
        }
    }
}
