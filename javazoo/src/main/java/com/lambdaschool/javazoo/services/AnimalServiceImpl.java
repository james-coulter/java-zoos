package com.lambdaschool.javazoo.services;

import com.lambdaschool.javazoo.repositories.AnimalRepository;
import com.lambdaschool.javazoo.views.AnimalCountZoos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "animalService")
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalrepo;

    @Override
    public List<AnimalCountZoos> getCountOfAnimalPresenceAtZoos() {
        return animalrepo.getListOfAnimalsZoos();
    }
}
