package cmds;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AbstractCommandTest {

  @Test
  public void testToArgs() throws Exception {
    List<String> actualArgs = AbstractCommand.toCommandParts(
      "AddEmp 1 \"David Cameron\" \"Downing Street 5, London, England\" H 1000"
    );
    String[] expectedArgs = new String[]{
      "AddEmp", "1", "\"David Cameron\"","\"Downing Street 5, London, England\"", "H", "1000"
    };
    assertArrayEquals(expectedArgs, actualArgs.toArray());
  }

  @Test
  public void yetAnotherTestToArgs() throws Exception {
    List<String> actualArgs = AbstractCommand.toCommandParts(
      "AddEmp 2 \"Donald J. Trump\" \"Trump Tower, NYC, USA\" H 1000"
    );
    String[] expectedArgs = new String[]{
      "AddEmp", "2", "\"Donald J. Trump\"","\"Trump Tower, NYC, USA\"", "H", "1000"
    };
    assertArrayEquals(expectedArgs, actualArgs.toArray());
  }

  @Test
  public void justAnotherTestToArgs() throws Exception {
    List<String> actualArgs = AbstractCommand.toCommandParts(
      "AddEmp 3 \"JZ\" \"JZ House, SF, USA\" H 1000"
    );
    String[] expectedArgs = new String[]{
      "AddEmp", "3", "\"JZ\"","\"JZ House, SF, USA\"", "H", "1000"
    };
    assertArrayEquals(expectedArgs, actualArgs.toArray());
  }
}