package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Conta;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Contas;

public class AbrirConta {
    
    private final Contas contas;

    public AbrirConta(Contas contas) {
        this.contas = contas;
    }
    
    public void executar(String numeroConta, String codigoPix) {
        Conta conta = new Conta(numeroConta);
        conta.setCodigoPix(codigoPix);
        
        try {
            contas.iniciarTransacao();
            contas.abrir(conta);
            contas.confirmarTransacao();
        } catch(Throwable e) {
            contas.cancelarTransacao();
        }
    }
    
}
