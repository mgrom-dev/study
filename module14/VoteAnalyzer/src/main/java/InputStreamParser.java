import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * Простой бинарный парсинг файла
 */
public class InputStreamParser {
    private static final int BLOCK_SIZE = 128 * 1024; // Считываем по 128 КБ за раз
    private static final int VOTER_BYTE_SIZE = 800; // Сколько выделяем байт на избирателя
    private static final long MAX_MEMORY =  Runtime.getRuntime().maxMemory(); // Максимальный объем памяти
    private FileInputStream fis;
    private long fileSize;

    private HashMap<String, Integer> votersCount = new HashMap<>(); // избиратели и количество и количство голосов
    CheckBytes voterByte = new CheckBytes(new byte[] {60, 118, 111, 116, 101, 114, 32}); // тег <voter
    CheckBytes voterExitByte = new CheckBytes(new byte[] {60, 47, 118, 111, 116, 101, 114, 62}); // закрывающий тег </voter>
    CheckBytes nameByte = new CheckBytes(new byte[] {32, 110, 97, 109, 101, 61, 34}); // аттрибут name="
    CheckBytes birthdayByte = new CheckBytes(new byte[] {32, 98, 105, 114, 116, 104, 68, 97, 121, 61, 34}); // аттрибут birthDay="
    Bytes name = new Bytes(); // имя избирателя
    Bytes birthDate = new Bytes(); // дата рождения
    Runnable method = () -> { };

    InputStreamParser(String path){
        // путь к файлу
        try {
            fis = new FileInputStream(path);
            fileSize = new File(path).length();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Устанавливаем метод, который будет вызываться при переполнении памяти
     */
    public void setMethodOverflow(Runnable method){
        this.method = method;
    }

    /**
     * Парсим файл
     */
    public void parse(){
        byte[] buff = new byte[BLOCK_SIZE];
        try{
            long grade = fileSize / BLOCK_SIZE / 100;
            int p = 0;
            while(fis.read(buff) != -1) { // считываем блок из файла
                for (int i = 0; i < BLOCK_SIZE; i++) { // ищем в блоках нужную информацию
                    if (voterByte.check(buff[i])) { // находимся в теге voter, ищем закрывающий тег
                        if (nameByte.check(buff[i])) { // аттрибут name
                            if (buff[i] == 34 && name.size() > 0) {
                                nameByte.reset();
                            } else {
                                name.add(buff[i], (byte) 34);
                            }
                            continue;
                        }
                        if (birthdayByte.check(buff[i])) { // аттрибут birthDate
                            if (buff[i] == 34 && birthDate.size() > 0) {
                                birthdayByte.reset();
                            } else {
                                birthDate.add(buff[i], (byte) 34);
                            }
                            continue;
                        }
                        if (voterExitByte.check(buff[i])){ // закрывающий тег
                            if (name.size() > 0 && birthDate.size() > 0) {
                                String result = "\"" +name + "\", \"" + birthDate.toString().replace(".","-") + "\"";
                                Integer voter = votersCount.getOrDefault(result, 0);
                                voter++;
                                votersCount.put(result, voter);
                            }
                            name.clear();
                            birthDate.clear();
                            voterByte.reset();
                            voterExitByte.reset();
                            if (votersCount.size() * VOTER_BYTE_SIZE > MAX_MEMORY || votersCount.size() * 50 > 30_000_000){
                                method.run();
                                votersCount.clear();
                            }
                        }
                    }
                }
                p++;
                if (p % grade == 0) {
                    System.out.println("Parsing file: " + p / grade + "%");
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        //12 348 949 - всего избирателей
    }

    public StringBuilder getInsertVoters(){
        StringBuilder stringBuilder = new StringBuilder();
        votersCount.forEach((voter, count) -> {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("(").append(voter);
            stringBuilder.append(", ").append(count).append(")");
        });
        return stringBuilder;
    }
}
