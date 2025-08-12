import com.litmus7.employeemanager.controller.EmployeeController;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;

public class TestClass {

    public static void main(String[] args) {
        
        EmployeeController controller = new EmployeeController();

        // === createNewEmployee ===
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
            10, // invalid ID
            "J",
            "D",
            "9876543210", // invalid mobile
            "john@example.com",
            "2020-01-01", // future date
            true
        );

        System.out.println("=== createNewEmployee ===");
        System.out.println(controller.createNewEmployee(validEmployee).getMessage());    // normal create
        System.out.println(controller.createNewEmployee(invalidEmployee).getMessage());  // invalid employee

        // === getAllEmployees ===
        System.out.println("\n=== getAllEmployees ===");
        System.out.println(controller.getAllEmployees().getData());  // list of employees or null

        // === getEmployeeById ===
        System.out.println("\n=== getEmployeeById ===");
        System.out.println(controller.getEmployeeById(11).getMessage());   // valid ID
        System.out.println(controller.getEmployeeById(-5).getMessage()); // invalid ID
        System.out.println(controller.getEmployeeById(9999).getMessage());  // ID likely not found

        // === deleteEmployee ===
        System.out.println("\n=== deleteEmployee ===");
        System.out.println(controller.deleteEmployee(1).getMessage());   // try deleting valid ID
        System.out.println(controller.deleteEmployee(-1).getMessage());  // invalid ID

        // === updateEmployee ===
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
        System.out.println(controller.updateEmployee(updatedEmployee).getMessage());    // valid update
        System.out.println(controller.updateEmployee(invalidEmployee).getMessage());    // invalid update
    }
}
