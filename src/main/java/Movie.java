import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Movie {
    private Long id;
    private String title;
    private String genre;
    private Integer yearOfRelease;

}
