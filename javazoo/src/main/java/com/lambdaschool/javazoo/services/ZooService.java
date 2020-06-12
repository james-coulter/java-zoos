package com.lambdaschool.javazoo.services;

import com.lambdaschool.javazoo.models.Zoo;

import java.awt.*;
import java.util.ArrayList;

public interface ZooService {

    ArrayList<Zoo> findAll();

    Zoo findZooById(long zooid);

    ArrayList<Zoo> findByZoonameLike(String namelike);

    Zoo save(Zoo zoo);

    Zoo update(Zoo zoo, long zooid);

    void delete(long zooid);

    void delete(long zooid, long animalid);

    void addZooAnimal(long zooid, long animalid);
}
