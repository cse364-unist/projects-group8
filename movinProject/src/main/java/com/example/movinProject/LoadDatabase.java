package com.example.movinProject;

import com.example.movinProject.domain.movie.domain.Movie;
import com.example.movinProject.domain.movie.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(MovieRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(Movie.create("aa", "cc", 1.1, "dd", "ee")));
            log.info("Preloading " + repository.save(Movie.create("bb", "zz", 2.2, "asd", "sda")));
        };
    }
}
