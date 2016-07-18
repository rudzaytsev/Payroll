package cmds;

import domain.DB;
import domain.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class DelEmpCmdTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @After
  public void tearDown() throws Exception {
    DB.getInstance().clearDB();
  }

  @Before
  public void setUp() throws Exception {
    DB db = DB.getInstance();
    db.clearDB();
    db.save(new Employee(1, "Chuck Noris", "Texas, USA"));

  }

  @Test
  public void testValidate() throws Exception {
    String commandStr = "DelEmp 111";
    Command command = new DelEmpCmd(commandStr);
    command.validate();
  }

  @Test
  public void incorrectCommandName() {
    String commandStr = "Del 111";
    Command command = new DelEmpCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Unknown command");
    command.validate();
  }

  @Test
  public void invalidEmployeeId() {
    String commandStr = "DelEmp ZZZ";
    Command command = new DelEmpCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Invalid Employee id");
    command.validate();
  }

  @Test
  public void employeeIdShouldBePositive() throws Exception {
    String commandStr = "DelEmp -1";
    Command command = new DelEmpCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Employee id should be positive");
    command.validate();
  }

  @Test
  public void tooFewArgumentsForCommand() throws Exception {
    String commandStr = "DelEmp ";
    Command command = new DelEmpCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Too few arguments for command DelEmp");
    command.validate();
  }

  @Test
  public void executeTest() throws Exception {
    String commandStr = "DelEmp 1";
    Command command = new DelEmpCmd(commandStr);
    command.validate();
    command.execute();

    DB db = DB.getInstance();
    Employee employee = db.findBy(1);
    assertNull(employee);
  }

  @Test
  public void noSuchEmployeeToDelete() {
    String commandStr = "DelEmp 111";
    Command command = new DelEmpCmd(commandStr);
    command.validate();
    expectedException.expect(CommandExecutionException.class);
    expectedException.expectMessage("No Entity with id = 111 to delete");
    command.execute();
  }
}