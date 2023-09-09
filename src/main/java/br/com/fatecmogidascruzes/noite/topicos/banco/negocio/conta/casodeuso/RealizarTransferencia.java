/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Conta;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Contas;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.excecao.ExcecaoContaInexistente;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica.PoliticaValidacaoSaldo;

/**
 *
 * @author Fatec
 */
public class RealizarTransferencia {
    private final Contas contas;
    private final PoliticaValidacaoSaldo politicaAtualizacaoSaldo;
    public RealizarTransferencia(Contas contas, PoliticaValidacaoSaldo politicaAtualizacaoSaldo) {
        this.contas = contas;
        this.politicaAtualizacaoSaldo = politicaAtualizacaoSaldo;
    }

    public void executar(String numeroContaOrigem, String numeroContaDestino, double valor) {
        try {
            Conta contaOrigem = contas.consultarPorNumero(numeroContaOrigem);
            Conta contaDestino = contas.consultarPorNumero(numeroContaDestino);
            if(contaOrigem == null) {
                throw new ExcecaoContaInexistente("A conta de origem com número " + numeroContaOrigem + " não existe");
            }
            if(contaDestino == null) {
                throw new ExcecaoContaInexistente("A conta de destino com número " + numeroContaDestino + " não existe");
            }
            contaOrigem.setPoliticaValidacaoSaldo(politicaAtualizacaoSaldo);
            contaDestino.setPoliticaValidacaoSaldo(politicaAtualizacaoSaldo);

            contaOrigem.sacar(valor, "Transferência para a conta " + numeroContaDestino + " no valor de R$" + valor);
            contaDestino.depositar(valor, "Transferência da conta " + numeroContaOrigem + " no valor de R$" + valor);
            contas.iniciarTransacao();
            contas.atualizar(contaOrigem);
            contas.atualizar(contaDestino);
            contas.confirmarTransacao();
        } catch(Throwable e) {
            contas.cancelarTransacao();
        }
    }
}
