package dao;

import entidades.Estoque;
import entidades.Marca;
import entidades.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Conexao;

/**
 * DAO do Estoque.
 *
 * @author Celiculos
 */
public final class EstoqueDao implements DataAccessObject {

    private final Estoque estoque;


    public EstoqueDao(final Estoque estoque) {
        this.estoque = estoque;
    }

    @Override
    public void insere() {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement(
                "INSERT INTO estoque (idProduto, quantidade) VALUES (?, ?)"
            );
            comando.setLong  (1, this.estoque.getProduto().getCodigo());
            comando.setDouble(2, this.estoque.getQuantidade());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao inserir o estoque: " + exception.getMessage());
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
                "UPDATE estoque SET quantidade = ? WHERE idProduto = ?"
            );
            comando.setDouble(1, this.estoque.getQuantidade());
            comando.setLong  (2, this.estoque.getProduto().getCodigo());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao atualizar o estoque: " + exception.getMessage());
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
                "DELETE FROM estoque WHERE idProduto = ?"
            );
            comando.setLong(1, this.estoque.getProduto().getCodigo());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao deletar o estoque: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public Estoque get(long id) {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement("SELECT * FROM estoque WHERE idProduto = ?");
            comando.setLong(1, id);
            ResultSet resultado = comando.executeQuery();
            resultado.next();
            return new Estoque(
              (new ProdutoDao(new Produto(0, null, null))).get(resultado.getLong("idProduto")),
              resultado.getDouble("quantidade")
            );
        } catch (SQLException e) {
            return null;
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public java.util.List<Estoque> getTodos() {
        Conexao cnx = new Conexao();
        Statement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().createStatement();
            ResultSet resultado = comando.executeQuery("SELECT * FROM estoque");
            java.util.ArrayList<Estoque> estoques = new java.util.ArrayList<>();
            while(resultado.next()) {
                estoques.add(
                  new Estoque(
                    (new ProdutoDao(new Produto(0, null, null))).get(resultado.getLong("idProduto")),
                    resultado.getDouble("quantidade")
                  )
                );
            }
            return estoques;
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar os estoques: " + exception.getMessage());
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
            ResultSet resultado = comando.executeQuery("SELECT COALESCE(MAX(idProduto), 0) + 1 AS proximo FROM estoque");
            resultado.next();
            return resultado.getLong("proximo");
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar o próximo código do estoque: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

}