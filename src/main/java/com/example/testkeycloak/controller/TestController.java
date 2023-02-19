package com.example.testkeycloak.controller;

import com.example.testkeycloak.dao.CustomerDAO;
import com.example.testkeycloak.entity.CustomerEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class TestController {

  private final CustomerDAO customerDAO;

  @GetMapping(path = "/")
  public String index() {
    return "external";
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request) throws Exception {
    request.logout();
    return "redirect:/";
  }

  @GetMapping(path = "/customers")
  public String customers(Principal principal, Model model) {
    addCustomers();
    List<CustomerEntity> customers = customerDAO.findAll();
    model.addAttribute("customers", customers);
    if (Objects.nonNull(principal)) {
      model.addAttribute("username", principal.getName());
    }
    return "customers";
  }

  // add customers for demonstration
  public void addCustomers() {

    CustomerEntity customer1 = new CustomerEntity();
    customer1.setAddress("1111 foo blvd");
    customer1.setName("Foo Industries");
    customer1.setServiceRendered("Important services");
    customerDAO.save(customer1);

    CustomerEntity customer2 = new CustomerEntity();
    customer2.setAddress("2222 bar street");
    customer2.setName("Bar LLP");
    customer2.setServiceRendered("Important services");
    customerDAO.save(customer2);

    CustomerEntity customer3 = new CustomerEntity();
    customer3.setAddress("33 main street");
    customer3.setName("Big LLC");
    customer3.setServiceRendered("Important services");
    customerDAO.save(customer3);
  }
}
