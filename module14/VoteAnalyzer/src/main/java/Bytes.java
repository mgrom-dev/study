/**
 * Класс для хранения байтов под имя избирателя и даты рождения
 */
class Bytes{
    private byte[] bytes = new byte[100]; // максимальный размер строки 100 байтов
    private int filledBytes = 0; // заполненные байты

    void add(byte b){
        bytes[filledBytes++] = b;
    }

    /**
     * добавления байта с игнорированием определенного байта
     */
    void add(byte b, byte ignore){
        if (b != ignore) {
            add(b);
        }
    }

    void clear(){
        filledBytes = 0;
    }

    /**
     * @return возвращаем строку из заполненных байт
     */
    public String toString(){
        byte[] ret = new byte[filledBytes];
        System.arraycopy(bytes, 0, ret, 0, filledBytes);
        return new String(ret);
    }

    int size(){
        return filledBytes;
    }
}