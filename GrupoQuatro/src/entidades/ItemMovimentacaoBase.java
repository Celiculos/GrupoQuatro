package entidades;

/**
 *
 * @author Celiculos
 */
public abstract class ItemMovimentacaoBase implements ItemMovimentacao {

    protected final long codigo;
    protected final Movimentacao movimentacao;
    protected final Produto produto;
    protected final double quantidade;


    public ItemMovimentacaoBase(final long codigo, Movimentacao movimentacao, Produto produto, double quantidade) {
        this.codigo       = codigo;
        this.movimentacao = movimentacao;
        this.produto      = produto;
        this.quantidade   = quantidade;
    }


    @Override
    public long getCodigo() {
        return codigo;
    }

    @Override
    public Movimentacao getMovimentacao() {
        return movimentacao;
    }

    @Override
    public Produto getProduto() {
        return produto;
    }

    @Override
    public double getQuantidade() {
        return quantidade;
    }

    @Override
    public void movimentaEstoque() {
        dao.EstoqueDao estDao = new dao.EstoqueDao(new Estoque(this.getProduto()));
        Estoque estoque = this.findEstoque();
        try {
            estoque = estDao.get(this.getProduto().getCodigo());
        } catch(NullPointerException nullPointer) {
            estoque = new Estoque(this.getProduto());
            (new dao.EstoqueDao(estoque)).insere();
        }
        this.contabiliza(estoque);
    }

    private Estoque findEstoque() {
        dao.EstoqueDao estDao = new dao.EstoqueDao(new Estoque(this.getProduto()));
        Estoque estoque = estDao.get(this.getProduto().getCodigo()) ;
        if(estoque == null) {
            estoque = new Estoque(this.getProduto());
            (new dao.EstoqueDao(estoque)).insere();
        }
        return estoque;
    }

    protected abstract void contabiliza(Estoque estoque);

}