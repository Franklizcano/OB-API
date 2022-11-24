package com.example.obapi1.repository;

import com.example.obapi1.domain.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {

    List<Laptop> findAll();

    Optional<Laptop> findById(Long id);

    Laptop save(Laptop laptop);

    void deleteById(Long id);
}
