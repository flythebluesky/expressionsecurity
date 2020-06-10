package me.rori.rbac.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Rori Stumpf on 6/9/20
 */
@Data
@Builder
@ToString
public class Employee {

  private String name;
  private String manager;
}
