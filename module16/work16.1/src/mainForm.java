import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class mainForm {
    private JPanel MainPanel;
    private JTextField surname;
    private JTextField name;
    private JTextField middleName;
    private JTextField fullName;
    private JButton collapse;
    private JButton expand;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;

    public mainForm() {
        //выделение текста в полях ввода при фокусировке
        FocusAdapter selectText = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getSource()).selectAll();
                super.focusGained(e);
            }
        };
        surname.addFocusListener(selectText);
        name.addFocusListener(selectText);
        middleName.addFocusListener(selectText);
        fullName.addFocusListener(selectText);

        //кнопка объединить поля
        collapse.addActionListener(actionEvent -> {
            if (surname.getText().isEmpty() || name.getText().isEmpty() || middleName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(MainPanel, "Заполните все поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } else {
                fullName.setText(surname.getText() + " " + name.getText() + " " + middleName.getText());
                visibleInverse();
            }
        });

        //кнопка разбить поле Фамилия, Имя, Отчество
        expand.addActionListener(actionEvent -> {
            if (fullName.getText().split(" ").length != 3) {
                JOptionPane.showMessageDialog(MainPanel, "Введите в поле ввода, через пробел: Фамилию Имя Отчество", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } else {
                String[] buff = fullName.getText().split(" ");
                surname.setText(buff[0]);
                name.setText(buff[1]);
                middleName.setText(buff[2]);
                visibleInverse();
            }
        });
    }

    /**
     * инвертация видимости
     */
    private void visibleInverse(){
        surname.setVisible(!surname.isVisible());
        name.setVisible(!name.isVisible());
        middleName.setVisible(!middleName.isVisible());
        fullName.setVisible(!fullName.isVisible());
        collapse.setVisible(!collapse.isVisible());
        expand.setVisible(!expand.isVisible());
        label1.setVisible(!label1.isVisible());
        label2.setVisible(!label2.isVisible());
        label3.setVisible(!label3.isVisible());
        label4.setVisible(!label4.isVisible());
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }
}
