import javax.swing.*;
import java.awt.*;

public class MenuMembros extends JFrame {

    public MenuMembros() {
        setTitle("Menu do Membro");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnEmprestimos = new JButton("Registrar Empréstimo");
        JButton btnDevolucao = new JButton("Devolver Livro");
        JButton btnSair = new JButton("Sair");

        add(btnEmprestimos);
        add(btnDevolucao);
        add(btnSair);

        // Agora usando as novas interfaces renomeadas:
        btnEmprestimos.addActionListener(e -> {
            dispose();
            new InterfaceRegistrarEmprestimoMembro();
        });

        btnDevolucao.addActionListener(e -> {
            dispose();
            new InterfaceDevolucaoMembro();
        });

        btnSair.addActionListener(e -> {
    dispose();
    new MenuEscolhaLogin(); // Volta para a tela de escolha
});

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuMembros::new);
    }
}
