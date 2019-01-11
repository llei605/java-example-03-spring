package com.example;

import com.example.bean.Person;
import com.example.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class App {

    @Autowired
    PersonService personService;

    @Test
    public void addTest() {
        Person person = new Person();
        person.setAccount("accaaa");
        person.setUsername("aaa");
        person.setPassword("111111");
        person.setAge(11);
        person.setSex(0);

        personService.add(person);
    }
}
