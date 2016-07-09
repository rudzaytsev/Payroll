package cmds;


import java.util.ArrayList;
import java.util.List;

public class AddEmpCmd extends AbstractCommand {

  private static final String COMMAND_NAME = "AddEmp";

  public AddEmpCmd(String commandStr) {
    super(commandStr);
  }

  @Override
  public void validate() {
    String actualCommandName = args.get(0);
    if (!COMMAND_NAME.equals(actualCommandName))
      throw new ValidationException("Unknown command");
    Integer employeeId = Integer.parseInt(args.get(1));
    if (employeeId <= 0)
      throw new ValidationException("Employee Id should be positive");
    String employeeName = args.get(2);
    if (!inQuotes(employeeName))
      throw new ValidationException("Employee name should be in quotes");
    String employeeAddress = args.get(3);
    if (!inQuotes(employeeAddress))
      throw new ValidationException("Employee address should be in quotes");

  }

  @Override
  public void execute() {

  }
}
