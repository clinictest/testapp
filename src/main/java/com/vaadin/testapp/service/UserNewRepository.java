package com.vaadin.testapp.service;

import com.vaadin.testapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNewRepository extends JpaRepository<User, Integer> {

}