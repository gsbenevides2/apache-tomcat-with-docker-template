package br.com.fatecmogidascruzes.noite.topicos.banco.comum;

import br.com.fatecmogidascruzes.noite.topicos.banco.infraestrutura.conta.persistencia.ContaRepositorioJPA;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.*;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica.PoliticaValidacaoSaldo;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica.PoliticaValidacaoSaldoComum;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Contas;

public class LocalizadorServico {

    public static PoliticaValidacaoSaldo politicaAtualizacaoSaldo() {
        return new PoliticaValidacaoSaldoComum();
    }

    public static Contas contaRepositorio() {
        EntityManagerFactory fabricaDAOGenerico = Persistence.createEntityManagerFactory("AplicacaoBancariaPU");
        ContaRepositorioJPA contaRepositorio = new ContaRepositorioJPA(fabricaDAOGenerico.createEntityManager());
        return contaRepositorio;
    }

    public static RealizarPix realizarPix() {
        return new RealizarPix(contaRepositorio(), politicaAtualizacaoSaldo());
    }

    public static Depositar depositar() {
        return new Depositar(contaRepositorio(), politicaAtualizacaoSaldo());
    }

    public static Sacar sacar() {
        return new Sacar(contaRepositorio(), politicaAtualizacaoSaldo());
    }

    public static AbrirConta abrirConta() {
        return new AbrirConta(contaRepositorio());
    }

    public static RealizarTransferencia realizarTransferencia() {
        return new RealizarTransferencia(contaRepositorio(), politicaAtualizacaoSaldo());
    }

    public static PagarBoleto pagarBoleto() {
        return new PagarBoleto(contaRepositorio(), politicaAtualizacaoSaldo());
    }

    public static ConsultaSaldo consultarSaldo() {
        return new ConsultaSaldo(contaRepositorio(), politicaAtualizacaoSaldo());
    }
}
