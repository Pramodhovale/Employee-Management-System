package com.EmployeeManagementSystem;

import java.util.List;
import java.util.Scanner;

public class EmpDriver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeService service = new EmployeeService();

        while (true) {
            System.out.println("=================================");
            System.out.println("|| EMPLOYEE MANAGEMENT SYSTEM  ||");
            System.out.println("=================================");
            System.out.println("1. Add Employee (with Transaction Support)");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. View Employees by Department (SQL JOIN)");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next();
            }
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();
                    System.out.print("Enter Department ID (1: HR, 2: Engineering, 3: Sales, 4: Marketing): ");
                    int deptId = sc.nextInt();

                    Employee newEmp = new Employee(name, email, salary, deptId);
                    service.addEmployeeWithTransaction(newEmp);
                    break;

                case 2:
                    List<Employee> employees = service.getAllEmployees();
                    if (employees != null && !employees.isEmpty()) {
                        System.out.println("\n--- All Employees ---");
                        System.out.printf("%-5s | %-20s | %-25s | %-10s | %-13s\n", "ID", "Name", "Email", "Salary", "Dept ID");
                        System.out.println("----------------------------------------------------------------------------------");
                        for (Employee e : employees) {
                            System.out.printf("%-5d | %-20s | %-25s | $%-9.2f | %-13d\n",
                                    e.getEmployeeId(), e.getName(), e.getEmail(), e.getSalary(), e.getDepartmentId());
                        }
                    } else {
                        System.out.println("No employees found.");
                    }
                    System.out.println();
                    break;

                case 3:
                    System.out.print("Enter Employee ID to search: ");
                    int searchId = sc.nextInt();
                    Employee emp = service.getEmployeeById(searchId);
                    if (emp != null) {
                        System.out.println("\nEmployee Found:");
                        System.out.println("ID: " + emp.getEmployeeId());
                        System.out.println("Name: " + emp.getName());
                        System.out.println("Email: " + emp.getEmail());
                        System.out.println("Salary: $" + emp.getSalary());
                        System.out.println("Department ID: " + emp.getDepartmentId());
                    } else {
                        System.out.println("Employee with ID " + searchId + " not found.");
                    }
                    System.out.println();
                    break;

                case 4:
                    System.out.print("Enter Employee ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new Name: ");
                    String upName = sc.nextLine();
                    System.out.print("Enter new Email: ");
                    String upEmail = sc.nextLine();
                    System.out.print("Enter new Salary: ");
                    double upSalary = sc.nextDouble();
                    System.out.print("Enter new Department ID: ");
                    int upDeptId = sc.nextInt();

                    Employee updateEmp = new Employee(updateId, upName, upEmail, upSalary, upDeptId);
                    service.updateEmployee(updateEmp);
                    break;

                case 5:
                    System.out.print("Enter Employee ID to delete: ");
                    int deleteId = sc.nextInt();
                    service.deleteEmployee(deleteId);
                    break;

                case 6:
                    List<String> joinResults = service.getEmployeesDepartmentWise();
                    if (joinResults != null && !joinResults.isEmpty()) {
                        System.out.println("\n--- Department-Wise Employee Report (SQL JOIN) ---");
                        System.out.printf("%-5s | %-20s | %-25s | %-10s | %-20s\n", "ID", "Name", "Email", "Salary", "Department");
                        System.out.println("--------------------------------------------------------------------------------------------------");
                        for (String row : joinResults) {
                            System.out.println(row);
                        }
                    } else {
                        System.out.println("No records found.");
                    }
                    System.out.println();
                    break;

                case 0:
                    System.out.println("Exiting application. Goodbye!");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }
}