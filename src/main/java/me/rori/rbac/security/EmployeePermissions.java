package me.rori.rbac.security;

import lombok.extern.slf4j.Slf4j;
import me.rori.rbac.model.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Created by Rori Stumpf on 6/9/20
 */
@Slf4j
@Component("employeePermissions")
public class EmployeePermissions {

  /**
   * Convenience bean to extract the principal from the current request
   *
   * @return
   */
  @Bean
  @RequestScope
  private UserDetails userDetails() {
    return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  /**
   * Returns true when a manager attempts to retrieve a team member record
   *
   * @param employee
   * @return
   */
  public boolean principalIsManagerOf(Employee employee) {
    return (employee != null) && userDetails().getUsername().equals(employee.getManager());
  }

  /**
   * Returns true when someone retrieves their own record
   *
   * @param employee
   * @return
   */
  public boolean principalIsSelf(Employee employee) {
    return (employee != null) && userDetails().getUsername().equals(employee.getName());
  }
}
