package com.lambdaschool.javazoo.controllers;

import com.lambdaschool.javazoo.models.Zoo;
import com.lambdaschool.javazoo.services.ZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/zoos")
public class ZooController {

    @Autowired
    private ZooService zooService;

    /**
     * http://localhost:2019/zoos/zoos
     * <p>
     * Return all zoos with their phone numbers and animals.
     * Return status OK
     */
    @GetMapping(value = "/zoos", produces = {"application/json"})
    public ResponseEntity<?> listZoos() {

        List<Zoo> allZoos = zooService.findAll();
        return new ResponseEntity<>(allZoos, HttpStatus.OK);
    }

    /**
     * http://localhost:2019/zoos/zoo/{zooid}
     *
     * @param zooid : long
     *              <p>
     *              Return all information related to a zoo based on its id.
     *              Return status OK
     */
    @GetMapping(value = "/zoo/{zooid}", produces = {"application/json"})
    public ResponseEntity<?> getZooById(@PathVariable Long zooid) {

        Zoo zoo = zooService.findZooById(zooid);
        return new ResponseEntity<>(zoo, HttpStatus.OK);
    }

    /**
     * http://localhost:2019/zoos/zoo/namelike/{namelike}
     */

    @GetMapping(value = "/zoo/namelike/{namelike}", produces = {"application/json"})
    public ResponseEntity<?> getZooByNameLike(@PathVariable String namelike) {

        List<Zoo> zoos = zooService.findByZoonameLike(namelike);
        return new ResponseEntity<>(zoos, HttpStatus.OK);
    }


    /**
     * http://localhost:2019/zoos/zoo
     * Adds a zoo and it's associated phone numbers (does NOT add
     * animals for which the zoo has)
     *
     * @param newZoo : Zoo
     *               <p>
     *               Return header containing the id of the newly created zoo
     *               Return status CREATE
     */
    @PostMapping(value = "/zoo", consumes = {"application/json"})
    public ResponseEntity<?> addNewZoo(@Validated @RequestBody Zoo newZoo) {

        newZoo = zooService.save(newZoo);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newZooURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{zooid}")
                .buildAndExpand(newZoo.getZooid())
                .toUri();
        responseHeaders.setLocation(newZooURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }

    /**
     * http://localhost:2019/zoos/zoo/{zooid}
     * Updates a zoo and it's associated phone numbers (does NOT
     * update zoo animal combinations)
     *
     * @param updateZoo : Zoo
     * @param zooid     : long
     *                  <p>
     *                  Return status OK
     */
    @PutMapping(value = "/zoo/{zooid}", consumes = {"application/json"})
    public ResponseEntity<?> updateZoo(@RequestBody Zoo updateZoo, @PathVariable long zooid) {

        zooService.update(updateZoo, zooid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * http://localhost:2019/zoos/zoo/{zooid}
     * Deletes a zoo, associated phone numbers, and zoo animal combination
     * associated with the provided zooid
     *
     * @param zooid : long
     *              <p>
     *              Return status OK
     */
    @DeleteMapping(value = "/zoo/{zooid}")
    public ResponseEntity<?> deleteZooById(@PathVariable long zooid) {

        zooService.delete(zooid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * http://localhost:2019/zoos/zoo/{zooid}/animals/{animalid}
     * Deletes a zoo animal combination based off of zooid & animalid
     *
     * @param zooid    : long
     * @param animalid : long
     *                 <p>
     *                 Return status OK
     */
    @DeleteMapping(value = "/zoo/{zooid}/animals/{animalid}")
    public ResponseEntity<?> deleteZooAnimal(@PathVariable long zooid, @PathVariable long animalid) {

        zooService.delete(zooid, animalid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * http://localhost:2019/zoos/zoo/{zooid}/animals/{animalid}
     * Adds a zoo animal combination based off of zooid & animalid
     *
     * @param zooid    : long
     * @param animalid : long
     *                 <p>
     *                 Return status CREATED
     */
    @PostMapping(value = "/zoo/{zooid}/animals/{animalid}")
    public ResponseEntity<?> postZooAnimalById(@PathVariable long zooid, @PathVariable long animalid) {

        zooService.addZooAnimal(zooid, animalid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(value = "/zoo/{id}", consumes = {"application/json"})
    public ResponseEntity<?> changeZoo( @RequestBody Zoo changeZoo, @PathVariable long id) {
        zooService.update(changeZoo, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}