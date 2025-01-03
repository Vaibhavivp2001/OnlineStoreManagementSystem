package org.techhub.repository;

import java.util.List;
import java.util.Optional;

import org.techhub.model.RegistrationModel;

public interface RegistrationRepository {
	//Admin Operations
	public boolean registerUser(RegistrationModel model);
	public boolean loginUser(String email, String password, String securityKey);
	public String getUserRole(String email);
	public String validateuser(String Email,String password);
	public Optional<List<RegistrationModel>> getAllUser();
	public boolean removeUser(int rid);
	public boolean insertStaffSalary(int rid, String role);
	public boolean displayStaffSalary();
	public boolean displayLoginId();//for all users to know their loginId
	public boolean displayCustomerLoginId(String email);
	

}
