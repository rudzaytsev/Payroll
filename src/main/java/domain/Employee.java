package domain;


public class Employee {


  public Integer id;

  public String name;

  public String address;

  public String paymentStrategy;

  public Employee(Integer id, String name, String address) {
    this.address = address;
    this.id = id;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Employee)) return false;

    Employee employee = (Employee) o;

    if (!address.equals(employee.address)) return false;
    if (!id.equals(employee.id)) return false;
    if (!name.equals(employee.name)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + address.hashCode();
    return result;
  }
}
