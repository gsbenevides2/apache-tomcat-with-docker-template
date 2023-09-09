package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.excecao.ExcecaoArgumentoInvalido;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Transacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
    private double valor;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    private String descricao;

    private Transacao() {}
    
    public Transacao(double valor, String descricao) {
        setValor(valor);
        setDataHora(new Date());
        setDescricao(descricao);
    }
    
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }
    
    public double getValor() {
        return valor;
    }

    private void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataHora() {
        return dataHora;
    }

    private void setDataHora(Date dataHora) {
        if(dataHora == null) {
            throw new ExcecaoArgumentoInvalido("A data/hora da transação é obrigatória");
        }
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    private void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
