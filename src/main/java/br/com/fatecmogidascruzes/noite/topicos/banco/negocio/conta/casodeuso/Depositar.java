package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Conta;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Contas;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.excecao.ExcecaoContaInexistente;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica.PoliticaValidacaoSaldo;

public class Depositar {
    
    private final Contas contas;    
    private final PoliticaValidacaoSaldo politicaValidacaoSaldo;

    public Depositar(Contas contas, PoliticaValidacaoSaldo politicaValidacaoSaldo) {
        this.contas = contas;
        this.politicaValidacaoSaldo = politicaValidacaoSaldo;
    }
    
    public void executar(String numeroConta, double valor) {
        Conta conta = contas.consultarPorNumero(numeroConta);
        if(conta == null) {
            throw new ExcecaoContaInexistente("A conta com número " + numeroConta + " não existe");
        }
        conta.setPoliticaValidacaoSaldo(politicaValidacaoSaldo);
        
        try {
            conta.depositar(valor, "Depósito de R$ " + valor);
            contas.iniciarTransacao();
            contas.atualizar(conta);
            contas.confirmarTransacao();
        } catch(Throwable e) {
            contas.cancelarTransacao();
        }
    }
    
}
