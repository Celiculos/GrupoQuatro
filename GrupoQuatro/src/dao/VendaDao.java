package dao;

import entidades.Venda;

/**
 * DAO da Venda.
 *
 * @author Celiculos
 */
public final class VendaDao implements DataAccessObject {

    private static final java.util.Map<Long, Venda> vendas = new java.util.HashMap<>();

    private final Venda venda;


    public VendaDao(final Venda venda) {
        this.venda = venda;
    }

    @Override
    public void insere() {
        vendas.put(this.venda.getNumero(), this.venda);
    }

    @Override
    public void atualiza() {
        vendas.put(this.venda.getNumero(), this.venda);
    }

    @Override
    public void deleta() {
        vendas.remove(this.venda.getNumero());
    }

    @Override
    public Venda get(long id) {
        return vendas.get(id);
    }

    @Override
    public java.util.List<Venda> getTodos() {
        return new java.util.ArrayList<>(vendas.values());
    }

    @Override
    public long proximoCodigo() {
        return vendas.size() + 1;
    }

}