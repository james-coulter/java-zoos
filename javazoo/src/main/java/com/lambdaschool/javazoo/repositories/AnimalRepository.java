package com.lambdaschool.javazoo.repositories;

import com.lambdaschool.javazoo.models.Animal;
import com.lambdaschool.javazoo.views.AnimalCountZoos;
import com.lambdaschool.javazoo.views.JustTheCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    @Query(value = "SELECT COUNT(*) AS count FROM zooanimals WHERE zooid = :zooid AND animalid = :animalid", nativeQuery = true)
    JustTheCount checkZooAnimalCombination(long zooid, long animalid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM zooanimals WHERE zooid = :zooid AND animalid = :animalid", nativeQuery = true)
    void deleteZooAnimal(long zooid, long animalid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO zooanimals(zooid, animalid) VALUES (:zooid, :animalid)", nativeQuery = true)
    void createZooAnimal(long zooid, long animalid);

    @Query(value = "SELECT a.animaltype, count(z.animalid) as countanimal FROM animal a JOIN zooanimals z ON z.animalid = a.animalid GROUP BY a.animalid",
            nativeQuery = true)
    List<AnimalCountZoos> getListOfAnimalsZoos();
}