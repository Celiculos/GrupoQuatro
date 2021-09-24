package dao;

import entidades.Marca;
import entidades.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Conexao;

/**
 * DAO do Produto.
 *
 * @author Celiculos
 */
public final class ProdutoDao implements DataAccessObject {

    private final Produto produto;


    public ProdutoDao(final Produto produto) {
        this.produto = produto;
    }

    @Override
    public void insere() {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement(
                "INSERT INTO produto (idProduto, descricao, idMarca) VALUES (?, ?, ?)"
            );
            comando.setLong  (1, this.produto.getCodigo());
            comando.setString(2, this.produto.getDescricao());
            comando.setLong  (3, this.produto.getMarca().getCodigo());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao inserir o produto: " + exception.getMessage());
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
                "UPDATE produto SET descricao = ?, idMarca = ? WHERE idProduto = ?"
            );
            comando.setString(1, this.produto.getDescricao());
            comando.setLong  (2, this.produto.getMarca().getCodigo());
            comando.setLong  (3, this.produto.getCodigo());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao atualizar o produto: " + exception.getMessage());
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
                "DELETE FROM produto WHERE idProduto = ?"
            );
            comando.setLong(1, this.produto.getCodigo());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao deletar o produto: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public Produto get(long id) {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement("SELECT * FROM produto WHERE idProduto = ?");
            comando.setLong(1, id);
            ResultSet resultado = comando.executeQuery();
            resultado.next();
            return new Produto(
              resultado.getLong("idProduto"),
              resultado.getString("descricao"),
              (new MarcaDao(new Marca(0, ""))).get(resultado.getLong("idMarca"))
            );
        } catch (SQLException e) {
            return null;
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public java.util.List<Produto> getTodos() {
        Conexao cnx = new Conexao();
        Statement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().createStatement();
            ResultSet resultado = comando.executeQuery("SELECT * FROM produto");
            java.util.ArrayList<Produto> produtos = new java.util.ArrayList<>();
            while(resultado.next()) {
                produtos.add(
                  new Produto(
                    resultado.getLong("idProduto"),
                    resultado.getString("descricao"),
                    (new MarcaDao(new Marca(0, ""))).get(resultado.getLong("idMarca"))
                  )
                );
            }
            return produtos;
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar os produtos: " + exception.getMessage());
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
            ResultSet resultado = comando.executeQuery("SELECT COALESCE(MAX(idProduto), 0) + 1 AS proximo FROM produto");
            resultado.next();
            return resultado.getLong("proximo");
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar o próximo código do Produto: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

}