package dao;

import entidades.ItemVenda;

/**
 * DAO da ItemVenda.
 *
 * @author Celiculos
 */
public final class ItemVendaDao implements DataAccessObject {

    private static final java.util.Map<Long, ItemVenda> itens = new java.util.HashMap<>();

    private final ItemVenda itemVenda;


    public ItemVendaDao(final ItemVenda itemVenda) {
        this.itemVenda = itemVenda;
    }

    @Override
    public void insere() {
        itens.put(this.itemVenda.getProduto().getCodigo(), this.itemVenda);
    }

    @Override
    public void atualiza() {
        itens.put(this.itemVenda.getProduto().getCodigo(), this.itemVenda);
    }

    @Override
    public void deleta() {
        itens.remove(this.itemVenda.getProduto().getCodigo());
    }

    @Override
    public ItemVenda get(long id) {
        return itens.get(id);
    }

    @Override
    public java.util.List<ItemVenda> getTodos() {
        return new java.util.ArrayList<>(itens.values());
    }

    @Override
    public long proximoCodigo() {
        return itens.size() + 1;
    }

}