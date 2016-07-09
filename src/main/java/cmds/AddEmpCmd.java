package cmds;



import domain.PaymentTypes;

import static domain.PaymentTypes.*;
import static java.lang.String.format;

public class AddEmpCmd extends AbstractCommand {

  private static final String COMMAND_NAME = "AddEmp";

  private final Integer MIN_ARGS_VALUE = 5;
  private final Integer MAX_ARGS_VALUE = 6;

  private Integer employeeId;
  private String employeeName;
  private String employeeAddress;
  private String paymentType;
  private Integer salary = 0;
  private Integer commissionRate = 0;

  public AddEmpCmd(String commandStr) {
    super(commandStr);
  }

  @Override
  public void validate() {
    validateCommandName();
    validateCommandArguments();
  }

  private void validateCommandName() {
    if (!COMMAND_NAME.equals(commandName))
      throw new ValidationException("Unknown command");
  }

  private void validateCommandArguments() {
    validateArgumentsNumber();
    validateEmployeeId();
    validateEmployeeName();
    validateEmployeeAddress();
    validatePaymentType();
    validateSalary();
    validateCommissionRate();
  }

  private void validateCommissionRate() {
    if (!COMMISSIONED.isSame(paymentType)) return;
    if (args.size() < MAX_ARGS_VALUE)
      throw new ValidationException("Commission rate not found");
    try {
      commissionRate = Integer.parseInt(args.get(5));
    }
    catch (NumberFormatException e) {
      throw new ValidationException("Invalid commission rate", e);
    }
  }

  private void validateSalary() {
    try {
      salary = Integer.parseInt(args.get(4));
    }
    catch (NumberFormatException e) {
      throw new ValidationException("Invalid salary", e);
    }
  }

  private void validatePaymentType() {
    paymentType = args.get(3);
    if (!contains(paymentType))
      throw new ValidationException(format("Invalid payment type. Should be %s, %s, or %s", values()));
  }

  private void validateEmployeeAddress() {
    employeeAddress = args.get(2);
    if (!inQuotes(employeeAddress))
      throw new ValidationException("Employee address should be in quotes");
  }

  private void validateEmployeeName() {
    employeeName = args.get(1);
    if (!inQuotes(employeeName))
      throw new ValidationException("Employee name should be in quotes");
  }

  private void validateEmployeeId() {
    employeeId = Integer.parseInt(args.get(0));
    if (employeeId <= 0)
      throw new ValidationException("Employee Id should be positive");
  }

  private void validateArgumentsNumber() {
    if (args.size() < MIN_ARGS_VALUE)
      throw new ValidationException(
        format("%s command should contains minimum %s arguments", COMMAND_NAME, MIN_ARGS_VALUE));
  }



  @Override
  public void execute() {

  }
}
