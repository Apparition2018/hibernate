package com.ljh.service;

import com.ljh.dao.PersonDao;
import com.ljh.entity.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * PersonService
 *
 * @author ljh
 * created on 2022/10/24 16:23
 */
@Service
public class PersonService {

    private final PersonDao personDao;
    @Resource
    private PersonService personService;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Transactional
    public void savePersons(Person p1, Person p2) {
        personDao.save(p1);
        try {
            personService.savePerson(p2);
        } catch (Exception ignored) {
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void savePerson(Person p) {
        personDao.save(p);
        int i = 1 / 0;
    }
}
