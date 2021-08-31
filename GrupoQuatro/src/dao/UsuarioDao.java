package dao;

import entidades.Usuario;

/**
 * DAO do Usuario.
 *
 * @author Celiculos
 */
public final class UsuarioDao implements DataAccessObject {

    private static final java.util.Map<Long, Usuario> usuarios = new java.util.HashMap<>();

    private final Usuario usuario;


    public UsuarioDao(final Usuario Usuario) {
        this.usuario = Usuario;
    }

    @Override
    public void insere() {
        usuarios.put(this.usuario.getMatricula(), this.usuario);
    }

    @Override
    public void atualiza() {
        usuarios.put(this.usuario.getMatricula(), this.usuario);
    }

    @Override
    public void deleta() {
        usuarios.remove(this.usuario.getMatricula());
    }

    @Override
    public Usuario get(long id) {
        return usuarios.get(id);
    }

    @Override
    public java.util.List<Usuario> getTodos() {
        return new java.util.ArrayList<>(usuarios.values());
    }

    @Override
    public long proximoCodigo() {
        return usuarios.size() + 1;
    }

}