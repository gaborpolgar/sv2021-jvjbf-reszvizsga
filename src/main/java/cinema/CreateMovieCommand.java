package cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieCommand {

    @NotBlank(message = "Movie's title can not be blank!")
    private String title;

    private LocalDateTime date;

    @Min(value = 20)
    private int maxReservation;

    public CreateMovieCommand(String title) {
        this.title = title;
    }
}
