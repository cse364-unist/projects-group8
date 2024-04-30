package com.example.movinProject;

import com.example.movinProject.domain.movie.repository.*;
import com.example.movinProject.domain.movie.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvLoader {

    private final MovieRepository movieRepository;

    @Autowired
    public CsvLoader(MovieRepository movieRepository) {
        movieRepository.deleteAll();
        this.movieRepository = movieRepository;
    }

    public void loadCsvDataToDB(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<Movie> movies = new ArrayList<>();
            double[] avg = new double[3953];
            long[] cnt = new long[3953];
            boolean a1 = false, a2 = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("::");
                // System.out.println(data[1]);
                // break;
                if (data.length == 3) {
                    a1 = true;
                    Movie movie = Movie.create(data[1], data[2], 5.0, "", "");
                    
                    // movie.setRating(Double.parseDouble(data[2])); // Assuming rating is the third field
                    movies.add(movie);
                }
                else if (data.length == 4) {
                    a2 = true;
                    avg[Integer.parseInt(data[1])] += Double.parseDouble(data[2]);
                    cnt[Integer.parseInt(data[1])]++;
                }
            }
            if (a2) {
                for (long i = 1L; i <= 3952L; i++) {
                    Optional<Movie> movie = movieRepository.findById((Long) i);
                    if (movie.isPresent() && cnt[i] != 0L) {
                        // System.out.println(avg[i]);
                        Movie newMovie = Movie.create(movie.get().getTitle(), movie.get().getGenre(), (Double) avg[i] / cnt[i], movie.get().getThumbnailUrl(), movie.get().getDescription());
                        // movie.setRating(avg[i] / cnt[i]);
                        
                        movies.add(newMovie);
                    }
                }
            }

            movieRepository.saveAll(movies);
            if(a1)
                System.out.println("movies.CSV data loaded successfully to MongoDB.");
            if(a2)
                System.out.println("ratings.CSV data loaded successfully to MongoDB.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
