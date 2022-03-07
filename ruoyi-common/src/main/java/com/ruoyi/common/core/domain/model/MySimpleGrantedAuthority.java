package com.ruoyi.common.core.domain.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * @author Jayden cxp
 * date 2021-09-10
 */
public class MySimpleGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 550L;
    private String role;

    public MySimpleGrantedAuthority(){}

    public MySimpleGrantedAuthority(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof MySimpleGrantedAuthority ? this.role.equals(((MySimpleGrantedAuthority)obj).role) : false;
        }
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
}
