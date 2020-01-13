import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Main
{

// Написать код, который будет копировать указанную папку
// с файлами с сохранением структуры в другую указанную папку.

    public static String folderPath = "jobs";
    public static String modPath = "_copy";
    public static int countFiles, countFolders;

    public static void main(String[] args)
    {
        try {
            Files.walk(Path.of(folderPath))
                    .forEach(path->{
                        try {
                            boolean isFile = path.toFile().isFile();
                            Path newPath = Path.of(path.toString().replace(folderPath, folderPath + modPath));
                            countFiles += isFile ? 1 : 0;
                            countFolders += !isFile ? 1 : 0;
                            if (!Files.exists(newPath) || isFile) {
                                Files.copy(path, newPath, StandardCopyOption.REPLACE_EXISTING);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Копирование папки: " + folderPath + " в папку: " + (folderPath + modPath)  + " завершено." +
                "\nСкопировано: " + countFiles + " файлов; папок: " + countFolders);
    }
}
