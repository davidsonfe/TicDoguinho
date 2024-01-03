package com.devcaotics.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.NavigationHandler;


/**
 *
 * @author david
 */

@ManagedBean
@RequestScoped
public class NavigationBean {

    public String irParaPostagem() {
        // Adicione o código para configurar a data e hora da postagem
        // Pode ser feito através de uma classe de modelo ou diretamente no bean

        // Navegação para a página de postagem
        return "postagem.xhtml?faces-redirect=true";
    }
}