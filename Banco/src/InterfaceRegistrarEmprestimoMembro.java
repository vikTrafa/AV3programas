import DAO.EmprestimoDao;
import DAO.Livros3Dao;
import entity.Emprestimo;
import entity.Livros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InterfaceRegistrarEmprestimoMembro extends JFrame {
    private JTextField idLivroField;
    private JTextField idMembroField;
    private JTextField dataEmprestimoField;
    private JTextField dataDevolucaoField;
    private JTextArea resultadoArea;
    private JTable tabelaLivros;
    private DefaultTableModel modelo;

    public InterfaceRegistrarEmprestimoMembro() {
        setTitle("Registrar Empréstimo de Livro");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelForm = new JPanel(new GridLayout(6, 2, 10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        idLivroField = new JTextField();
        idLivroField.setEditable(false);

        idMembroField = new JTextField();
        dataEmprestimoField = new JTextField();
        dataDevolucaoField = new JTextField();

        painelForm.add(new JLabel("ID do Livro (selecione abaixo):"));
        painelForm.add(idLivroField);
        painelForm.add(new JLabel("ID do Membro:"));
        painelForm.add(idMembroField);
        painelForm.add(new JLabel("Data do Empréstimo (YYYY-MM-DD):"));
        painelForm.add(dataEmprestimoField);
        painelForm.add(new JLabel("Data da Devolução (YYYY-MM-DD):"));
        painelForm.add(dataDevolucaoField);

        JButton registrarBtn = new JButton("Registrar Empréstimo");
        JButton voltarBtn = new JButton("Voltar ao Menu");
        painelForm.add(registrarBtn);
        painelForm.add(voltarBtn);

        resultadoArea = new JTextArea(3, 30);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Título");
        modelo.addColumn("Autor");
        modelo.addColumn("Categoria");
        modelo.addColumn("Disponibilidade");

        tabelaLivros = new JTable(modelo);
        JScrollPane scrollTabela = new JScrollPane(tabelaLivros);
        scrollTabela.setPreferredSize(new Dimension(750, 150));

        carregarLivros();

        tabelaLivros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = tabelaLivros.getSelectedRow();
                if (linhaSelecionada != -1) {
                    String idLivroSelecionado = modelo.getValueAt(linhaSelecionada, 0).toString();
                    idLivroField.setText(idLivroSelecionado);
                }
            }
        });

        registrarBtn.addActionListener(e -> registrarEmprestimo());
        voltarBtn.addActionListener(e -> {
            dispose();
            new MenuMembros(); // Alterado para abrir MenuMembros
        });

        add(painelForm, BorderLayout.NORTH);
        add(scrollTabela, BorderLayout.CENTER);
        add(new JScrollPane(resultadoArea), BorderLayout.SOUTH);

        setVisible(true);
    }

    private void carregarLivros() {
        try {
            modelo.setRowCount(0);
            ArrayList<Livros> lista = new Livros3Dao().listarTodos();
            for (Livros l : lista) {
                modelo.addRow(new Object[]{
                        l.getId(), l.getTitulo(), l.getAutor(), l.getCategoria(), l.getDisponibilidade()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar livros: " + ex.getMessage());
        }
    }

    private void registrarEmprestimo() {
        try {
            if (idLivroField.getText().isEmpty() || idMembroField.getText().isEmpty()) {
                resultadoArea.setText("Preencha todos os campos.");
                return;
            }

            int selectedRow = tabelaLivros.getSelectedRow();
            if (selectedRow == -1) {
                resultadoArea.setText("Selecione um livro disponível.");
                return;
            }

            String disponibilidade = modelo.getValueAt(selectedRow, 4).toString();
            if (!disponibilidade.equalsIgnoreCase("Disponível")) {
                resultadoArea.setText("O livro selecionado não está disponível.");
                return;
            }

            int idLivro = Integer.parseInt(idLivroField.getText().trim());
            int idMembro = Integer.parseInt(idMembroField.getText().trim());
            String dataEmprestimo = dataEmprestimoField.getText().trim();
            String dataDevolucao = dataDevolucaoField.getText().trim();

            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setIdLivro(idLivro);
            emprestimo.setIdMembro(idMembro);
            emprestimo.setDataEmprestimo(dataEmprestimo);
            emprestimo.setDataDevolucao(dataDevolucao);

            new EmprestimoDao().registrarEmprestimo(emprestimo);

            resultadoArea.setText("Empréstimo registrado com sucesso!");
            idLivroField.setText("");
            idMembroField.setText("");
            dataEmprestimoField.setText("");
            dataDevolucaoField.setText("");

            carregarLivros(); // Atualiza a tabela após registrar
        } catch (NumberFormatException ex) {
            resultadoArea.setText("ID inválido. Digite números válidos.");
        } catch (Exception ex) {
            ex.printStackTrace();
            resultadoArea.setText("Erro ao registrar empréstimo: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfaceRegistrarEmprestimoMembro::new);
    }
}
