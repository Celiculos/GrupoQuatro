package dao;

import entidades.ItemCompra;

/**
 * DAO da ItemCompra.
 *
 * @author Celiculos
 */
public final class ItemCompraDao implements DataAccessObject {

    private static final java.util.Map<Long, ItemCompra> itens = new java.util.HashMap<>();

    private final ItemCompra itemCompra;


    public ItemCompraDao(final ItemCompra itemCompra) {
        this.itemCompra = itemCompra;
    }

    @Override
    public void insere() {
        itens.put(this.itemCompra.getProduto().getCodigo(), this.itemCompra);
    }

    @Override
    public void atualiza() {
        itens.put(this.itemCompra.getProduto().getCodigo(), this.itemCompra);
    }

    @Override
    public void deleta() {
        itens.remove(this.itemCompra.getProduto().getCodigo());
    }

    @Override
    public ItemCompra get(long id) {
        return itens.get(id);
    }

    @Override
    public java.util.List<ItemCompra> getTodos() {
        return new java.util.ArrayList<>(itens.values());
    }

    @Override
    public long proximoCodigo() {
        return itens.size() + 1;
    }

}