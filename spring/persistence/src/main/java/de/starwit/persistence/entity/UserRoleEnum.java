package de.starwit.persistence.entity;

public enum UserRoleEnum {

    NONE,PBUSER,ADMIN;

    public String getRoleName() {
        return "ROLE_" + this.toString();
    }

	public static UserRoleEnum fromString(String roleName) {
        System.out.println("Try to match " + roleName);
        if("ROLE_PBUSER".equals(roleName) || "PBUSER".equals(roleName)) {
            return PBUSER;
        }
        if("ROLE_ADMIN".equals(roleName) || "ADMIN".equals(roleName)) {
            return ADMIN;
        }
        return NONE;
	}
}
