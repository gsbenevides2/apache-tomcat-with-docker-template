package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta;

public interface Contas {

    void abrir(Conta conta);

    void atualizar(Conta conta);

    Conta consultarPorNumero(String numero);
    
    Conta consultarPorPix(String codigoPix);
    
    void iniciarTransacao();
    
    void confirmarTransacao();
    
    void cancelarTransacao();
    
}
