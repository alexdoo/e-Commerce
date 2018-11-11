made changes to SQL script: 
	- primary key for Item has changed to ID and sellerId (previously only Id)
	- all foreign keys now cascade on updates
	- OrderDate in Orders changed data types from char[] to DATE
	- created a new table : Ordered to keep track of items in an order
	- deleted AUTOINCREMENT functions where not needed

Changes have been made in java class files and SQLHandler to adjust to edited SQL script.

functions/interactions regarding customer--shopping cart--items have been made in start() of Ecommerce.

Class objects in Ecommerce has been moved into start() (previously in main).


_______________________________________________________________

- Deleted Admin Table from SQL script, I don't see a huge need for a table of Admin. I figure we can just have 1 admin

- Made rest of the functions besides calling data from db to view in UI and Category DB.

- Planning to make a Domain for categories.