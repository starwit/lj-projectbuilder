package de.starwit.persistence.entity;

public enum Role {
    NONE("ROLE_NONE"),
    PBUSER("ROLE_PBUSER"),
    ADMIN("ROLE_ADMIN");

    public final String authorityString;

    private Role(String authorityString) {
        this.authorityString = authorityString;
    }
}
