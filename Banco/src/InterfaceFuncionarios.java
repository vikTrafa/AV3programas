import DAO.FuncionariosDao;
import entity.Funcionarios;

import javax.swing.*;
import java.awt.*;

public class InterfaceFuncionarios extends JFrame {
    private JTextField nomeField, cargoField, loginField;
    private JPasswordField senhaField;
    private JTextArea resultadoArea;

    public InterfaceFuncionarios() {
        setTitle("Cadastro de Funcionários");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        nomeField = new JTextField();
        cargoField = new JTextField();
        loginField = new JTextField();
        senhaField = new JPasswordField();

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Cargo:"));
        formPanel.add(cargoField);
        formPanel.add(new JLabel("Login:"));
        formPanel.add(loginField);
        formPanel.add(new JLabel("Senha:"));
        formPanel.add(senhaField);

        JButton cadastrarButton = new JButton("Cadastrar");
        JButton voltarButton = new JButton("Voltar ao Menu");

        formPanel.add(cadastrarButton);
        formPanel.add(voltarButton);

        resultadoArea = new JTextArea(4, 30);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);

        cadastrarButton.addActionListener(e -> cadastrarFuncionario());
        voltarButton.addActionListener(e -> {
            dispose();
            new MenuPrincipal();
        });

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void cadastrarFuncionario() {
        String nome = nomeField.getText().trim();
        String cargo = cargoField.getText().trim();
        String login = loginField.getText().trim();
        String senha = new String(senhaField.getPassword()).trim();

        if (nome.isEmpty() || login.isEmpty() || senha.isEmpty()) {
            resultadoArea.setText("Preencha os campos obrigatórios: Nome, Login e Senha.");
            return;
        }

        Funcionarios f = new Funcionarios();
        f.setNome(nome);
        f.setCargo(cargo);
        f.setLogin(login);
        f.setSenha(senha);

        try {
            new FuncionariosDao().cadastrarFuncionario(f);
            resultadoArea.setText("Funcionário cadastrado com sucesso:\n" + nome + " - " + cargo);
            limparCampos();
        } catch (Exception ex) {
            resultadoArea.setText("Erro ao cadastrar funcionário: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        cargoField.setText("");
        loginField.setText("");
        senhaField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfaceFuncionarios::new);
    }
}
