package entidades;

/**
 *
 * @author Celiculos
 */
public class ItemVenda extends ItemMovimentacaoBase {

    public ItemVenda(final long codigo, final Venda venda, final Produto produto, final double quantidade) {
        super(codigo, venda, produto, quantidade);
    }


    @Override
    protected void contabiliza(final Estoque estoque) {
        estoque.saida(this.getQuantidade());
        (new dao.EstoqueDao(estoque)).atualiza();
    }

}