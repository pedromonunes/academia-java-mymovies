package pt.rumos.mymovies.movie;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Component
public class MovieCatalog {

    private final List<Movie> movies;

    public MovieCatalog(ObjectMapper objectMapper, @Value("classpath:movies.json") Resource moviesResource)
            throws IOException {
        try (InputStream inputStream = moviesResource.getInputStream()) {
            movies = List.copyOf(objectMapper.readValue(inputStream, new TypeReference<List<Movie>>() {
            }));
        }
    }

    public List<Movie> findAll() {
        return movies;
    }

    public Optional<Movie> findById(Long id) {
        return movies.stream()
                .filter(movie -> movie.id().equals(id))
                .findFirst();
    }
}