package aplicacao;

import entidades.*;
import dao.*;

public class Main {

    private static final int MENU_ESTOQUE       = 1;
    private static final int MENU_MOVIMENTACOES = 2;
    private static final int MENU_CADASTROS     = 3;

    private static final int MOVIMENTACAO_COMPRA = 1;
    private static final int MOVIMENTACAO_VENDA  = 2;

    private static final int CADASTRO_MARCA   = 1;
    private static final int CADASTRO_PRODUTO = 2;
    private static final int CADASTRO_USUARIO = 3;

    private static final int ACAO_INSERIR = 1;
    private static final int ACAO_ALTERAR = 2;
    private static final int ACAO_DELETAR = 3;

    private static final java.util.Map<String, DataAccessObject> daos = new java.util.HashMap<>();

    private static final java.util.Scanner scanner = new java.util.Scanner(System.in);


    public static void main(String[] args) {
        init();
        menu();
    }


    private static void init() {
        initDaos();
    }

    private static void initDaos() {
        initDaoMarca();
        initDaoProduto();
        initDaoUsuario();
        initDaoCompra();
        initDaoVenda();
        initDaoEstoque();
    }

    private static void initDaoMarca() {
        (new MarcaDao(new Marca(1, "ElmaChips"))).insere();
        (new MarcaDao(new Marca(2, "Coca-Cola"))).insere();
        daos.put("marca", new MarcaDao(new Marca(0, "")));
    }

    private static void initDaoProduto() {
        (new ProdutoDao(new Produto(1, "Doritos"  , (Marca) daos.get("marca").get(1)))).insere();
        (new ProdutoDao(new Produto(2, "Ruffles"  , (Marca) daos.get("marca").get(1)))).insere();
        (new ProdutoDao(new Produto(3, "Cheetos"  , (Marca) daos.get("marca").get(1)))).insere();
        (new ProdutoDao(new Produto(4, "Coca-Cola", (Marca) daos.get("marca").get(2)))).insere();
        (new ProdutoDao(new Produto(5, "Fanta"    , (Marca) daos.get("marca").get(2)))).insere();
        daos.put("produto", new ProdutoDao(new Produto(0, "", new Marca(0, ""))));
    }

    private static void initDaoUsuario() {
        (new UsuarioDao(new Usuario(1, "programador"  , "Célio"  ))).insere();
        (new UsuarioDao(new Usuario(2, "programador"  , "Thiago" ))).insere();
        (new UsuarioDao(new Usuario(3, "desenvolvedor", "Rafael" ))).insere();
        (new UsuarioDao(new Usuario(4, "desenvolvedor", "Marcelo"))).insere();
        daos.put("usuario", new UsuarioDao(new Usuario(0, "", "")));
    }

    private static void initDaoCompra() {
        Compra compra1 = new Compra(1, "Mercado da Esquina");
        compra1.adicionaItem((Produto) daos.get("produto").get(1), 2);
        compra1.adicionaItem((Produto) daos.get("produto").get(5), 2);

        Compra compra2 = new Compra(2, "Venda no fim da rua");
        compra2.adicionaItem((Produto) daos.get("produto").get(3), 2);
        compra2.adicionaItem((Produto) daos.get("produto").get(4), 1);

        compra1.movimentaEstoque((Usuario) daos.get("usuario").get(3));
        compra2.movimentaEstoque((Usuario) daos.get("usuario").get(4));

        daos.put("compra", new CompraDao(new Compra(0, "")));
    }

    private static void initDaoVenda() {
        Venda venda1 = new Venda(1);
        venda1.adicionaItem((Produto) daos.get("produto").get(3), 1);

        Venda venda2 = new Venda(2);
        venda2.adicionaItem((Produto) daos.get("produto").get(4), 1);

        venda1.movimentaEstoque((Usuario) daos.get("usuario").get(1));
        venda2.movimentaEstoque((Usuario) daos.get("usuario").get(2));

        daos.put("venda", new VendaDao(new Venda(0)));
    }

