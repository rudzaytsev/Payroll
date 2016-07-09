package cmds;


import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand implements Command {

  public static final String COMMAND_PARTS_SEPARATOR = " ";

  public static final String QUOTE = "\"";

  protected List<String> args;

  public AbstractCommand(String cmd) {
    args = toArgs(cmd);

  }

  public static List<String> toArgs(String cmd) {
    List<String> cmdParts = Splitter.on(COMMAND_PARTS_SEPARATOR).trimResults().
                                     omitEmptyStrings().splitToList(cmd);

    List<String> args = new ArrayList<>();
    StringBuilder builder = new StringBuilder();
    boolean openedQuotes = false;
    for (String cmdPart : cmdParts) {
      if (inQuotes(cmdPart) && !openedQuotes) {
        args.add(cmdPart);
      }
      else if (cmdPart.startsWith(QUOTE) && !openedQuotes) {
        String begin = cmdPart + COMMAND_PARTS_SEPARATOR;
        builder = new StringBuilder(begin);
        openedQuotes = true;
      } else if (cmdPart.endsWith(QUOTE) && openedQuotes) {
        builder.append(cmdPart);
        args.add(builder.toString());
        openedQuotes = false;
      }
      else if (openedQuotes) {
        builder.append(cmdPart + COMMAND_PARTS_SEPARATOR);
      }
      else {
        args.add(cmdPart);
      }
    }
    return args;
  }

  public static boolean inQuotes(String str) {
    return str.startsWith(QUOTE) && str.endsWith(QUOTE);
  }
}
