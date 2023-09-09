package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Conta;

public interface PoliticaValidacaoSaldo {

    boolean eValido(Conta conta, double saldoDesejado);
    
}
