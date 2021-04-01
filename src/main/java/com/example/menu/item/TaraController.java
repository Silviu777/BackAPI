package com.example.menu.item;

// Folosesc clasa ResponseEntity, deoarece asigura o descriere ampla a raspunsului si face accesibila introducerea de valori.
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import javax.validation.Valid;

@RestController
@RequestMapping("api/menu/items")

public class TaraController {
    InMemoryTaraRepository repository;

    private final TaraService service;

    public TaraController(TaraService service) {
        this.service = service;
    }

    // GET /api/menu/items
    @GetMapping
    public ResponseEntity<List<Tara>> findAll() {
        List<Tara> items = new ArrayList<>();
        return ResponseEntity.ok().body(items);  // Shortcut pentru statusul "200 OK" la executia GET localhost:7000/api/menu/items (am folosit Postman pentru a testa endpoint-urile API-ului)
    }


    @GetMapping("/{id}")
    public ResponseEntity<Tara> find(@PathVariable("id") Long id) {
        Optional<Tara> tara = service.find(id);

        return ResponseEntity.of(tara);
    }

    // POST /api/menu/items
    @PostMapping
    public ResponseEntity<Tara> create(@RequestBody Tara tara) {
        Tara tara1 = service.create(tara);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // Pregateste un constructor de la host
                .path("/{id}")
                .buildAndExpand(tara1.getId())
                .toUri();

        return ResponseEntity.created(location).body(tara1);
    }

    // Metoda aditionala pentru crearea unei surse
    @PutMapping("/{id}")  // Endpoint care identifica o entitate dupa cheia primara
    public ResponseEntity<Tara> updateId(@PathVariable("id") Long id, @Valid @RequestBody Tara updatedTara) {
            Optional<Tara> updated = service.updateTara(id,updatedTara);

            return updated
                    .map(val -> ResponseEntity.ok().body(val))
                    .orElseGet(() -> {
                        Tara tara = service.create(updatedTara);
                        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                                                                       .buildAndExpand(tara.getId())
                                                                                       .toUri();
                        return ResponseEntity.created(location).body(tara);

        });

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tara> deleteId(@PathVariable("id") Long id) {
        service.deleteTara(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exc) {
        List<ObjectError> errors = exc.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());

        errors.forEach((error) -> {
            String catchErr = ((FieldError) error).getField();
            String valoare = error.getDefaultMessage();
            map.put(catchErr, valoare);
        });
        return ResponseEntity.badRequest().body(map);
    }


    @GetMapping("/create")
    public String createTara() {
        repository.save(new Tara(15, "Romania","Sibiu", "Paltinis"));
        repository.saveAll(Arrays.asList(new Tara(16, "Romania", "Zalau", "Zalau"),
                                         new Tara(17, "Romania", "Brasov", "Moeciu"),
                                         new Tara(18, "Romania","Constanta","Mangalia")
                                        ));
        
        return "Datele au fost inregistrate";
    }

    @PostMapping("/create")
    public String singleTara(@RequestBody Tara tara) {
        repository.save(new Tara(tara.getId(), tara.getDenumire(), tara.getRegiune(), tara.getLocalitate()));

        return "Tara s-a creat";
    }

    @GetMapping("/findTot")
    public List<Tara> findTot() {
        List<Tara> tari = repository.findAll();
        List<Tara> tariNoi = new ArrayList<>();

        for(Tara tara : tari) {
            tariNoi.add(new Tara(tara.getId(), tara.getDenumire(), tara.getRegiune(), tara.getLocalitate()));
        }
        return tariNoi;
    }

    @RequestMapping("/cauta/{id}")
    public String cautaTari(@PathVariable long id) {
        String tara;
        tara = repository.findById(id).toString();
        return tara;
    }

    @RequestMapping("/cautaDupaTara/{denumire}")
    public List<Tara> cautaDupaNume(@PathVariable String denumire) {
        List<Tara> tari = repository.findTaraByDenumire(denumire);
        List<Tara> newTara = new ArrayList<>();

        for(Tara tara : tari) {
            newTara.add(new Tara(tara.getId(), tara.getDenumire(), tara.getRegiune(), tara.getLocalitate()));
        }
        return newTara;
    }
}
