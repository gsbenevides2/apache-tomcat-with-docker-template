package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Conta;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Contas;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica.PoliticaValidacaoSaldo;

public class ConsultaSaldo {
    private final Contas contas;
    private final PoliticaValidacaoSaldo politicaValidacaoSaldo;

    public ConsultaSaldo(Contas contas, PoliticaValidacaoSaldo politicaValidacaoSaldo) {
        this.contas = contas;
        this.politicaValidacaoSaldo = politicaValidacaoSaldo;
    }

    public double consultarSaldo(String numeroDaConta) {
        if(numeroDaConta == null || numeroDaConta.isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser nulo ou vazio");
        }
        Conta conta = contas.consultarPorNumero(numeroDaConta);
        if(conta == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }
        conta.setPoliticaValidacaoSaldo(politicaValidacaoSaldo);
        return conta.getSaldo();

    }

}
