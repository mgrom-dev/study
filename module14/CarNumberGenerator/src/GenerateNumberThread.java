/**
 * класс для генерации автомобильных номеров
 */
class GenerateNumberThread {
    private final String[] LETTERS; //массив букв, которые будут использоваться в номере
    private final String PATH; //путь к файлу для сохранения номеров

    GenerateNumberThread(String letters, String path){
        LETTERS = letters.split("");
        PATH = path.replace(".txt", "_" + letters + ".txt");
    }

    /**
     * @param method функция callback, которая запуститься по завершению потока генерации номеров
     */
    public void run(Runnable method) {
        //запускаем отдельный поток
        new Thread(() -> {
            BufferedWriter BufferedWrite = new BufferedWriter(PATH);
            for(int number = 1; number < 1000; number++) {
                for (int regionCode = 1; regionCode < 100; regionCode++) {
                    for (String firstLetter : LETTERS) {
                        for (String secondLetter : LETTERS) {
                            for (String thirdLetter : LETTERS) {
                                BufferedWrite.append(firstLetter).
                                        append(padNumber(number, 3)).
                                        append(secondLetter + thirdLetter).
                                        append(padNumber(regionCode, 2)).
                                        append("\n");
                            }
                        }
                    }
                }
            }
            BufferedWrite.flush(); //записываем остатки в буфере на диск

            method.run(); //функция callback
        }).start();
    }

    /**
     * метод для добавления 0 перед number, если он меньше длины numberLength
     * (для увеличения производительности работает только с numberLength 3 или 2)
     */
    private static String padNumber(int number, int numberLength)
    {
        return numberLength == 2 ? (number < 10 ? "0" + number : number + "") : (number < 10 ? "00" + number : number < 100 ? "0" + number : number + "");
    }
}
