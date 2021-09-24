package dao;

import entidades.Marca;
import java.sql.*;
import util.Conexao;

/**
 * DAO da Marca de Produto.
 *
 * @author Celiculos
 */
public final class MarcaDao implements DataAccessObject {

    private final Marca marca;


    public MarcaDao(final Marca marca) {
        this.marca = marca;
    }

    @Override
    public void insere() {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement(
                "INSERT INTO marca (idMarca, nome) VALUES (?, ?)"
            );
            comando.setLong  (1, this.marca.getCodigo());
            comando.setString(2, this.marca.getNome());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao inserir a marca: " + exception.getMessage());
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
                "UPDATE marca SET nome = ? WHERE idMarca = ?"
            );
            comando.setString(1, this.marca.getNome());
            comando.setLong  (2, this.marca.getCodigo());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao atualizar a marca: " + exception.getMessage());
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
                "DELETE FROM marca WHERE idMarca = ?"
            );
            comando.setLong(1, this.marca.getCodigo());
            comando.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao deletar a marca: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public Marca get(long id) {
        Conexao cnx = new Conexao();
        PreparedStatement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().prepareStatement("SELECT * FROM marca WHERE idMarca = ?");
            comando.setLong(1, id);
            ResultSet resultado = comando.executeQuery();
            resultado.next();
            return new Marca(resultado.getLong("idMarca"), resultado.getString("nome"));
        } catch (SQLException e) {
            return null;
        } finally {
            cnx.fechar();
        }
    }

    @Override
    public java.util.List<Marca> getTodos() {
        Conexao cnx = new Conexao();
        Statement comando;
        try {
            cnx.conecta();
            comando = cnx.getConexao().createStatement();
            ResultSet resultado = comando.executeQuery("SELECT * FROM marca");
            java.util.ArrayList<Marca> marcas = new java.util.ArrayList<>();
            while(resultado.next()) {
                marcas.add(new Marca(
                    resultado.getLong("idMarca"),
                    resultado.getString("nome")
                ));
            }
            return marcas;
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar as marcas: " + exception.getMessage());
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
            ResultSet resultado = comando.executeQuery("SELECT COALESCE(MAX(idMarca), 0) + 1 AS proximo FROM marca");
            resultado.next();
            return resultado.getLong("proximo");
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar o próximo código da marca: " + exception.getMessage());
        } finally {
            cnx.fechar();
        }
    }

}