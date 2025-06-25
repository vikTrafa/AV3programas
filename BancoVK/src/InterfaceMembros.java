import DAO.MembrosDao;
import entity.Membros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceMembros extends JFrame {
    private JTextField nomeField, cpfField, telefoneField, emailField;
    private JTextArea resultadoArea;

    public InterfaceMembros() {
        setTitle("Cadastro de Membros");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha só esta janela
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        nomeField = new JTextField();
        cpfField = new JTextField();
        telefoneField = new JTextField();
        emailField = new JTextField();

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("CPF:"));
        formPanel.add(cpfField);
        formPanel.add(new JLabel("Telefone:"));
        formPanel.add(telefoneField);
        formPanel.add(new JLabel("E-mail:"));
        formPanel.add(emailField);

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
                cadastrarMembro();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela atual
                new MenuPrincipal(); // Abre a tela de menu principal
            }
        });

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void cadastrarMembro() {
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().trim();
        String telefone = telefoneField.getText().trim();
        String email = emailField.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty()) {
            resultadoArea.setText("Por favor, preencha pelo menos o nome e o CPF.");
            return;
        }

        Membros m = new Membros();
        m.setNome(nome);
        m.setCpf(cpf);
        m.setTelefone(telefone);
        m.setEmail(email);

        try {
            new MembrosDao().CadastrarMembros(m);
            resultadoArea.setText("Membro cadastrado com sucesso:\n" +
                    "Nome: " + nome + "\nCPF: " + cpf);
            limparCampos();
        } catch (Exception ex) {
            resultadoArea.setText("Erro ao cadastrar: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        cpfField.setText("");
        telefoneField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfaceMembros());
    }
}
