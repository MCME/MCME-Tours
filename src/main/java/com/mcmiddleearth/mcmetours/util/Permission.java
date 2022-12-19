package com.mcmiddleearth.mcmetours.util;

/**
 * @author Jubo
 */
public enum Permission {

    ADMIN   ("Tours.admin"),
    HOST    ("Tours.ranger"),
    USER    ("Tours.user");

    private final String permissionNode;

    Permission(String permissionNode){
        this.permissionNode = permissionNode;
    }

    public String getPermissionNode(){
        return permissionNode;
    }
}
