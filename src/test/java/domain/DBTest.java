package domain;


import static org.junit.Assert.*;
import org.junit.Test;

public class DBTest {


  @Test
  public void saveEmployeeTest() throws Exception {
    DB db = new DB();
    Employee employee = new Employee(1, "David Cameron", "Downing Street 5, London, England");
    db.save(employee);
    Employee storedEmployee = db.findBy(employee.id);
    assertEquals(employee, storedEmployee);
  }
}
