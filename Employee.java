package com.EmployeeManagementSystem;

public class Employee {
    private int employeeId;
    private String name;
    private String email;
    private double salary;
    private int departmentId;

    // Constructor with ID (Used when fetching from DB)
    public Employee(int employeeId, String name, String email, double salary, int departmentId) {
        this.employeeId = employeeId;
        this.name = name;              // <-- Make sure is present
        this.email = email;            // <-- Make sure 'this.' is present
        this.salary = salary;          // <-- Make sure 'this.' is present
        this.departmentId = departmentId; // <-- Make sure 'this.' is present
    }

    // Constructor without ID (Used when inserting new records)
    public Employee(String name, String email, double salary, int departmentId) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    // Getters and Setters
    public int getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public double getSalary() { return salary; }
    public int getDepartmentId() { return departmentId; }
}