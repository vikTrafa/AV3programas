import DAO.FuncionariosDao;
import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private JTextField loginField;
    private JPasswordField senhaField;
    private JTextArea resultadoArea;

    public TelaLogin() {
        setTitle("Login de Funcionário");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        panelCampos.add(new JLabel("Login:"));
        loginField = new JTextField();
        panelCampos.add(loginField);

        panelCampos.add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        panelCampos.add(senhaField);

        JButton entrarButton = new JButton("Entrar");
        panelCampos.add(entrarButton);

        JButton sairButton = new JButton("Sair");
        panelCampos.add(sairButton);

        resultadoArea = new JTextArea(3, 30);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);

        add(panelCampos, BorderLayout.CENTER);
        add(new JScrollPane(resultadoArea), BorderLayout.SOUTH);

        entrarButton.addActionListener(e -> realizarLogin());
       sairButton.addActionListener(e -> {
    dispose();
    new MenuEscolhaLogin(); // Volta para a tela de escolha
});


        setVisible(true);
    }

    private void realizarLogin() {
        String login = loginField.getText().trim();
        String senha = new String(senhaField.getPassword());

        if (login.isEmpty() || senha.isEmpty()) {
            resultadoArea.setText("Por favor, preencha todos os campos.");
            return;
        }

        boolean autenticado = new FuncionariosDao().verificarLogin(login, senha);

        if (autenticado) {
            resultadoArea.setText("Login realizado com sucesso!");
            dispose(); // fecha a tela de login
            new MenuPrincipal(); // abre o menu principal
        } else {
            resultadoArea.setText("Login ou senha incorretos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaLogin::new);
    }
}
