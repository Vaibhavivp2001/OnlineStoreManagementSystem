package org.techhub.service;

import java.util.List;
import java.util.Optional;

import org.techhub.model.RegistrationModel;
import org.techhub.repository.*;

public class RegistrationServiceImp implements RegistrationService {
	
	RegistrationRepository regRepo=new RegistrationRepositoryImp();
	@Override
	public boolean registerUser(RegistrationModel model) {
		return regRepo.registerUser(model);
	}
	@Override
	public boolean loginUser(String Email, String password,String securityKey) {
		return regRepo.loginUser(Email, password,securityKey);
	}
	@Override
	public String validateuser(String Email, String password) {
		return regRepo.validateuser(Email, password);
	}
	@Override
	public Optional<List<RegistrationModel>> getAllUser() {
		
		return regRepo.getAllUser();
	}
	
	@Override
	public boolean removeUser(int rid) {
		
		return regRepo.removeUser(rid);
	}
	@Override
	public boolean insertStaffSalary(int rid, String role) {

		return regRepo.insertStaffSalary(rid, role);
	}
	@Override
	public boolean displayStaffSalary() {
		
		return regRepo.displayStaffSalary();
	}
	@Override
	public boolean displayLoginId() {
		return regRepo.displayLoginId();
		
		
	}
	@Override
	public boolean displayCustomerLoginId(String email) {
		
		return regRepo.displayCustomerLoginId(email);
	}
	@Override
	public String getUserRole(String email) {
		// TODO Auto-generated method stub
		return regRepo.getUserRole(email);
	}
	
	
}
