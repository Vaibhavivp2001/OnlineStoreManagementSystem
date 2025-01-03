package org.techhub.clientapp;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.techhub.model.ProductModel;
import org.techhub.model.RegistrationModel;

import org.techhub.service.*;

public class OsmsMain {
	public static Logger logger = Logger.getLogger(OsmsMain.class);
	static {
		logger.info("Initializing Log4j");
		PropertyConfigurator
				.configure("C:\\Users\\Shlok\\tech-hubworkspace\\OSMA\\src\\main\\resources\\Log4j.properties");
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		logger.info("MainMethod Started");
		RegistrationService regService = new RegistrationServiceImp();
		ProductService prodService = new ProductServiceImp();
		ProductModel model = new ProductModel();
		System.out.println(
				"=====================================================================================================");
		System.err
				.println("  ************************        WElCOME TO SmARTmART   **********************           ");
		System.out.println(
				"=====================================================================================================");

		System.out.println("0:LOGOUT");
		System.out.println("1:FOR OLD USER -LOGIN");
		System.out.println("2:FOR NEW USER -REGISTER");
		
		
		String Email1 = "";
		String Password1 = "";
		String Email, Password, Role, name, contact, address, product;
		int Pid, Lid, Quantity;

		System.out.print("Enter Choice:");
		int choice = sc.nextInt();
		switch (choice) {
		case 0:
			System.out.println("Logging Out !!");
			System.exit(0);
		
		case 2:System.err.println("-------------------------------Registration Section--------------------------------");
			sc.nextLine();
			System.out.println("Enter Id:");
			int id = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter name:");
			name = sc.nextLine();
			System.out.println("Enter Email:");
			Email = sc.nextLine();
			System.out.println("Enter Contact:");
			contact = sc.nextLine();
			System.out.println("Enter Address:");
			address = sc.nextLine();
			System.out.println("Enter Password:");
			Password = sc.nextLine();
			System.out.println("Enter Role:");
			Role = sc.nextLine();
			if (regService.registerUser(new RegistrationModel(id, name, Email, contact, address, Password, Role))) {
				System.out.println("Registration Successfull...");
				
			} else {
				System.out.println("Cannot Register User !!!");
			}
			
		case 1: System.err.println("-----------------------------Login Section----------------------------");
			    sc.nextLine();
			    System.out.println("Enter Email:");
			    Email = sc.nextLine();
			    Email1 = Email;
			    System.out.println("Enter Password:");
			    Password = sc.nextLine();
			    Password1 = Password;

			    // Checking  if the user is admin or cashier
			    String userRole = regService.getUserRole(Email);
			    String securityKey = "";
			    if (userRole.equalsIgnoreCase("Admin") || userRole.equalsIgnoreCase("Cashier")) {
			        System.out.println("Enter Security Key:");
			        securityKey = sc.nextLine();
			    }

			    if (regService.loginUser(Email, Password, securityKey)) {
			        System.out.println("Login Successfull..");
			    } else {
			        System.out.println("Login Failed!!!");
			    }
			    break;
		}

		if (regService.validateuser(Email1, Password1).equalsIgnoreCase("Admin")) {

			do {
				System.err.println("\n**************   THIS IS ADMIN MENU   *************\n");
				System.out.println("0)Log Out");
				System.out.println("1) Add Product to Display");
				System.out.println("2) Show Product at Display");
				System.out.println("3) Show my LoginId ");
				System.out.println("4) Remove Product from Display");
				System.out.println("5) Update Product Information");
				System.out.println("6) Add User");
				System.out.println("7) Show User");
				System.out.println("8) Remove User");
				System.out.println("9) Disburse Salary");
				System.out.println("10) Show Salary");
				System.out.println("Please Enter Your Choice =");
				int adminChoice = sc.nextInt();

				switch (adminChoice) {
				case 0:
					System.out.println("Logging Out");
					System.exit(0);

				case 1:
					sc.nextLine();
					System.out.println("Enter the Product Name =");
					name = sc.nextLine();
					System.out.println("Enter the Product Description =");
					String desc = sc.nextLine();
					System.out.println("Enter the Price =");
					int price = sc.nextInt();
					System.out.println("Enter the Stock =");
					int stock = sc.nextInt();

					if (prodService.addProduct(new ProductModel(0, name, desc, price, stock))) {
						System.out.println("Product Added Successfully ......");

					} else {
						System.out.println("Product Not Added.......");
					}
					break;
				case 2:

					Optional<Set<ProductModel>> o = prodService.getAllProducts1();
					if (o.isPresent()) {
						Set<ProductModel> product1 = o.get();
						if (!product1.isEmpty()) {
							System.out.println("\n"
									+ "=========================================================================================================================================================================");
							System.out.println(
									"                                                                            PRODUCT LIST ");
							System.out.println(
									"=========================================================================================================================================================================");
							System.out.printf("%-10s | %-30s | %-60s | %-6s | %-6s\n", "ProductId", "ProductName",
									"Description", "Price", "Stock");
							System.out.println(
									"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
							product1.forEach((p) -> {
								String description = p.getDescription();
								if (description.length() > 60) {
									description = description.substring(0, 57) + "...";
								}
								System.out.printf("%-10d | %-30s | %-60s | %-6d | %-6d\n", p.getPid(), p.getName(),
										description, p.getPrice(), p.getStock());
							});
							System.out.println(
									"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
						} else {
							System.out.println("Product is Empty.....");
						}
					} else {
						System.out.println("No Data Present in Product Table.....");
					}
					break;

				case 3:
					sc.nextLine();
					regService.displayLoginId();
					break;

				case 4:
					sc.nextLine();
					System.out.println("Enter Product ID to Delete=");
					int productId = sc.nextInt();
					if (prodService.isDeleteProduct(productId)) {
						System.out.println("Product Deleted Successfully........");
					} else {
						System.out.println("Product Not Deleted....");
					}
					break;
				case 5:
					sc.nextLine();
					System.out.print("Enter Product ID: ");
					int pid = sc.nextInt();
					model.setPid(pid);
					sc.nextLine();

					System.out.print("Enter Product Name: ");
					String name1 = sc.nextLine();
					model.setName(name1);
         
					System.out.print("Enter Product Description: ");
					String description = sc.nextLine();
					model.setDescription(description);
					sc.nextLine();

					System.out.print("Enter Product Price: ");
					int price1 = sc.nextInt();
					model.setPrice(price1);

					System.out.print("Enter Product Stock: ");
					int stock1 = sc.nextInt();
					model.setStock(stock1);

				    if (prodService.updateProduct(model)) {
				        System.out.println("Product updated successfully!");
				    } else {
				        System.out.println("Failed to update product!");
				    }

                   break;

				case 6:
					sc.nextLine();
					System.out.println("Enter Id:");
					int id = sc.nextInt();
					sc.nextLine();
					System.out.println("Enter name:");
					name1 = sc.nextLine();
					System.out.println("Enter Email:");
					Email = sc.nextLine();
					System.out.println("Enter Contact:");
					contact = sc.nextLine();
					System.out.println("Enter Address:");
					address = sc.nextLine();
					System.out.println("Enter Password:");
					Password = sc.nextLine();
					System.out.println("Enter Role:");
					Role = sc.nextLine();
					
					
					if (regService
							.registerUser(new RegistrationModel(id, name1, Email, contact, address, Password, Role))) {
						System.out.println("User Registration Successfull...");
					} else {
						System.out.println("Cannot Register User !!!");
					}
					break;
				case 7:
					Optional<List<RegistrationModel>> o1 = regService.getAllUser();
					if (o1.isPresent()) {
						List<RegistrationModel> user1 = o1.get();
						if (!user1.isEmpty()) {
							System.out.println(
									"========================================================================================================================");
							System.out.println("\n"
									+ "                                          USER REGISTRATION TABLE                                             ");
							System.out.println(
									"=========================================================================================================================");
							System.out.printf("%-8s | %-15s | %-25s | %-15s | %-10s | %-6s\n", "UserId", "UserName",
									"UserEmail", "Contact", "Address", "Role");
							System.out.println(
									"------------------------------------------------------------------------------------------------------------------------");
							user1.forEach((p) -> System.out.printf("%-8d | %-15s | %-25s | %-15s | %-10s | %-6s\n",
									p.getRid(), p.getName(), p.getEmail(), p.getContact(), p.getAddress(),
									p.getRole()));
							System.out.println(
									"------------------------------------------------------------------------------------------------------------------------");
						} else {
							System.out.println("Registration Table is Empty.....");
						}
					} else {
						System.out.println("No Data Present in Registration Table.....");
					}
					break;
				case 8:
					sc.nextLine();
					System.out.println("Enter User ID to Delete=");
					int userId = sc.nextInt();

					if (regService.removeUser(userId)) {
						System.out.println("User  Deleted Successfully........");
					} else {
						System.out.println("User Not Deleted....");
					}
					break;
				case 9:

					sc.nextLine(); // Insert salary for a staff member
					System.out.println("Enter the ID =");
					id = sc.nextInt();
					sc.nextLine();
					System.out.println("Enter the Role =");
					Role = sc.nextLine();
					if (regService.insertStaffSalary(id, Role)) {
						System.out.println("Salary inserted successfully for staff member with RID " + id);
					} else {
						System.out.println("Failed to insert salary for staff member with RID " + id);
					}

					break;
				case 10:
					sc.nextLine();
					if (regService.displayStaffSalary()) {
						System.out.println("Staff salary records displayed successfully.");
					} else {
						System.out.println("Failed to display staff salary records.");
					}
					break;
				default:
					System.err.println("Wrong Choice");

				}
			} while (true);

		} else if (regService.validateuser(Email1, Password1).equalsIgnoreCase("Customer")) {

			System.err.println("\n******************    THIS IS CUSTOMER MENU  ***********************\n");
			do {
				System.out.println("0) Logout");
				System.out.println("1) Show my LoginId");
				System.out.println("2) View Products At Display");
				System.out.println("3) Add Product to Cart");
				System.out.println("4) Remove Product from Cart");
				System.out.println("5) Display The Products In the Cart");
				System.out.println("6) Request Bill");
				System.out.println("Please Enter Your Choice =");
				int customerChoice = sc.nextInt();

				switch (customerChoice) {
				case 0:
					System.out.println("Logging Out !!");
					System.exit(0);

				case 1:
					sc.nextLine();
					System.out.println("Please Enter Your Email=");
					Email = sc.nextLine();
					regService.displayCustomerLoginId(Email);

					break;
				case 2:
					sc.nextLine();
					Optional<List<ProductModel>> o = prodService.getAllProducts();

					if (o.isPresent()) {
						List<ProductModel> product1 = o.get();
						if (!product1.isEmpty()) {
							System.out.println("\n"
									+ "=========================================================================================================================================================================");
							System.out.println(
									"                                                                                   PRODUCTS AT DISPLAY ");
							System.out.println(
									"=========================================================================================================================================================================");
							System.out.printf("%-12s | %-25s | %-60s | %-9s | %-9s\n", "ProductId", "ProductName",
									"Description", "Price", "Stock");
							System.out.println(
									"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
							product1.forEach((p) -> System.out.printf("%-12d | %-25s | %-60s | %-9d | %-9d\n",
									p.getPid(), p.getName(), p.getDescription(), p.getPrice(), p.getStock()));
							System.out.println(
									"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
						} else {
							System.out.println("Product is Empty.....");
						}
					} else {
						System.out.println("No Data Present in Product Table.....");
					}
					break;

				case 3:
					sc.nextLine();
					System.out.println("Enter LoginId =");
					Lid = sc.nextInt();
					System.out.println("Enter ProductId =");
					Pid = sc.nextInt();
					System.out.println("Enter the Quantity =");
					Quantity = sc.nextInt();
					if (prodService.addProductToCart(Lid, Pid, Quantity)) {
						System.out.println("Product added to cart successfully!");
					} else {
						System.out.println("Failed to add product to cart.");
					}

					break;
				case 4:
					sc.nextLine();
					System.out.println("Enter LoginId =");
					Lid = sc.nextInt();
					System.out.println("Enter ProductId =");
					Pid = sc.nextInt();

					if (prodService.deleteProductFromCart(Lid, Pid)) {
						System.out.println("Product deleted from cart successfully!");
					} else {
						System.out.println("Failed to delete product from cart.");
					}

					System.out.println(
							"-------------------------------------------------------------------------------------------------------------------------------");
					break;
				case 5:
					sc.nextLine();
					System.out.println("Enter your login id (Lid)=");
					Lid = sc.nextInt();
					if (prodService.displayCart(Lid)) {
						System.out.println("Cart Displayed Successfully...............");
					} else {
						System.out.println("Failed to show Cart Products.......");
					}

					break;
				case 6:
					sc.nextLine();
					System.out.println("Enter your login id (Lid)=");
					Lid = sc.nextInt();

					// Call the displayCart method
					if (prodService.displayCart(Lid)) {
						System.out.println("Cart displayed successfully!");
					} else {
						System.out.println("Failed to display cart.");
					}

					// Call the requestBill method
					if (prodService.requestBill(Lid)) {
						System.out.println("Bill requested successfully!");
					} else {
						System.out.println("Failed to request bill.");
					}
					break;
				default:
					System.err.println("Wrong Choice");

				}
			} while (true);
		} else if (regService.validateuser(Email1, Password1).equalsIgnoreCase("Cashier")) {

			System.err.println("\n******************    THIS IS CASHIER MENU  ***********************\n");
			do {
				System.out.println("0) LogOut");
				System.out.println("1) Show LoginId");
				System.out.println("2) Generate Bill And Ask CUSTOMERS FEEDBACK");

				System.out.println("Enter Your Choice =");

				int cashierChoice = sc.nextInt();
				switch (cashierChoice) {
				case 0:
					System.out.println("Logging Out");
					System.exit(0);
					break;
				case 1:
					sc.nextLine();
					regService.displayLoginId();

					break;

				case 2:
					sc.nextLine();
					System.out.println("Enter Customers LID=");
					Lid = sc.nextInt();
					int billId = prodService.getLatestBillID(Lid); // Call the getLatestBillID method
					if (prodService.printFinalBill(Lid)) {
						System.out.println("Bill Displayed successfully!");
						// Ask for customer feedback
						System.out.println("Please provide your feedback:");
						System.out.println("Enter your rating (1-5 star):");
						int rating = sc.nextInt();
						sc.nextLine(); // Consume newline left-over
						System.out.println("Enter your comments:");
						String comments = sc.nextLine();
						if (prodService.collectCustomerFeedback(billId, Lid, rating, comments)) { // Pass billId instead
																									// of Lid
							System.out.println("Thank you for your feedback!");
						} else {
							System.out.println("Failed to collect feedback.");
						}
					} else {
						System.out.println("Failed to display Bill.");
					}
					break;
				default:
					System.err.println("Wrong Choice");
				}

			} while (true);

		}

		else {
			System.out.println("User Validation Failed....");
		}

	}

}
