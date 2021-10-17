import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static java.lang.Integer.parseInt;

@ToString
@Getter
@Setter
public class Artistas {
    private int index;
    private int year;
    private int age;
    private String name;
    private String movie;

    public Artistas(int index, int year, int age, String name, String movie) {
        this.index = index;
        this.year = year;
        this.age = age;
        this.name = name;
        this.movie = movie;
    }

    public static Artistas fromLine(String line) {
        String[] split = line.split("; ");
        return new Artistas(
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2]),
                split[3],
                split[4]
        );
    }
}
