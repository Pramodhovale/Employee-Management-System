package com.EmployeeManagementSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeDAO {

    // 1. Add Employee (PreparedStatement & executeUpdate)
    public void addEmployee(Connection conn, Employee emp) throws SQLException {
        String sql = "INSERT INTO employees (name, email, salary, department_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getEmail());
            pstmt.setDouble(3, emp.getSalary());
            pstmt.setInt(4, emp.getDepartmentId());
            pstmt.executeUpdate();
        }
    }

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDouble("salary"),
                        rs.getInt("department_id")
                ));
            }
        }
        return list;
    }

    public Employee getEmployeeById(int id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                            rs.getInt("employee_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getDouble("salary"),
                            rs.getInt("department_id")
                    );
                }
            }
        }
        return null;
    }

    public boolean updateEmployee(Employee emp) throws SQLException {
        String sql = "UPDATE employees SET name = ?, email = ?, salary = ?, department_id = ? WHERE employee_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getEmail());
            pstmt.setDouble(3, emp.getSalary());
            pstmt.setInt(4, emp.getDepartmentId());
            pstmt.setInt(5, emp.getEmployeeId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    // SQL JOIN Demonstration
    public List<String> getEmployeesDepartmentWise() throws SQLException {
        List<String> results = new ArrayList<>();
        String sql = "SELECT e.employee_id, e.name, e.email, e.salary, d.department_name " +
                "FROM employees e " +
                "JOIN departments d ON e.department_id = d.department_id " +
                "ORDER BY d.department_name, e.name";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String row = String.format("%-5d | %-20s | %-25s | $%-9.2f | %-20s",
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDouble("salary"),
                        rs.getString("department_name"));
                results.add(row);
            }
        }
        return results;
    }
}