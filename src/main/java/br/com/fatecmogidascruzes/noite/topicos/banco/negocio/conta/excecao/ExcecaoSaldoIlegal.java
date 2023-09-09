package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.excecao;

public class ExcecaoSaldoIlegal extends RuntimeException {

    public ExcecaoSaldoIlegal(String mensagem) {
        super(mensagem);
    }
    
}
