package entidades;

/**
 *
 * @author Celiculos
 */
public interface ItemMovimentacao {

    /**
     * Código sequencial do item.
     *
     * @return long
     */
    public long getCodigo();

    /**
     * Movimentação.
     *
     * @return Movimentacao
     */
    public Movimentacao getMovimentacao();

    /**
     * Produto estocado.
     *
     * @return Produto
     */
    public Produto getProduto();

    /**
     * Quantidade movimentada no estoque.
     *
     * @return quantidade
     */
    public double getQuantidade();

    /**
     * Movimenta o estoque
     */
    public void movimentaEstoque();

}