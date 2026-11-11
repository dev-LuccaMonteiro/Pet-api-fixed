package br.com.senai.techbyte.service;

import br.com.senai.techbyte.model.Produto;
import br.com.senai.techbyte.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.*;

public class ProdutoService {
    private final ProdutoRepository repository;


    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto cadastrarProduto(String nome, String descricao, BigDecimal preco) {
        validarDadosProduto(nome, preco ,descricao);

        //regra de negocio:adiciona tag em produtos com preco >=5000
        if (preco.compareTo(new BigDecimal("5000")) >= 0 && !descricao.contains("[VALOR ALTO]")) {
            descricao = "[VALOR ALTO]" + descricao;
        }
        Produto novoProduto = new Produto(null, nome.trim(), descricao.trim(), preco);
        return repository.salvar(novoProduto);
    }

    public List<Produto> listarProdutos() {
        return repository.listarTodos();
    }

    public Optional<Produto> buscarPorID(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID informado deve ser numero e maior que zero");
        }
        return repository.buscarporID(id);
    }

    public boolean atualizarProduto(Long id, String nome, String descricao, BigDecimal preco) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalido para atualizar");
        }
        validarDadosProduto(nome, preco, descricao);

        if (preco.compareTo(new BigDecimal("5000")) >= 0 && !descricao.contains("[VALOR ALTO]")) {
            descricao = "[VALOR ALTO]" + descricao;
        }
        Produto produtoAtualizado = new Produto(id, nome.trim(), descricao.trim(), preco);
        return repository.atualizar(produtoAtualizado);
    }
    public boolean removerProduto(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return repository.deletar(id);
    }
    private void validarDadosProduto(String nome, BigDecimal preco , String descricao) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        if (nome.trim().length() < 3) {
            throw new IllegalArgumentException("O nome do produto deve ter no mínimo 3 caracteres.");
        }
        if (descricao== null || descricao.isEmpty()){
            throw new IllegalArgumentException("o produto deve ter descrição");
        }
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço do produto não pode ser negativo.");
        }
    }
}