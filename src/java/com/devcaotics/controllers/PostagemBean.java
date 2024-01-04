
package com.devcaotics.controllers;

import com.devcaotics.model.dao.ManagerDao;
import com.devcaotics.model.dao.PostagemDao;
import com.devcaotics.model.negocio.Pet;
import com.devcaotics.model.negocio.Postagem;
import com.devcaotics.model.negocio.Tutor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.IOUtils;



@ManagedBean
@ViewScoped
public class PostagemBean {

    private PostagemDao postagemDao = PostagemDao.getInstance();
    private Postagem postagem = new Postagem();
   


   

   
public String realizarPostagem() {
    postagem.setDataHoraPostagem(new Date());
    
    postagem.setTextoPostagem(postagem.getTextoPostagem());
    
    Pet pet= ((PetController) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true))
                .getAttribute("petController")).getSelectionPet();
    
    
    
    Tutor tutor = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(true))
                    .getAttribute("loginController")).getTutorLogado();
    
    postagem.setPet(pet);
    postagem.setTutor(tutor);
    postagemDao.salvarPostagem(postagem);

    // Redireciona para a página index_1.xhtml com os parâmetros de vídeo
    return "index_1";
}

    public List<Postagem> postagemVideos() {

        Pet pet = ((PetController) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true))
                .getAttribute("petController")).getSelectionPet();

        List<Postagem> postagems = ManagerDao.getCurrentInstance().read("select p from Postagem p where p.pet.codigo = " + pet.getCodigo() + " order by p.dataHoraPostagem desc", Postagem.class);
        
        return postagems;
    }
    
    public String formatarVideo(byte[] blob) {

        return blob != null ? Base64.getEncoder().encodeToString(blob) : "";

    }


    // Método utilitário para codificar um vídeo em Base64
    private String encodeVideo(byte[] video) {
        return video != null ? Base64.getEncoder().encodeToString(video) : "";
    }



    // Método para recuperar a lista de postagens
    public List<Postagem> listarPostagens() {
        return postagemDao.listarPostagens();
    }
    


public void handleFileUploadPet(FileUploadEvent event) {
    if (event != null && event.getFile() != null) {
        try {
            InputStream inputStream = event.getFile().getInputstream();
            byte[] videoBytes = convertInputStreamToByteArray(inputStream);

            postagem.setVideoPet(videoBytes);
            System.out.println("Vídeo do Pet carregado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
            addErrorMessage("Erro ao converter o arquivo de vídeo do pet para bytes.");
        }
    } else {
        addErrorMessage("Erro ao carregar arquivo de vídeo do pet. Evento ou arquivo nulo.");
    }
}

public void handleFileUploadTutor(FileUploadEvent event) {
    if (event != null && event.getFile() != null) {
        try {
            InputStream inputStream = event.getFile().getInputstream();
            byte[] videoBytes = convertInputStreamToByteArray(inputStream);

            postagem.setVideoTutor(videoBytes);
            System.out.println("Vídeo do Tutor carregado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
            addErrorMessage("Erro ao converter o arquivo de vídeo do tutor para bytes.");
        }
    } else {
        addErrorMessage("Erro ao carregar arquivo de vídeo do tutor. Evento ou arquivo nulo.");
    }
}

private byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    int nRead;
    byte[] data = new byte[16384];

    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
        buffer.write(data, 0, nRead);
    }

    buffer.flush();
    return buffer.toByteArray();
}

    public List<Postagem> videosSeguindo() {
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
                .getSession(true)).getAttribute("loginController")).getTutorLogado();
        List<Pet> pets = ManagerDao.getCurrentInstance().read("select p from Pet p join Tutor t where t.codigo = " + tutorLogado.getCodigo(), Tutor.class);

        List<Pet> petsSeguindo = new ArrayList<>();
        for (Pet p : pets) {
            for (Pet ps : p.getSeguindo()) {
                petsSeguindo.add(ps);
            }
        }

        List<Postagem> posts = new ArrayList<>();

        for (Pet p : petsSeguindo) {
            List<Postagem> postagemList = ManagerDao.getCurrentInstance().read("select po from Postagem po where po.pet.codigo = " + p.getCodigo(), Postagem.class);
            if (!postagemList.isEmpty()) {
                posts.add(postagemList.get(0));
            }
        }

        return posts;
    }


    
    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }
    

    public PostagemDao getPostagemDao() {
        return postagemDao;
    }

    public void setPostagemDao(PostagemDao postagemDao) {
        this.postagemDao = postagemDao;
    }

    public Postagem getPostagem() {
        return postagem;
    }

    public void setPostagem(Postagem postagem) {
        this.postagem = postagem;
    }

    
}
