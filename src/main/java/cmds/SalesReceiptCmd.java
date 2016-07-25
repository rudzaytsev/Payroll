package cmds;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

import static java.lang.Integer.parseInt;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.format.ResolverStyle.STRICT;

public class SalesReceiptCmd extends AbstractCommand {

  private static final String COMMAND_NAME = "SalesReceipt";
  private Integer employeeId;
  private LocalDate date;

  public SalesReceiptCmd(String cmd) {
    super(cmd);
  }

  @Override
  public void validate() {
    validateCommandName();
    validateCommandArguments();
  }

  private void validateCommandArguments() {
    validateEmployeeId();
    validateDate();
  }

  private void validateEmployeeId() {
    try {
      employeeId = parseInt(args.get(0));
      if (employeeId <= 0)
        throw new ValidationException("Employee id should be positive");
    }
    catch (NumberFormatException e) {
      throw new ValidationException("Employee id should have Integer type", e);
    }
  }

  private void validateDate() {
    try {
      DateTimeFormatter formatter = ofPattern("dd.MM.uuuu").withResolverStyle(STRICT);
      TemporalAccessor ta = formatter.parse(args.get(1));
      date = ta.query(LocalDate::from);
    }
    catch (DateTimeParseException e) {
      throw new ValidationException("Invalid date", e);
    }
  }

  private void validateCommandName() {
    if (!COMMAND_NAME.equals(commandName))
      throw new ValidationException("Unknown command");
  }

  @Override
  public void execute() {

  }
}
