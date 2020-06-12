package com.lambdaschool.javazoo.repositories;

import com.lambdaschool.javazoo.models.Zoo;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface ZooRepository extends CrudRepository<Zoo, Long> {

    ArrayList<Zoo> findByZoonameContainingIgnoringCase(String namelike);

    Zoo findByZooname(String zooname);
}
