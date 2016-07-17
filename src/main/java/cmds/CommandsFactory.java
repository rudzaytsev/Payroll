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

  public Command createCommand(String commandStr) {
    if (commandStr.startsWith("AddEmp")) {
      return new AddEmpCmd(commandStr);
    }
    else if (commandStr.startsWith("DelEmp")) {
      return new DelEmpCmd(commandStr);
    }
    else if (commandStr.equals("exit")) {
      return new ExitCmd(commandStr);
    }
    else {
      return new EmptyCmd(commandStr);
    }
  }
}
