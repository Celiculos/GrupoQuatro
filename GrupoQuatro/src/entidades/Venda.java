package entidades;

/**
 *
 * @author Celiculos
 */
public class Venda extends MovimentacaoBase {

    public Venda(final long numero) {
        super(numero);
    }

    public Venda(long numero, String dataHora, Usuario usuario) {
        super(numero, dataHora, usuario);
    }


    @Override
    protected ItemMovimentacao criaItem(Produto produto, double quantidade) {
        return new ItemVenda(
            (
                new dao.ItemVendaDao(
                    new ItemVenda(0, this, null, 0)
                ).proximoCodigo()
            ),
            this,
            produto,
            quantidade
        );
    }

    @Override
    protected void salva() {
        (new dao.VendaDao(this)).insere();
    }

}