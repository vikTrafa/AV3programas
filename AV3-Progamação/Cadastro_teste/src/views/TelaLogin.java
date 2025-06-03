package views;

import dao.TelaLoginDAO;
import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JLabel mensagemLabel;

    public TelaLogin() {
        setTitle("Tela de Login");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Logo
        ImageIcon icon = new ImageIcon("src/imagens/logo.png"); // Caminho relativo
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(image));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Título
        JLabel titulo = new JLabel("Bem-vindo!");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(new Color(60, 60, 60));
        mainPanel.add(titulo);
        mainPanel.add(Box.createVerticalStrut(20));

        // Campos de entrada
        usuarioField = new JTextField();
        usuarioField.setMaximumSize(new Dimension(200, 25));
        usuarioField.setAlignmentX(Component.CENTER_ALIGNMENT);

        senhaField = new JPasswordField();
        senhaField.setMaximumSize(new Dimension(200, 25));
        senhaField.setAlignmentX(Component.CENTER_ALIGNMENT);

        mensagemLabel = new JLabel(" ", SwingConstants.CENTER);
        mensagemLabel.setForeground(Color.RED);

        mainPanel.add(new JLabel("Usuário:"));
        mainPanel.add(usuarioField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(new JLabel("Senha:"));
        mainPanel.add(senhaField);
        mainPanel.add(Box.createVerticalStrut(10));

        // Botão
        JButton loginBtn = new JButton("Entrar");
        loginBtn.setBackground(new Color(0, 123, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setPreferredSize(new Dimension(100, 30));
        loginBtn.addActionListener(e -> fazerLogin());

        mainPanel.add(loginBtn);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(mensagemLabel);

        add(mainPanel);
        setVisible(true);
    }

    private void fazerLogin() {
        String usuario = usuarioField.getText();
        String senha = new String(senhaField.getPassword());

        TelaLoginDAO dao = new TelaLoginDAO();
        boolean sucesso = dao.autenticar(usuario, senha);

        if (sucesso) {
            mensagemLabel.setForeground(new Color(0, 128, 0));
            new TelaInicial(); 
            dispose();
        } else {
            mensagemLabel.setForeground(Color.RED);
            mensagemLabel.setText("Usuário ou senha inválidos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaLogin::new);
    }
}
