package domain;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class DBTest {


  @After
  public void tearDown() throws Exception {
    DB.getInstance().clearDB();
  }

  @Test
  public void saveEmployeeTest() throws Exception {
    DB db = DB.getInstance();
    Employee employee = new Employee(1, "David Cameron", "Downing Street 5, London, England");
    db.save(employee);
    Employee storedEmployee = db.findBy(employee.id);
    assertEquals(employee, storedEmployee);
  }

  @Test
  public void deleteEmployeeTest() throws Exception {
    DB db = DB.getInstance();
    Employee employee = new Employee(25, "Donald Trump", "USA");
    db.save(employee);
    db.delete(25);
    Employee deletedEmployee = db.findBy(25);
    assertNull(deletedEmployee);
  }
}
