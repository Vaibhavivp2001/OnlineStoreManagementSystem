package org.techhub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
	public class ProductModel {
		private int pid;
		private String name;
		private String description;
		private int price;
		private int Stock;
	//	private String role;
		
	}
