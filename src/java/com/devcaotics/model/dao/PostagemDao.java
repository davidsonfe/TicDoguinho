package com.devcaotics.model.dao;

import com.devcaotics.model.negocio.Postagem;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class PostagemDao {

    private static PostagemDao instance = null;
    
    // Obtém a instância da classe ManagerDao
    private ManagerDao managerDao = ManagerDao.getCurrentInstance();

    // Método estático para obter a instância da classe PostagemDao
    public static PostagemDao getInstance() {
        if (instance == null) {
            instance = new PostagemDao();
        }
        return instance;
    }

    // Método para salvar uma postagem no banco de dados
   public void salvarPostagem(Postagem postagem) {
    EntityManager em = managerDao.getEntityManager();
    EntityTransaction transaction = em.getTransaction();

    try {
        transaction.begin();
        em.persist(postagem);
        transaction.commit();

        // Adicione mensagens de log ou depuração
        System.out.println("Postagem salva com sucesso. ID: " + postagem.getId());
    } catch (Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace(); // Trate a exceção apropriadamente
    } finally {
        em.close();
    }
}



    // Método para recuperar todas as postagens do banco de dados
    public List<Postagem> listarPostagens() {
        EntityManager em = managerDao.getEntityManager();
        List<Postagem> postagens = em.createQuery("SELECT p FROM Postagem p", Postagem.class).getResultList();
        em.close();
        return postagens;
    }

    // Adicione outros métodos conforme necessário (atualizar, excluir, etc.)
}
