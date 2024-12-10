package com.app.s03.cmn.security.vo;

import java.beans.ConstructorProperties;

import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1000984321752660527L;
	private final String authority;

    @ConstructorProperties({ "authority" })
    public UserRole(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public int hashCode() {
        return this.authority.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserRole)
            return authority.equals(((UserRole) obj).authority);
        return false;
    }

    @Override
    public String toString() {
        return this.authority;
    }

}
