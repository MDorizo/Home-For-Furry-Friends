package veterinaria.view;

import veterinaria.dao.ExameDAO;
import veterinaria.model.Exame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaExame extends JFrame {

    private JTextField txtId, txtNome, txtValor;
    private JTextArea txtDescricao;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private ExameDAO exameDAO;

    public TelaExame() {

        exameDAO = new ExameDAO();

        // CORES
        Color verdeEscuro = Color.decode("#245B52");
        Color verde = Color.decode("#3D8B7D");
        Color fundo = Color.decode("#F3F5F4");
        Color branco = Color.WHITE;
        Color vermelho = Color.decode("#D9534F");

        setTitle("Home for Furry Friends - Cadastro de Exames");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(fundo);
        setContentPane(painelPrincipal);


        // ================= HEADER =================

        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(verdeEscuro);
        painelHeader.setBorder(new EmptyBorder(12, 10, 12, 10));

        JLabel lblTitulo = new JLabel("Cadastro de Exames");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 21));

        painelHeader.add(lblTitulo);

        painelPrincipal.add(painelHeader, BorderLayout.NORTH);


        // ================= LADO ESQUERDO =================

        JPanel painelEsquerda = new JPanel(new BorderLayout());
        painelEsquerda.setBackground(fundo);
        painelEsquerda.setPreferredSize(new Dimension(380, 400));
        painelEsquerda.setBorder(new EmptyBorder(25, 30, 20, 20));


        // ================= FORMULÁRIO =================

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(branco);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#D5DDDA")),
                new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 6, 8, 6);
        gbc.anchor = GridBagConstraints.WEST;


        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblId = new JLabel("ID");
        lblId.setFont(new Font("Arial", Font.BOLD, 13));
        painelForm.add(lblId, gbc);

        txtId = new JTextField(16);
        txtId.setEditable(false);
        txtId.setPreferredSize(new Dimension(180, 30));

        gbc.gridx = 1;
        painelForm.add(txtId, gbc);


        // NOME
        gbc.gridx = 0;
        gbc.gridy = 1;

        JLabel lblNome = new JLabel("Nome *");
        lblNome.setFont(new Font("Arial", Font.BOLD, 13));
        painelForm.add(lblNome, gbc);

        txtNome = new JTextField(16);
        txtNome.setPreferredSize(new Dimension(180, 30));

        gbc.gridx = 1;
        painelForm.add(txtNome, gbc);


        // DESCRIÇÃO
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JLabel lblDescricao = new JLabel("Descrição");
        lblDescricao.setFont(new Font("Arial", Font.BOLD, 13));
        painelForm.add(lblDescricao, gbc);

        txtDescricao = new JTextArea(4, 16);

        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);

        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);

        scrollDescricao.setPreferredSize(new Dimension(185, 80));

        gbc.gridx = 1;
        painelForm.add(scrollDescricao, gbc);


        // VALOR
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblValor = new JLabel("Valor (R$) *");
        lblValor.setFont(new Font("Arial", Font.BOLD, 13));
        painelForm.add(lblValor, gbc);

        txtValor = new JTextField("0.00", 10);
        txtValor.setPreferredSize(new Dimension(110, 30));

        gbc.gridx = 1;
        painelForm.add(txtValor, gbc);


        painelEsquerda.add(painelForm, BorderLayout.CENTER);


        // ================= BOTÕES =================

        JButton btnSalvar = new JButton("Salvar");

        btnSalvar.setBackground(verde);
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 13));
        btnSalvar.setPreferredSize(new Dimension(105, 36));


        JButton btnExcluir = new JButton("Excluir");

        btnExcluir.setBackground(vermelho);
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 13));
        btnExcluir.setPreferredSize(new Dimension(105, 36));


        JPanel painelBotoes = new JPanel(new FlowLayout(
                FlowLayout.CENTER,
                12,
                15
        ));

        painelBotoes.setBackground(fundo);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);

        painelEsquerda.add(painelBotoes, BorderLayout.SOUTH);

        painelPrincipal.add(painelEsquerda, BorderLayout.WEST);


        // ================= TABELA =================

        JPanel painelTabela = new JPanel(new BorderLayout());

        painelTabela.setBackground(fundo);

        painelTabela.setBorder(
                new EmptyBorder(25, 0, 25, 25)
        );


        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Valor (R$)"},
                0
        ) {

            // Impede editar dados diretamente na tabela
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        tabela = new JTable(tableModel);

        tabela.setRowHeight(32);

        tabela.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        tabela.setGridColor(
                Color.decode("#E1E7E5")
        );

        tabela.setSelectionBackground(
                Color.decode("#CFE5DF")
        );

        tabela.setSelectionForeground(
                Color.BLACK
        );

        tabela.setShowVerticalLines(false);

        tabela.getTableHeader().setBackground(verdeEscuro);
        tabela.getTableHeader().setForeground(Color.WHITE);

        tabela.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        tabela.getTableHeader().setPreferredSize(
                new Dimension(0, 34)
        );


        // Centraliza ID e Valor
        DefaultTableCellRenderer centro =
                new DefaultTableCellRenderer();

        centro.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        tabela.getColumnModel()
                .getColumn(0)
                .setCellRenderer(centro);

        tabela.getColumnModel()
                .getColumn(2)
                .setCellRenderer(centro);


        // largura das colunas
        tabela.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(50);

        tabela.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(180);

        tabela.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(100);


        JScrollPane scrollTabela =
                new JScrollPane(tabela);

        scrollTabela.setBorder(
                BorderFactory.createLineBorder(
                        Color.decode("#D5DDDA")
                )
        );

        painelTabela.add(
                scrollTabela,
                BorderLayout.CENTER
        );

        painelPrincipal.add(
                painelTabela,
                BorderLayout.CENTER
        );


        // ================= EVENTOS =================

        btnSalvar.addActionListener(
                e -> salvar()
        );

        btnExcluir.addActionListener(
                e -> excluir()
        );


        carregarTabela();
    }


    private void carregarTabela() {

        tableModel.setRowCount(0);

        for (Exame ex : exameDAO.listarTodos()) {

            tableModel.addRow(new Object[]{
                    ex.getId(),
                    ex.getNome(),
                    String.format("%.2f", ex.getValor())
            });

        }
    }


    private void salvar() {

        if (txtNome.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nome do exame é obrigatório!"
            );

            return;
        }

        try {

            double valor =
                    Double.parseDouble(
                            txtValor.getText()
                    );

            Exame ex =
                    new Exame(
                            txtNome.getText(),
                            txtDescricao.getText(),
                            valor
                    );

            if (exameDAO.cadastrar(ex)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Exame cadastrado!"
                );

                txtNome.setText("");
                txtDescricao.setText("");
                txtValor.setText("0.00");

                carregarTabela();
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe um valor numérico válido!"
            );

        }
    }


    private void excluir() {

        int linha = tabela.getSelectedRow();

        if (linha != -1) {

            int id =
                    (int) tableModel
                            .getValueAt(linha, 0);

            if (exameDAO.excluir(id)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Exame excluído!"
                );

                carregarTabela();
            }

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um exame na tabela para excluir."
            );

        }
    }
}
