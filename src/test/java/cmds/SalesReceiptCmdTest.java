package cmds;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class SalesReceiptCmdTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void incorrectCommandName() throws Exception {
    String commandStr = "XSalesReceipt 22 02.04.2016 30";
    Command command = new SalesReceiptCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Unknown command");
    command.validate();
  }

  @Test
  public void employeeIdShouldHaveIntegerType() throws Exception {
    String commandStr = "SalesReceipt 2V2 02.04.2016 30";
    Command command = new SalesReceiptCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Employee id should have Integer type");
    command.validate();
  }

  @Test
  public void employeeIdShouldBePositive() throws Exception {
    String commandStr = "SalesReceipt -22 02.04.2016 30";
    Command command = new SalesReceiptCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Employee id should be positive");
    command.validate();
  }

  @Test
  public void invalidDate() throws Exception {
    String commandStr = "SalesReceipt 22 31.02.2016 30";
    Command command = new SalesReceiptCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Invalid date");
    command.validate();
  }

  @Test
  public void validDate() throws Exception {
    String commandStr = "SalesReceipt 22 04.02.2016 30";
    Command command = new SalesReceiptCmd(commandStr);
    command.validate();
  }

  /*
  @Test
  public void testExecute() throws Exception {

  }
  */
}