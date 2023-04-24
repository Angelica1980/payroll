import com.payroll.Employee;
import com.payroll.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private payroll.EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees () {
        return employeeRepository.findAll();
    }
    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id).orElseThrow();
    }
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee Employee) {
        return employeeRepository.save(Employee);
    }
    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    Properties repository;
                    return employeeRepository.save(newEmployee);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }
}