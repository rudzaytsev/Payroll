package domain;

import java.util.List;

import static java.util.Arrays.*;

public enum PaymentTypes {
  HOURLY("H"), SALARIED("S"), COMMISSIONED("C");

  private String typeShortName;

  PaymentTypes(String typeShortName) {
    this.typeShortName = typeShortName;
  }

  @Override
  public String toString() {
    return typeShortName;
  }

  public static List<PaymentTypes> all() {
    return asList(HOURLY, SALARIED, COMMISSIONED);
  }

  public static boolean contains(String paymentType) {
    for (PaymentTypes type: all()) {
      if (type.isSame(paymentType)) return true;
    }
    return false;
  }

  public boolean isSame(String str) {
    return typeShortName.equals(str);
  }
}
