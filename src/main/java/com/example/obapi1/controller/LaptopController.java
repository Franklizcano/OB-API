package com.example.obapi1.controller;

import com.example.obapi1.domain.Laptop;
import com.example.obapi1.repository.LaptopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    private final Logger logger = LoggerFactory.getLogger(LaptopController.class);

    private LaptopRepository laptopRepository;

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    @GetMapping("/api/laptops")
    public ResponseEntity<List<Laptop>> findAll() {
        return ResponseEntity.ok(laptopRepository.findAll());
    }

    @PostMapping("/api/laptops")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop) {
        if(laptop.getId() != null) {
            logger.warn("Trying to create a laptop without id!");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.accepted().body(laptopRepository.save(laptop));
    }

    @GetMapping("/api/laptops/{id}")
    public ResponseEntity<Optional<Laptop>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(laptopRepository.findById(id));
    }

    @PutMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop) {
        if(laptop.getId() == null) {
            logger.warn("You need a id for update a laptop!");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(laptopRepository.save(laptop));
    }

    @DeleteMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id) {
        if(!laptopRepository.existsById(id)) {
            logger.warn("Trying to delete a non existent laptop}");
            return ResponseEntity.notFound().build();
        }
        laptopRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/laptops")
    public ResponseEntity<List<Laptop>> deleteAll() {
        logger.info("All laptops are deleted");
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
