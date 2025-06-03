package views;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame {

    public TelaInicial() {
        setTitle("Bem-vindo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(230, 240, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adiciona logo no topo
        ImageIcon icon = new ImageIcon("src/imagens/logo.png"); // Caminho relativo
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(image));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(logoLabel, gbc);

        // Mensagem de boas-vindas
        JLabel mensagem = new JLabel("Bem-vindo ao sistema", SwingConstants.CENTER);
        mensagem.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridy = 1;
        painel.add(mensagem, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;

        JButton btnProfessor = new JButton("Professor");
        JButton btnTecnico = new JButton("Técnico");
        JButton btnAluno = new JButton("Aluno");

        gbc.gridx = 0;
        painel.add(btnProfessor, gbc);

        gbc.gridx = 1;
        painel.add(btnTecnico, gbc);

        gbc.gridx = 2;
        painel.add(btnAluno, gbc);

        add(painel);

        btnProfessor.addActionListener(e -> {
            new InterfaceProfessor();
            dispose();
        });

        btnTecnico.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Tela Técnico ainda não implementada.");
        });

        btnAluno.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Tela Aluno ainda não implementada.");
        });

        setVisible(true);
    }
}
