import DAO.EmprestimoDao;
import entity.Emprestimo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InterfaceHistoricoEmprestimos extends JFrame {
    private JTable tabelaEmprestimos;
    private DefaultTableModel modelo;
    private JTextArea resultadoArea;

    public InterfaceHistoricoEmprestimos() {
        setTitle("Histórico de Empréstimos");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel();
        modelo.addColumn("ID Empréstimo");
        modelo.addColumn("ID Livro");
        modelo.addColumn("ID Membro");
        modelo.addColumn("Data Empréstimo");

        tabelaEmprestimos = new JTable(modelo);
        JScrollPane scrollTabela = new JScrollPane(tabelaEmprestimos);

        resultadoArea = new JTextArea(3, 30);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);

        JButton voltarButton = new JButton("Voltar ao Menu");
        voltarButton.addActionListener(e -> {
            dispose();
            new MenuPrincipal();
        });

        add(scrollTabela, BorderLayout.CENTER);
        add(new JScrollPane(resultadoArea), BorderLayout.SOUTH);
        add(voltarButton, BorderLayout.NORTH);

        carregarHistorico();

        setVisible(true);
    }

    private void carregarHistorico() {
        try {
            EmprestimoDao dao = new EmprestimoDao();
            List<Emprestimo> lista = dao.buscarTodosEmprestimos();

            modelo.setRowCount(0);

            for (Emprestimo e : lista) {
                modelo.addRow(new Object[]{
                        e.getIdEmprestimo(),
                        e.getIdLivro(),
                        e.getIdMembro(),
                        e.getDataEmprestimo()
                });
            }

            resultadoArea.setText("Histórico carregado com sucesso! Total de registros: " + lista.size());
        } catch (Exception ex) {
            ex.printStackTrace();
            resultadoArea.setText("Erro ao carregar histórico: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfaceHistoricoEmprestimos::new);
    }
}
