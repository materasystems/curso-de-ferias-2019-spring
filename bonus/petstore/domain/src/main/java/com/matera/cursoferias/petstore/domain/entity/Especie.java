package com.matera.cursoferias.petstore.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Especie extends EntidadeBase {

    @Column
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
