package entidades;

public class Usuario {

    private final long matricula;
    private final String funcao;
    private final String nome;


    public Usuario(final long matricula, String funcao, String nome) {
        this.matricula = matricula;
        this.funcao = funcao;
        this.nome = nome;
    }


    public long getMatricula() {
        return matricula;
    }

    public String getFuncao() {
        return funcao;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return new util.BoxPrint(
          java.util.Map.<String, String>of(
            "matricula", String.valueOf(this.getMatricula()),
            "funcao"   , this.getFuncao(),
            "nome"     , this.getNome()
          )
        ).toString();
    }

}