package domain;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

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
}
