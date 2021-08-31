package dao;

import entidades.Produto;

/**
 * DAO do Produto.
 *
 * @author Celiculos
 */
public final class ProdutoDao implements DataAccessObject {

    private static final java.util.Map<Long, Produto> produtos = new java.util.HashMap<>();

    private final Produto produto;


    public ProdutoDao(final Produto produto) {
        this.produto = produto;
    }

    @Override
    public void insere() {
        produtos.put(this.produto.getCodigo(), this.produto);
    }

    @Override
    public void atualiza() {
        produtos.put(this.produto.getCodigo(), this.produto);
    }

    @Override
    public void deleta() {
        produtos.remove(this.produto.getCodigo());
    }

    @Override
    public Produto get(long id) {
        return produtos.get(id);
    }

    @Override
    public java.util.List<Produto> getTodos() {
        return new java.util.ArrayList<>(produtos.values());
    }

    @Override
    public long proximoCodigo() {
        return produtos.size() + 1;
    }

}