    private static void initDaoEstoque() {
        daos.put("estoque", new EstoqueDao(new Estoque(new Produto(0, "", new Marca(0, "")))));
    }

    private static void menu() {
        int opcao;
        do {
            imprimeOpcoes();
            opcao = scanner.nextInt();
            trataOpcaoMenu(opcao);
        } while(opcao != 0);
    }

    private static void imprimeOpcoes() {
        System.out.println("+-------------------------+");
        System.out.println("| ### PROJETO ESTOQUE ### |");
        System.out.println("|                         |");
        System.out.println("| 1 - Estoque             |");
        System.out.println("| 2 - Movimentações       |");
        System.out.println("| 3 - Cadastros           |");
        System.out.println("|                         |");
        System.out.println("| 0 - Sair                |");
        System.out.println("+-------------------------+");
    }

    private static void trataOpcaoMenu(int opcao) {
        switch(opcao) {
            case MENU_ESTOQUE -> menuEstoque();

            case MENU_MOVIMENTACOES -> menuMovimentacoes();

            case MENU_CADASTROS -> menuCadastros();
        }
    }

    private static void imprimeOpcoesCadastros() {
        System.out.println("+-------------------------+");
        System.out.println("| ### PROJETO ESTOQUE ### |");
        System.out.println("|                         |");
        System.out.println("| 3 - Cadastros           |");
        System.out.println("|     1 - Marcas          |");
        System.out.println("|     2 - Produtos        |");
        System.out.println("|     3 - Usuários        |");
        System.out.println("|                         |");
        System.out.println("| 0 - Voltar              |");
        System.out.println("+-------------------------+");
    }

    private static void menuCadastros() {
        int opcao;
        do {
            imprimeOpcoesCadastros();
            opcao = scanner.nextInt();
            trataOpcaoMenuCadastros(opcao);
        } while(opcao != 0);
    }

    private static void imprimeAcoesCadastros() {
        System.out.println("\r\nInforme a ação desejada:");
        System.out.println("+-------------------------+");
        System.out.println("| 1 - Inserir             |");
        System.out.println("| 2 - Alterar             |");
        System.out.println("| 3 - Excluir             |");
        System.out.println("|                         |");
        System.out.println("| 0 - Voltar              |");
        System.out.println("+-------------------------+");
    }

    private static void trataOpcaoMenuCadastros(int cadastro) {
        switch(cadastro) {
            case CADASTRO_MARCA -> {
                System.out.println("Marcas cadastradas:");
                consultaMarca();
                int opcao;
                do {
                    imprimeAcoesCadastros();
                    opcao = scanner.nextInt();
                    trataAcaoMenuCadastros(cadastro, opcao);
                } while(opcao != 0);
            }
            case CADASTRO_PRODUTO -> {
                System.out.println("Produtos cadastrados:");
                consultaProduto();
                int opcao;
                do {
                    imprimeAcoesCadastros();
                    opcao = scanner.nextInt();
                    trataAcaoMenuCadastros(cadastro, opcao);
                } while(opcao != 0);
            }
            case CADASTRO_USUARIO -> {
                System.out.println("Usuários cadastrados:");
                consultaProduto();
                int opcao;
                do {
                    imprimeAcoesCadastros();
                    opcao = scanner.nextInt();
                    trataAcaoMenuCadastros(cadastro, opcao);
                } while(opcao != 0);
            }
        }
    }

    private static void trataAcaoMenuCadastros(int cadastro, int acao) {
        switch(cadastro) {
            case CADASTRO_MARCA -> trataAcaoCadastroMarca(acao);

            case CADASTRO_PRODUTO -> trataAcaoMovimentacaoProduto(acao);

            case CADASTRO_USUARIO -> trataAcaoCadastroUsuario(acao);
        }
    }

    private static void trataAcaoCadastroMarca(int acao) {
        switch(acao) {
            case ACAO_INSERIR -> insereMarca();

            case ACAO_ALTERAR -> alteraMarca();

            case ACAO_DELETAR -> deletaMarca();
        }
    }

