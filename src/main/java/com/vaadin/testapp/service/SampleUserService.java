package com.vaadin.testapp.service;

import com.vaadin.testapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class SampleUserService extends CrudService<User, Integer> {

    private final UserNewRepository repository;

    public SampleUserService(@Autowired UserNewRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<User, Integer> getRepository() {
        return repository;
    }

}
