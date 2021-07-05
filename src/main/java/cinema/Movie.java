package cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    private long id;
    private String title;
    private LocalDateTime date;
    private int maxReservation;
    private int freeSpaces;

    public void reserveSpaces(int reservedSpaces) throws IllegalStateException{
        if (freeSpaces - reservedSpaces < 0) {
            throw new IllegalStateException("Not enough spaces in this film! Choose an other one!");
        }
        freeSpaces = getFreeSpaces() - reservedSpaces;
    }

}
