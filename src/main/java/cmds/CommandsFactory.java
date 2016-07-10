package cmds;


public class CommandsFactory {

  private static CommandsFactory factory;

  private CommandsFactory() {
  }

  public static synchronized CommandsFactory getInstance() {
    if (factory == null) {
      factory = new CommandsFactory();
    }
    return factory;
  }

  public Command createCommand(String cmd) {
    if (cmd.startsWith("AddEmp")) {
      return new AddEmpCmd(cmd);
    }
    else if (cmd.equals("exit")) {
      return new ExitCmd(cmd);
    }
    else {
      return new EmptyCmd(cmd);
    }
  }
}
