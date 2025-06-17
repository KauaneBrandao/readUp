package DAO;

import Connection.ConnectionFactory;
import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection conexao;

    public UsuarioDAO() {
        ConnectionFactory factory = new ConnectionFactory();
        this.conexao = factory.obtemConexao();
    }

    public void inserirUsuario(String loginUsuario, String senhaUsuario, String telefoneUsuario, String emailUsuario, String privilegioUsuario, int idadeUsuario) {
        String sql = "INSERT INTO tb_Usuario(login_Usuario, senha_Usuario, telefone_Usuario, email_Usuario, privilegio_Usuario, idade_Usuario) VALUES(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, loginUsuario);
            ps.setString(2, senhaUsuario);
            ps.setString(3, telefoneUsuario);
            ps.setString(4, emailUsuario);
            ps.setString(5, privilegioUsuario);
            ps.setInt(6, idadeUsuario);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario verificarLogin(String loginUsuario, String senhaUsuario) {
        String sql = "SELECT * FROM tb_Usuario WHERE login_Usuario = ? AND senha_Usuario = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, loginUsuario);
            ps.setString(2, senhaUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_Usuario"),
                        rs.getString("login_Usuario"),
                        rs.getString("senha_Usuario"),
                        rs.getString("telefone_Usuario"),
                        rs.getString("email_Usuario"),
                        rs.getString("privilegio_Usuario"),
                        rs.getInt("idade_Usuario")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Usuario pesquisarUsuario(String login) {
    String sql = "SELECT * FROM tb_Usuario WHERE login_Usuario = ?";
    Usuario usuario = null;

    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setString(1, login);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("id_Usuario"));
            usuario.setLoginUsuario(rs.getString("login_Usuario"));
            usuario.setSenhaUsuario(rs.getString("senha_Usuario"));
            usuario.setTelefoneUsuario(rs.getString("telefone_Usuario"));
            usuario.setEmailUsuario(rs.getString("email_Usuario"));
            usuario.setPrivilegioUsuario(rs.getString("privilegio_Usuario"));
            usuario.setIdadeUsuario(rs.getInt("idade_Usuario"));
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar usuário: " + e.getMessage());
    }

    return usuario;
}

    public boolean atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE tb_Usuario SET telefone_Usuario = ?, email_Usuario = ?, senha_Usuario = ?, privilegio_Usuario = ? WHERE login_Usuario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getTelefoneUsuario());
            stmt.setString(2, usuario.getEmailUsuario());
            stmt.setString(3, usuario.getSenhaUsuario());
            stmt.setString(4, usuario.getPrivilegioUsuario());
            stmt.setString(5, usuario.getLoginUsuario());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Usuário atualizado com sucesso!");
                return true;
            } else {
                System.err.println("Nenhuma linha foi atualizada. Verifique se o login_Usuario está correto.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

   
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM tb_Usuario";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_Usuario"));
                usuario.setLoginUsuario(rs.getString("login_Usuario"));
                usuario.setSenhaUsuario(rs.getString("senha_Usuario"));
                usuario.setTelefoneUsuario(rs.getString("telefone_Usuario"));
                usuario.setEmailUsuario(rs.getString("email_Usuario"));
                usuario.setPrivilegioUsuario(rs.getString("privilegio_Usuario"));
                usuario.setIdadeUsuario(rs.getInt("idade_Usuario"));

                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

}
