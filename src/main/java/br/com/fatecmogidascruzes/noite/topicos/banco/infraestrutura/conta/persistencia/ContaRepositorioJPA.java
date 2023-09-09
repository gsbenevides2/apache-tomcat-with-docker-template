package br.com.fatecmogidascruzes.noite.topicos.banco.infraestrutura.conta.persistencia;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Conta;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Contas;

public class ContaRepositorioJPA implements Contas {
    
    private final EntityManager entityManager;

    public ContaRepositorioJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public void abrir(Conta conta) {
        entityManager.persist(conta);
    }
    
    @Override
    public void atualizar(Conta conta) {
        entityManager.merge(conta);
    }
    
    @Override
    public Conta consultarPorNumero(String numero) {
        Query consulta = entityManager.createQuery(
                "SELECT conta FROM Conta conta WHERE conta.numero=:numero"
        );
        consulta.setParameter("numero", numero);
        
        try {
            return (Conta) consulta.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
    
    @Override
    public Conta consultarPorPix(String codigoPix) {
        Query consulta = entityManager.createQuery(
                "SELECT conta FROM Conta conta WHERE conta.codigoPix=:codigoPix"
        );
        consulta.setParameter("codigoPix", codigoPix);
        
        try {
            return (Conta) consulta.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public void iniciarTransacao() {
        entityManager.getTransaction().begin();
    }

    @Override
    public void confirmarTransacao() {
        entityManager.getTransaction().commit();
    }

    @Override
    public void cancelarTransacao() {
        entityManager.getTransaction().rollback();
    }
    
}
