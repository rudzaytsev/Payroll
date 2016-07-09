package cmds;



import static java.lang.String.format;

public class AddEmpCmd extends AbstractCommand {

  private static final String COMMAND_NAME = "AddEmp";

  private final Integer MIN_ARGS_VALUE = 5;

  public AddEmpCmd(String commandStr) {
    super(commandStr);
  }

  @Override
  public void validate() {
    if (!COMMAND_NAME.equals(commandName))
      throw new ValidationException("Unknown command");

    if (args.size() < MIN_ARGS_VALUE)
      throw new ValidationException(
        format("%s command should contains minimum %s arguments",COMMAND_NAME, MIN_ARGS_VALUE));

    Integer employeeId = Integer.parseInt(args.get(0));
    if (employeeId <= 0)
      throw new ValidationException("Employee Id should be positive");

    String employeeName = args.get(1);
    if (!inQuotes(employeeName))
      throw new ValidationException("Employee name should be in quotes");

    String employeeAddress = args.get(2);
    if (!inQuotes(employeeAddress))
      throw new ValidationException("Employee address should be in quotes");

  }

  @Override
  public void execute() {

  }
}
