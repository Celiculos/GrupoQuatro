package dao;

import entidades.Usuario;
import entidades.Venda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Conexao;

/**
 * DAO da Venda.
 *
 * @author Celiculos
 */
public final class VendaDao implements DataAccessObject {

    private final Venda venda;


    public VendaDao(final Venda venda) {
        this.venda = venda;
    }

    @Override
    public void insere() {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement(
                "INSERT INTO venda (idVenda, dataHora, idMatricula) VALUES (?, ?::TIMESTAMP, ?)"
            );
            comando.setLong  (1, this.venda.getNumero());
            comando.setString(2, this.venda.getDataHora());
            comando.setLong  (3, this.venda.getUsuario().getMatricula());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao inserir a venda: " + exception.getMessage());
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
                "UPDATE venda SET dataHora = ?, idMatricula = ? WHERE idVenda = ?"
            );
            comando.setString(1, this.venda.getDataHora());
            comando.setLong  (2, this.venda.getUsuario().getMatricula());
            comando.setLong  (2, this.venda.getNumero());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao atualizar a venda: " + exception.getMessage());
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
                "DELETE FROM venda WHERE idVenda = ?"
            );
            comando.setLong(1, this.venda.getNumero());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao deletar a venda: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public Venda get(long id) {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement("SELECT * FROM venda WHERE idVenda = ?");
            comando.setLong(1, id);
            ResultSet resultado = comando.executeQuery();
            resultado.next();
            return new Venda(
              resultado.getLong("idVenda"),
              resultado.getString("dataHora"),
              (new UsuarioDao(new Usuario(0, null, null))).get(resultado.getLong("idMatricula"))
            );
        } catch (SQLException e) {
            return null;
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public java.util.List<Venda> getTodos() {
        Conexao cnx = new Conexao();
        Statement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().createStatement();
            ResultSet resultado = comando.executeQuery("SELECT * FROM venda");
            java.util.ArrayList<Venda> vendas = new java.util.ArrayList<>();
            while(resultado.next()) {
                vendas.add(new Venda(
                    resultado.getLong("idVenda"),
                    resultado.getString("dataHora"),
                    (new UsuarioDao(new Usuario(0, null, null))).get(resultado.getLong("idMatricula"))
                ));
            }
            return vendas;
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar as vendas: " + exception.getMessage());
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
            ResultSet resultado = comando.executeQuery("SELECT COALESCE(MAX(idVenda), 0) + 1 AS proximo FROM venda");
            resultado.next();
            return resultado.getLong("proximo");
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar o próximo código de venda: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

}