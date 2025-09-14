package com.girikgarg.learningspringboot.Controller;

import com.girikgarg.learningspringboot.DTO.Movie;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MovieController {
    @GetMapping("/movies")
    public String getMovies() {
        return "Fetching movie details";
    }

    @GetMapping(path = "/movies/{identifier}")
    public String getMovie (@PathVariable (name="identifier") String id) {
        return "Fetching movie details for ID: " + id;
    }

    @PostMapping("/movies")
    public String postMovies(@RequestBody Movie movie) {
        return "Creating movie with ID:" + movie.getId();
    }
}
