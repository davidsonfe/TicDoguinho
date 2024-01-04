/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devcaotics.model.negocio;


import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "postagem")
public class Postagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] videoPet;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] videoTutor;
    
    @Column(name = "data_hora_postagem")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraPostagem;
    
    @ManyToOne
    @JoinColumn(name = "pet_codigo")
    private Pet pet;
    
    
     @ManyToOne
    @JoinColumn(name = "tutor_codigo")
    private Tutor tutor;
     
     
    @Column(name = "texto_postagem") 
    private String textoPostagem;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getVideoPet() {
        return videoPet;
    }

    public void setVideoPet(byte[] videoPet) {
        this.videoPet = videoPet;
    }

    public byte[] getVideoTutor() {
        return videoTutor;
    }

    public void setVideoTutor(byte[] videoTutor) {
        this.videoTutor = videoTutor;
    }

    public Date getDataHoraPostagem() {
        return dataHoraPostagem;
    }

    public void setDataHoraPostagem(Date dataHoraPostagem) {
        this.dataHoraPostagem = dataHoraPostagem;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public String getTextoPostagem() {
        return textoPostagem;
    }

    public void setTextoPostagem(String textoPostagem) {
        this.textoPostagem = textoPostagem;
    }
}
