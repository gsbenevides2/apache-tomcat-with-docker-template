package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica.PoliticaValidacaoSaldo;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Conta;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Conta;

public class PoliticaValidacaoSaldoComum implements PoliticaValidacaoSaldo {
 
    private static final double MENOR_SALDO_POSSIVEL = -1000;
    
    @Override
    public boolean eValido(Conta conta, double saldoDesejado) {
        return saldoDesejado >= MENOR_SALDO_POSSIVEL;
    }
    
}
