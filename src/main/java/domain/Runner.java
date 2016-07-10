package domain;


import cmds.Command;
import cmds.CommandsFactory;
import cmds.ValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

public class Runner {

  private final Integer DEFAULT_BUFF_SIZE = 1024;
  public static AtomicBoolean systemRunningValue = new AtomicBoolean(true);

  CommandsFactory commandsFactory = CommandsFactory.getInstance();

  public static void main(String[] args) {
    Runner runner = new Runner();
    runner.run();
  }

  private void run() {
    System.out.println("System is running");
    try (BufferedReader reader = new BufferedReader(
         new InputStreamReader(System.in),  DEFAULT_BUFF_SIZE) ) {
      boolean running = systemRunningValue.get();
      while (running) {
        try {
          String cmd = reader.readLine();
          Command command = commandsFactory.createCommand(cmd);
          runCommand(command);
        }
        catch (IOException e) {
          e.printStackTrace();
        }
        finally {
          running = systemRunningValue.get();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("System work finished");
  }

  private void runCommand(Command command) {
    try {
      command.validate();
      command.execute();
    }
    catch (ValidationException e) {
      System.out.println(e.getMessage());
    }
  }
}
