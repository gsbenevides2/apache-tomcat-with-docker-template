package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica.PoliticaValidacaoSaldo;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.excecao.ExcecaoArgumentoInvalido;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.excecao.ExcecaoPoliticaValidacaoSaldoObrigatoria;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.excecao.ExcecaoSaldoIlegal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Conta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String numero;
    private String codigoPix;
    private double saldo;
    // ATENÇÃO: qualquer mudança em transacoes deve ser feita com
    // addTransacao
    // Invariante para garantir que o saldo bate com a soma das
    // transações.
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private final Set<Transacao> transacoes = new HashSet<>();

    @Transient
    private PoliticaValidacaoSaldo politicaValidacaoSaldo;

    private Conta() {
    }
    
    public Conta(String numero) {
        setNumero(numero);
    }
    
    public Conta(String numero, PoliticaValidacaoSaldo politicaValidacaoSaldo) {
        setNumero(numero);
        setPoliticaValidacaoSaldo(politicaValidacaoSaldo);
    }
    
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }
    
    public String getNumero() {
        return numero;
    }

    private void setNumero(String numero) {
        if(numero == null || numero.trim().isEmpty()) {
            throw new ExcecaoArgumentoInvalido("O número é obrigatório");
        }
        this.numero = numero;
    }
    
    public String getCodigoPix() {
        return codigoPix;
    }

    public void setCodigoPix(String codigoPix) {
        if(codigoPix == null || codigoPix.trim().isEmpty()) {
            throw new ExcecaoArgumentoInvalido("O código PIX é obrigatório");
        }
        this.codigoPix = codigoPix;
    }

    public double getSaldo() {
        return saldo;
    }

    private void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Transacao> getTransacoes() {
        return new ArrayList<>(transacoes);
    }
    
    private void addTransacao(Transacao transacao) {
        double novoSaldo = saldo + transacao.getValor();
        
        garantirExistenciaPoliticaValidacaoSaldo();
        if(politicaValidacaoSaldo.eValido(this, novoSaldo)) {
            transacoes.add(transacao);
            setSaldo(saldo + transacao.getValor());
        } else {
            throw new ExcecaoSaldoIlegal("O saldo não pode ser menor que 1000");
        }
    }

    public void sacar(double valor, String descricao) {
        addTransacao(new Transacao(valor > 0 ? -valor : valor, descricao));
    }
    
    public void depositar(double valor, String descricao) {
        addTransacao(new Transacao(valor < 0 ? -valor : valor, descricao));
    }

    public PoliticaValidacaoSaldo getPoliticaValidacaoSaldo() {
        return politicaValidacaoSaldo;
    }

    public void setPoliticaValidacaoSaldo(PoliticaValidacaoSaldo politicaValidacaoSaldo) {
        if(politicaValidacaoSaldo == null) {
            throw new ExcecaoArgumentoInvalido("A política de validação de saldo é obrigatória");
        }
        this.politicaValidacaoSaldo = politicaValidacaoSaldo;
    }
    
    public void garantirExistenciaPoliticaValidacaoSaldo() {
        if(politicaValidacaoSaldo == null) {
            throw new ExcecaoPoliticaValidacaoSaldoObrigatoria("A política de validação de saldo é obrigatória");
        }
    }
    
}
