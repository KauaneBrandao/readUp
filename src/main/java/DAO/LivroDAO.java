package DAO;

import Connection.ConnectionFactory;
import Model.Livro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LivroDAO {
    

    private final Connection conexao;

    public LivroDAO() {
        ConnectionFactory factory = new ConnectionFactory();
        this.conexao = factory.obtemConexao();
    }
    
    

   public boolean cadastrarLivro(Livro livro) {
    String sql = "INSERT INTO tb_livro (nome_Livro, autor_Livro, dsec_Livro, id_GeneroLiv, dataCriacao_Livro, curtida, imagemCapa) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setString(1, livro.getNomeLivro());
        stmt.setString(2, livro.getAutorLivro());
        stmt.setString(3, livro.getDsecLivro());
        stmt.setInt(4, livro.getIdGeneroLiv());
        
        // Garantir que a data seja inserida
        Date dataCriacao = livro.getDataCriacaoLivro();
        if (dataCriacao == null) {
            dataCriacao = new Date(); // usa a data atual se não tiver sido definida
        }
        stmt.setDate(5, new java.sql.Date(dataCriacao.getTime()));

        // Curtida (pode ser null)
        if (livro.getCurtida() != 0) {
            stmt.setInt(6, livro.getCurtida());
        } else {
            stmt.setNull(6, java.sql.Types.INTEGER);
        }

        // Imagem (pode ser null)
        if (livro.getImagemCapa() != null) {
            stmt.setString(7, livro.getImagemCapa());
        } else {
            stmt.setNull(7, java.sql.Types.VARCHAR);
        }

        stmt.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}



    public Livro buscarLivroPorID(int id) {
        Livro livro = null;
        String sql = "SELECT * FROM tb_livro WHERE id_Livro = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                livro = new Livro();
                livro.setIdLivro(rs.getInt("id_Livro"));
                livro.setNomeLivro(rs.getString("nome_Livro"));
                livro.setAutorLivro(rs.getString("autor_Livro"));
                livro.setDsecLivro(rs.getString("dsec_Livro"));
                livro.setIdGeneroLiv(rs.getInt("id_GeneroLiv"));
                livro.setDataCriacaoLivro(rs.getDate("dataCriacao_Livro"));
                livro.setCurtida(rs.getInt("curtida"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livro;
    }

    public void atualizarLivro(Livro livro) {
       String sql = "INSERT INTO tb_livro (nome_Livro, autor_Livro, dsec_Livro, id_GeneroLiv, dataCriacao_Livro, curtida, imagemCapa) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, livro.getNomeLivro());
            stmt.setString(2, livro.getAutorLivro());
            stmt.setString(3, livro.getDsecLivro());
            stmt.setInt(4, livro.getIdGeneroLiv());
            stmt.setDate(6, new java.sql.Date(livro.getDataCriacaoLivro().getTime()));
            stmt.setInt(7, livro.getCurtida());
            stmt.setInt(8, livro.getIdLivro());
            stmt.setString(8, livro.getImagemCapa());

            stmt.executeUpdate();
            System.out.println("Livro atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Livro> buscarLivrosPorNome(String nomeParcial) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT id_Livro, nome_Livro FROM tb_livro WHERE nome_Livro LIKE ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeParcial + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setIdLivro(rs.getInt("id_Livro"));
                livro.setNomeLivro(rs.getString("nome_Livro"));
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public List<Livro> consultarLivrosEmAlta() {
        List<Livro> livrosEmAlta = new ArrayList<>();
        String sql = "SELECT id_Livro, nome_Livro, dsec_Livro, curtida FROM tb_livro ORDER BY curtida DESC LIMIT 3";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setIdLivro(rs.getInt("id_Livro"));
                livro.setNomeLivro(rs.getString("nome_Livro"));
                livro.setDsecLivro(rs.getString("dsec_Livro"));
                livro.setCurtida(rs.getInt("curtida"));
                livrosEmAlta.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livrosEmAlta;
    }
    
    

    public void incrementarCurtida(int idLivro) {
        String sql = "UPDATE tb_livro SET curtida = curtida + 1 WHERE id_Livro = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idLivro);
            stmt.executeUpdate();
            System.out.println("Curtida incrementada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void inserirCapaLivro(Livro livro) {
    String sql = "INSERT INTO tb_livro (imagemCapa) VALUES (?)";

    try (PreparedStatement ps = conexao.prepareStatement(sql)) {
        ps.setString(1, livro.getImagemCapa());
        ps.execute();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}
