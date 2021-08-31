package entidades;

import java.util.Map;

/**
 * Movimentação de Entrada.
 *
 * @author Celiculos
 */
public class Compra extends MovimentacaoBase {

    private final String fornecedor;


    public Compra(final long numero, final String fornecedor) {
        super(numero);
        this.fornecedor = fornecedor;
    }


    public String getFornecedor() {
        return this.fornecedor;
    }

    @Override
    protected ItemMovimentacao criaItem(Produto produto, double quantidade) {
        return new ItemCompra(
            (
                new dao.ItemCompraDao(
                    new ItemCompra(0, this, null, 0)
                ).proximoCodigo()
            ),
            this,
            produto,
            quantidade
        );
    }

    @Override
    protected Map mapForBox() {
        Map map = new java.util.HashMap<String, String>(super.mapForBox());
        map.put("Fornecedor", this.getFornecedor());
        return map;
    }

    @Override
    protected void salva() {
        (new dao.CompraDao(this)).insere();
    }

}