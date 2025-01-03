package org.techhub.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.techhub.model.RegistrationModel;

public class RegistrationRepositoryImp extends DBLogin implements RegistrationRepository {

	@Override
	public boolean registerUser(RegistrationModel model) {
		String Query = "insert into RegistrationMaster values(?,?,?,?,?,?,?)";
		try {
			stmt = conn.prepareStatement(Query);
			stmt.setInt(1, model.getRid());
			stmt.setString(2, model.getName());
			stmt.setString(3, model.getEmail());
			stmt.setString(4, model.getContact());
			stmt.setString(5, model.getAddress());
			stmt.setString(6, model.getPassword());
			stmt.setString(7, model.getRole());
			int result = stmt.executeUpdate();
			if (result > 0) {
				Query = "insert into LoginMaster(Email,Password,Role) Values(?,?,?)";
				try {
					stmt = conn.prepareStatement(Query);
					stmt.setString(1, model.getEmail());
					stmt.setString(2, model.getPassword());
					stmt.setString(3, model.getRole());
					result = stmt.executeUpdate();
					return result > 0 ? true : false;
				} catch (Exception ex) {
					System.out.println("Error is:" + ex.getMessage());
				}
			} else {
				System.out.println("Registration Failed !!!!");
			}
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
		}
		return false;
	}

	@Override
	public boolean loginUser(String Email, String password, String securityKey) {
	    String Query = "select * from LoginMaster Where Email=? and password=?";
	    try {
	        stmt = conn.prepareStatement(Query);
	        stmt.setString(1, Email);
	        stmt.setString(2, password);
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            String role = rs.getString("Role");
	            if (role.equalsIgnoreCase("Admin")) {
	                // Validate security key for admin
	                return securityKey.equals("Admin@11");
	            } else if (role.equalsIgnoreCase("Cashier")) {
	                // Validate security key for cashier
	                return securityKey.equals("Cashier@11");
	            } else {
	                // No security key validation required for customer
	                return true;
	            }
	        } else {
	            return false;
	        }
	    } catch (Exception ex) {
	        System.out.println("Error is:" + ex.getMessage());
	    }
	    return false;
	}
	@Override
	public String getUserRole(String email) {
		String Query = "select Role from LoginMaster Where Email=?";
	    try {
	        stmt = conn.prepareStatement(Query);
	        stmt.setString(1, email);
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getString("Role");
	        } else {
	            return "";
	        }
	    } catch (Exception ex) {
	        System.out.println("Error is:" + ex.getMessage());
	    }
	    return "";
	}

	
	
	@Override
	public String validateuser(String Email, String password) {
		String Query = "Select Role From LoginMaster Where Email=? and password=?";
		try {
			stmt = conn.prepareStatement(Query);
			// stmt.setString(1,rs.getString("Role"));
			stmt.setString(1, Email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}

		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public Optional<List<RegistrationModel>> getAllUser() {
	    List<RegistrationModel> list = new ArrayList<>();
	    try {
	        stmt = conn.prepareStatement("Select * from Registrationmaster");
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            list.add(new RegistrationModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
	        }
	        rs.close();
	        stmt.close();
	        return list.isEmpty() ? Optional.empty() : Optional.of(list);
	    } catch (SQLException e) {
	        System.out.println("Error is:" + e);
	    }
	    return Optional.empty();
	}

	@Override
	public boolean removeUser(int rid) {

		String query = "delete from  registrationmaster where rid = ? ";
		try {
			stmt = conn.prepareStatement(query);

			stmt.setInt(1, rid);

			int value = stmt.executeUpdate();
			return value > 0 ? true : false;

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	@Override
	public boolean insertStaffSalary(int rid, String role) {
		  String query = "insert into Salary (rid, Salary) VALUES (?, ?)";
		  
		  int salary = 0;
		  
		  // Convert the role to title case
		 role = role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase();
		  
		  switch (role) {
		    case "Admin":
		      salary = 50000;
		      break;
		    case "Employee":
		      salary = 30000;
		      break;
		    case "Cashier":
		      salary = 25000;
		      break;
		    default:
		      System.out.println("Invalid role");
		      return false;
		  }
		  
		  try {
		    stmt = conn.prepareStatement(query);
		    stmt.setInt(1, rid);
		    stmt.setInt(2, salary);
		    int rowsAffected = stmt.executeUpdate();
		    
		    if (rowsAffected > 0) {
		      System.out.println("Salary inserted successfully!");
		      return true;
		    } else {
		      System.out.println("Failed to insert salary");
		      return false;
		    }
		  } catch (SQLException e) {
		    System.out.println("Error: " + e.getMessage());
		    return false;
		  }
		}

	@Override
	public boolean displayStaffSalary() {
	    String query = "SELECT rm.rid, rm.name, rm.role, s.Salary FROM registrationMaster rm JOIN Salary s ON rm.rid = s.rid";
	    try {
	        stmt = conn.prepareStatement(query);
	        rs = stmt.executeQuery();
	        System.out.println("\n" + "=========================================================================================================");
	        System.out.println("                                                 STAFF SALARY LIST ");
	        System.out.println("=========================================================================================================");
	        System.out.printf("%-10s | %-20s | %-15s | %-10s\n", "RID", "Name", "Role", "Salary");
	        System.out.println("-----------------------------------------------------------------------------------------------------------");
	        if (rs.next()) {
	            do {
	                System.out.printf("%-10d | %-20s | %-15s | %-10d\n", rs.getInt("rid"), rs.getString("name"), rs.getString("role"), rs.getInt("Salary"));
	            } while (rs.next());
	            return true;
	        } else {
	            System.out.println("No staff salary records found.");
	            return false;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	        return false;
	    }
	}

	@Override
	public boolean displayLoginId() {
		String query = "SELECT Lid,Email, Role FROM LoginMaster";
	    try {
	        stmt = conn.prepareStatement(query);
	        ResultSet rs = stmt.executeQuery();
	        System.out.println("\n" + "=========================================================================================================");
	        System.out.println("                                                         LOGIN IDs                                     ");
	        System.out.println("=========================================================================================================");
	        System.out.printf("%-10s | %-25s | %-10s\n", "Login ID", "Email", "Role");
	        System.out.println("---------------------------------------------------------------------------------------------------------------");
	        while (rs.next()) {
	            System.out.printf("%-10d | %-25s | %-10s\n", rs.getInt("Lid"), rs.getString("Email"), rs.getString("Role"));
	        }
	        System.out.println("---------------------------------------------------------------------------------------------------------------");
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    return false;
	 }

	@Override
	public boolean displayCustomerLoginId(String email) {
		String query = "select Lid,Email, Role from LoginMaster where Email = ?";
	    try {
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, email);
	        ResultSet rs = stmt.executeQuery();
	        System.out.println("\n" + "=======================================================================================");
	        System.out.println("                              LOGIN ID ");
	        System.out.println("==============================================================================================");
	        System.out.printf("%-10s | %-25s | %-10s\n", "Login ID", "Email", "Role");
	        System.out.println("----------------------------------------------------------------------------------------------");
	        while (rs.next()) {
	            System.out.printf("%-10d | %-25s | %-10s\n", rs.getInt("Lid"), rs.getString("Email"), rs.getString("Role"));
	        }
	        System.out.println("------------------------------------------------------------------------------------------------");
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    return false;
		
	}

	

	

	
}	


	


		
