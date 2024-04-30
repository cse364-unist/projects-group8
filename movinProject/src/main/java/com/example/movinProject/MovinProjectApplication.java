package com.example.movinProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.movinProject.CsvLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class MovinProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(MovinProjectApplication.class, args);
	}
	@Bean
    public CommandLineRunner loadMovieData(CsvLoader csvLoader) {
        return args -> {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            csvLoader.loadCsvDataToDB("./movinProject/data/movies.csv");
            csvLoader.loadCsvDataToDB("./movinProject/data/ratings.csv");
        };
    }
}
