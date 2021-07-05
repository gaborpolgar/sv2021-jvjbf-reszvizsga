package cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private long id;
    private String title;
    private LocalDateTime date;
    private int maxReservation;
    private int freeSpaces;
}
