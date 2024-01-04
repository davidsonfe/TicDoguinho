/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devcaotics.model.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Davidson Felix
 */
@Entity
public class Pet {
    
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int codigo;
    @Column(name = "nome_pet", length = 30)
    private String nome;
    
    
    @Column(name = "codigo_unico", unique = true)
    private String codigoUnico;
    
    private String raca;
    @Lob
    private byte[] imagem;
    private String porte;
    @Column(length = 2)
    private String sexo;
    private String mesAnoNascimento;
    
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<CompartilharPet> compartilhamentos;
    
    @ManyToMany(mappedBy = "seguindo")
    private List<Pet> seguidor = new ArrayList<>();
    @ManyToMany
    private List<Pet> seguindo = new ArrayList<>();
    
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Pet otherPet = (Pet) obj;

        return Objects.equals(this.getCodigo(), otherPet.getCodigo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCodigo());
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }



    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMesAnoNascimento() {
        return mesAnoNascimento;
    }

    public void setMesAnoNascimento(String mesAnoNascimento) {
        this.mesAnoNascimento = mesAnoNascimento;
    }
    
    public List<CompartilharPet> getCompartilhamentos() {
        return compartilhamentos;
    }

    public void setCompartilhamentos(List<CompartilharPet> compartilhamentos) {
        this.compartilhamentos = compartilhamentos;
    }

    public List<Pet> getSeguidor() {
        return seguidor;
    }

    public void setSeguidor(List<Pet> seguidor) {
        this.seguidor = seguidor;
    }

    public List<Pet> getSeguindo() {
        return seguindo;
    }

    public void setSeguindo(List<Pet> seguindo) {
        this.seguindo = seguindo;
    }
}