    private static void consultaMarca() {
        daos.get("marca").getTodos().forEach(marca -> {
            System.out.println(marca);
        });
    }

    private static void insereMarca() {
        try {
            System.out.println("Informe o nome da marca:");
            (
                new MarcaDao(
                    new Marca(daos.get("marca").proximoCodigo(), scanner.next())
                )
            ).insere();
            System.out.println("Marca inserida com sucesso!");
        } catch(Exception exception) {
            System.out.println("Erro ao incluir a marca: " + exception.getMessage());
        } finally {
            consultaMarca();
        }
    }

    private static void alteraMarca() {
        System.out.println("Informe o código da marca:");
        try {
            Marca marca = (Marca) daos.get("marca").get(scanner.nextLong());
            System.out.println("Informe o nome correto:");
            Marca marcaAtualizada = new Marca(marca.getCodigo(), scanner.next());
            (new MarcaDao(marcaAtualizada)).atualiza();
            System.out.println("Marca alterada com sucesso!");
        } catch(NullPointerException nullPointer) {
            System.out.println("Erro ao atualizar a marca: não foi encontrada nenhuma marca com o código informado!");
        } catch(Exception exception) {
            System.out.println("Erro ao alterar a marca: " + exception.getMessage());
        } finally {
            consultaMarca();
        }
    }

    private static void deletaMarca() {
        System.out.println("Informe o código da marca:");
        try {
            (
                new MarcaDao( (Marca) daos.get("marca").get(scanner.nextLong()))
            ).deleta();
            System.out.println("Marca deletada com sucesso!");
        } catch(NullPointerException nullPointer) {
            System.out.println("Erro ao deletar a marca: não foi encontrada nenhuma marca com o código informado!");
        } catch(Exception exception) {
            System.out.println("Erro ao deletar a marca: " + exception.getMessage());
        } finally {
            consultaMarca();
        }
    }

    private static void trataAcaoMovimentacaoProduto(int acao) {
        switch(acao) {
            case ACAO_INSERIR -> insereProduto();

            case ACAO_ALTERAR -> alteraProduto();

            case ACAO_DELETAR -> deletaProduto();
        }
    }

    private static void consultaProduto() {
        daos.get("produto").getTodos().forEach(produto -> {
            System.out.println(produto);
        });
    }

    private static void insereProduto() {
        try {
            System.out.println("Informe o nome do produto:");
            String descricao = scanner.next();
            System.out.println("Informe o código da marca:");
            Marca marca = (Marca) daos.get("marca").get(scanner.nextLong());
            (
                new ProdutoDao(
                    new Produto(
                        daos.get("produto").proximoCodigo(),
                        descricao,
                        marca
                    )
                )
            ).insere();
            System.out.println("Produto inserido com sucesso!");
        } catch(Exception exception) {
            System.out.println("Erro ao incluir o produto: " + exception.getMessage());
        } finally {
            consultaProduto();
        }
    }

    private static void alteraProduto() {
        System.out.println("Informe o código do produto:");
        try {
            Produto produto = (Produto) daos.get("produto").get(scanner.nextLong());
            System.out.println("Informe a descrição correta:");
            Produto produtoAtualizado = new Produto(produto.getCodigo(), scanner.next(), produto.getMarca());
            (new ProdutoDao(produtoAtualizado)).atualiza();
            System.out.println("Produto alterado com sucesso!");
        } catch(NullPointerException nullPointer) {
            System.out.println("Erro ao alterar o produto: não foi encontrada nenhum produto com o código informado!");
        } catch(Exception exception) {
            System.out.println("Erro ao alterar o produto: " + exception.getMessage());
        } finally {
            consultaProduto();
        }
    }

    private static void deletaProduto() {
        System.out.println("Informe o código do produto:");
        try {
            (
                new ProdutoDao((Produto)daos.get("produto").get(scanner.nextLong()))
            ).deleta();
            System.out.println("Produto deletado com sucesso!");
        } catch(NullPointerException nullPointer) {
            System.out.println("Erro ao deletar o produto: não foi encontrada nenhum produto com o código informado!");
        } catch(Exception exception) {
            System.out.println("Erro ao deletar o produto: " + exception.getMessage());
        } finally {
            consultaProduto();
        }
    }


