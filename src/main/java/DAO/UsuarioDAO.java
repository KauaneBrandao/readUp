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
        String sql = "UPDATE tb_Usuario SET telefone_Usuario = ?, email_Usuario = ?, senha_Usuario = ?, privilegio_Usuario = ?, idade_Usuario = ? WHERE login_Usuario = ?";

        System.out.println("=== DEBUG ATUALIZAÇÃO USUÁRIO ===");
        System.out.println("Login para busca: " + usuario.getLoginUsuario());
        System.out.println("Telefone: " + usuario.getTelefoneUsuario());
        System.out.println("Email: " + usuario.getEmailUsuario());
        System.out.println("Senha: " + usuario.getSenhaUsuario());
        System.out.println("Privilégio: " + usuario.getPrivilegioUsuario());
        System.out.println("Idade: " + usuario.getIdadeUsuario());

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getTelefoneUsuario());
            stmt.setString(2, usuario.getEmailUsuario());
            stmt.setString(3, usuario.getSenhaUsuario());
            stmt.setString(4, usuario.getPrivilegioUsuario());
            stmt.setInt(5, usuario.getIdadeUsuario());
            stmt.setString(6, usuario.getLoginUsuario());

            System.out.println("SQL a ser executado: " + sql);

            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Número de linhas afetadas: " + rowsUpdated);

            if (rowsUpdated > 0) {
                System.out.println("Atualização realizada com sucesso!");

                Usuario usuarioVerificacao = pesquisarUsuario(usuario.getLoginUsuario());
                if (usuarioVerificacao != null) {
                    System.out.println("=== DADOS APÓS ATUALIZAÇÃO ===");
                    System.out.println("Email no banco: " + usuarioVerificacao.getEmailUsuario());
                    System.out.println("Telefone no banco: " + usuarioVerificacao.getTelefoneUsuario());
                    System.out.println("Senha no banco: " + usuarioVerificacao.getSenhaUsuario());
                }

                return true;
            } else {
                System.err.println("ERRO: Nenhuma linha foi atualizada! Verifique se o login existe no banco.");

                Usuario usuarioExiste = pesquisarUsuario(usuario.getLoginUsuario());
                if (usuarioExiste == null) {
                    System.err.println("PROBLEMA: Usuário com login '" + usuario.getLoginUsuario() + "' não foi encontrado no banco!");
                } else {
                    System.out.println("Usuário existe no banco. Problema pode ser nos dados sendo atualizados.");
                }

                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erro SQL ao atualizar usuário: " + e.getMessage());
            e.printStackTrace();
            try {
                if (!conexao.getAutoCommit()) {
                    conexao.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean atualizarUsuarioComNovoLogin(String loginAntigo, Usuario usuarioAtualizado) {
        String sql = "UPDATE tb_Usuario SET login_Usuario = ?, telefone_Usuario = ?, email_Usuario = ?, senha_Usuario = ?, privilegio_Usuario = ?, idade_Usuario = ? WHERE login_Usuario = ?";

        System.out.println("=== DEBUG ATUALIZAÇÃO COM NOVO LOGIN ===");
        System.out.println("Login antigo: " + loginAntigo);
        System.out.println("Novo login: " + usuarioAtualizado.getLoginUsuario());
        System.out.println("Telefone: " + usuarioAtualizado.getTelefoneUsuario());
        System.out.println("Email: " + usuarioAtualizado.getEmailUsuario());

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuarioAtualizado.getLoginUsuario());
            stmt.setString(2, usuarioAtualizado.getTelefoneUsuario());
            stmt.setString(3, usuarioAtualizado.getEmailUsuario());
            stmt.setString(4, usuarioAtualizado.getSenhaUsuario());
            stmt.setString(5, usuarioAtualizado.getPrivilegioUsuario());
            stmt.setInt(6, usuarioAtualizado.getIdadeUsuario());
            stmt.setString(7, loginAntigo);

            System.out.println("SQL a ser executado: " + sql);

            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Número de linhas afetadas: " + rowsUpdated);

            if (rowsUpdated > 0) {
                System.out.println("Atualização com novo login realizada com sucesso!");
                return true;
            } else {
                System.err.println("ERRO: Nenhuma linha foi atualizada!");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erro SQL ao atualizar usuário com novo login: " + e.getMessage());

            // Verificar se o novo login já existe
            if (e.getMessage().contains("Duplicate entry") || e.getSQLState().equals("23000")) {
                System.err.println("ERRO: O novo login já existe no banco de dados!");
            }

            e.printStackTrace();
        }
        return false;
    }

    public boolean verificarLoginExiste(String login) {
        String sql = "SELECT COUNT(*) FROM tb_Usuario WHERE login_Usuario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar se login existe: " + e.getMessage());
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

    public boolean deletarUser(int id) {
        String sql = "DELETE FROM tb_Usuario WHERE id_Usuario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário deletado com sucesso!");
                return true;
            } else {
                System.err.println("Nenhum usuário encontrado com o id: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public Usuario pesquisarUsuarioPorId(int id) {
        String sql = "SELECT * FROM tb_Usuario WHERE id_Usuario = ?";
        Usuario usuario = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_Usuario"));
                usuario.setLoginUsuario(rs.getString("login_Usuario"));
                usuario.setTelefoneUsuario(rs.getString("telefone_Usuario"));
                usuario.setEmailUsuario(rs.getString("email_Usuario"));
                usuario.setPrivilegioUsuario(rs.getString("privilegio_Usuario"));
                usuario.setIdadeUsuario(rs.getInt("idade_Usuario"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }

        return usuario;
    }
    
    
}