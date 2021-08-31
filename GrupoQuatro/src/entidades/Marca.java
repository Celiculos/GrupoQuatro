package entidades;

/**
 * Marca de Produto.
 *
 * @author Celiculos
 */
public final class Marca {

    private long codigo;
    private String nome;


    public Marca(final long codigo, final String nome) {
        this.codigo = codigo;
        this.nome   = nome;
    }


    public long getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return new util.BoxPrint(
            java.util.Map.<String, String>of(
                "codigo", String.valueOf(this.getCodigo()),
                "nome"  , this.getNome()
            )
        ).toString();
    }

}