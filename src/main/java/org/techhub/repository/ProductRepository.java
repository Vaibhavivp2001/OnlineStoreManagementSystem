package org.techhub.repository;

import java.util.List;

import java.util.Optional;
import java.util.Set;

import org.techhub.model.*;


public interface ProductRepository {
    //Admin Operation
	public boolean addProduct(ProductModel model);
	public Optional<List<ProductModel>> getAllProducts();
	public boolean isDeleteProduct(int pid);
	public boolean updateProduct(ProductModel model);
	public boolean addUser(RegistrationModel model);
	
	//Customer operation
	public Optional<Set<ProductModel>>getAllProducts1();
	public boolean addProductToCart(int Lid, int PID, int quantity);
	public boolean deleteProductFromCart(int Lid, int Pid) ;
	public boolean displayCart(int Lid);
	public boolean requestBill(int Lid);
	
	//Cashier Operation
	public boolean printFinalBill(int Lid);
	public boolean collectCustomerFeedback(int billId,int Lid, int rating,String comments);
	public int getLatestBillID(int Lid);
	//public boolean emptyCart(int Lid);

	
    
}
