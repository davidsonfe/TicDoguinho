package com.devcaotics.model.negocio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SeguirPets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Tutor seguidor;

    @ManyToOne
    private Pet petSeguido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tutor getSeguidor() {
        return seguidor;
    }

    public void setSeguidor(Tutor seguidor) {
        this.seguidor = seguidor;
    }

    public Pet getPetSeguido() {
        return petSeguido;
    }

    public void setPetSeguido(Pet petSeguido) {
        this.petSeguido = petSeguido;
    }
}
