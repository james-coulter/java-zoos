package com.lambdaschool.javazoo.services;

import com.lambdaschool.javazoo.models.Telephone;
import com.lambdaschool.javazoo.models.Zoo;
import com.lambdaschool.javazoo.repositories.AnimalRepository;
import com.lambdaschool.javazoo.repositories.ZooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;


@Service(value = "zooService")
public class ZooServiceImpl implements ZooService{

    @Autowired
    private ZooRepository zooRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public ArrayList<Zoo> findAll() {

        ArrayList<Zoo> zooList = new ArrayList<>();
        zooRepository.findAll().iterator().forEachRemaining(zooList::add);
        return zooList;
    }

    @Override
    public Zoo findZooById(long zooid) {

        return zooRepository.findById(zooid).orElseThrow(() ->
                new EntityNotFoundException("Zoo id " + zooid + " not found"));
    }

    @Override
    public ArrayList<Zoo> findByZoonameLike(String namelike) {

        return zooRepository.findByZoonameContainingIgnoringCase(namelike);
    }

    @Override
    public Zoo save(Zoo zoo) {

        if (zooRepository.findByZooname(zoo.getZooname()) != null) {
            throw new EntityNotFoundException(zoo.getZooname() + " is already taken!");
        }

        Zoo newZoo = new Zoo();
        newZoo.setZooname(zoo.getZooname());

        for (Telephone t : zoo.getTelephones()) {
            newZoo.getTelephones()
                    .add(new Telephone(t.getPhonetype(),
                            t.getPhonenumber(), newZoo));
        }

        return zooRepository.save(newZoo);
    }

    @Transactional
    @Override
    public Zoo update(Zoo zoo, long zooid) {

        Zoo existingZoo = findZooById(zooid);
        if (zoo.getZooname() != null) {
            existingZoo.setZooname(zoo.getZooname());
        }

        if (zoo.getTelephones() != null) {
            for (Telephone t : zoo.getTelephones()) {
                existingZoo.getTelephones()
                        .add(new Telephone(t.getPhonetype(),
                                t.getPhonenumber(), existingZoo));
            }
        }

        return zooRepository.save(existingZoo);
    }

    @Override
    public void delete(long zooid) {

        zooRepository.findById(zooid).orElseThrow(() ->
                new EntityNotFoundException("Zoo id " + zooid + "not found!"));
        zooRepository.deleteById(zooid);
    }

    @Override
    public void delete(long zooid, long animalid) {

        zooRepository.findById(zooid).orElseThrow(() ->
                new EntityNotFoundException("Zoo id " + zooid + "not found!"));
        animalRepository.findById(animalid).orElseThrow(() ->
                new EntityNotFoundException("Zoo id " + animalid + "not found!"));

        if (animalRepository.checkZooAnimalCombination(zooid, animalid).getCount() >  0) {
            animalRepository.deleteZooAnimal(zooid, animalid);
        } else {
            throw new EntityNotFoundException("The zoo has no animals of that type!");
        }
    }

    @Override
    public void addZooAnimal(long zooid, long animalid) {
        zooRepository.findById(zooid).orElseThrow(() ->
                new EntityNotFoundException("Zoo id " + zooid + " not found!"));
        animalRepository.findById(animalid).orElseThrow(() ->
                new EntityNotFoundException("Animal id " + animalid + " not found!"));
        if (animalRepository.checkZooAnimalCombination(zooid, animalid).getCount() <= 0) {
            animalRepository.createZooAnimal(zooid, animalid);
        } else throw new EntityNotFoundException("Zoo and Animal Combination Already Exists");
    }
}