package com.lambdaschool.javazoo.services;

import com.lambdaschool.javazoo.models.ZooAnimals;
import com.lambdaschool.javazoo.views.AnimalCountZoos;
import com.lambdaschool.javazoo.views.JustTheCount;

import java.util.List;

public interface AnimalService {
    List<AnimalCountZoos> getCountOfAnimalPresenceAtZoos();
}
