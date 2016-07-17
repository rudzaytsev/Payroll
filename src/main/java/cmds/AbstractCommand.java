package cmds;


import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand implements Command {

  public static final String COMMAND_PARTS_SEPARATOR = " ";

  public static final String QUOTE = "\"";

  protected String commandName;

  protected List<String> args = new ArrayList<>();

  public AbstractCommand(String cmd) {
    List<String> cmdParts = toCommandParts(cmd);
    if (!cmdParts.isEmpty()) {
      commandName = cmdParts.get(0);
      if (cmdParts.size() >= 2) {
        args = cmdParts.subList(1, cmdParts.size());
      }
    }

  }

  public static List<String> toCommandParts(String cmd) {
    List<String> cmdParts = Splitter.on(COMMAND_PARTS_SEPARATOR).trimResults().
                                     omitEmptyStrings().splitToList(cmd);

    List<String> commandParts = new ArrayList<>();
    StringBuilder builder = new StringBuilder();
    boolean openedQuotes = false;
    for (String cmdPart : cmdParts) {
      if (inQuotes(cmdPart) && !openedQuotes) {
        commandParts.add(cmdPart);
      }
      else if (cmdPart.startsWith(QUOTE) && !openedQuotes) {
        String begin = cmdPart + COMMAND_PARTS_SEPARATOR;
        builder = new StringBuilder(begin);
        openedQuotes = true;
      }
      else if (cmdPart.endsWith(QUOTE) && openedQuotes) {
        builder.append(cmdPart);
        commandParts.add(builder.toString());
        openedQuotes = false;
      }
      else if (openedQuotes) {
        builder.append(cmdPart + COMMAND_PARTS_SEPARATOR);
      }
      else {
        commandParts.add(cmdPart);
      }
    }
    return commandParts;
  }

  public static boolean inQuotes(String str) {
    return str.startsWith(QUOTE) && str.endsWith(QUOTE);
  }
}
