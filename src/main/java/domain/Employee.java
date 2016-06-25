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
    if (o == null || getClass() != o.getClass()) return false;

    Employee employee = (Employee) o;

    if (!id.equals(employee.id)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
