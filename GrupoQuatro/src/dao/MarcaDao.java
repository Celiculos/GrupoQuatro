package dao;

import entidades.Marca;

/**
 * DAO da Marca de Produto.
 *
 * @author Celiculos
 */
public final class MarcaDao implements DataAccessObject {

    private static final java.util.Map<Long, Marca> marcas = new java.util.HashMap<>();

    private final Marca marca;


    public MarcaDao(final Marca marca) {
        this.marca = marca;
    }

    @Override
    public void insere() {
        marcas.put(this.marca.getCodigo(), this.marca);
    }

    @Override
    public void atualiza() {
        marcas.put(this.marca.getCodigo(), this.marca);
    }

    @Override
    public void deleta() {
        marcas.remove(this.marca.getCodigo());
    }

    @Override
    public Marca get(long id) {
        return marcas.get(id);
    }

    @Override
    public java.util.List<Marca> getTodos() {
        return new java.util.ArrayList<>(marcas.values());
    }

    @Override
    public long proximoCodigo() {
        return marcas.size() + 1;
    }

}