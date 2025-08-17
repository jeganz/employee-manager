import org.apache.logging.log4j.*;

import com.litmus7.employeemanager.controller.EmployeeController;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;

public class TestClass {
	private static Logger mainLogger = LogManager.getLogger(TestClass.class);
    public static void main(String[] args) {
        
        EmployeeController controller = new EmployeeController();
         Response<Boolean> response= controller.deleteEmployee(11);
        Employee validEmployee = new Employee(
            11,
            "John",
            "Doe",
            "9876543210",
            "john@example.com",
            "2020-01-01",
            true
        );
        Employee invalidEmployee = new Employee(
            10, 
            "J",
            "D",
            "9876540", 
            "john@example.com",
            "2020-01-01", 
            true
        );

        System.out.println("=== createNewEmployee ===");
        System.out.println(controller.createNewEmployee(validEmployee).getMessage());    
        System.out.println(controller.createNewEmployee(invalidEmployee).getMessage());  

   
        System.out.println("\n=== getAllEmployees ===");
        System.out.println(controller.getAllEmployees().getData());  

   
        System.out.println("\n=== getEmployeeById ===");
        System.out.println(controller.getEmployeeById(11).getMessage());   
        System.out.println(controller.getEmployeeById(-5).getMessage()); 
        System.out.println(controller.getEmployeeById(9999).getMessage());  


        System.out.println("\n=== deleteEmployee ===");
        System.out.println(controller.deleteEmployee(1).getMessage());   
        System.out.println(controller.deleteEmployee(-1).getMessage());  

        
        System.out.println("\n=== updateEmployee ===");
        Employee updatedEmployee = new Employee(
            11,
            "Johnny",
            "Doe",
            "9876543210",
            "johnny@example.com",
            "2020-01-01",
            true
        );
        System.out.println(controller.updateEmployee(updatedEmployee).getMessage());    
        System.out.println(controller.updateEmployee(invalidEmployee).getMessage());    
    }
}
