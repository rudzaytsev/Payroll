package cmds;


import domain.Cheque;
import domain.DB;
import domain.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.format.ResolverStyle.STRICT;
import static java.util.stream.Collectors.toList;

public class PaydayCmd extends AbstractCommand {

  public static final String COMMAND_NAME = "Payday";

  private LocalDate date;
  private List<Cheque> chequeList = new ArrayList<>();



  public PaydayCmd(String cmd) {
    super(cmd);
  }

  @Override
  public void validate() {
    validateCommandName();
    validateDate();
  }

  private void validateCommandName() {
    if (!COMMAND_NAME.equals(commandName)) {
      throw new ValidationException("Incorrect command name");
    }
  }

  private void validateDate() {
    try {
      DateTimeFormatter formatter = ofPattern("dd.MM.uuuu").withResolverStyle(STRICT);
      TemporalAccessor ta = formatter.parse(args.get(0));
      date = ta.query(LocalDate::from);
    }
    catch (DateTimeParseException e) {
      throw new ValidationException("Invalid date", e);
    }
  }

  @Override
  public void execute() {
    chequeList.clear();
    DB db = DB.getInstance();
    List<Employee> employees = db.findAllOrderedById();
    chequeList = employees.stream().map(employee -> employee.calculatePayment(date)).collect(toList());
  }

  public List<Cheque> getResults() {
    return chequeList;
  }
}
