package com.cameriere.oauth.dtos;

import java.io.Serializable;
import java.util.UUID;

public class RoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String roleName;
	
	public RoleDTO() {
	}

	public RoleDTO(UUID id, String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
