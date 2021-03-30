package com.epam.esm.entity;

public enum Permission {

    CERTIFICATE_READ("certificate:read"), CERTIFICATE_WRITE("certificate:write"), TAG_READ("tag:read"), TAG_WRITE(
            "tag:write"), ORDER_READ("order:read"), ORDER_WRITE("order:write"), USER_READ("user:read"), USER_WRITE(
            "user:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
