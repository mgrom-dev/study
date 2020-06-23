package sample;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class FileExplorer extends SplitMenuFactory {
    private static final KeyCode KEY_CODE = KeyCode.F1; //Клавиша для доступа к пунктам меню докера
    private static boolean isReady = false;

    @SuppressWarnings("unchecked")
    private static final ListView<String> CONTENT = (ListView<String>) Controller.getById("content"); //Элемент для навигации по файлам

    //Информационные сообщения
    private static String infoText;

    static {
        infoText = "Use arrows for navigate folders. 'Enter' for open directory, 'BackSpace' for previous root.\nPC ";
        try {
            infoText += String.format("[%s] <IP: %s> ", InetAddress.getLocalHost().getHostName(), InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        infoText += "DISKS:\n";
    }

    FileExplorer(){
        configMenuButton();

        //задаем горячую клавишу и дополнительные команды
        command.KEY = KEY_CODE;
        //открыть директорию
        Commands.add(new Command(KeyCode.ENTER, KeyEvent.KEY_RELEASED, () -> getRoots(new File(CONTENT.getSelectionModel().getSelectedItem()))));
        //вернуться в предыдущий каталог
        Commands.add(new Command(KeyCode.BACK_SPACE, KeyEvent.KEY_RELEASED, () -> {
            File current = new File(CONTENT.getSelectionModel().getSelectedItem());
            getRoots(current.getParentFile() != null ? current.getParentFile().getParentFile(): null);
        }));

        //при навигации по ListView
        CONTENT.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                File file = new File(newValue);
                if (file.isFile()) {
                    sizeFile(file);
                }
            }
        });

        getRoots(null);
        setStatusText("");
    }

    /**
     * Настраиваем SplitMenuButton
     */
    private void configMenuButton(){
        menuButton.setText("[F1] - FileExplorer");
        MenuItem deleteFile = new MenuItem("delete file") {{ setOnAction(e -> menuDeleteFileAction()); }};
        MenuItem openFile = new MenuItem("open file") {{ setOnAction(e -> menuOpenFile()); }};
        MenuItem newFile = new MenuItem("new file") {{ setOnAction(e -> menuNewFileAction()); }};

        //Добавляем комманды
        menuButton.getItems().addAll(newFile, deleteFile, openFile);

        //добавляем обработчик при открытии меню, для настройки видимости комманд в соответствии с выбраным файлом
        menuButton.addEventHandler(SplitMenuButton.ON_SHOWN, (e) -> {
            File file = new File(CONTENT.getSelectionModel().getSelectedItem());
            deleteFile.setVisible(file.getParentFile() != null && file.canRead() && file.canWrite() && file.isFile());
            openFile.setVisible(file.canRead() && file.canWrite() && file.isFile() && file.canExecute());
        });
    }

    /**
     * Действие кнопки открыть файл
     */
    private void menuOpenFile(){
        try {
            Runtime.getRuntime().exec("cmd /c \"" + CONTENT.getSelectionModel().getSelectedItem() + "\"");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Действие кнопки новый файл
     */
    private void menuNewFileAction(){
        new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> MessageBlock.messageInput("Enter the file name", () -> {
                try {
                    String fileName = ((TextField) Controller.getById("inputText")).getText();
                    fileName = fileName.replaceAll("[^a-zA-ZА-Яа-я0-9.]", "");
                    if (fileName.length() > 0) {
                        File root = new File(CONTENT.getSelectionModel().getSelectedItem()).getParentFile();
                        if (!new File(root.getPath() + "/" + fileName).createNewFile()) {
                            setStatusText("File not create");
                        }
                        getRoots(root);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }));
        }).start();
    }

    /**
     * Действие кнопки удалить файл
     */
    private void menuDeleteFileAction(){
        new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> MessageBlock.messageYesNo("Delete this file?", () -> {
                File target = new File(CONTENT.getSelectionModel().getSelectedItem());
                File parent = target.getParentFile();
                if (target.delete()) {
                    getRoots(parent);
                }
            }));
        }).start();
    }

    /**
     * инициализируем FileExplorer
     */
    public static void init(){
        if (!isReady) {
            new FileExplorer();
            isReady = true;
        }
    }

    /**
     * Выводит информацию о размере выбранного файла
     */
    private static void sizeFile(File file){
        if (file.isFile()) {
            double size = file.length();
            String[] types = {"byte", "kilobyte", "megabyte", "gigabyte"};
            int i;
            for(i = 0; size > 1024; i++) {
                size = size / 1024;
            }
            setStatusText(String.format("File size: %.2f %s", size, types[i]));
        } else {
            setStatusText("");
        }
    }

    private static void setStatusText(String text){
        ((Text) Controller.getById("info")).setText(infoText + text);
    }

    /**
     * Получаем список файлов для директории
     */
    private static void getRoots(File dir){
        if (dir != null && dir.isFile()) {
            return;
        }
        File[] roots = dir == null ? File.listRoots() : dir.listFiles();
        if (roots != null && roots.length > 0) {
            CONTENT.getItems().clear();
            for (File file : roots){
                CONTENT.getItems().add(file.getPath());
            }
            CONTENT.getSelectionModel().select(0);
            CONTENT.requestFocus();
        } else {
            setStatusText("No files in selected directory.");
        }
    }
}
