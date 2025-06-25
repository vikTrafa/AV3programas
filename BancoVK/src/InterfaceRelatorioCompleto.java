import DAO.MembrosDao;
import DAO.LivrosDao;
import entity.Membros;
import entity.Livros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class InterfaceRelatorioCompleto extends JFrame {
    private JTextArea areaRelatorio;

    public InterfaceRelatorioCompleto() {
        setTitle("Relatórios da Biblioteca");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Botões
        JPanel botoesPanel = new JPanel();
        JButton btnGerar = new JButton("Gerar Relatório");
        JButton btnFechar = new JButton("Fechar");

        botoesPanel.add(btnGerar);
        botoesPanel.add(btnFechar);

        // Área de texto
        areaRelatorio = new JTextArea();
        areaRelatorio.setEditable(false);
        areaRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaRelatorio);

        // Ações
        btnGerar.addActionListener((ActionEvent e) -> gerarRelatorioCompleto());
        btnFechar.addActionListener((ActionEvent e) -> dispose());

        add(botoesPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void gerarRelatorioCompleto() {
        areaRelatorio.setText(""); // Limpa
        carregarRelatorioMembros();
        carregarRelatorioLivros();
        carregarRelatorioMembrosExcluidos();
        carregarRelatorioLivrosExcluidos();
    }

    private void carregarRelatorioMembros() {
        try {
            MembrosDao dao = new MembrosDao();
            List<Membros> membros = dao.listarTodos();
            areaRelatorio.append("=== RELATÓRIO DE MEMBROS ATIVOS ===\n");
            for (Membros m : membros) {
                areaRelatorio.append("ID: " + m.getCodigo() + "\n");
                areaRelatorio.append("Nome: " + m.getNome() + "\n");
                areaRelatorio.append("CPF: " + m.getCpf() + "\n");
                areaRelatorio.append("Telefone: " + m.getTelefone() + "\n");
                areaRelatorio.append("Email: " + m.getEmail() + "\n");
                areaRelatorio.append("-----------------------------\n");
            }
            areaRelatorio.append("\n");
        } catch (SQLException e) {
            areaRelatorio.append("Erro ao carregar membros ativos: " + e.getMessage() + "\n");
        }
    }

    private void carregarRelatorioLivros() {
        LivrosDao dao = new LivrosDao();
        List<Livros> livros = dao.listarTodos();
        areaRelatorio.append("=== RELATÓRIO DE LIVROS ATIVOS ===\n");
        for (Livros l : livros) {
            areaRelatorio.append("ID: " + l.getId() + "\n");
            areaRelatorio.append("Título: " + l.getTitulo() + "\n");
            areaRelatorio.append("Autor: " + l.getAutor() + "\n");
            areaRelatorio.append("Categoria: " + l.getCategoria() + "\n");
            areaRelatorio.append("Disponível: " + (l.getDisponibilidade().equalsIgnoreCase("true") ? "Sim" : "Não") + "\n");
            areaRelatorio.append("-----------------------------\n");
        }
        areaRelatorio.append("\n");
    }

    private void carregarRelatorioMembrosExcluidos() {
        try {
            MembrosDao dao = new MembrosDao();
            List<Membros> excluidos = dao.listarExcluidos();
            areaRelatorio.append("=== RELATÓRIO DE MEMBROS EXCLUÍDOS ===\n");
            for (Membros m : excluidos) {
                areaRelatorio.append("ID: " + m.getCodigo() + "\n");
                areaRelatorio.append("Nome: " + m.getNome() + "\n");
                areaRelatorio.append("CPF: " + m.getCpf() + "\n");
                areaRelatorio.append("Telefone: " + m.getTelefone() + "\n");
                areaRelatorio.append("Data de Exclusão: " + m.getEmail() + "\n"); // data_exclusao está no campo Email
                areaRelatorio.append("-----------------------------\n");
            }
            areaRelatorio.append("\n");
        } catch (Exception e) {
            areaRelatorio.append("Erro ao carregar membros excluídos: " + e.getMessage() + "\n");
        }
    }

    private void carregarRelatorioLivrosExcluidos() {
    try {
        LivrosDao dao = new LivrosDao();
        List<Livros> excluidos = dao.listarLivrosExcluidos();
        System.out.println("Livros excluídos encontrados: " + excluidos.size()); // DEBUG
        areaRelatorio.append("=== RELATÓRIO DE LIVROS EXCLUÍDOS ===\n");
        for (Livros l : excluidos) {
            areaRelatorio.append("ID: " + l.getId() + "\n");
            areaRelatorio.append("Título: " + l.getTitulo() + "\n");
            areaRelatorio.append("Autor: " + l.getAutor() + "\n");
            areaRelatorio.append("Categoria: " + l.getCategoria() + "\n");
            areaRelatorio.append("Data de Exclusão: " + l.getDisponibilidade() + "\n");
            areaRelatorio.append("-----------------------------\n");
        }
        areaRelatorio.append("\n");
    } catch (SQLException e) {
        areaRelatorio.append("Erro ao carregar livros excluídos: " + e.getMessage() + "\n");
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfaceRelatorioCompleto());
    }
}
