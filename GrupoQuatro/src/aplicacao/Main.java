package aplicacao;

import entidades.*;
import dao.*;

public class Main {

    private static final int MENU_ESTOQUE       = 1;
    private static final int MENU_MOVIMENTACOES = 2;
    private static final int MENU_CADASTROS     = 3;

    private static final int CADASTRO_MARCA   = 1;
    private static final int CADASTRO_PRODUTO = 2;
    private static final int CADASTRO_USUARIO = 3;

    private static final int ACAO_INSERIR = 1;
    private static final int ACAO_ALTERAR = 2;
    private static final int ACAO_DELETAR = 3;

    private static java.util.Map<String, DataAccessObject> daos = new java.util.HashMap<>();
    private static java.util.Map<String, Produto> produtos = new java.util.HashMap<>();
    private static java.util.Map<String, Usuario> usuarios = new java.util.HashMap<>();

    private static final java.util.Scanner scanner = new java.util.Scanner(System.in);


    public static void main(String[] args) {
        init();
        menu();
//        criaUsuarios();
//        criaProdutos();
//        criaEstoqueInicial();
//        imprimeEstoqueInicial();
//        criaEntradas();
//        imprimeEstoquePosCompras();
//        criaSaidas();
//        imprimeEstoqueFinal();
    }


    private static void init() {
        initDaos();
    }

    private static void initDaos() {
        initDaoMarca();
        initDaoProduto();
        initDaoUsuario();
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
            case MENU_CADASTROS -> menuCadastros();

            case MENU_MOVIMENTACOES -> menuCadastros();
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

            case CADASTRO_PRODUTO -> trataAcaoCadastroProduto(acao);

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
        } catch(Exception e) {
            System.out.println("Erro ao incluir a marca: " + e.toString());
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
        } catch(Exception e) {
            System.out.println("Erro ao alterar a marca: " + e.toString());
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
        } catch(Exception e) {
            System.out.println("Erro ao deletar a marca: " + e.toString());
        } finally {
            consultaMarca();
        }
    }

    private static void trataAcaoCadastroProduto(int acao) {
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
        } catch(Exception e) {
            System.out.println("Erro ao incluir o produto: " + e.toString());
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
        } catch(Exception e) {
            System.out.println("Erro ao alterar o produto: " + e.toString());
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
        } catch(Exception e) {
            System.out.println("Erro ao deletar o produto: " + e.toString());
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
        } catch(Exception e) {
            System.out.println("Erro ao incluir o usuario: " + e.toString());
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
        } catch(Exception e) {
            System.out.println("Erro ao alterar o usuario: " + e.toString());
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
        } catch(Exception e) {
            System.out.println("Erro ao deletar o usuario: " + e.toString());
        } finally {
            consultaUsuario();
        }
    }

    private static void criaUsuarios() {
//        usuarios.put("celio"  , new Usuario(1, "programador"  , new Pessoa("Célio"  , "1")));
//        usuarios.put("thiago" , new Usuario(2, "programador"  , new Pessoa("Thiago" , "2")));
//        usuarios.put("rafael" , new Usuario(3, "desenvolvedor", new Pessoa("Rafael" , "3")));
//        usuarios.put("marcelo", new Usuario(4, "desenvolvedor", new Pessoa("Marcelo", "4")));
    }

    private static void criaProdutos() {
        Marca elmaChips = new Marca(1, "ElmaChips");
        Marca coca      = new Marca(2, "Coca-Cola");
        produtos.put("doritos", new Produto(1, "Doritos", elmaChips));
        produtos.put("ruffles", new Produto(2, "Ruffles", elmaChips));
        produtos.put("cheetos", new Produto(3, "Cheetos", elmaChips));
        produtos.put("coca"   , new Produto(4, "Coca-Cola", coca));
        produtos.put("fanta"  , new Produto(5, "Fanta"    , coca));
    }

    private static void criaEstoqueInicial() {
        Compra compra1_2020 = new Compra(1, 2020, new Pessoa("Pepsico", "XX.XXX.XXX/0001-XX"));
        compra1_2020.adicionaItem(produtos.get("doritos"), 10, 100);
        compra1_2020.adicionaItem(produtos.get("ruffles"),  5,  75);
        compra1_2020.adicionaItem(produtos.get("cheetos"), 10,  10);

        Compra compra2_2020 = new Compra(2, 2020, new Pessoa("João do Caminhão", "000.123.456-78"));
        compra2_2020.adicionaItem(produtos.get("coca"), 10, 50);

        compra1_2020.movimentaEstoque(usuarios.get("celio"));
        compra2_2020.movimentaEstoque(usuarios.get("thiago"));
    }

    private static void imprimeEstoqueInicial() {
        System.out.println("*** Estoque Inicial ***");
        imprimeEstoque();
    }

    private static void imprimeEstoque() {
        Estoque.estoque.entrySet().forEach(pair -> {
            System.out.println(pair.getValue());
        });
    }

    private static void criaEntradas() {
        Compra compra1_2021 = new Compra(1, 2021, new Pessoa("Mercadão", "CNPJ"));
        compra1_2021.adicionaItem(produtos.get("ruffles"), 2, 20);
        compra1_2021.adicionaItem(produtos.get("fanta")  , 1,  5);

        Compra compra2_2021 = new Compra(2, 2021, new Pessoa("Pubzinho", "CNPJ 2"));
        compra2_2021.adicionaItem(produtos.get("coca")   , 1, 6);
        compra2_2021.adicionaItem(produtos.get("doritos"), 1, 8);

        compra1_2021.movimentaEstoque(usuarios.get("rafael"));
        compra2_2021.movimentaEstoque(usuarios.get("marcelo"));

        System.out.println("");
        System.out.println("*** Compras ***");
        System.out.println(compra1_2021);
        System.out.println(compra2_2021);
    }

    private static void imprimeEstoquePosCompras() {
        System.out.println("*** Estoque Após Compras ***");
        imprimeEstoque();
    }

    private static void criaSaidas() {
        Venda venda1_2021 = new Venda(1, 2021);
        venda1_2021.adicionaItem(produtos.get("ruffles"), 3, 20);
        venda1_2021.adicionaItem(produtos.get("doritos"), 1,  8);

        Venda venda2_2021 = new Venda(2, 2021);
        venda2_2021.adicionaItem(produtos.get("cheetos"), 1, 10);
        venda2_2021.adicionaItem(produtos.get("doritos"), 1,  8);

        venda1_2021.movimentaEstoque(usuarios.get("celio"));
        venda2_2021.movimentaEstoque(usuarios.get("marcelo"));

        System.out.println("");
        System.out.println("*** Vendas ***");
        System.out.println(venda1_2021);
        System.out.println(venda2_2021);
    }


    private static void imprimeEstoqueFinal() {
        System.out.println("*** Estoque Final ***");
        imprimeEstoque();
    }

}