import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Menu Principal");
        setSize(350, 500); // Ajustado para comportar mais um botão
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(10, 1, 10, 10)); // Agora 10 linhas

        JButton btnLivros = new JButton("Cadastro de Livros");
        JButton btnExcluirLivros = new JButton("Excluir Livro");
        JButton btnMembros = new JButton("Cadastro de Membros");
        JButton btnExcluirMembros = new JButton("Excluir Membro");
        JButton btnEmprestimos = new JButton("Registro de Empréstimos");
        JButton btnHistorico = new JButton("Histórico de Empréstimos");
        JButton btnFuncionarios = new JButton("Cadastro de Funcionários");
        JButton btnDevolucao = new JButton("Devolução de Livros");
        JButton btnRelatorio = new JButton("Relatórios"); // Novo botão
        JButton btnSair = new JButton("Sair");

        add(btnLivros);
        add(btnExcluirLivros);
        add(btnMembros);
        add(btnExcluirMembros);
        add(btnEmprestimos);
        add(btnHistorico);
        add(btnFuncionarios);
        add(btnDevolucao);
        add(btnRelatorio); // Adiciona ao layout
        add(btnSair);

        btnLivros.addActionListener(e -> new InterfaceLivros());
        btnExcluirLivros.addActionListener(e -> new InterfaceExcluirLivros());
        btnMembros.addActionListener(e -> new InterfaceMembros());
        btnExcluirMembros.addActionListener(e -> new InterfaceExcluirMembros());
        btnEmprestimos.addActionListener(e -> new InterfaceRegistrarEmprestimo());
        btnHistorico.addActionListener(e -> new InterfaceHistoricoEmprestimos());
        btnFuncionarios.addActionListener(e -> new InterfaceFuncionarios());
        btnDevolucao.addActionListener(e -> new InterfaceDevolucao());
        btnRelatorio.addActionListener(e -> new InterfaceRelatorioCompleto()); // Ação do novo botão
        btnSair.addActionListener(e -> {
            dispose();
            new MenuEscolhaLogin();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrincipal::new);
    }
}
