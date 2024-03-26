package project.batdongsan.model.enums;

public enum UserRole {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_GUEST;

    public static UserRole getDefault() {
        return ROLE_USER;
    }
}
