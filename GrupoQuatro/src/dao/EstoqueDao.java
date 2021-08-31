package dao;

import entidades.Estoque;

/**
 * DAO do Estoque.
 *
 * @author Celiculos
 */
public final class EstoqueDao implements DataAccessObject {

    private static final java.util.Map<Long, Estoque> estoques = new java.util.HashMap<>();

    private final Estoque estoque;


    public EstoqueDao(final Estoque estoque) {
        this.estoque = estoque;
    }

    @Override
    public void insere() {
        estoques.put(this.estoque.getProduto().getCodigo(), this.estoque);
    }

    @Override
    public void atualiza() {
        estoques.put(this.estoque.getProduto().getCodigo(), this.estoque);
    }

    @Override
    public void deleta() {
        estoques.remove(this.estoque.getProduto().getCodigo());
    }

    @Override
    public Estoque get(long id) {
        return estoques.get(id);
    }

    @Override
    public java.util.List<Estoque> getTodos() {
        return new java.util.ArrayList<>(estoques.values());
    }

    @Override
    public long proximoCodigo() {
        return estoques.size() + 1;
    }

}