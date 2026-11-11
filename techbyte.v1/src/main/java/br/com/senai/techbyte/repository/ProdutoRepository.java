package br.com.senai.techbyte.repository;

import br.com.senai.techbyte.model.Produto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface ProdutoRepository {
    Produto salvar(Produto produto);
    List<Produto> listarTodos();
    Optional<Produto> buscarporID(Long id);
    boolean atualizar(Produto produtoA);
    boolean deletar(Long id);
}
