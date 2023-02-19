package com.example.testkeycloak.dao;

import com.example.testkeycloak.entity.CustomerEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CustomerDAO  {

  private final List<CustomerEntity> list = new ArrayList<>();
  Integer lastId = 1;

  public void save(CustomerEntity entity){
    entity.setId(lastId++);
    list.add(entity);
  }

  public List<CustomerEntity> findAll(){
    return list;
  }
}
