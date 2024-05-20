package com.razermel.diploma.user;

public enum Role {
    USER,
    ADMIN,
    EMPLOYEE,
    EMPLOYER;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
