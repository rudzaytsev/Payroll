package cmds;


import domain.DB;

import domain.Employee;

import java.time.LocalDate;

import java.time.format.DateTimeParseException;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.*;

public class TimeCardCmd extends AbstractCommand {

  private static String COMMAND_NAME = "TimeCard";

  private static final int MIN_ARGUMENTS_NUMBER = 3;
  private Integer employeeId;
  private LocalDate timeCardDate;
  private Integer workingTimeHours;

  public TimeCardCmd(String cmd) {
    super(cmd);
  }

  @Override
  public void validate() {
    validateCommandName();
    validateCommandArguments();
  }

  private void validateCommandName() {
    if (!COMMAND_NAME.equals(commandName)) {
      throw new ValidationException("unknown command");
    }
  }

  private void validateCommandArguments() {
    validateCommandArgumentsNumber();
    validateEmployeeId();
    validateTimeCardDate();
    validateWorkingTimeHours();
  }

  private void validateCommandArgumentsNumber() {
    if (args.size() < MIN_ARGUMENTS_NUMBER)
      throw new ValidationException(format("%s command required %d arguments", COMMAND_NAME, MIN_ARGUMENTS_NUMBER));
  }

  private void validateEmployeeId() {
    try {
      employeeId = parseInt(args.get(0));
      if (employeeId <= 0)
        throw new ValidationException("Employee id should be positive");
    }
    catch(NumberFormatException e) {
      throw new ValidationException("Employee id should have Integer type value", e);
    }
  }

  private void validateTimeCardDate() {
    try {
      timeCardDate = LocalDate.parse(args.get(1), ofPattern("dd.MM.yyyy"));
    }
    catch (DateTimeParseException e) {
      throw new ValidationException("Invalid date format, should be dd.MM.yyyy", e);
    }
  }

  private void validateWorkingTimeHours() {
    try {
      workingTimeHours = parseInt(args.get(2));
      if (workingTimeHours <= 0)
        throw new ValidationException("Number of working time hours should be positive");
    }
    catch (NumberFormatException e) {
      throw new ValidationException("Number of working time hours should have Integer type value", e);
    }
  }

  @Override
  public void execute() {
    DB db = DB.getInstance();
    Employee employee = db.findBy(employeeId);
    if (employee == null)
      throw new CommandExecutionException("Employee for time card not found");

    if (!employee.isChargedHourly())
      throw new CommandExecutionException("Employee linked to time card is not charged hourly");

    TimeCard timeCard = new TimeCard(timeCardDate, workingTimeHours);
    employee.addTimeCard(timeCard);
    db.save(employee);
  }
}
