package domain;

import java.util.*;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class DB {

  private static DB db;

  public static synchronized DB getInstance() {
    if (db == null) {
      db = new DB();
    }
    return db;
  }

  private DB() {
  }

  private static Set<Employee> entities = new HashSet<>();

  public void save(Employee employee) {
    entities.add(employee);
  }

  public Employee findBy(Integer id) {
    Optional<Employee> result = entities.stream().filter(e -> e.id.equals(id)).findFirst();
    return result.orElse(null);
  }

  public void clearDB() {
    entities.clear();
  }

  public void delete(Integer employeeId) {
    boolean deleted = entities.removeIf((employee) -> employee.id.equals(employeeId));
    if (!deleted)
      throw new DataBaseException(format("No Entity with id = %d to delete", employeeId));
  }

  public List<Employee> findAll() {
    return entities.stream().collect(toList());
  }

  public List<Employee> findAllOrderedById() {
    List<Employee> employees = findAll();
    Collections.sort(employees, (e1, e2) -> e1.id >= e2.id ? 1 : -1 );
    return employees;
  }


}
