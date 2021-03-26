package com.example.menu.item;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableMapRepositories
public class TaraService {
    private final CrudRepository<Tara, Long> repository;

    public TaraService(CrudRepository<Tara, Long> repository) {
        this.repository = repository;

        this.repository.saveAll(defaultTara());
    }

    private static List<Tara> defaultTara() {
        return List.of (
                new Tara(1, "Romania", "Brasov", "Poiana Brasov"),
                new Tara(2, "Romania", "Brasov", "Bran"),
                new Tara(3, "Romania", "Brasov", "Codlea"),
                new Tara(4, "Romania", "Brasov", "Predeal"),
                new Tara(5, "Romania", "Prahova", "Azuga"),
                new Tara(6, "Romania", "Prahova", "Sinaia"),
                new Tara(7, "Romania", "Prahova", "Busteni"),
                new Tara(8, "Romania", "Alba", "Arieseni"),
                new Tara(9, "Romania", "Constanta", "Mamaia"),
                new Tara(10,"Romania", "Buzau", "Nehoiu")

                       );
        
    }

    public List<Tara> findAll() {

        List<Tara> list = new ArrayList<>();
        Iterable<Tara> places = repository.findAll();
        places.forEach(list :: add);
        return list;
    }

    // Optional - incapsulare a unei valori optionale (obiect container care poate sau nu sa contina o valoare)
    public Optional<Tara> find(Long id) {
        return repository.findById(id);
        
    }

    public Tara create(Tara tara) {
        Tara aux = new Tara (
                   new Date().getTime(),
                   tara.getDenumire(),
                   tara.getRegiune(),
                   tara.getLocalitate()
                            );

        return repository.save(aux);
    }

    public Optional<Tara> updateTara(long id, Tara newTara) {
        return repository.findById(id)
                .map(prevTara -> {
                    Tara updated = prevTara.updateWith(newTara);
                    return repository.save(updated);
                    
                } );
        
    }

    public void deleteTara(Long id) {
        repository.deleteById(id);
    }

}
