package sample;

import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Commands {

    //Комманды
    public static final ArrayList<Command> COMMANDS = new ArrayList<>(){
        @Override
        public Command get(int index) {
            for (Command command : this) {
                if (command.KEY.getCode() == index) {
                    return command;
                }
            }
            return null;
        }
    };

    public static Command getByKey(KeyCode keyCode){
        for (Command command: COMMANDS) {
            if (command.KEY == keyCode) {
                return command;
            }
        }
        return null;
    }

    public static void clear(){
        COMMANDS.clear();
    }

    public static void add(Command command){
        COMMANDS.add(command);
    }

    public static void addAll(Collection<Command> collection){
        COMMANDS.addAll(collection);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Command> copy(){
        return (ArrayList<Command>) COMMANDS.clone();
    }

    public static void remove(Command command){
        COMMANDS.remove(command);
    }

    /**
     * Выполнить команду в командной строке и получить результат выполнения данной комманды
     */
    public static String executeCmd(String command){
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec("cmd /c \"" + command + "\"");
            String output = new String(process.getInputStream().readAllBytes(), "cp866");
            String errorMessage = new String(process.getErrorStream().readAllBytes(), "cp866");
            result = errorMessage.length() > 0 ? errorMessage : output;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
