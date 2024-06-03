
package TheBeachClub;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Employee {
    
    
            private int id;
            private String name;
            private double salary;
            private String job;
            
            
            public Employee(int id, String name, double salary, String job)
            {
                this.id = id;
                this.name = name;
                this.salary = salary;
                this.job = job;
            }
            
            // Getters & Setters

            /**
             * @return the id
             */
            public int getId() {
                return id;
            }

            /**
             * @param id the id to set
             */
            public void setId(int id) {
                this.id = id;
            }

            /**
             * @return the name
             */
            public String getName() {
                return name;
            }

            /**
             * @param name the name to set
             */
            public void setName(String name) {
                this.name = name;
            }

            /**
             * @return the salary
             */
            public double getSalary() {
                return salary;
            }

            /**
             * @param salary the salary to set
             */
            public void setSalary(double salary) {
                this.salary = salary;
            }

            /**
             * @return the job
             */
            public String getJob() {
                return job;
            }

            /**
             * @param job the job to set
             */
            public void setJob(String job) {
                this.job = job;
            }
            
            
            
            // Read employees from file
            public static ArrayList<Employee> readEmployees() throws FileNotFoundException
            {
                ArrayList<Employee> employees = new ArrayList<>();
                BufferedReader br = null;
                
                try
                {
                    
                    br = new BufferedReader(new FileReader("./resources/employees.txt"));
                    
                    String line;
                    while ((line = br.readLine()) != null)
                    {
                        // Split content of a line by using a white space
                        String[] parts = line.split(",");
                        
                        // Ensures the line has the expected no. of elements
                        if (parts.length == 4)
                        {
                            try
                            {
                                // Parse to int and trimming
                                int id = Integer.parseInt(parts[0].trim());
                                String name = parts[1].trim();
                                double salary = Double.parseDouble(parts[2].trim());
                                String job = parts[3].trim();
                                
                                //Create an Employee object and add file 'employees.txt'
                                employees.add(new Employee(id, name, salary, job));
                            }
                            
                            catch (NumberFormatException e) 
                            {
                                System.err.println("Error parsing room data: " + line);
                            }
                        }
                        
                        else
                        {
                            System.out.println("Invalid room data: " + line);
                        }
                    }
                }
                
                catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                
                return employees;
            
            }
            
            
            
            // Write employee to file
            public static void writeEmployees(ArrayList<Employee> employees)
            {
                try (PrintWriter pw = new PrintWriter(new FileWriter("./resources/employees.txt")))
                {
                    for (Employee employee : employees)
                    {
                        pw.println(String.format("%d,%s,%.2f,%s", 
                                employee.getId(), employee.getName(),
                                employee.getSalary(), employee.getJob()));
                    }
                    
                }
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
            
            
            @Override
        public String toString() 
        {
            return "—— ⛱ "
                    + "Employee " + id 
                    + " ——————————————————"
                    + "\n\nName: " + name 
                    + "\nSalary: " + salary 
                    + "\nJob: " + job
                    + "\n";
        }

}
