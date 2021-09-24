package dao;

import entidades.ItemVenda;
import entidades.Produto;
import entidades.Venda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Conexao;

/**
 * DAO da ItemVenda.
 *
 * @author Celiculos
 */
public final class ItemVendaDao implements DataAccessObject {

    private final ItemVenda itemVenda;


    public ItemVendaDao(final ItemVenda itemVenda) {
        this.itemVenda = itemVenda;
    }

    @Override
    public void insere() {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement(
                "INSERT INTO itemvenda (idItemVenda, idVenda, idProduto, quantidade) VALUES (?, ?, ?, ?)"
            );
            comando.setLong  (1, this.itemVenda.getCodigo());
            comando.setLong  (2, this.itemVenda.getMovimentacao().getNumero());
            comando.setLong  (3, this.itemVenda.getProduto().getCodigo());
            comando.setDouble(4, this.itemVenda.getQuantidade());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao inserir o item da venda: " + exception.getMessage());
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
                "UPDATE itemvenda SET quantidade = ? WHERE idItemVenda = ?"
            );
            comando.setDouble(1, this.itemVenda.getQuantidade());
            comando.setLong  (2, this.itemVenda.getCodigo());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao atualizar o item da venda: " + exception.getMessage());
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
                "DELETE FROM itemvenda WHERE idItemVenda = ?"
            );
            comando.setLong(1, this.itemVenda.getCodigo());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao deletar o item da venda: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public ItemVenda get(long id) {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement("SELECT * FROM itemvenda WHERE idItemVenda = ?");
            comando.setLong(1, id);
            ResultSet resultado = comando.executeQuery();
            resultado.next();
            return new ItemVenda(
              resultado.getLong("idItemVenda"),
              (new VendaDao(new Venda(0))).get(resultado.getLong("idVenda")),
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
    public java.util.List<ItemVenda> getTodos() {
        Conexao cnx = new Conexao();
        Statement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().createStatement();
            ResultSet resultado = comando.executeQuery("SELECT * FROM itemvenda");
            java.util.ArrayList<ItemVenda> itens = new java.util.ArrayList<>();
            while(resultado.next()) {
                itens.add(
                    new ItemVenda(
                        resultado.getLong("idItemVenda"),
                        (new VendaDao(new Venda(0))).get(resultado.getLong("idVenda")),
                        (new ProdutoDao(new Produto(0, null, null))).get(resultado.getLong("idProduto")),
                        resultado.getDouble("quantidade")
                    )
                );
            }
            return itens;
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar os itens das vendas: " + exception.getMessage());
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
            ResultSet resultado = comando.executeQuery("SELECT COALESCE(MAX(idItemvenda), 0) + 1 AS proximo FROM itemvenda");
            resultado.next();
            return resultado.getLong("proximo");
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar o próximo código do item da venda: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

}