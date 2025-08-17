package com.litmus7.employeemanager.constant;

public class ApplicationStatusConstant {
    // Status Codes
    public static final int SUCCESS = 200;
    public static final int BAD_REQUEST = 400;
    public static final int SERVER_ERROR = 500;
    
    public static final int DUPLICATE_ENTRY=409;
    public static final int NOT_FOUND=404;
    
    


    public static final String MSG_SUCCESS_CREATE = "Employee created successfully";
    public static final String MSG_ERROR_CREATE = "Failed to create employee";

    public static final String MSG_SUCCESS_UPDATE = "Employee updated successfully";
    public static final String MSG_ERROR_UPDATE = "Failed to update employee";
 
    public static final String MSG_SUCCESS_DELETE = "Employee deleted successfully";
    public static final String MSG_ERROR_DELETE = "Failed to delete employee";

    public static final String MSG_SUCCESS_SELECT_ONE = "Employee fetched successfully";
    public static final String MSG_ERROR_SELECT_ONE = "Failed to fetch employee";

    public static final String MSG_SUCCESS_SELECT_ALL = "Employees fetched successfully";
    public static final String MSG_ERROR_SELECT_ALL = "Failed to fetch employees";
    
    
}
