import DAO.LivrosDao;
import entity.Livros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceLivros extends JFrame {
    private JTextField tituloField, autorField, categoriaField, disponibilidadeField;
    private JTextArea resultadoArea;

    public InterfaceLivros() {
        setTitle("Cadastro de Livros");
        setSize(400, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas esta janela
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10)); // +1 linha para o botão de voltar
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        tituloField = new JTextField();
        autorField = new JTextField();
        categoriaField = new JTextField();
        disponibilidadeField = new JTextField();

        formPanel.add(new JLabel("Título:"));
        formPanel.add(tituloField);
        formPanel.add(new JLabel("Autor:"));
        formPanel.add(autorField);
        formPanel.add(new JLabel("Categoria:"));
        formPanel.add(categoriaField);
        formPanel.add(new JLabel("Disponibilidade (1/0):"));
        formPanel.add(disponibilidadeField);

        JButton cadastrarButton = new JButton("Cadastrar");
        JButton voltarButton = new JButton("Voltar ao Menu");

        formPanel.add(cadastrarButton);
        formPanel.add(voltarButton);

        resultadoArea = new JTextArea(4, 30);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarLivro();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela atual
                new MenuPrincipal(); // Abre o menu principal
            }
        });

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void cadastrarLivro() {
        String titulo = tituloField.getText().trim();
        String autor = autorField.getText().trim();
        String categoria = categoriaField.getText().trim();
        String disponibilidade = disponibilidadeField.getText().trim();

        if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty() || disponibilidade.isEmpty()) {
            resultadoArea.setText("Preencha todos os campos.");
            return;
        }

        Livros livro = new Livros();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setCategoria(categoria);
        livro.setDisponibilidade(disponibilidade);

        try {
            new LivrosDao().CadastrarLivros(livro);
            resultadoArea.setText("Livro cadastrado com sucesso:\n" +
                    "Título: " + titulo +
                    "\nAutor: " + autor +
                    "\nCategoria: " + categoria +
                    "\nDisponibilidade: " + disponibilidade);
            limparCampos();
        } catch (Exception ex) {
            resultadoArea.setText("Erro ao cadastrar: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        tituloField.setText("");
        autorField.setText("");
        categoriaField.setText("");
        disponibilidadeField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfaceLivros());
    }
}