    private static void trataAcaoCadastroUsuario(int acao) {
        switch(acao) {
            case ACAO_INSERIR -> insereUsuario();

            case ACAO_ALTERAR -> alteraUsuario();

            case ACAO_DELETAR -> deletaUsuario();
        }
    }

    private static void consultaUsuario() {
        daos.get("usuario").getTodos().forEach(usuario -> {
            System.out.println(usuario);
        });
    }

    private static void insereUsuario() {
        try {
            System.out.println("Informe a função:");
            String funcao = scanner.next();
            System.out.println("Informe o nome do usuario:");
            String nome = scanner.next();
            (
                new UsuarioDao(
                    new Usuario(
                        daos.get("usuario").proximoCodigo(),
                        funcao,
                        nome
                    )
                )
            ).insere();
            System.out.println("Usuario inserido com sucesso!");
        } catch(Exception exception) {
            System.out.println("Erro ao incluir o usuario: " + exception.getMessage());
        } finally {
            consultaUsuario();
        }
    }

    private static void alteraUsuario() {
        System.out.println("Informe a matrícula do usuario:");
        try {
            Usuario usuario = (Usuario) daos.get("usuario").get(scanner.nextLong());
            System.out.println("Informe a função correta:");
            Usuario usuarioAlterado = new Usuario(usuario.getMatricula(), scanner.next(), usuario.getNome());
            (new UsuarioDao(usuarioAlterado)).atualiza();
            System.out.println("Usuario alterado com sucesso!");
        } catch(NullPointerException nullPointer) {
            System.out.println("Erro ao alterar o usuario: não foi encontrada nenhum usuario com a matrícula informada!");
        } catch(Exception exception) {
            System.out.println("Erro ao alterar o usuario: " + exception.getMessage());
        } finally {
            consultaUsuario();
        }
    }

    private static void deletaUsuario() {
        System.out.println("Informe a matrícula do usuario:");
        try {
            (
                new UsuarioDao((Usuario)daos.get("usuario").get(scanner.nextLong()))
            ).deleta();
            System.out.println("Usuario deletado com sucesso!");
        } catch(NullPointerException nullPointer) {
            System.out.println("Erro ao deletar o usuario: não foi encontrada nenhum usuario com o código informado!");
        } catch(Exception exception) {
            System.out.println("Erro ao deletar o usuario: " + exception.getMessage());
        } finally {
            consultaUsuario();
        }
    }

    private static void menuMovimentacoes() {
        int opcao;
        do {
            imprimeOpcoesMovimentacoes();
            opcao = scanner.nextInt();
            trataOpcaoMenuMovimentacoes(opcao);
        } while(opcao != 0);
    }

    private static void imprimeOpcoesMovimentacoes() {
        System.out.println("+-------------------------+");
        System.out.println("| ### PROJETO ESTOQUE ### |");
        System.out.println("|                         |");
        System.out.println("| 2 - Movimentações       |");
        System.out.println("|     1 - Compras         |");
        System.out.println("|     2 - Vendas          |");
        System.out.println("|                         |");
        System.out.println("| 0 - Voltar              |");
        System.out.println("+-------------------------+");
    }

    private static void imprimeAcoesMovimentacoes() {
        System.out.println("\r\nInforme a ação desejada:");
        System.out.println("+-------------------------+");
        System.out.println("| 1 - Inserir             |");
        System.out.println("|                         |");
        System.out.println("| 0 - Voltar              |");
        System.out.println("+-------------------------+");
    }

