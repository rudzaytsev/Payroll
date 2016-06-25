package domain;


import static org.junit.Assert.*;
import org.junit.Test;

public class ParserTest {

  CmdParser parser = new CmdParser();

  @Test
  public void employeeParsingTest() {
    String inputCmd = "Add Emp 1 \"David Cameron\" \"Downing Street 5, London, England\" H 1000";
    Employee employee = parser.parseEmployee(inputCmd);
    Employee davidCameron = new Employee(1, "David Cameron", "Downing Street 5, London, England");
    davidCameron.paymentStrategy = "H:1000";
    assertEquals(davidCameron, employee);
  }
}
