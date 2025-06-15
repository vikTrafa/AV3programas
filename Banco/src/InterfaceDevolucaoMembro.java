import DAO.EmprestimoDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class InterfaceDevolucaoMembro extends JFrame {
    private JTextField idMembroField;
    private JComboBox<String> emprestimosComboBox;
    private JTextArea resultadoArea;

    public InterfaceDevolucaoMembro() {
        setTitle("Devolução de Livro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        idMembroField = new JTextField();
        emprestimosComboBox = new JComboBox<>();
        JButton buscarButton = new JButton("Buscar Livros");
        JButton devolverButton = new JButton("Devolver Livro");
        JButton voltarButton = new JButton("Voltar ao Menu");

        formPanel.add(new JLabel("ID do Membro:"));
        formPanel.add(idMembroField);
        formPanel.add(buscarButton);
        formPanel.add(emprestimosComboBox);
        formPanel.add(devolverButton);
        formPanel.add(voltarButton);

        resultadoArea = new JTextArea(4, 30);
        resultadoArea.setEditable(false);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        buscarButton.addActionListener((ActionEvent e) -> buscarLivrosEmprestados());
        devolverButton.addActionListener((ActionEvent e) -> devolverLivro());
        voltarButton.addActionListener(e -> {
            dispose();
            new MenuMembros(); // Alterado para abrir MenuMembros
        });

        setVisible(true);
    }

    private void buscarLivrosEmprestados() {
        emprestimosComboBox.removeAllItems();
        String idTexto = idMembroField.getText().trim();

        if (idTexto.isEmpty()) {
            resultadoArea.setText("Informe o ID do membro.");
            return;
        }

        try {
            int idMembro = Integer.parseInt(idTexto);
            List<String> emprestimos = new EmprestimoDao().listarEmprestimosPorMembro(idMembro);

            if (emprestimos.isEmpty()) {
                resultadoArea.setText("Nenhum livro emprestado encontrado para este membro.");
            } else {
                for (String item : emprestimos) {
                    emprestimosComboBox.addItem(item);
                }
                resultadoArea.setText("Livros carregados.");
            }
        } catch (NumberFormatException e) {
            resultadoArea.setText("ID inválido.");
        }
    }

    private void devolverLivro() {
        String itemSelecionado = (String) emprestimosComboBox.getSelectedItem();
        if (itemSelecionado == null) {
            resultadoArea.setText("Nenhum empréstimo selecionado.");
            return;
        }

        int idEmprestimo = Integer.parseInt(itemSelecionado.split(" - ")[0]);

        try {
            new EmprestimoDao().devolverLivro(idEmprestimo);
            resultadoArea.setText("Livro devolvido com sucesso.");
            emprestimosComboBox.removeItem(itemSelecionado); // remove da lista
        } catch (Exception e) {
            resultadoArea.setText("Erro ao devolver: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfaceDevolucaoMembro());
    }
}
