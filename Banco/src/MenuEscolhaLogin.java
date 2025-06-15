import javax.swing.*;
import java.awt.*;

public class MenuEscolhaLogin extends JFrame {

    public MenuEscolhaLogin() {
        setTitle("Biblioteca - Escolha de Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel titulo = new JLabel("Escolha o tipo de login:", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnFuncionario = new JButton("Login como Funcionário");
        JButton btnMembro = new JButton("Login como Membro");

        btnFuncionario.addActionListener(e -> {
            dispose();
            new TelaLogin();
        });

        btnMembro.addActionListener(e -> {
            dispose();
            new TelaLoginMembro();
        });

        add(titulo);
        add(btnFuncionario);
        add(btnMembro);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuEscolhaLogin::new);
    }
}
