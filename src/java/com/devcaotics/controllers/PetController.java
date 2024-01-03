/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devcaotics.controllers;

import com.devcaotics.model.dao.ManagerDao;
import com.devcaotics.model.negocio.CompartilharPet;
import com.devcaotics.model.negocio.Pet;
import com.devcaotics.model.negocio.Tutor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Davidson Felix
 */
@ManagedBean
@SessionScoped
public class PetController {

    private Pet petCadastro;

    private Pet selectionPet;
    
    private String nomePesquisa;
    private List<Pet> petsEncontrados;

    @PostConstruct
    public void init() {
        this.petCadastro = new Pet();

    }

    public String inserir() {
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
                .getSession(true)).getAttribute("loginController")).getTutorLogado();
        CompartilharPet compartilharPet = new CompartilharPet();

        if (this.petCadastro != null) {
            UUID codigoUnico = UUID.randomUUID();
            this.petCadastro.setCodigoUnico(codigoUnico.toString());

            compartilharPet.setTutor(tutorLogado);
            compartilharPet.setPet(this.petCadastro);

            ManagerDao.getCurrentInstance().insert(this.petCadastro);
            ManagerDao.getCurrentInstance().insert(compartilharPet);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Sucesso", "O Pet foi cadastrado"));

            this.petCadastro = new Pet();
        }

        return "indexTutor";
    }

    public void handleFileUploadPet(FileUploadEvent e) throws IOException {
        byte[] blob = new byte[(int) e.getFile().getSize()];
        e.getFile().getInputstream().read(blob);
        UploadedFile upFile = e.getFile();

        if (upFile != null && !upFile.getFileName().isEmpty()) {
            this.petCadastro.setImagem(blob);
            FacesMessage message = new FacesMessage("Successo!", upFile.getFileName() + " foi salva.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro.", "Não foi possível salvar a imagem.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public String imagemParaIndexTutor(byte[] blob) {
        return blob != null ? Base64.getEncoder().encodeToString(blob) : "";
    }

    public String imagemDoPetSelectionado() {
        byte[] blob = this.selectionPet.getImagem();
        return blob != null ? Base64.getEncoder().encodeToString(blob) : "";
    }

    public String compartilhar(String codigo) {
        Pet pet = (Pet) ManagerDao.getCurrentInstance().read("select p from Pet p where p.codigoUnico = '" + codigo + "'", Pet.class).get(0);
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
                .getSession(true)).getAttribute("loginController")).getTutorLogado();

        CompartilharPet compartilharPet = new CompartilharPet();
        compartilharPet.setTutor(tutorLogado);
        compartilharPet.setPet(pet);

        ManagerDao.getCurrentInstance().insert(compartilharPet);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pet foi compartilhado."));

        return "indexTutor";
    }

    public List<CompartilharPet> tutores() {
        return ManagerDao.getCurrentInstance().read("select cp from CompartilharPet cp where cp.pet.codigo = " + this.selectionPet.getCodigo(), Pet.class);
    }

    public List<String> imagensDosTutoresDoPet(List<CompartilharPet> tutores) {
        List<String> imagens = new ArrayList<>();
        for (CompartilharPet cp : tutores) {
             byte[] blob = cp.getTutor().getFoto();
            imagens.add(blob != null ? Base64.getEncoder().encodeToString(cp.getTutor().getFoto()) : "");
        }

        return imagens;
    }

    public List<Pet> takeAllPets() {
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
                .getSession(true)).getAttribute("loginController")).getTutorLogado();

        if (tutorLogado != null) {
            List<Pet> pets = null;
            pets = ManagerDao.getCurrentInstance().read("select cp.pet from CompartilharPet cp where cp.tutor.codigo = " + tutorLogado.getCodigo(), CompartilharPet.class);

            return pets;
        }

        return new ArrayList<>();
    }
    
    public String alterar() {

        ManagerDao.getCurrentInstance().update(selectionPet);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "As informações foram atualizadas com sucesso."));

        return "editarPet.xhtml";
    }


    public String excluir() {
    // Lógica de exclusão aqui
    List<CompartilharPet> compartilhamentos = ManagerDao.getCurrentInstance().read("select cp from CompartilharPet cp where cp.pet.codigo = " + selectionPet.getCodigo(), CompartilharPet.class);

    // Excluir os compartilhamentos
    for (CompartilharPet compartilhamento : compartilhamentos) {
        ManagerDao.getCurrentInstance().delete(compartilhamento);
    }

    // Finalmente, excluir o pet
    ManagerDao.getCurrentInstance().delete(selectionPet);

    FacesContext.getCurrentInstance().addMessage("editarPetForm:mensagemExclusao",
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "O pet foi excluído com sucesso."));

    return "editarPet.xhtml";
}

    
    public String selectPetForEditing(Pet pet) {
        this.selectionPet = pet;
        return "editarPet.xhtml?faces-redirect=true";
    }
    
    
    // Adicione o seguinte método ao seu controlador
public void pesquisarPets() {
    if (nomePesquisa != null && !nomePesquisa.isEmpty()) {
        String query = "select p from Pet p where p.nome = '" + nomePesquisa + "'";
        petsEncontrados = ManagerDao.getCurrentInstance().read(query, Pet.class);

        // Lógica adicional, se necessário

    } else {
        addErrorMessage("Digite um nome para realizar a pesquisa.");
    }
}


    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }
    


    public Pet getPetCadastro() {
        return petCadastro;
    }

    public void setPetCadastro(Pet petCadastro) {
        this.petCadastro = petCadastro;
    }

    public Pet getSelectionPet() {
        return selectionPet;
    }

    public void setSelectionPet(Pet selectionPet) {
        this.selectionPet = selectionPet;
    }

    public String getNomePesquisa() {
        return nomePesquisa;
    }

    public void setNomePesquisa(String nomePesquisa) {
        this.nomePesquisa = nomePesquisa;
    }

    public List<Pet> getPetsEncontrados() {
        return petsEncontrados;
    }

    public void setPetsEncontrados(List<Pet> petsEncontrados) {
        this.petsEncontrados = petsEncontrados;
    }
    
}
