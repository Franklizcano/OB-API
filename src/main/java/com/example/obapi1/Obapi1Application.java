package com.example.obapi1;

import com.example.obapi1.domain.Laptop;
import com.example.obapi1.repository.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Obapi1Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Obapi1Application.class, args);
		LaptopRepository laptopRepository = context.getBean(LaptopRepository.class);

		Laptop laptop1 = new Laptop(null, "Lenovo", "Thinkpad");
		Laptop laptop2 = new Laptop(null, "Apple", "Macbook Pro");

		laptopRepository.save(laptop1);
		System.out.println(laptopRepository.findAll().size());

		laptopRepository.save(laptop2);
		System.out.println(laptopRepository.findAll().size());
	}

}
