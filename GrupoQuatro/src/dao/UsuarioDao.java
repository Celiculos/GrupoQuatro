package dao;

import entidades.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Conexao;

/**
 * DAO do Usuario.
 *
 * @author Celiculos
 */
public final class UsuarioDao implements DataAccessObject {

    private final Usuario usuario;


    public UsuarioDao(final Usuario Usuario) {
        this.usuario = Usuario;
    }

    @Override
    public void insere() {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement(
                "INSERT INTO usuario (idMatricula, funcao, nome) VALUES (?, ?, ?)"
            );
            comando.setLong  (1, this.usuario.getMatricula());
            comando.setString(2, this.usuario.getFuncao());
            comando.setString(3, this.usuario.getNome());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao inserir o usuário: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public void atualiza() {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement(
                "UPDATE usuario SET funcao = ?, nome = ? WHERE idMatricula = ?"
            );
            comando.setString(1, this.usuario.getFuncao());
            comando.setString(2, this.usuario.getNome());
            comando.setLong  (3, this.usuario.getMatricula());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao atualizar o usuário: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public void deleta() {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement(
                "DELETE FROM usuario WHERE idMatricula= ?"
            );
            comando.setLong(1, this.usuario.getMatricula());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao deletar o usuário: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public Usuario get(long id) {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement("SELECT * FROM usuario WHERE idMatricula = ?");
            comando.setLong(1, id);
            ResultSet resultado = comando.executeQuery();
            resultado.next();
            return new Usuario(resultado.getLong("idMatricula"), resultado.getString("funcao"), resultado.getString("nome"));
        } catch (SQLException e) {
            return null;
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public java.util.List<Usuario> getTodos() {
        Conexao cnx = new Conexao();
        Statement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().createStatement();
            ResultSet resultado = comando.executeQuery("SELECT * FROM usuario");
            java.util.ArrayList<Usuario> usuarios = new java.util.ArrayList<>();
            while(resultado.next()) {
                usuarios.add(new Usuario(
                    resultado.getLong("idMatricula"),
                    resultado.getString("funcao"),
                    resultado.getString("nome")
                ));
            }
            return usuarios;
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar os usuários: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public long proximoCodigo() {
        Conexao cnx = new Conexao();
        Statement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().createStatement();
            ResultSet resultado = comando.executeQuery("SELECT COALESCE(MAX(idMatricula), 0) + 1 AS proximo FROM usuario");
            resultado.next();
            return resultado.getLong("proximo");
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar o próximo código de usuário: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

}