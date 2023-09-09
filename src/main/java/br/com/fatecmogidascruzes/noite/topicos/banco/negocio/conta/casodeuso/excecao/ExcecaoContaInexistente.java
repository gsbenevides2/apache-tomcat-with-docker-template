package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.excecao;

public class ExcecaoContaInexistente extends RuntimeException {

    public ExcecaoContaInexistente(String mensagem) {
        super(mensagem);
    }
    
}