    private static void trataOpcaoMenuMovimentacoes(int movimentacao) {
        switch(movimentacao) {
            case MOVIMENTACAO_COMPRA -> {
                System.out.println("Compras realizadas:");
                consultaCompra();
                int opcao;
                do {
                    imprimeAcoesMovimentacoes();
                    opcao = scanner.nextInt();
                    trataAcaoMenuMovimentacoes(movimentacao, opcao);
                } while(opcao != 0);
            }
            case MOVIMENTACAO_VENDA -> {
                System.out.println("Vendas realizadas:");
                consultaVenda();
                int opcao;
                do {
                    imprimeAcoesMovimentacoes();
                    opcao = scanner.nextInt();
                    trataAcaoMenuMovimentacoes(movimentacao, opcao);
                } while(opcao != 0);
            }
        }
    }

    private static void trataAcaoMenuMovimentacoes(int movimentacao, int acao) {
        switch(movimentacao) {
            case MOVIMENTACAO_COMPRA -> trataAcaoMovimentacaoCompra(acao);

            case MOVIMENTACAO_VENDA -> trataAcaoMovimentacaoVenda(acao);
        }
    }

    private static void trataAcaoMovimentacaoCompra(int acao) {
        switch(acao) {
            case ACAO_INSERIR -> insereCompra();
        }
    }

    private static void consultaCompra() {
        daos.get("compra").getTodos().forEach(compra -> {
            System.out.println(compra);
        });
    }

    private static void insereCompra() {
        System.out.println("Informe o fornecedor:");
        Compra compra = new Compra(daos.get("compra").proximoCodigo(), scanner.next());

        final Produto produto;
        try {
            System.out.println("Informe o código do produto a ser dada a entrada:");
            produto = (Produto) daos.get("produto").get(scanner.nextLong());
        } catch(NullPointerException nullPointer) {
            System.out.println("Não foi possível recuperar o produto!");
            return;
        } catch(Exception exception) {
            System.out.println("Erro ao recuperar o produto:" + exception.getMessage());
            return;
        }

        System.out.println("Informe a quantidade do produto:");
        compra.adicionaItem(produto, scanner.nextDouble());

        final Usuario usuario;
        try {
            System.out.println("Informe o código do usuário realizando a movimentação:");
            usuario = (Usuario) daos.get("usuario").get(scanner.nextLong());
        } catch(NullPointerException nullPointer) {
            System.out.println("Não foi possível recuperar o usuário!");
            return;
        }

        try {
            compra.movimentaEstoque(usuario);
        } catch(Exception exception) {
            System.out.println("Erro ao inserir a compra: " + exception.getMessage());
        } finally {
            consultaCompra();
        }
    }

    private static void trataAcaoMovimentacaoVenda(int acao) {
        switch(acao) {
            case ACAO_INSERIR -> insereVenda();
        }
    }

    private static void consultaVenda() {
        daos.get("venda").getTodos().forEach(compra -> {
            System.out.println(compra);
        });
    }

    private static void insereVenda() {
        Venda venda = new Venda(daos.get("venda").proximoCodigo());

        final Produto produto;
        try {
            System.out.println("Informe o código do produto a ser dada a saída:");
            produto = (Produto) daos.get("produto").get(scanner.nextLong());
        } catch(NullPointerException nullPointer) {
            System.out.println("Não foi possível recuperar o produto!");
            return;
        } catch(Exception exception) {
            System.out.println("Erro ao recuperar o produto:" + exception.getMessage());
            return;
        }

        System.out.println("Informe a quantidade do produto:");
        venda.adicionaItem(produto, scanner.nextDouble());

        final Usuario usuario;
        try {
            System.out.println("Informe o código do usuário realizando a movimentação:");
            usuario = (Usuario) daos.get("usuario").get(scanner.nextLong());
        } catch(NullPointerException nullPointer) {
            System.out.println("Não foi possível recuperar o usuário!");
            return;
        }

        try {
            venda.movimentaEstoque(usuario);
        } catch(Exception exception) {
            System.out.println("Erro ao inserir a venda: " + exception.getMessage());
        } finally {
            consultaVenda();
        }
    }

    private static void menuEstoque() {
        consultaEstoque();
    }

    private static void consultaEstoque() {
        daos.get("estoque").getTodos().forEach(estoque -> {
            System.out.println(estoque);
        });
    }

}