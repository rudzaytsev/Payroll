package cmds;


import static java.lang.String.format;

public class EmptyCmd extends AbstractCommand {


  public EmptyCmd(String cmd) {
    super(cmd);
  }

  @Override
  public void validate() {
    throw new ValidationException(format("Unknown command %s",commandName));
  }

  @Override
  public void execute() {
  }
}
