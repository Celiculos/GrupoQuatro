package entidades;

/**
 *
 * @author Celiculos
 */
public class ItemCompra extends ItemMovimentacaoBase {


    public ItemCompra(final long codigo, Compra compra, Produto produto, double quantidade) {
        super(codigo, compra, produto, quantidade);
    }


    @Override
    protected void contabiliza(Estoque estoque) {
        estoque.entrada(this.getQuantidade());
        (new dao.EstoqueDao(estoque)).atualiza();
    }

}