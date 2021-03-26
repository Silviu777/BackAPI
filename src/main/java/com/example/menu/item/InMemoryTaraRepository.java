package com.example.menu.item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InMemoryTaraRepository extends CrudRepository<Tara, Long> {

}
