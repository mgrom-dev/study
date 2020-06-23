package sample;

import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.io.File;
import java.util.Objects;

public class Git extends SplitMenuFactory {
    private static final KeyCode KEY_CODE = KeyCode.F4; //Клавиша для доступа к пунктам гита
    private static boolean isReady = false;

    private Git(){
        menuButton.setText("[F4] - Git");
        MenuItem initGit = new MenuItem("init git here") {{ setOnAction(e -> initGit()); }};
        MenuItem statusGit = new MenuItem("git status") {{ setOnAction(e -> statusGit()); }};
        MenuItem hashAllGit = new MenuItem("hash all files") {{ setOnAction(e -> hashAllGit()); }};
        MenuItem commitGit = new MenuItem("commit changes") {{ setOnAction(e -> commitGit()); }};
        MenuItem pushGit = new MenuItem("push commits on server") {{ setOnAction(e -> pushGit()); }};
        menuButton.getItems().addAll(initGit, statusGit, hashAllGit, commitGit, pushGit);

        menuButton.addEventHandler(SplitMenuButton.ON_SHOWN, (e) -> {
            File gitDirectory = getGitDirectory();
            initGit.setVisible(gitDirectory == null);
            statusGit.setVisible(gitDirectory != null);
            hashAllGit.setVisible(gitDirectory != null);
            commitGit.setVisible(gitDirectory != null);
            pushGit.setVisible(gitDirectory != null);
        });

        command.KEY = KEY_CODE;
    }

    /**
     * Пытаемся получить корневую директорию инициализированного гита
     */
    private static File getGitDirectory(){
        File currentDir = getDirectory();
        while (currentDir != null && currentDir.listFiles() != null) {
            for (File file : Objects.requireNonNull(currentDir.listFiles((file, s) -> s.equals(".git")))){
                return file.getParentFile();
            }
            currentDir = currentDir.getParentFile();
        }
        return null;
    }

    /**
     * Получить текущую директорию для выбранного файла в списке
     */
    @SuppressWarnings("unchecked")
    private static File getDirectory(){
        return new File(((ListView<String>) Controller.getById("content")).getSelectionModel().getSelectedItem()).getParentFile();
    }

    private static void pushGit(){
        setStatusText(Commands.executeCmd("cd \"" + Objects.requireNonNull(getGitDirectory()).getPath() + "\" && git push origin master"));
    }

    private static void commitGit(){
        MessageBlock.messageInput("Input comment to commit", () ->
                setStatusText(Commands.executeCmd("cd \"" + Objects.requireNonNull(getGitDirectory()).getPath() +
                        "\" && git commit -m \"" + ((TextField) Controller.getById("inputText")).getText() + "\"")));
    }

    private static void hashAllGit(){
        setStatusText(Commands.executeCmd("cd \"" + Objects.requireNonNull(getGitDirectory()).getPath() + "\" && git add ."));
    }

    private static void initGit(){
        setStatusText(Commands.executeCmd("cd \"" + Objects.requireNonNull(getGitDirectory()).getPath() + "\" && git init"));
    }

    private static void statusGit(){
        setStatusText(Commands.executeCmd("cd \"" + Objects.requireNonNull(getGitDirectory()).getPath() + "\" && git status"));
    }

    private static void setStatusText(String text){
        ((Text) Controller.getById("info")).setText(text);
    }

    public static void init(){
        if (!isReady) {
            new Git();
            isReady = true;
        }
    }
}
