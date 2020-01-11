import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Main
{

// Написать программу, которая будет измерять размер всего содержимого папки,
// путь которой передаётся на вход, и выводить его в удобочитаемом виде — в байтах,
// килобайтах, мегабайтах или гигабайтах.

    public static final String folderPath = "jobs/";
    public static long countFolders;
    public static long countFiles;
    public static long totalBytes;

    public static void main(String[] args)
    {
        //Создаем новый экземпляр класса File, который будет содержать папку folderPath
        File folderJobs = new File(folderPath);
        ArrayList<File> files = new ArrayList<>();
        //Получаем список файлов в директории и добавляем в коллекцию
        Collections.addAll(files, folderJobs.listFiles());
        //перебираем всю коллекцию, удаляя из нее проверенный файл
        while(files.size() > 0)
        {
            File file = files.get(0);
            //если текущий файл является папкой, то получаем список файлов в ней и добавляем в коллекцию
            if (file.isDirectory())
            {
                countFolders++;
                Collections.addAll(files, file.listFiles());
            }
            else
            {
                countFiles++;
                totalBytes += file.length();
            }
            files.remove(file);
        }
        //получаем размер файлов в читабельном виде
        String[] typesSize = {"Байт", "КилоБайт", "МегаБайт", "ГигаБайт"};
        String typeSize = typesSize[0];
        double totalSize = totalBytes;
        for (int i = 0; totalSize > 1024 && i < typesSize.length; i++, totalSize /= 1024, typeSize = typesSize[i]) ;
        //выводим иноформацию по размеру файлов
        System.out.printf("Файлов: %d; папок: %d\nРазмер файлов: %.2f %s (%,d байт)",
                countFiles, countFolders, totalSize, typeSize, totalBytes);
    }
}
