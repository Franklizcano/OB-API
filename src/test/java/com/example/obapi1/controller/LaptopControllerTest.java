package com.example.obapi1.controller;

import com.example.obapi1.domain.Laptop;
import com.example.obapi1.repository.LaptopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    private LaptopRepository laptopRepository;

    @BeforeEach
    public void setUp() {
        restTemplateBuilder =  restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
        laptopRepository.deleteAll();
        Laptop laptop1 = new Laptop(null, "laptop1", "laptop1");
        Laptop laptop2 = new Laptop(null, "laptop2", "laptop2");
        Laptop laptop3 = new Laptop(null, "laptop3", "laptop3");
        Laptop laptop4 = new Laptop(null, "laptop4", "laptop4");
        List<Laptop> laptops = List.of(laptop1, laptop2, laptop3, laptop4);
        laptopRepository.saveAll(laptops);
    }

    @Test
    @DisplayName("Getting all laptops")
    void findAll() {
        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());

        List<Laptop> laptops = Arrays.asList(response.getBody());
        assertNotEquals(0, laptops.size());
    }

    @Test
    @DisplayName("Creating a new laptop")
    void create() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "brand": "laptopTest",
                    "model": "laptopTest"
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);
        Laptop result = response.getBody();

        assertEquals(5L, result.getId());
        assertEquals("laptopTest", result.getBrand());
    }

    @Test
    @DisplayName("Updating a laptop")
    void update() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "id": 1,
                    "brand": "laptopTestChanged",
                    "model": "laptopTest"
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops/1", HttpMethod.PUT, request, Laptop.class);
        Laptop result = response.getBody();

        assertEquals("laptopTestChanged", result.getBrand());
    }

    @Test
    @DisplayName("Getting a single laptop")
    void findById() {
        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/api/laptops/1", Laptop.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
    }
}