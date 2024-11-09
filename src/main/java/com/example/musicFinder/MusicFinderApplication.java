package com.example.musicFinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.musicFinder"})
public class MusicFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicFinderApplication.class, args);
	}

}
