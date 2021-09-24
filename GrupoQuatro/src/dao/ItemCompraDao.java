package dao;

import entidades.Compra;
import entidades.ItemCompra;
import entidades.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Conexao;

/**
 * DAO da ItemCompra.
 *
 * @author Celiculos
 */
public final class ItemCompraDao implements DataAccessObject {

    private final ItemCompra itemCompra;


    public ItemCompraDao(final ItemCompra itemCompra) {
        this.itemCompra = itemCompra;
    }

    @Override
    public void insere() {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement(
                "INSERT INTO itemcompra (idItemVenda, idVenda, idProduto, quantidade) VALUES (?, ?, ?, ?)"
            );
            comando.setLong  (1, this.itemCompra.getCodigo());
            comando.setLong  (2, this.itemCompra.getMovimentacao().getNumero());
            comando.setLong  (3, this.itemCompra.getProduto().getCodigo());
            comando.setDouble(4, this.itemCompra.getQuantidade());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao inserir o item da compra: " + exception.getMessage());
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
                "UPDATE itemcompra SET quantidade = ? WHERE idItemCompra = ?"
            );
            comando.setDouble(1, this.itemCompra.getQuantidade());
            comando.setLong  (2, this.itemCompra.getCodigo());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao atualizar o item da compra: " + exception.getMessage());
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
                "DELETE FROM itemcompra WHERE idItemCompra = ?"
            );
            comando.setLong(1, this.itemCompra.getCodigo());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao deletar o item da compra: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public ItemCompra get(long id) {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement("SELECT * FROM itemcompra WHERE idItemCompra = ?");
            comando.setLong(1, id);
            ResultSet resultado = comando.executeQuery();
            resultado.next();
            return new ItemCompra(
              resultado.getLong("idItemCompra"),
              (new CompraDao(new Compra(0, null))).get(resultado.getLong("idCompra")),
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
    public java.util.List<ItemCompra> getTodos() {
        Conexao cnx = new Conexao();
        Statement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().createStatement();
            ResultSet resultado = comando.executeQuery("SELECT * FROM itemcompra");
            java.util.ArrayList<ItemCompra> itens = new java.util.ArrayList<>();
            while(resultado.next()) {
                itens.add(
                    new ItemCompra(
                        resultado.getLong("idItemCompra"),
                        (new CompraDao(new Compra(0, null))).get(resultado.getLong("idCompra")),
                        (new ProdutoDao(new Produto(0, null, null))).get(resultado.getLong("idProduto")),
                        resultado.getDouble("quantidade")
            )
                );
            }
            return itens;
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar os itens da compra: " + exception.getMessage());
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
            ResultSet resultado = comando.executeQuery("SELECT COALESCE(MAX(idItemCompra), 0) + 1 AS proximo FROM itemcompra");
            resultado.next();
            return resultado.getLong("proximo");
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar o próximo código do item da compra: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

}