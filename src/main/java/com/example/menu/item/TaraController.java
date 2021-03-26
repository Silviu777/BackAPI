package com.example.menu.item;

// Folosesc clasa ResponseEntity, deoarece asigura o descriere ampla a raspunsului si face accesibila introducerea de valori.
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/menu/items")
public class TaraController {
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
    public ResponseEntity<Tara> updateId(@PathVariable("id") Long id, @RequestBody Tara updatedTara) {
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

}


