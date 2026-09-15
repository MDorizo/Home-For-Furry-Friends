package veterinaria.dao;

import veterinaria.Conexao;
import veterinaria.model.Atendimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtendimentoDAO {

    // CREATE - registra um novo atendimento no banco de dados.
    public boolean registrar(Atendimento atendimento) {
        String sql = "INSERT INTO atendimento (pet_id, veterinario_id, data_atendimento, hora_atendimento, descricao, diagnostico, valor) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, atendimento.getPetId());
            stmt.setInt(2, atendimento.getVeterinarioId());
            stmt.setDate(3, atendimento.getDataAtendimento());
            stmt.setTime(4, atendimento.getHoraAtendimento());
            stmt.setString(5, atendimento.getDescricao());
            stmt.setString(6, atendimento.getDiagnostico());
            stmt.setDouble(7, atendimento.getValor());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ — pega todos os atendimentos já registrados, do mais recente para o mais antigo.
    public List<Atendimento> listarTodos() {
        List<Atendimento> atendimentos = new ArrayList<>();
        String sql = "SELECT * FROM atendimento ORDER BY id DESC";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Atendimento a = new Atendimento(
                    rs.getInt("id"),
                    rs.getInt("pet_id"),
                    rs.getInt("veterinario_id"),
                    rs.getDate("data_atendimento"),
                    rs.getTime("hora_atendimento"),
                    rs.getString("descricao"),
                    rs.getString("diagnostico"),
                    rs.getDouble("valor")
                );
                atendimentos.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atendimentos;
    }

    // UPDATE - atualiza um atendimento existente
            public boolean atualizar(Atendimento atendimento){
                String sql = "UPDATE atendimento SET pet_id = ?, veterinario_id = ?, descricao = ?, diagnostico = ?, valor = ?,  WHERE id = ?";
                try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, atendimento.getPetId());
            stmt.setInt(2, atendimento.getVeterinarioId());
            stmt.setString(3, atendimento.getDescricao());
            stmt.setString(4, atendimento.getDiagnostico());
            stmt.setDouble(5, atendimento.getValor());
            stmt.setInt(6, atendimento.getId()); // define qual atendimento (WHERE id = ?) deve ser atualizado
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
            }

    // DELETE - exclui um atendimento existente.
    public boolean excluir(int id) {
        String sql = "DELETE FROM atendimento WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
