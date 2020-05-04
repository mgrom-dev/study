/**
 * Класс возвращает true при методе check, если все байты совпали
 */
class CheckBytes {
    private byte[] correctBytes;
    private int matches = 0;
    private boolean match = false;

    CheckBytes(byte[] bytes){
        correctBytes = bytes;
    }

    boolean check(byte b){
        if (!match) {
            matches = correctBytes[matches] == b ? ++matches : 0;
            match = matches == correctBytes.length;
        }
        return match;
    }

    void reset(){
        matches = 0;
        match = false;
    }
}
