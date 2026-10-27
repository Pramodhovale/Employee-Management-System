package com.EmployeeManagementSystem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    // Demonstrating Transaction Rollback
    public void addEmployeeWithTransaction(Employee emp) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Begin Transaction

            // Perform DAO operation
            employeeDAO.addEmployee(conn, emp);

            // Simulating a business validation error to test rollback
            if (emp.getSalary() < 0) {
                throw new IllegalArgumentException("Salary cannot be negative. Triggering rollback.");
            }

            conn.commit(); // Commit Transaction
            System.out.println("Transaction committed successfully: Employee added.\n");
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transaction Rolled Back due to error: " + e.getMessage() + "\n");
                } catch (SQLException ex) {
                    System.err.println("Rollback failed: " + ex.getMessage());
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    public List<Employee> getAllEmployees() {
        try {
            return employeeDAO.getAllEmployees();
        } catch (SQLException e) {
            System.out.println("Error fetching employees: " + e.getMessage());
            return null;
        }
    }

    public Employee getEmployeeById(int id) {
        try {
            return employeeDAO.getEmployeeById(id);
        } catch (SQLException e) {
            System.out.println("Error searching employee: " + e.getMessage());
            return null;
        }
    }

    public void updateEmployee(Employee emp) {
        try {
            boolean updated = employeeDAO.updateEmployee(emp);
            if (updated) {
                System.out.println("Employee updated successfully!\n");
            } else {
                System.out.println("Employee ID not found.\n");
            }
        } catch (SQLException e) {
            System.out.println("Error updating employee: " + e.getMessage() + "\n");
        }
    }

    public void deleteEmployee(int id) {
        try {
            boolean deleted = employeeDAO.deleteEmployee(id);
            if (deleted) {
                System.out.println("Employee deleted successfully!\n");
            } else {
                System.out.println("Employee ID not found.\n");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage() + "\n");
        }
    }

    public List<String> getEmployeesDepartmentWise() {
        try {
            return employeeDAO.getEmployeesDepartmentWise();
        } catch (SQLException e) {
            System.out.println("Error fetching department join records: " + e.getMessage());
            return null;
        }
    }
}
