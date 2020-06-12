package com.lambdaschool.javazoo.repositories;

import com.lambdaschool.javazoo.models.Telephone;
import org.springframework.data.repository.CrudRepository;

public interface TelephoneRepository extends CrudRepository<Telephone, Long> {

}
