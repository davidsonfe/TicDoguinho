/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devcaotics.controllers;

import com.devcaotics.model.dao.ManagerDao;
import com.devcaotics.model.negocio.CompartilharPet;
import com.devcaotics.model.negocio.Tutor;
import static com.sun.faces.facelets.util.Path.context;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import javax.faces.context.ExternalContext;




/**
 *
 * @author Davidson Felix
 */

@ManagedBean
@SessionScoped
public class TutorController {
    
    private Tutor tutorCadastro;
    private Tutor selection;
    private String modalType;
    private StreamedContent imagemStream;

    private UploadedFile file;
    
    @PostConstruct
    public void init(){
        this.tutorCadastro = new Tutor();
        this.modalType = "create";
    }
    
    public void inserir(String confirma){
        
        if(!this.tutorCadastro.getSenha().equals(confirma)){
            
            FacesContext.getCurrentInstance().addMessage("formCadTutor:pswSenha", 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro Severo","A senha e a confirmação não batem!"));
            
            return;
        }
        
        ManagerDao.getCurrentInstance().insert(this.tutorCadastro);
        
        this.tutorCadastro = new Tutor();
        
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage("Tutor cadastrado com sucesso!"));
        
    }
    
    public String imagemTutorLogado() {
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(true))
                    .getAttribute("loginController")).getTutorLogado();
         byte[] blob = tutorLogado.getFoto();
         return blob != null ? Base64.getEncoder().encodeToString(blob) : "";
    }
    
    public void handleFileUpload(FileUploadEvent e) throws IOException{
         byte[] blob = new byte[(int) e.getFile().getSize()];
        e.getFile().getInputstream().read(blob);
        UploadedFile upFile = e.getFile();

        if (upFile != null && !upFile.getFileName().isEmpty()) {
            Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(true))
                    .getAttribute("loginController")).getTutorLogado();
            tutorLogado.setFoto(blob);
            FacesMessage message = new FacesMessage("Successo!", upFile.getFileName() + " foi salva.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro.", "Não foi possível salvar a imagem.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public String editarFoto() {
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(true))
                    .getAttribute("loginController")).getTutorLogado();
        ManagerDao.getCurrentInstance().update(tutorLogado);
        
        FacesContext.getCurrentInstance()
                .addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Sucesso!", "foto modificada com Sucesso"));
        
        return "perfilTutor";
    }


    
    public List<Tutor> readTutores(){
        
        List<Tutor> tutores = null;
        
        tutores = ManagerDao.getCurrentInstance()
                .read("select u from Tutor u", Tutor.class);
        
        return tutores;
        
    }

    public Tutor getTutorCadastro() {
        return tutorCadastro;
    }

    public void setTutorCadastro(Tutor tutorCadastro) {
        this.tutorCadastro = tutorCadastro;
    }

    public Tutor getSelection() {
        return selection;
    }

    public void setSelection(Tutor selection) {
        this.selection = selection;
    }
    
    public String alterar(){
        
        ManagerDao.getCurrentInstance().update(this.selection);
        
        FacesContext.getCurrentInstance()
                .addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Sucesso!", "tutor Cadastrado com Sucesso"));
        
        return "tutores";
    }
    
    public String editarPerfil(){
        
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(true))
                    .getAttribute("loginController")).getTutorLogado();
        ManagerDao.getCurrentInstance().update(tutorLogado);
        
        FacesContext.getCurrentInstance()
                .addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Sucesso!", "tutor alterado com Sucesso"));
        
        return "perfilTutor.xhtml";
        
    }
    
    public void deletar(){
        
        ManagerDao.getCurrentInstance().delete(this.selection);
        
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage("Sucesso!", "Infelizmente você deletou seu tutor"));
        
    }
    
    public void alterarSenha(String senha, String novaSenha, String confirma){
        
        //código para recuperar qualquer atributo na sessão
        Tutor uLogado = ((LoginController)((HttpSession)FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true))
                .getAttribute("loginController")).getTutorLogado();
        
    
        if(!uLogado.getSenha().equals(senha)){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("A senha digitada está incorreta. "
                            + "Por favor, digite novamente de forma correta, seu animal"));
            return ;
        }
        
        if(!novaSenha.equals(confirma)){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("A nova senha não bate com a confirmação. "
                            + "Por favor, digite novamente de forma correta."));
            return ;
        }
        
        uLogado.setSenha(novaSenha);
        
        ManagerDao.getCurrentInstance().update(uLogado);
        
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Senha alterada com sucesso!"));
    }
    
    
    public void modalType(String type){
        this.modalType = type;
    }
    
    public String getModalType() {
        return modalType;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    
    
}
