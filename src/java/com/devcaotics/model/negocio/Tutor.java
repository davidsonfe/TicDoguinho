/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devcaotics.model.negocio;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 *
 * @author Davidson Felix
 */

@Entity
public class Tutor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int codigo;
    private String usuario;
    private String senha;
    private String email;
    private boolean mamae;
    @Lob
    private byte[] foto;
    
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    private List<CompartilharPet> compartilharPets;
    
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMamae() {
        return mamae;
    }

    public void setMamae(boolean mamae) {
        this.mamae = mamae;
    }

    public List<CompartilharPet> getCompartilharPets() {
        return compartilharPets;
    }

    public void setCompartilharPets(List<CompartilharPet> compartilharPets) {
        this.compartilharPets = compartilharPets;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    

}
