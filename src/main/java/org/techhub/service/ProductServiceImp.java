package org.techhub.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.techhub.model.ProductModel;
import org.techhub.model.RegistrationModel;
import org.techhub.repository.*;

public class ProductServiceImp implements ProductService {
	

	ProductRepository admRepo = new ProductRepositoryImp();

	@Override
	public boolean addProduct(ProductModel model) {

		return admRepo.addProduct(model);
	}

	@Override
	public Optional<List<ProductModel>> getAllProducts() {
		return admRepo.getAllProducts();
		
	}

	@Override
	public boolean isDeleteProduct(int productId) {
		
		return admRepo.isDeleteProduct(productId);
	}
	
	@Override
	public boolean addUser(RegistrationModel model) {
		
		return admRepo.addUser(model);
	}

	@Override
	public Optional<Set<ProductModel>> getAllProducts1() {
		
		return admRepo.getAllProducts1();
	}

	@Override
	public boolean addProductToCart(int Lid, int PID, int quantity) {
		
		return admRepo.addProductToCart( Lid,PID, quantity);
	}

	@Override
	public boolean deleteProductFromCart(int Lid, int Pid) {
	
		return admRepo.deleteProductFromCart(Lid, Pid);
	}

	@Override
	public boolean displayCart(int Lid) {
		
    return admRepo.displayCart(Lid);
		
	}

	@Override
	public boolean requestBill(int Lid) {
	
		return admRepo.requestBill(Lid);
	}

	@Override
	public boolean printFinalBill(int Lid) {
		return admRepo.printFinalBill(Lid);
	}

	@Override
	public boolean collectCustomerFeedback(int billId, int Lid,int rating ,String comments) {

		return admRepo.collectCustomerFeedback(billId,Lid, rating, comments);
	}

	@Override
	public int getLatestBillID(int Lid) {
		
		return admRepo.getLatestBillID(Lid);
	}

	@Override
	public boolean updateProduct(ProductModel model) {
		
		return admRepo.updateProduct(model);
	}

	

	/*@Override
	public boolean emptyCart(int Lid) {
		
		return admRepo.emptyCart(Lid);
	}*/

	
}	
