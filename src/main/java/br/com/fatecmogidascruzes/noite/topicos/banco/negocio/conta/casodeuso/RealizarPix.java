package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Conta;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Contas;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.excecao.ExcecaoContaInexistente;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica.PoliticaValidacaoSaldo;

public class RealizarPix {
    
    private final Contas contas;
    private final PoliticaValidacaoSaldo politicaValidacaoSaldo;

    public RealizarPix(Contas contas, PoliticaValidacaoSaldo politicaValidacaoSaldo) {
        this.contas = contas;
        this.politicaValidacaoSaldo = politicaValidacaoSaldo;
    }
    
    public void executar(String numeroContaOrigem,
                         String codigoPixDestino,
                         double valor) {
        Conta contaOrigem = contas.consultarPorNumero(numeroContaOrigem);
        if(contaOrigem == null) {
            throw new ExcecaoContaInexistente("A conta de origem com número " + numeroContaOrigem + " não existe");
        }
        
        Conta contaDestino = contas.consultarPorPix(codigoPixDestino);
        if(contaDestino == null) {
            throw new ExcecaoContaInexistente("A conta de destino com código pix " + codigoPixDestino + " não existe");
        }
        
        contaOrigem.setPoliticaValidacaoSaldo(politicaValidacaoSaldo);
        contaDestino.setPoliticaValidacaoSaldo(politicaValidacaoSaldo);
        
        contaOrigem.sacar(valor, "Pix no valor de R$" + valor);
        contaDestino.depositar(valor, "Pix no valor de R$" + valor);
        
        try {
            contas.iniciarTransacao();
            contas.atualizar(contaOrigem);
            contas.atualizar(contaDestino);
            contas.confirmarTransacao();
        } catch(Throwable e) {
            contas.cancelarTransacao();
        }
    }
    
}
