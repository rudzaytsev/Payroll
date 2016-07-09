package domain;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
}
