package org.techhub.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;
import org.techhub.clientapp.OsmsMain;
import org.techhub.model.ProductModel;
import org.techhub.model.RegistrationModel;

public class ProductRepositoryImp extends DBLogin implements ProductRepository {
	List<ProductModel> list = new ArrayList<ProductModel>();
	Set<ProductModel> set = new HashSet<ProductModel>();
	//public static Logger logger =Logger.getLogger(ProductRepositoryImp.class);
	//logger.info("Adding product to database");
	@Override
	public boolean addProduct(ProductModel model) {

		String Query = "Insert into Product (name,description,price,stock) values(?,?,?,?)";

		try {
			stmt = conn.prepareStatement(Query);
			stmt.setString(1, model.getName());
			stmt.setString(2, model.getDescription());
			stmt.setInt(3, model.getPrice());
			stmt.setInt(4, model.getStock());
			int result = stmt.executeUpdate();
			if (result > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			System.out.println(" Error is " + ex.getMessage());
		}

		return false;

	}
	

	@Override
	public Optional<List<ProductModel>> getAllProducts() {
		try {
			stmt = conn.prepareStatement("Select*from Product");
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(
						(new ProductModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5))));
			}
			return list.size() > 0 ? Optional.ofNullable(list) : null;

		} catch (Exception ex) {
			System.out.println("Error is:" + ex);
		}
		return null;
	}

	@Override
	public boolean isDeleteProduct(int pid) {
		String query = "delete from  product WHERE pid = ? ";
		try {
			stmt = conn.prepareStatement(query);

			stmt.setInt(1, pid);

			int value = stmt.executeUpdate();
			return value > 0 ? true : false;

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	@Override
	public boolean updateProduct(ProductModel model) {
		String Query = "UPDATE Product SET name=?, description=?, price=?, stock=? WHERE pid=?";
	    try {
	        stmt = conn.prepareStatement(Query);
	        stmt.setString(1, model.getName());
	        stmt.setString(2, model.getDescription());
	        stmt.setInt(3, model.getPrice());
	        stmt.setInt(4, model.getStock());
	        stmt.setInt(5, model.getPid());
	        int result = stmt.executeUpdate();
	        if (result > 0) {
	            return true;
	        } else {
	            return false;
	        }
	    } catch (Exception ex) {
	        System.out.println("Error is " + ex.getMessage());
	    }
	    return false;
	}
	

	

	@Override

	public boolean addUser(RegistrationModel model) {// RegistrationModel is class that represents user registration
														// data
		try { // model this is a inastance of RegistrationModel class here we are using object
				// as a parameter
				// Checking if email already exists
			stmt = conn.prepareStatement("select *from  registrationmaster  where Email=?");
			stmt.setString(1, model.getEmail());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				System.out.println("Email already exists!");
				return false;
			}

			// Inserting into Registration table
			stmt = conn.prepareStatement(
					"insert into   registrationmaster  (Name,Email, Address,Password, Role) values (  ?, ?, ?, ?, ?)");

			stmt.setString(1, model.getName());
			stmt.setString(2, model.getEmail());
			stmt.setString(3, model.getAddress());
			stmt.setString(4, model.getPassword());
			stmt.setString(5, model.getRole());

			return true;

		} catch (SQLException e) {
			System.out.println("Error registering user: " + e.getMessage());
			return false;
		}

	}

	@Override
	public Optional<Set<ProductModel>> getAllProducts1() {

		try {

			set = new HashSet<ProductModel>();
			stmt = conn.prepareStatement("Select*from Product");
			rs = stmt.executeQuery();
			while (rs.next()) {
				set.add((new ProductModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5))));
			}
			return Optional.ofNullable(set);

		} catch (Exception ex) {
			System.out.println("Error is:" + ex);
		}
		return Optional.empty();
	}

	@Override

	public boolean addProductToCart(int Lid, int PID, int quantity) {
		String query = "insert into Cart (Lid, PID, Quantity) values ( ?, ?, ?)";

		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, Lid);
			stmt.setInt(2, PID);
			stmt.setInt(3, quantity);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Product added to cart successfully!");
				return true;
			} else {
				System.out.println("Failed to add product to cart.");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean deleteProductFromCart(int Lid, int Pid) {
		String query = "delete from Cart where Lid = ? and Pid = ?";
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, Lid);
			stmt.setInt(2, Pid);
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Product deleted from cart successfully!");
				return true;
			} else {
				System.out.println("Failed to delete product from cart.");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}

	}

	@Override
	public boolean displayCart(int Lid) {
	    String query = "SELECT c.CartID, p.PID, p.Name, p.Description, p.Price, c.Quantity FROM Cart c JOIN Product p ON c.PID = p.PID WHERE c.Lid = ?";
	    try {
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, Lid);
	        ResultSet rs = stmt.executeQuery();
	        System.out.println("\n" + "=========================================================================================================================================================================");
	        System.out.println("                                                                                CART ");
	        System.out.println("=========================================================================================================================================================================");
	        System.out.printf("%-10s | %-10s | %-20s | %-50s | %-8s | %-8s\n", "Cart ID", "Product ID", "Product Name", "Description", "Price", "Quantity");
	        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	        while (rs.next()) {
	            System.out.printf("%-10d | %-10d | %-20s | %-50s | %-8d | %-8d\n", rs.getInt("CartID"), rs.getInt("PID"), rs.getString("Name"), rs.getString("Description"), rs.getInt("Price"), rs.getInt("Quantity"));
	        }
	        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    return false;
	}
	@Override

	public boolean requestBill(int Lid) {
	    // Generate bill with all details
	    String query = "SELECT c.CartID, p.PID, p.Name AS ProductName, p.Price, c.Quantity, rm.name, rm.address, rm.phone_number, lm.Email " +
	            "FROM Cart c " +
	            "JOIN Product p ON c.PID = p.PID " +
	            "JOIN LOGINMASTER lm ON c.Lid = lm.Lid " +
	            "JOIN REGISTRATIONMASTER rm ON lm.Email = rm.email " +
	            "WHERE c.Lid = ?";

	    try {
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, Lid);
	        ResultSet rs = stmt.executeQuery();

	        double totalBill = 0;
	        int cartId = 0;
	        String name = " ";
	        String address = "";
	        String phoneNumber = "";
	        String email = "";
	        String billDate = "";
	        String deliveryDate = "";
	        String estimatedDeliveryDate = "";
            System.err.println("----------------------------------------------------------------------------------");
	        System.err.println("\n                        SmARTmarT                      \n");
	        System.err.println("-----------------------------------------------------------------------------------");
	        System.out.println("RETAIL INVOICE");
	        System.out.println("BILL DATE: " + java.time.LocalDate.now());
	        System.out.println("TIME: " + java.time.LocalTime.now());
	        System.out.println("FSSAI No.: 123456");
	        System.out.println("--------------------------------------------------------");
	        System.out.println("Particulars\tRate\tQty\tAmount");
	        System.out.println("--------------------------------------------------------");

	        while (rs.next()) {
	            cartId = rs.getInt("CartID");
	            int price = rs.getInt("Price");
	            int quantity = rs.getInt("Quantity");
	            double totalProductPrice = price * quantity;
	            totalBill += totalProductPrice;
	            name = rs.getString("name");
	            address = rs.getString("address");
	            phoneNumber = rs.getString("phone_number");
	            email = rs.getString("Email");

	            System.out.println(rs.getString("ProductName") + "\t" + price + "\t" + quantity + "\t" + totalProductPrice);
	        }

	        System.out.println("--------------------------------------------------------");

	        // Calculate CGST and SGST
	        double cgst = (totalBill * 5) / 100;
	        double sgst = (totalBill * 5) / 100;

	        System.out.println("Sub Total\t\t\t\t" + totalBill);
	        System.out.println("CGST 5%\t\t\t\t" + cgst);
	        System.out.println("SGST 5%\t\t\t\t" + sgst);

	        // Calculate total bill with taxes
	        totalBill += cgst + sgst;

	        System.out.println("Total\t\t\t\t" + totalBill);
	        System.out.println("----------------------------------------------------------------------");
	        System.out.println("                         ***CUSTOMER DETAILS***                        ");
	        System.out.println("Name:" + name);
	        System.out.println("Address: " + address);
	        System.out.println("Phone Number: " + phoneNumber);
	        System.out.println("Email: " + email);
	        System.out.println("------------------------------------------------------------------------");
	        System.out.println("                         ***ORDER DETAILS***                          ");
	        System.out.println("Order Date: " + java.time.LocalDate.now());
	        System.out.println("Delivery Date: " + java.time.LocalDate.now().plusDays(3));
	        System.out.println("Estimated Delivery Date: " + java.time.LocalDate.now().plusDays(5));
	        System.out.println("Bill Status: UNPAID");
	        System.out.println("Please proceed to cashier.......................");
	      

	       
	     // Insert bill details into database
	        String insertQuery = "insert into BillMaster(CartID, Lid, TotalBill, BillStatus, PaymentMethod, DeliveryAddress, PhoneNumber, Email, Name, OrderDate, DeliveryDate, EstimatedDeliveryDate) VALUES (?, ?, ?, 'Unpaid', 'Cash', ?, ?, ?, ?, ?, ?, ?)";
	        stmt = conn.prepareStatement(insertQuery);
	        stmt.setInt(1, cartId);
	        stmt.setInt(2, Lid);
	        stmt.setDouble(3, totalBill);
	        stmt.setString(4, address);
	        stmt.setString(5, phoneNumber);
	        stmt.setString(6, email);
	        stmt.setString(7, name); // Add the name here
	        stmt.setString(8, java.time.LocalDate.now().toString());
	        stmt.setString(9, java.time.LocalDate.now().plusDays(3).toString());
	        stmt.setString(10, java.time.LocalDate.now().plusDays(5).toString());
	        stmt.executeUpdate();


	        System.out.println("\n------------------------- **END OF BILL **-------------------------\n");

	        return true;
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	        return false;
	    }
	}

	

	public boolean printFinalBill(int Lid) {
	    try {
	        // Update bill status to 'Paid'
	        String updateQuery = "UPDATE BillMaster SET BillStatus = 'Paid' WHERE Lid = ? AND BillStatus = 'Unpaid'";
	        stmt = conn.prepareStatement(updateQuery);
	        stmt.setInt(1, Lid);
	        stmt.executeUpdate();

	        // Retrieve and print the final bill
	        String query = "SELECT bm.Bill_ID, bm.CartID, bm.TotalBill, bm.BillStatus, bm.OrderDate, bm.DeliveryDate, bm.EstimatedDeliveryDate, rm.Name, rm.Email, rm.phone_number, rm.Address " +
	                "FROM BillMaster bm " +
	                "JOIN LoginMaster lm ON bm.Lid = lm.Lid " +
	                "JOIN RegistrationMaster rm ON lm.Email = rm.email " +
	                "WHERE bm.Lid = ? AND bm.BillStatus = 'Paid' ORDER BY bm.Bill_ID DESC LIMIT 1";
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, Lid);
	        ResultSet rs = stmt.executeQuery();

	        if (!rs.next()) {
	            System.out.println("No bills found for the given Lid.");
	            return false;
	        }

	        int billId = rs.getInt("Bill_ID");
	        int cartId = rs.getInt("CartID");
	        double totalBill = rs.getDouble("TotalBill");
	        String billStatus = rs.getString("BillStatus");
	        String orderDate = rs.getString("OrderDate");
	        String deliveryDate = rs.getString("DeliveryDate");
	        String estimatedDeliveryDate = rs.getString("EstimatedDeliveryDate");
	        String name = rs.getString("Name");
	        String email = rs.getString("Email");
	        String phoneNumber = rs.getString("phone_number");
	        String address = rs.getString("Address");

	        System.out.println("\n\n-----------------------------------------------");
	        System.err.println("\t\tSmARTmART");
	        System.out.println("-----------------------------------------------");
	        System.out.println("Bill ID: " + billId);
	        System.out.println("Cart ID: " + cartId);
	        System.out.println("Order Date: " + orderDate);
	        System.out.println("Delivery Date: " + deliveryDate);
	        System.out.println("Estimated Delivery Date: " + estimatedDeliveryDate);
	        System.out.println("--------------------------------------------------");
	        System.err.println("Customer Details:");
	        System.out.println("Name: " + name);
	        System.out.println("Email: " + email);
	        System.out.println("Phone Number: " + phoneNumber);
	        System.out.println("Address: " + address);
	        System.out.println("-----------------------------------------------");
	        System.out.println("Total Bill: " + totalBill);
	        System.out.println("Bill Status: " + billStatus);
	        System.out.println("-----------------------------------------------");
	        System.err.println("\t\t!!Thank you for shopping!!");
	        System.out.println("-----------------------------------------------\n\n");

	        return true;
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	        return false;
	    }
	}
	
	@Override
	public boolean collectCustomerFeedback(int billId, int Lid, int rating, String comments) {
	    try {
	        conn.setAutoCommit(false); // Disable auto-commit mode

	        // Retrieve the latest bill ID associated with the given login ID
	        billId = getLatestBillID(Lid);
	        if (billId == -1) {
	            System.out.println("No bills found for the given login ID.");
	            return false;
	        }

	        // Validate rating
	        if (rating < 1 || rating > 5) {
	            System.out.println("Invalid rating. Please enter a rating between 1 and 5 stars.");
	            return false;
	        }

	        // Check if feedback already exists for the given bill ID
	        String query = "select * from Feedback where BillID = ?";
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, billId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            System.out.println("Feedback already exists for this bill.");
	            return false;
	        }

	        // Insert feedback into database
	        query = "insert into Feedback (BillID, LoginID, Rating, Comments, Date) values (?, ?, ?, ?, ?)";
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, billId);
	        stmt.setInt(2, Lid);
	        stmt.setInt(3, rating);
	        stmt.setString(4, comments);
	        stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
	        stmt.executeUpdate();

	        conn.commit(); // Commit the transaction

	        // Retrieve and display the latest feedback
	        query = "select * from Feedback WHERE BillID = ? ORDER BY Date DESC LIMIT 1";
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, billId);
	        rs = stmt.executeQuery();

	        System.out.println("\n------------------- Customer Feedback -------------------");
	        while (rs.next()) {
	            System.out.println("Bill ID: " + rs.getInt("BillID"));
	            System.out.println("Login ID: " + rs.getInt("LoginID"));
	            System.out.println("Rating: " + rs.getInt("Rating"));
	            System.out.println("Comments: " + rs.getString("Comments"));
	            System.out.println("Date: " + rs.getDate("Date"));
	        }
	        System.out.println("-------------------------------------------------------------\n");

	        return true;
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	        System.out.println("SQL State: " + e.getSQLState());
	        System.out.println("Error Code: " + e.getErrorCode());
	        try {
	            conn.rollback(); // Rollback the transaction
	        } catch (SQLException ex) {
	            System.out.println("Error rolling back: " + ex.getMessage());
	        }
	        return false;
	    } 
	  finally {
	        try {
	            conn.setAutoCommit(true); // Enable auto-commit mode
	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	}
	
	@Override
	public int getLatestBillID(int Lid) {
		try {
	        String query = "SELECT Bill_ID FROM BillMaster WHERE Lid = ? ORDER BY Bill_ID DESC LIMIT 1";
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, Lid);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("Bill_ID");
	        } else {
	            return -1;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	        return -1;
	    }
		
	}


	
	
	
		
	

	/*@Override
	public boolean emptyCart(int Lid) {
		try {
	        String query = "DELETE FROM Cart WHERE Lid = ?";
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, Lid);
	        stmt.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	        return false;
	    }

		
	}*/
}

	
