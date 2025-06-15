import DAO.MembrosDao;
import entity.Membros;

import javax.swing.*;
import java.awt.*;

public class TelaLoginMembro extends JFrame {

    private JTextField cpfField;
    private JTextArea resultadoArea;

    public TelaLoginMembro() {
        setTitle("Login de Membro");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        panelCampos.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        panelCampos.add(cpfField);

        JButton entrarButton = new JButton("Entrar");
        JButton cadastrarButton = new JButton("Cadastrar-se");
        JButton sairButton = new JButton("Sair");

        panelCampos.add(entrarButton);
        panelCampos.add(cadastrarButton);
        panelCampos.add(sairButton);

        resultadoArea = new JTextArea(3, 30);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);

        add(panelCampos, BorderLayout.CENTER);
        add(new JScrollPane(resultadoArea), BorderLayout.SOUTH);

        // Ações dos botões
        entrarButton.addActionListener(e -> realizarLogin());
        cadastrarButton.addActionListener(e -> {
            dispose();
            new InterfaceCadastroMembroSimples(); // Abre tela de cadastro
        });
        sairButton.addActionListener(e -> {
    dispose();
    new MenuEscolhaLogin(); // Volta para a tela de escolha
});


        setVisible(true);
    }

    private void realizarLogin() {
        String cpf = cpfField.getText().trim();

        if (cpf.isEmpty()) {
            resultadoArea.setText("Por favor, insira seu CPF.");
            return;
        }

        Membros membro = new MembrosDao().buscarMembroPorCpf(cpf);

        if (membro != null) {
            resultadoArea.setText("Bem-vindo, " + membro.getNome() + "!");
            dispose();
            new MenuMembros(); // Redireciona para o menu de membros
        } else {
            resultadoArea.setText("CPF não encontrado. Clique em 'Cadastrar-se' para criar uma conta.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaLoginMembro::new);
    }
}
