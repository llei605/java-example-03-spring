package com.example.service;

import com.example.bean.Person;
import com.example.bean.PersonExample;
import com.example.dao.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonMapper personMapper;

    public String add(Person person) {
        personMapper.insert(person);
        return person.toString();
    }
}
