package dao;

import entidades.Compra;
import entidades.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Conexao;

/**
 * DAO da Compra.
 *
 * @author Celiculos
 */
public final class CompraDao implements DataAccessObject {

    private final Compra compra;


    public CompraDao(final Compra compra) {
        this.compra = compra;
    }

    @Override
    public void insere() {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement(
                "INSERT INTO compra (idCompra, dataHora, idMatricula, fornecedor) VALUES (?, ?::TIMESTAMP, ?, ?)"
            );
            comando.setLong  (1, this.compra.getNumero());
            comando.setString(2, this.compra.getDataHora());
            comando.setLong  (3, this.compra.getUsuario().getMatricula());
            comando.setString(4, this.compra.getFornecedor());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao inserir a compra: " + exception.getMessage());
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
                "UPDATE compra SET dataHora = ?, idMatricula = ?, fornecedor = ? WHERE idVenda = ?"
            );
            comando.setString(1, this.compra.getDataHora());
            comando.setLong  (2, this.compra.getUsuario().getMatricula());
            comando.setString(3, this.compra.getFornecedor());
            comando.setLong  (4, this.compra.getNumero());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao atualizar a compra: " + exception.getMessage());
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
                "DELETE FROM compra WHERE idCompra = ?"
            );
            comando.setLong(1, this.compra.getNumero());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao deletar a compra: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public Compra get(long id) {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement("SELECT * FROM compra WHERE idCompra = ?");
            comando.setLong(1, id);
            ResultSet resultado = comando.executeQuery();
            resultado.next();
            return new Compra(
              resultado.getLong("idCompra"),
              resultado.getString("dataHora"),
              (new UsuarioDao(new Usuario(0, null, null))).get(resultado.getLong("idMatricula")),
              resultado.getString("fornecedor")
            );
        } catch (SQLException e) {
            return null;
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public java.util.List<Compra> getTodos() {
        Conexao cnx = new Conexao();
        Statement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().createStatement();
            ResultSet resultado = comando.executeQuery("SELECT * FROM compra");
            java.util.ArrayList<Compra> vendas = new java.util.ArrayList<>();
            while(resultado.next()) {
                vendas.add(
                    new Compra(
                      resultado.getLong("idCompra"),
                      resultado.getString("dataHora"),
                      (new UsuarioDao(new Usuario(0, null, null))).get(resultado.getLong("idMatricula")),
                      resultado.getString("fornecedor")
                    )
                );
            }
            return vendas;
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar as compras: " + exception.getMessage());
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
            ResultSet resultado = comando.executeQuery("SELECT COALESCE(MAX(idCompra), 0) + 1 AS proximo FROM compra");
            resultado.next();
            return resultado.getLong("proximo");
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar o próximo código de compra: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

}