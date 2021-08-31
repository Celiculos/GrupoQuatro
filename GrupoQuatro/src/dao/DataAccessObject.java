package dao;

public interface DataAccessObject {

    public void insere();

    public void atualiza();

    public void deleta();

    public Object get(long id);

    public java.util.List getTodos();

    public long proximoCodigo();

}