package domain;


import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.List;

public class CmdParser {

  public Employee parseEmployee(String cmd) {
    List<String> cmdParts = Splitter.on("\"").omitEmptyStrings().trimResults().splitToList(cmd);
    String employeeIdStr = cmdParts.get(0);
    employeeIdStr = employeeIdStr.replaceAll("[a-zA-Z]*","").trim();
    int employerId = Integer.parseInt(employeeIdStr);
    String employerName = cmdParts.get(1);
    String employerAddress = cmdParts.get(2);
    Employee employee = new Employee(employerId, employerName, employerAddress);
    List<String> paymentStrategyDetails = Splitter.on(" ").trimResults().splitToList(cmdParts.get(3));
    employee.paymentStrategy = Joiner.on(":").join(paymentStrategyDetails);
    return employee;
  }
}
