package me.rori.rbac.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Rori Stumpf on 6/8/20
 */
@Controller
public class UiController {

  @GetMapping("/")
  public String homePage() {
    return "homepage";
  }
}
