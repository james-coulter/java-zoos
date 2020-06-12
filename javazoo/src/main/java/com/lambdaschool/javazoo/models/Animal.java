package com.lambdaschool.javazoo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animal")
public class Animal extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long animalid;

//    @Column(nullable = false, unique = true)
    private String animaltype;

    @OneToMany(mappedBy = "animal",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "animal")
    private List<ZooAnimals> zooAnimals = new ArrayList<>();

    public Animal() {
    }

    public Animal(String animaltype) {
        this.animaltype = animaltype;
    }

    public long getAnimalid() {
        return animalid;
    }

    public void setAnimalid(long animalid) {
        this.animalid = animalid;
    }

    public String getAnimaltype() {
        return animaltype;
    }

    public void setAnimaltype(String animaltype) {
        this.animaltype = animaltype;
    }

    public List<ZooAnimals> getZooAnimals() {
        return zooAnimals;
    }

    public void setZooAnimals(List<ZooAnimals> zooanimals) {
        this.zooAnimals = zooanimals;
    }
}
