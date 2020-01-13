import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main
{

// Написать программу, которая будет измерять размер всего содержимого папки,
// путь которой передаётся на вход, и выводить его в удобочитаемом виде — в байтах,
// килобайтах, мегабайтах или гигабайтах.

    public static final String folderPath = "jobs/";
    public static long countFolders = -1;
    public static long countFiles;
    public static long totalBytes;

    public static void main(String[] args) throws IOException {
        totalBytes = Files.walk(Path.of(folderPath))
                .map(Path::toFile)
                .filter(file->{
                    boolean isFile = file.isFile();
                    countFiles += isFile ? 1 : 0;
                    countFolders += !isFile ? 1 : 0;
                    return isFile;
                })
                .mapToLong(File::length)
                .sum();
        String[] typesSize = {"Байт", "КилоБайт", "МегаБайт", "ГигаБайт"};
        String typeSize = typesSize[0];
        double totalSize = totalBytes;
        for (int i = 0; totalSize > 1024 && i < typesSize.length; i++, totalSize /= 1024, typeSize = typesSize[i]) ;
        //выводим иноформацию по размеру файлов
        System.out.printf("Файлов: %d; папок: %d\nРазмер файлов: %.2f %s (%,d байт)",
                countFiles, countFolders, totalSize, typeSize, totalBytes);
    }
}
