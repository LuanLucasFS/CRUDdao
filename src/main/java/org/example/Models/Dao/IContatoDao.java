package org.example.Models.Dao;

import org.example.Entities.Contato;

import java.util.List;

public interface IContatoDao {
    void insert(Contato contato);

    void update(Contato contato);

    void deleteById(Integer id);

    Contato findById(Integer id);

    List<Contato> findAll();
}
