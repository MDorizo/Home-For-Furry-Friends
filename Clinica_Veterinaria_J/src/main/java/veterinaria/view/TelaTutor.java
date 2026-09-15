package veterinaria.view;

import veterinaria.dao.TutorDAO;
import veterinaria.model.Tutor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaTutor extends JFrame {

    private JTextField txtId, txtNome, txtCpf, txtTelefone, txtEmail, txtEndereco;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private TutorDAO tutorDAO;

    // cores que eu vou usar na tela toda
    private final Color verdeEscuro = Color.decode("#245B52");
    private final Color verde = Color.decode("#3D8B7D");
    private final Color azul = Color.decode("#4F9DAC");
    private final Color fundo = Color.decode("#F3F5F4");
    private final Color branco = Color.WHITE;
    private final Color vermelho = Color.decode("#D9534F");
    private final Color cinza = Color.decode("#757575");
    private final Color borda = Color.decode("#D5DDDA");

    public TelaTutor() {

        // cria o DAO para conseguir mexer nos dados do banco
        tutorDAO = new TutorDAO();

        // configurações principais da janela
        setTitle("Home for Furry Friends - Cadastro de Tutor");
        setSize(1050, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // esse painel é tipo a base da tela inteira
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(fundo);
        setContentPane(painelPrincipal);

        // cabeçalho da tela
        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(verdeEscuro);
        painelHeader.setBorder(new EmptyBorder(12, 10, 12, 10));

        JLabel lblTitulo = new JLabel("Cadastro de Tutor");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 21));

        painelHeader.add(lblTitulo);
        painelPrincipal.add(painelHeader, BorderLayout.NORTH);

        // painel da esquerda, onde ficam os campos
        JPanel painelEsquerda = new JPanel(new BorderLayout());
        painelEsquerda.setBackground(fundo);
        painelEsquerda.setPreferredSize(new Dimension(430, 500));
        painelEsquerda.setBorder(new EmptyBorder(25, 30, 20, 20));

        // formulario
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(branco);

        // faz uma borda em volta do formulario
        painelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borda),
                new EmptyBorder(20, 22, 20, 22)
        ));

        // controla a posição dos componentes no GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelForm.add(criarLabel("ID"), gbc);

        txtId = criarCampo(18);

        // o ID vem do banco, então o usuario não precisa digitar
        txtId.setEditable(false);

        gbc.gridx = 1;
        painelForm.add(txtId, gbc);

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelForm.add(criarLabel("Nome *"), gbc);

        txtNome = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtNome, gbc);

        // CPF
        gbc.gridx = 0;
        gbc.gridy = 2;
        painelForm.add(criarLabel("CPF *"), gbc);

        txtCpf = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtCpf, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        painelForm.add(criarLabel("Telefone"), gbc);

        txtTelefone = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtTelefone, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 4;
        painelForm.add(criarLabel("E-mail"), gbc);

        txtEmail = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtEmail, gbc);

        // Endereço
        gbc.gridx = 0;
        gbc.gridy = 5;
        painelForm.add(criarLabel("Endereço"), gbc);

        txtEndereco = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtEndereco, gbc);

        painelEsquerda.add(painelForm, BorderLayout.CENTER);

        // painel dos botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 15));
        painelBotoes.setBackground(fundo);

        JButton btnSalvar = criarBotao("Salvar", verde);
        JButton btnEditar = criarBotao("Atualizar", azul);
        JButton btnExcluir = criarBotao("Excluir", vermelho);
        JButton btnLimpar = criarBotao("Limpar", cinza);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        painelEsquerda.add(painelBotoes, BorderLayout.SOUTH);
        painelPrincipal.add(painelEsquerda, BorderLayout.WEST);

        // parte da tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(fundo);
        painelTabela.setBorder(new EmptyBorder(25, 0, 25, 25));

        // define quais colunas a tabela vai ter
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "CPF", "Telefone", "E-mail", "Endereço"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // false para não conseguir editar direto na tabela
                return false;
            }
        };

        tabela = new JTable(tableModel);

        // estilo da tabela
        tabela.setRowHeight(31);
        tabela.setFont(new Font("Arial", Font.PLAIN, 12));
        tabela.setGridColor(Color.decode("#E1E7E5"));
        tabela.setShowVerticalLines(false);
        tabela.setShowHorizontalLines(true);

        // cor quando seleciona uma linha
        tabela.setSelectionBackground(Color.decode("#CFE5DF"));
        tabela.setSelectionForeground(Color.BLACK);

        // deixa selecionar só uma linha de cada vez
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // estilo do cabeçalho da tabela
        tabela.getTableHeader().setBackground(verdeEscuro);
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabela.getTableHeader().setPreferredSize(new Dimension(0, 35));
        tabela.getTableHeader().setReorderingAllowed(false);

        // centraliza só a coluna do ID
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        tabela.getColumnModel().getColumn(0).setCellRenderer(centro);

        // tamanho das colunas
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(160);

        // coloca a tabela dentro de um scroll
        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBorder(BorderFactory.createLineBorder(borda));
        scrollTabela.getViewport().setBackground(Color.WHITE);

        painelTabela.add(scrollTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        // eventos dos botões
        btnSalvar.addActionListener(e -> salvarTutor());
        btnEditar.addActionListener(e -> atualizarTutor());
        btnExcluir.addActionListener(e -> excluirTutor());
        btnLimpar.addActionListener(e -> limparCampos());

        // quando clicar em uma linha, os dados vão pros campos
        tabela.getSelectionModel().addListSelectionListener(e -> selecionarLinha());

        carregarTabela();
    }

    // cria os labels já com o mesmo estilo
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        return label;
    }

    // cria os campos de texto no mesmo padrão
    private JTextField criarCampo(int colunas) {
        JTextField campo = new JTextField(colunas);
        campo.setPreferredSize(new Dimension(190, 30));
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        return campo;
    }

    // cria os botões já com tamanho, fonte e cor
    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setPreferredSize(new Dimension(90, 36));
        return botao;
    }

    // limpa a tabela e carrega os tutores que estão no banco
    private void carregarTabela() {
        tableModel.setRowCount(0);

        for (Tutor t : tutorDAO.listarTodos()) {
            tableModel.addRow(new Object[]{
                    t.getId(),
                    t.getNome(),
                    t.getCpf(),
                    t.getTelefone(),
                    t.getEmail(),
                    t.getEndereco()
            });
        }
    }

    // salva um novo tutor
    private void salvarTutor() {

        // nome e cpf são obrigatorios
        if (txtNome.getText().trim().isEmpty() || txtCpf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios (Nome e CPF)!");
            return;
        }

        // cria o objeto com os dados que foram digitados
        Tutor t = new Tutor(
                txtNome.getText(),
                txtCpf.getText(),
                txtTelefone.getText(),
                txtEmail.getText(),
                txtEndereco.getText()
        );

        // tenta cadastrar no banco
        if (tutorDAO.cadastrar(t)) {
            JOptionPane.showMessageDialog(this, "Tutor cadastrado com sucesso!");
            limparCampos();
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar tutor.");
        }
    }

    // atualiza um tutor que ja existe
    private void atualizarTutor() {

        // se não tiver ID é porque nenhuma linha foi selecionada
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um tutor na tabela para atualizar!");
            return;
        }

        if (txtNome.getText().trim().isEmpty() || txtCpf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios!");
            return;
        }

        Tutor t = new Tutor(
                Integer.parseInt(txtId.getText()),
                txtNome.getText(),
                txtCpf.getText(),
                txtTelefone.getText(),
                txtEmail.getText(),
                txtEndereco.getText()
        );

        if (tutorDAO.atualizar(t)) {
            JOptionPane.showMessageDialog(this, "Tutor atualizado com sucesso!");
            limparCampos();
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar tutor.");
        }
    }

    // exclui o tutor selecionado
    private void excluirTutor() {

        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um tutor na tabela para excluir!");
            return;
        }

        // confirmação para não excluir sem querer
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este tutor?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());

            if (tutorDAO.excluir(id)) {
                JOptionPane.showMessageDialog(this, "Tutor excluído com sucesso!");
                limparCampos();
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir tutor.");
            }
        }
    }

    // pega os dados da linha selecionada e joga nos campos
    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();

        if (linha != -1) {
            txtId.setText(tableModel.getValueAt(linha, 0).toString());
            txtNome.setText(tableModel.getValueAt(linha, 1).toString());
            txtCpf.setText(tableModel.getValueAt(linha, 2).toString());

            // esses campos podem vir null do banco, então faz essa verificação
            txtTelefone.setText(
                    tableModel.getValueAt(linha, 3) != null
                            ? tableModel.getValueAt(linha, 3).toString()
                            : ""
            );

            txtEmail.setText(
                    tableModel.getValueAt(linha, 4) != null
                            ? tableModel.getValueAt(linha, 4).toString()
                            : ""
            );

            txtEndereco.setText(
                    tableModel.getValueAt(linha, 5) != null
                            ? tableModel.getValueAt(linha, 5).toString()
                            : ""
            );
        }
    }

    // limpa tudo depois de salvar, atualizar ou excluir
    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        tabela.clearSelection();
    }

    // serve para testar essa tela sozinha
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaTutor().setVisible(true));
    }
}
