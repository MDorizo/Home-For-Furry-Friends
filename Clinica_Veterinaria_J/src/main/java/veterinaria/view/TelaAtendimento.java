package veterinaria.view;

import veterinaria.dao.AtendimentoDAO;
import veterinaria.dao.PetDAO;
import veterinaria.dao.VeterinarioDAO;
import veterinaria.model.Atendimento;
import veterinaria.model.Pet;
import veterinaria.model.Veterinario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;

public class TelaAtendimento extends JFrame {

    private JTextField txtId, txtDescricao, txtDiagnostico, txtValor;
    private JComboBox<Pet> cbPets;
    private JComboBox<Veterinario> cbVets;
    private JTable tabela;
    private DefaultTableModel tableModel;

    private AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
    private PetDAO petDAO = new PetDAO();
    private VeterinarioDAO veterinarioDAO = new VeterinarioDAO();

    public TelaAtendimento() {
        setTitle("Home for Furry Friends - Cadastro de Atendimentos");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel header = new JPanel();
        header.setBackground(Color.decode("#2E7D6B"));

        JLabel titulo = new JLabel("Home for Furry Friends - Cadastro de Atendimentos");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        header.add(titulo);
        add(header, BorderLayout.NORTH);

        // Formulário
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        form.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        form.add(txtId);

        form.add(new JLabel("Pet*:"));
        cbPets = new JComboBox<>();
        carregarPets();
        form.add(cbPets);

        form.add(new JLabel("Veterinário*:"));
        cbVets = new JComboBox<>();
        carregarVeterinarios();
        form.add(cbVets);

        form.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        form.add(txtDescricao);

        form.add(new JLabel("Diagnóstico / Procedimentos:"));
        txtDiagnostico = new JTextField();
        form.add(txtDiagnostico);

        form.add(new JLabel("Valor (R$)*:"));
        txtValor = new JTextField();
        form.add(txtValor);

        // Botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(Color.decode("#43A047"));
        btnSalvar.setForeground(Color.WHITE);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBackground(Color.decode("#5BB8C5"));
        btnAtualizar.setForeground(Color.WHITE);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBackground(Color.decode("#D9534F"));
        btnExcluir.setForeground(Color.WHITE);

        JButton btnLimpar = new JButton("Limpar");

        botoes.add(btnSalvar);
        botoes.add(btnAtualizar);
        botoes.add(btnExcluir);
        botoes.add(btnLimpar);

        JPanel esquerda = new JPanel(new BorderLayout());
        esquerda.add(form, BorderLayout.NORTH);
        esquerda.add(botoes, BorderLayout.SOUTH);

        add(esquerda, BorderLayout.WEST);

        // Tabela
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Pet ID", "Vet ID", "Data", "Diagnóstico", "Valor"}, 0
        );

        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Eventos
        btnSalvar.addActionListener(e -> salvarAtendimento());
        btnAtualizar.addActionListener(e -> atualizarAtendimento());
        btnExcluir.addActionListener(e -> excluirAtendimento());
        btnLimpar.addActionListener(e -> limparCampos());

        tabela.getSelectionModel().addListSelectionListener(e -> selecionarLinha());

        carregarTabela();
    }

    private void carregarPets() {
        cbPets.removeAllItems();
        for (Pet p : petDAO.listarTodos()) cbPets.addItem(p);
    }

    private void carregarVeterinarios() {
        cbVets.removeAllItems();
        for (Veterinario v : veterinarioDAO.listarTodos()) cbVets.addItem(v);
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);

        for (Atendimento a : atendimentoDAO.listarTodos()) {
            tableModel.addRow(new Object[]{
                    a.getId(), a.getPetId(), a.getVeterinarioId(),
                    a.getDataAtendimento(), a.getDiagnostico(), a.getValor()
            });
        }
    }

    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) return;

        int id = Integer.parseInt(tableModel.getValueAt(linha, 0).toString());
        int petId = Integer.parseInt(tableModel.getValueAt(linha, 1).toString());
        int vetId = Integer.parseInt(tableModel.getValueAt(linha, 2).toString());

        txtId.setText(String.valueOf(id));

        for (int i = 0; i < cbPets.getItemCount(); i++)
            if (cbPets.getItemAt(i).getId() == petId) cbPets.setSelectedIndex(i);

        for (int i = 0; i < cbVets.getItemCount(); i++)
            if (cbVets.getItemAt(i).getId() == vetId) cbVets.setSelectedIndex(i);

        for (Atendimento a : atendimentoDAO.listarTodos()) {
            if (a.getId() == id) {
                txtDescricao.setText(a.getDescricao() == null ? "" : a.getDescricao());
                txtDiagnostico.setText(a.getDiagnostico() == null ? "" : a.getDiagnostico());
                txtValor.setText(String.valueOf(a.getValor()));
                break;
            }
        }
    }

    private void salvarAtendimento() {
        Pet pet = (Pet) cbPets.getSelectedItem();
        Veterinario vet = (Veterinario) cbVets.getSelectedItem();

        if (pet == null || vet == null || txtValor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!");
            return;
        }

        try {
            Atendimento a = new Atendimento(
                    pet.getId(), vet.getId(),
                    new Date(System.currentTimeMillis()),
                    new Time(System.currentTimeMillis()),
                    txtDescricao.getText(), txtDiagnostico.getText(),
                    Double.parseDouble(txtValor.getText())
            );

            if (atendimentoDAO.registrar(a)) {
                JOptionPane.showMessageDialog(this, "Atendimento cadastrado com sucesso!");
                limparCampos();
                carregarTabela();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Informe um valor válido!");
        }
    }

    private void atualizarAtendimento() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um atendimento para atualizar!");
            return;
        }

        Pet pet = (Pet) cbPets.getSelectedItem();
        Veterinario vet = (Veterinario) cbVets.getSelectedItem();

        try {
            Atendimento a = new Atendimento(
                    Integer.parseInt(txtId.getText()),
                    pet.getId(), vet.getId(), null, null,
                    txtDescricao.getText(), txtDiagnostico.getText(),
                    Double.parseDouble(txtValor.getText())
            );

            if (atendimentoDAO.atualizar(a)) {
                JOptionPane.showMessageDialog(this, "Atendimento atualizado com sucesso!");
                limparCampos();
                carregarTabela();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Verifique os dados informados!");
        }
    }

    private void excluirAtendimento() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um atendimento para excluir!");
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this, "Deseja realmente excluir este atendimento?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION &&
                atendimentoDAO.excluir(Integer.parseInt(txtId.getText()))) {

            JOptionPane.showMessageDialog(this, "Atendimento excluído!");
            limparCampos();
            carregarTabela();
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtDescricao.setText("");
        txtDiagnostico.setText("");
        txtValor.setText("");
        tabela.clearSelection();

        if (cbPets.getItemCount() > 0) cbPets.setSelectedIndex(0);
        if (cbVets.getItemCount() > 0) cbVets.setSelectedIndex(0);
    }
}