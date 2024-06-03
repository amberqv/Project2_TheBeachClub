
package TheBeachClub;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class EmployeeManager {
    
      
        
        // Add New Employee
        public static void addNewEmployee(ArrayList<Employee> employees, Scanner scanner) 
        {
            
            
            System.out.println(" Adding New Employee...\n");
            
            // Display existing exmployee IDs
            System.out.println("\nCurrently existing Employee IDs: \n");
            for (Employee employee : employees) 
                    {
                        System.out.println(" ⛱ Employee "+employee.getId());
                    }
            
            
            int id;
            String name, job = null;
            double salary;
            
            // Employee ID
            while (true)
            {
                try
                {
                    System.out.println("\nଳ New Employee ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    
                    // checks if employee ID already exists
                    if (isEmployeeIdExists(employees, id))
                    {
                        System.out.println("Employee ID already exists. Please enter a different ID.");
                        continue; // Ask user to enter different ID
                    }
                    break; // Exits loop if input is read
                        
                }
                
                catch (InputMismatchException e) 
                {
                    System.out.println("Invalid input format for room ID. Please enter a valid integer.");
                    scanner.nextLine(); // Consume invalid input
                }

            }
            
            // Name
            System.out.print("Enter name: ");
            name = scanner.nextLine();
            
            // Salary
            while (true) {
                try {
                    System.out.print("Enter salary: ");
                    salary = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline character
                    break; // Exit the loop if input is successfully read
                    
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Enter number");
                    scanner.nextLine(); // Consume invalid input
                }
            }
            
            // Job
            System.out.print("Enter job: ");
            job = scanner.nextLine();
            
            // Create new employee object and add to file
            Employee employee = new Employee(id, name, salary, job);
            employees.add(employee);
            Employee.writeEmployees(employees);
            
            System.out.println("\nEmployee added successfully.");
            
            // Prompt the user to add another room or go back to the Dashboard
                    while (true) 
                    {
                    System.out.print("\n1. Add another Employee\n2. Dashboard\n\n ଳ ");
                    int choice;
                    try 
                    {
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        if (choice == 2) 
                        {
                            return; // Return to the Dashboard
                        } 
                        
                        else if (choice == 1) 
                        {
                           addNewEmployee(employees, scanner); // Add another employee 
                           return;
                        }
                    } 
                    
                    catch (InputMismatchException e) 
                    {
                        System.out.println("Invalid input.");
                        System.out.println(" ଳ ");
                        scanner.nextLine(); // Consume invalid input
                    }
                }
        }
        
        
        // Show All Employees
        public static void showAllEmployees(ArrayList<Employee> employees)
        {
            
            if (employees.isEmpty())
            {
                System.out.println("No listed employees.");
            }
            
            else
            {
                System.out.println("\nCurrent Employees:\n");
                for(Employee employee : employees)
                {
                    System.out.println(employee);
                }
            }
            
            System.out.println("\nଳ Press Enter to go back to Dashboard");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine(); // Wait for Enter key press
        }
        
        
        // Edit Employees
        
        public static void editEmployee(ArrayList<Employee> employees, Scanner scanner) 
        {
            
            int employeeId;
            while (true) 
            {
                try 
                {
                    // Display existing exmployees
                    System.out.println("\nCurrent Employees:\n");
                for(Employee employee : employees)
                {
                    System.out.println(employee);
                }
                    
                    System.out.print("\nEnter employee ID to edit"
                            + "\n'x' to cancel: "
                            +"\n ଳ ");
                    
                    
                    String input = scanner.nextLine().trim();
                    
                    if (input.equalsIgnoreCase("x")) {
                        System.out.println("\nCancelling edit operation...");
                        return; // Cancel editing
                    }
                    
                    
                    employeeId = Integer.parseInt(input);
                    
                    // find employee with the given ID
                    Employee employeeToEdit = null;
                    for (Employee employee : employees)
                    {
                        if (employee.getId() == employeeId)
                        {
                            employeeToEdit = employee;
                            break;
                        }
                    }
                    
                    if (employeeToEdit == null)
                    {
                        System.out.println("Employee " + employeeId + " not found.");
                        continue; // Prompt for ID again
                    }
                    
                    
                    System.out.println("\nNow editing:\n");
                    System.out.println(employeeToEdit);
                    
                    
                    // Gather new employee details
                    
                    //Name
                    System.out.print("Enter new Name (press Enter to skip): ");
                    String newName = scanner.nextLine();
                    if (!newName.isEmpty()) {
                        employeeToEdit.setName(newName);
                    }
                    
                    // Salary
                    while (true) 
                    {
                        try {
                            System.out.print("Enter new Salary (press Enter to skip): ");
                            String salaryInput = scanner.nextLine();
                            if (!salaryInput.isEmpty()) 
                            {
                                double newSalary = Double.parseDouble(salaryInput);
                                employeeToEdit.setSalary(newSalary);
                            }
                            break; // Exit the loop if salary input is successfully read
                        } catch (NumberFormatException e) 
                        {
                            System.out.println("Invalid input format. Please enter a valid number");
                        }
                    }
                    
                    //Job
                    System.out.print("Enter new Job (press Enter to skip): ");
                    String newJob = scanner.nextLine();
                    if (!newJob.isEmpty()) 
                    {
                        employeeToEdit.setJob(newJob);
                    }
                    
                    // Write updated rooms to file
                    System.out.println("\n——— ⛱ " + "Employee "+ employeeId +" updated successfully ⛱ ——— \n");
                    Employee.writeEmployees(employees);
                    
                    
                    
                    // Prompt the user to edit another employee or go back to dashboard
                    while (true) 
                    {
                        try {
                            System.out.print("\n1. Edit another employee\n2. Dashboard \nଳ  ");
                            int choice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline character

                            if (choice == 2) {
                                System.out.println("\nGoing back to Dashboard...");
                                return; // Return to Dashboard
                            } else if (choice == 1) {
                                System.out.println("\nEditing another employee...");
                                break; // Continue editing another room
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input format.");
                            scanner.nextLine(); // Consume invalid input
                        }
                    }
                    
                }

                catch (InputMismatchException e) 
                    {
                        System.out.println("Invalid input format.");
                        scanner.nextLine(); // Consume invalid input
                    }
            }
        }
    
        // Delete Employee
        public static void deleteEmployee(ArrayList<Employee> employees, Scanner scanner) {
            System.out.println("\n⛱ Delete Employee ⛱\n");

            // Display existing employee IDs
            System.out.println("Currently existing Employee IDs: ");
            for (Employee employee : employees) {
                System.out.println(" ⛱ Employee " + employee.getId());
            }

            // Prompt user for the employee ID to delete
            System.out.print("\nEnter the ID of the employee you want to delete"
                             + "\n'x' to cancel: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("x")) {
                System.out.println("\nCancelling delete operation...");
                return; // Cancel deleting
            }

            int employeeIdToDelete;
            try {
                employeeIdToDelete = Integer.parseInt(input);

                // Find the employee with the given ID
                Employee employeeToDelete = null;
                for (Employee employee : employees) {
                    if (employee.getId() == employeeIdToDelete) {
                        employeeToDelete = employee;
                        break;
                    }
                }

                if (employeeToDelete == null) {
                    System.out.println("Employee with ID " + employeeIdToDelete + " not found.");
                } else {
                    // Remove the employee from the list
                    employees.remove(employeeToDelete);
                    // Write updated employees to file
                    Employee.writeEmployees(employees);
                    System.out.println("\nEmployee " + employeeIdToDelete + " deleted successfully.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please enter a valid integer.");
            }
        }

    
    
        // Check if employee ID already exists
        public static boolean isEmployeeIdExists(ArrayList<Employee> employees, int employeeId)
        {
            for (Employee employee : employees)
            {
                if (employee.getId() == employeeId)
                {
                    return true;
                }
            }
            
            return false;
        }

    
            
    
}
