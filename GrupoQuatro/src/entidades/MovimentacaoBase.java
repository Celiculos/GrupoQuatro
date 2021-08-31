package entidades;

import java.util.List;

/**
 * Movimentação de Entrada.
 *
 * @author Celiculos
 */
abstract class MovimentacaoBase implements Movimentacao {

    private final long numero;
    private String dataHora;
    private Usuario usuario;
    private final List<ItemMovimentacao> itens;


    public MovimentacaoBase(final long numero) {
        this.numero = numero;
        this.itens  = new java.util.ArrayList<ItemMovimentacao>();
    }


    public long getNumero() {
        return numero;
    }

    public String getDataHora() {
        return dataHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public final ItemMovimentacao adicionaItem(Produto produto, double quantidade) {
        ItemMovimentacao item = this.criaItem(produto, quantidade);
        this.itens.add(this.criaItem(produto, quantidade));
        return item;
    }

    protected abstract ItemMovimentacao criaItem(Produto produto, double quantidade);

    protected abstract void salva();

    @Override
    public final void movimentaEstoque(final Usuario usuario) {
        this.defineUsuario(usuario);
        this.defineDataHora();
        this.salva();
        this.movimentaItens();
    }

    private void defineDataHora() {
        this.dataHora = java.time.format.DateTimeFormatter.ofPattern(
          "dd/MM/uuuu HH:mm:ss"
        ).format(java.time.LocalDateTime.now());
    }

    private void defineUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }

    protected void movimentaItens() {
        this.itens.forEach(item -> {
            this.movimentaItem(item);
        });
    }

    protected void movimentaItem(ItemMovimentacao item) {
        item.movimentaEstoque();
    }

    protected java.util.Map mapForBox() {
        return java.util.Map.<String, String>of(
            "Nº"       , String.valueOf(this.getNumero()),
            "Data Hora", this.getDataHora(),
            "Itens"    , String.valueOf(this.itens.size()),
            "Usuário"  , this.getUsuario().getNome()
        );
    }

    protected final util.BoxPrint asBox() {
        return new util.BoxPrint(mapForBox());
    }


    @Override
    public String toString() {
        return this.asBox().toString();
    }

}