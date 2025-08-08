
import com.litmus7.employeemanager.controller.EmployeeController;
import com.litmus7.employeemanager.dto.Employee;


public class TestClass {

	public static void main(String[] args) {
		Employee newEmployee = new Employee(
	    11,
	    "John",
	    "Doe",
	    "987654321",
	    "john@example.com",
	    "2020-01-01",
	    true);
	EmployeeController controller = new EmployeeController();
	System.err.println(controller.deleteEmployee(1).getData());
	
	}

}
