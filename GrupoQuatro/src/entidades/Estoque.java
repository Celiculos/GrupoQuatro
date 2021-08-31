package entidades;

/**
 * Estoque de Produto.
 *
 * @author Celiculos
 */
public class Estoque {

    private final Produto produto;
    private double quantidade;


    public Estoque(final Produto produto, final double quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Estoque(Produto produto) {
        this(produto, 0);
    }


    public Produto getProduto() {
        return produto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void entrada(double quantidade) {
        this.quantidade += quantidade;
    }

    public void saida(double quantidade) {
        //TODO validar quantidade negativa
        this.quantidade -= quantidade;
    }

    @Override
    public String toString() {
        return new util.BoxPrint(
          java.util.Map.<String, String>of(
            "Produto"       , this.getProduto().getDescricao(),
            "Marca"         , this.getProduto().getMarca().getNome(),
            "quantidade"    , String.format("%.2f", this.getQuantidade())
          )
        ).toString();
    }

}