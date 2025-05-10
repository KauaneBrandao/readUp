package DAO;

import Connection.ConnectionFactory;
import Model.GeneroLiv;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GeneroLivDAO {
    private final Connection conexao;

    public GeneroLivDAO() {
        ConnectionFactory factory = new ConnectionFactory();
        this.conexao = factory.obtemConexao();
    }

    // Retorna uma lista com todos os objetos GeneroLiv
    public ArrayList<GeneroLiv> getTodosGeneros() {
        ArrayList<GeneroLiv> lista = new ArrayList<>();
        String sql = "SELECT id_GeneroLiv, nome_GeneroLiv FROM tb_generoliv";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                GeneroLiv genero = new GeneroLiv();
                genero.setIdGeneroLiv(rs.getInt("id_GeneroLiv"));
                genero.setNomeGeneroLiv(rs.getString("nome_GeneroLiv"));
                lista.add(genero);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar gêneros: " + e.getMessage());
        }
        return lista;
    }

    // Retorna o ID do gênero a partir do nome
    public int getIdByNome(String nomeGenero) {
        String query = "SELECT id_GeneroLiv FROM tb_generoliv WHERE nome_GeneroLiv = ?";
        int idGenero = -1;

        try (PreparedStatement stmt = conexao.prepareStatement(query)) {
            stmt.setString(1, nomeGenero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idGenero = rs.getInt("id_GeneroLiv");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ID do gênero: " + e.getMessage());
        }

        return idGenero;
    }
}
