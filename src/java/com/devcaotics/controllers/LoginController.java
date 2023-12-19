/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devcaotics.controllers;

import com.devcaotics.model.dao.ManagerDao;
import com.devcaotics.model.negocio.Tutor;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Davidson Felix
 */
@ManagedBean
@SessionScoped
public class LoginController {

    private Tutor tutorLogado;
    
    public String realizarLogin(String usuario, String senha) {

        try {
            Tutor uLogin = (Tutor) ManagerDao.getCurrentInstance()
                    .read("select u from Tutor u"
                            + " where u.usuario = '" + usuario
                            + "' and u.senha = '" + senha+"'", Tutor.class).get(0);
            
            
            this.tutorLogado = uLogin;
            
            return "indexTutor";
        } catch (Exception e) {
            
            e.printStackTrace();
            FacesContext.getCurrentInstance()
                    .addMessage(null, 
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao Logar","Tutor e/ou senha estão incorretos"));
            return null;
        }

    }
    
    
    
    
    public String logout(){
        this.tutorLogado = null;
        
        return "login";
    }

    public Tutor getTutorLogado() {
        return tutorLogado;
    }

    public void setTutorLogado(Tutor tutorLogado) {
        this.tutorLogado = tutorLogado;
    }
    
    

}
