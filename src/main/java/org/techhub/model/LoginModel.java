package org.techhub.model;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel {
	private int lId;
	private String email;
	private String password;
	private String role;
}