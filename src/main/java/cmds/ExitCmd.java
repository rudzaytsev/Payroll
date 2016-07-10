package cmds;

import domain.Runner;

/**
 * Created by rudolph on 10.07.16.
 */
public class ExitCmd extends AbstractCommand {


  public ExitCmd(String cmd) {
    super(cmd);
  }

  @Override
  public void validate() {
  }

  @Override
  public void execute() {
    Runner.systemRunningValue.compareAndSet(true, false);
  }
}
