package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Conta;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Contas;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.excecao.ExcecaoContaInexistente;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica.PoliticaValidacaoSaldo;

/**
 *
 * @author Fatec
 */
public class PagarBoleto {
    private final Contas contas;
    private final PoliticaValidacaoSaldo politicaValidacaoSaldo;
    public PagarBoleto(Contas contas, PoliticaValidacaoSaldo politicaValidacaoSaldo){
        this.contas = contas;
        this.politicaValidacaoSaldo = politicaValidacaoSaldo;
    }

    public void executar(
            String numeroContaPagadora,
            String numeroBoleto,
            double valorBoleto
    ) {
        if (numeroContaPagadora == null || numeroContaPagadora.trim().isEmpty()) {
            throw new IllegalArgumentException("Número da conta pagadora não pode ser nulo ou vazio");
        }
        if (numeroBoleto == null || numeroBoleto.trim().isEmpty()) {
            throw new IllegalArgumentException("Número do boleto não pode ser nulo ou vazio");
        }
        if (valorBoleto <= 0) {
            throw new IllegalArgumentException("Valor do boleto deve ser maior que zero");
        }
        Conta contaPagadora = contas.consultarPorNumero(numeroContaPagadora);
        if (contaPagadora == null) {
            throw new ExcecaoContaInexistente("Conta pagadora não existe");
        }
        contaPagadora.setPoliticaValidacaoSaldo(politicaValidacaoSaldo);
        try {
            contaPagadora.sacar(valorBoleto, "Pagamento de boleto " + numeroBoleto);
            contas.iniciarTransacao();
            contas.atualizar(contaPagadora);
            contas.confirmarTransacao();
        } catch (Throwable e) {
            contas.cancelarTransacao();
        }
    }
}
