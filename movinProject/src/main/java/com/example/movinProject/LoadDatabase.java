package com.example.movinProject;

import com.example.movinProject.domain.movie.domain.Movie;
import com.example.movinProject.domain.movie.repository.MovieRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    /**/
    @Bean
    CommandLineRunner initDatabase(MovieRepository repository) {

        return args -> {
            String filePath = "./movinProject/imdb_top_250_movies.csv";

            // CSVReader 객체 생성
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
                String[] values;
                boolean header = true;
                while ((values = csvReader.readNext()) != null) {
                    // 헤더는 건너뛰기
                    if (header) {
                        header = false;
                        continue;
                    }

                    // CSV의 각 열을 Movie 엔티티 필드에 매핑
                    String title = values[0];
//                    System.out.println("title is : " + title);
                    int year = Integer.parseInt(values[1]);
                    Double avgRating = Double.parseDouble(values[2]);
                    String genre = values[3];
                    String description = values[4];
                    String thumbnailUrl = values[5];


                    // Movie 엔티티 생성 및 저장
                    if (!repository.existsByTitle(title)) {
                        // Movie 엔티티 생성 및 저장
                        Movie movie = Movie.loadCreate(title, year, avgRating, genre, description, thumbnailUrl);
                        repository.save(movie);
                        log.info("Preloading " + movie);
                    } else {
                        log.info("Movie with title " + title + " already exists.");
                    }


                }
            } catch (IOException | CsvValidationException e) {
                log.error("Failed to parse CSV file", e);
            }
        };
}
}
