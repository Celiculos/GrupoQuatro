package entidades;

/**
 * Movimentação de Estoque.
 *
 * @author Celiculos
 */
public interface Movimentacao {

    /**
     * Número da Movimentação.
     *
     * @return número
     */
    public long getNumero();

    /**
     * Texto com a data e hora.
     *
     * @return data e hora
     */
    public CharSequence getDataHora();

    /**
     * Usuário que realizou a movimetação.
     *
     * @return Usuario
     */
    public Usuario getUsuario();

    /**
     * Adiciona um produto na movimentação.
     *
     * @param produto
     * @param quantidade
     *
     * @return item que movimenta o estoque
     */
    public ItemMovimentacao adicionaItem(Produto produto, double quantidade);

    /**
     * Movimenta o estoque.
     *
     * @param usuario
     */
    public void movimentaEstoque(Usuario usuario);

}