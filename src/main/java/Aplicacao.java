import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aplicacao {
    private List<Artistas> atores;

    public static void main(String[] args) {
        Aplicacao app = new Aplicacao();
        Aplicacao homem = new Aplicacao();
        Aplicacao mulher = new Aplicacao();

        homem.TesteLeituraArquivosCsv("male.csv");
        mulher.TesteLeituraArquivosCsv("female.csv");

        homem.getAtorMaisNovo();
        mulher.getAtrizMaisPremiada();
        mulher.getAtrizMaisVezesPremiada();
        homem.getRecebeuMaisDeUmOscar();
        mulher.getRecebeuMaisDeUmOscar();
        homem.getEscolherAtor("David Niven");
        mulher.getEscolherAtor("Joanne Woodward");


    }

    private void TesteLeituraArquivosCsv(String filename) {
        String filepath = getFilePathResourceAsStream(filename);

        try (Stream<String> lines = Files.lines(Path.of(filepath))) {
            this.atores = lines.skip(1)
                    .map(Artistas::fromLine)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFilePathResourceAsStream(String fileName) {
        URL url = getClass().getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        return file.getPath();
    }

    private void getAtorMaisNovo() {
        String caminho = getFilePathResourceAsStream("male.csv");
        System.out.println("O ator mais jovem a ganhar o oscar é: ");
        try (Stream<String> line = Files.lines(Path.of(caminho))) {
            this.atores.stream()
                    .min(Comparator.comparingInt(Artistas::getAge))
                    .ifPresent(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAtrizMaisPremiada() {
        String filePathAtriz = getFilePathResourceAsStream("female.csv");
        System.out.println();
        System.out.println("A atriz mais premiada: ");
        try (Stream<String> line = Files.lines(Path.of(filePathAtriz))) {
            this.atores.stream()
                    .collect(Collectors.groupingBy(Artistas::getName, Collectors.counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .ifPresent(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getAtrizMaisVezesPremiada() {
        String filePathAtriz = getFilePathResourceAsStream("female.csv");
        System.out.println();
        System.out.println("A atriz mais vezes premiada: ");
        this.atores.stream()
                .filter(e -> e.getAge() >= 20 && e.getAge() <= 30)
                .collect(Collectors.groupingBy(Artistas::getName, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .ifPresent(System.out::println);
    }

    private void getRecebeuMaisDeUmOscar() {
        String fileAtor = getFilePathResourceAsStream("male.csv");
        String fileAtriz = getFilePathResourceAsStream("female.csv");
        System.out.println();
        System.out.println("Atores e atrizes que ganharam mais de um oscar: ");
        HashMap<String, Long> nomeAtriz = (HashMap<String, Long>) this.atores.stream()
                .map(Artistas::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        nomeAtriz.entrySet().stream()
                .filter(n -> n.getValue() > 1)
                .forEach(System.out::println);
    }

    private void getEscolherAtor(String name) {
        String fileAtor = getFilePathResourceAsStream("male.csv");
        String fileAtriz = getFilePathResourceAsStream("female.csv");
        System.out.println();
        System.out.println("Artista escolhido: ");
        this.atores.stream()
                .filter(a -> Objects.equals(a.getName(), name))
                .forEach(System.out::println);

    }
}
