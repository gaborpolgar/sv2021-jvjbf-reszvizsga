package cinema;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private ModelMapper modelMapper;
    private AtomicLong idGenerator = new AtomicLong();
    private List<Movie> movies = Collections.synchronizedList(new ArrayList<>());

    public MovieService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<MovieDTO> getMoviesByTitle(Optional<String> title) {
        Type targetListType = new TypeToken<List<MovieDTO>>() {
        }.getType();
        List<Movie> filtered = movies.stream().
                filter(e -> title.isEmpty() || e.getTitle().equalsIgnoreCase(title.get().toLowerCase()))
                .collect(Collectors.toList());
        return modelMapper.map(filtered, targetListType);
    }

    public MovieDTO findMoviesById(long id) {
        return modelMapper.map(movies.stream()
                        .filter(movie -> movie.getId() == id).findAny()
                        .orElseThrow(() -> new IllegalArgumentException("Movie not found" + id)),
                MovieDTO.class);
    }

    public MovieDTO reserveSpaces(long id, CreateReservationCommand command) {
        Movie movie = movies.stream()
                        .filter(m -> m.getId() == id).findAny()
                        .orElseThrow(() -> new IllegalArgumentException("Movie not found" + id));
                        movie.reserveSpaces(command.getReservedSeats());
        return modelMapper.map(movie, MovieDTO.class);
    }

    public MovieDTO updateMovie(long id, UpdateDateCommand command) {
        Movie movie = movies.stream()
                .filter(e -> e.getId() == id)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Movie not found: " + id));
        movie.setDate(command.getDate());
        return modelMapper.map(movie, MovieDTO.class);
    }

    public MovieDTO createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(idGenerator.incrementAndGet(), command.getTitle(), command.getDate(), command.getMaxReservation(), command.getMaxReservation());
        movies.add(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }

    public void deleteAllMovies() {
        idGenerator = new AtomicLong();
        movies.clear();
    }
}
