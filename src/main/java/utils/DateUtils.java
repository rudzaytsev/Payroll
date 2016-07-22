package utils;


import java.time.LocalDate;
import java.time.Month;

public class DateUtils {

  public static LocalDate date(int year, Month month, int day) {
    return LocalDate.of(year, month, day);
  }
}
