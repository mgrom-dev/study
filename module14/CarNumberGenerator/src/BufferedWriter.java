import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Класс для буферезации строки в памяти и последующей записи в файл
 */
class BufferedWriter {
    private static final int BUFFER_SIZE = 4096; //размер буффера при превышении, которого будет идти запись в файл
    private final StringBuilder BUFFER = new StringBuilder(); //буффер
    private FileOutputStream writer;

    BufferedWriter(String path){
        try {
            writer = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected BufferedWriter append(String string) {
        BUFFER.append(string);
        if (BUFFER.length() > BUFFER_SIZE) {
            try {
                writer.write(BUFFER.toString().getBytes());
                BUFFER.setLength(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    protected void flush() {
        try {
            writer.write(BUFFER.toString().getBytes());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
