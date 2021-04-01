package com.example.menu.item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InMemoryTaraRepository extends CrudRepository<Tara, Long> {

        List<Tara> findTaraByDenumire(String denumire);
        List<Tara> findAll();
}
