package me.rori.rbac.controllers;

import lombok.extern.slf4j.Slf4j;
import me.rori.rbac.model.Employee;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by Rori Stumpf on 6/7/20
 */

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public String isAuthenticated(Principal principal) {
    return String.format("User '%s' is authenticated", principal.getName());
  }

  @GetMapping("/principal")
  public String getPrincipalExample(Principal principal) {
    return String.format("The current user is '%s'", principal.getName());
  }

  @GetMapping("/performance/{manager}/{username}")
  @PreAuthorize("(#manager == authentication.principal.username) || (#username == authentication.principal.username)")
  public String getPrincipalByUserName(@PathVariable String manager,
                                       @PathVariable String username,
                                       Principal principal) {
    String message;
    if (principal.getName().equals(manager)) {
      message = String.format("user '%s' has been granted management access to '%s'", manager, username);
    } else {
      message = String.format("user '%s' has been granted access to their own data.", username);
    }
    return message;
  }

  @PreAuthorize("hasAnyRole('MANAGER_ROLE', 'SUPERUSER_ROLE')")
  @GetMapping("/role")
  public String getAuthenticatedRole() {
    return "you are allowed access because you are a member of 'manager_role' or 'superuser_role'";
  }

  /**
   * An example of a custom permissions evaluator class. In this case we make sure that the
   * user is allowed to have access to an Employee record.
   *
   * @return
   */
  @PostAuthorize("@employeePermissions.principalIsManagerOf(returnObject) or @employeePermissions.principalIsSelf(returnObject)")
  @GetMapping("/employee/1")
  public Employee postAuthorize() {
    // Mock employee
    return Employee.builder()
                   .name("user")
                   .manager("manager")
                   .build();
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/all")
  public String getPermitAll() {
    return "anyone can access this endpoint";
  }
}