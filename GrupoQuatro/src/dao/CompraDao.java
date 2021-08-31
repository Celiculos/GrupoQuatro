package dao;

import entidades.Compra;

/**
 * DAO da Compra.
 *
 * @author Celiculos
 */
public final class CompraDao implements DataAccessObject {

    private static final java.util.Map<Long, Compra> compras = new java.util.HashMap<>();

    private final Compra compra;


    public CompraDao(final Compra compra) {
        this.compra = compra;
    }

    @Override
    public void insere() {
        compras.put(this.compra.getNumero(), this.compra);
    }

    @Override
    public void atualiza() {
        compras.put(this.compra.getNumero(), this.compra);
    }

    @Override
    public void deleta() {
        compras.remove(this.compra.getNumero());
    }

    @Override
    public Compra get(long id) {
        return compras.get(id);
    }

    @Override
    public java.util.List<Compra> getTodos() {
        return new java.util.ArrayList<>(compras.values());
    }

    @Override
    public long proximoCodigo() {
        return compras.size() + 1;
    }

}