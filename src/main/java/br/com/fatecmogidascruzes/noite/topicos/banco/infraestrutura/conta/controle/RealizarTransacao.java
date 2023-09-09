package br.com.fatecmogidascruzes.noite.topicos.banco.infraestrutura.conta.controle;

import br.com.fatecmogidascruzes.noite.topicos.banco.comum.LocalizadorServico;
import java.io.IOException;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RealizarTransacao", urlPatterns = {"/realizarTransacao"})
public class RealizarTransacao extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String numeroConta = request.getParameter("numeroConta");
        String codigoPix = request.getParameter("codigoPix");
        String numeroContaDestino = request.getParameter("numeroContaDestino");
        String codigoBoleto = request.getParameter("codigoBoleto");
        String valorStr = request.getParameter("valor");
        double valor = Double.valueOf(valorStr);
        
        if(valor == 0) {
            request.setAttribute("resultado", "VALOR_INVALIDO");
            request.getRequestDispatcher("/banco/index.jsp").forward(request, response);
            return;
        } 
        
        if(request.getParameter("abrir") != null) {
            AbrirConta abrirConta = LocalizadorServico.abrirConta();
            abrirConta.executar(numeroConta, codigoPix);
        } if(request.getParameter("sacar") != null) {
            Sacar sacar = LocalizadorServico.sacar();
            sacar.executar(numeroConta, valor);
        } else if(request.getParameter("depositar") != null) {
            Depositar depositar = LocalizadorServico.depositar();
            depositar.executar(numeroConta, valor);
        } else if(request.getParameter("realizarPix") != null) {
            RealizarPix realizarPix = LocalizadorServico.realizarPix();
            realizarPix.executar(numeroConta, codigoPix, valor);
        } else if(request.getParameter("realizarTransferencia") != null) {
            RealizarTransferencia realizarTransferencia = LocalizadorServico.realizarTransferencia();
            realizarTransferencia.executar(numeroConta, numeroContaDestino, valor);
        } else if(request.getParameter("pagarBoleto") != null){
            PagarBoleto pagarBoleto = LocalizadorServico.pagarBoleto();
            pagarBoleto.executar(numeroConta, codigoBoleto, valor);
        }
        
        // TODO: Pegar saldo da conta
        double saldo = LocalizadorServico.consultarSaldo().consultarSaldo(numeroConta);
        response.sendRedirect(response.encodeRedirectURL("/banco/index.jsp?resultado=SUCESSO&saldo=" + saldo));

    }
   
}
