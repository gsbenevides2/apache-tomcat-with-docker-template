<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Aplicação bancária</title>
    </head>
    <style>
        label {
            display: inline-block;
            width: 200px;
        }
    </style>
    <body>
        <h1>Aplicação bancária</h1>
        <c:if test="${resultado == 'CONTA_INEXISTENTE'}">
            <span class="error">A conta informada não existe.</span>
        </c:if>
        <c:if test="${resultado == 'VALOR_INVALIDO'}">
            <span class="error">O valor informado é inválido.</span>
        </c:if>
        <form method="POST" action="realizarTransacao">
            <label for="conta">Conta:</label>
            <input type="text" id="conta" name="numeroConta" required />
            <br>
            <label for="codigoPix">Código PIX:</label>
            <input type="text" id="codigoPix" name="codigoPix" required />
            <br>
            <label for="valor">Valor (R$):</label>
            <input type="number" id="valor" name="valor" step="any" min="1" required />
            <br>
            <label for="numeroContaDestino">Conta de Destino da Transferencia:</label>
            <input type="text" id="numeroContaDestino" name="numeroContaDestino" required />
            <br>
            <label for="codigoBoleto">Código do Boleto:</label>
            <input type="text" id="codigoBoleto" name="codigoBoleto" required />
            <br>
            <button type="submit" name="abrir">Abrir</button>
            <button type="submit" name="sacar">Sacar</button>
            <button type="submit" name="depositar">Depositar</button>
            <button type="submit" name="realizarPix">Fazer PIX</button>
            <button type="submit" name="realizarTransferencia">Transferir</button>
            <button type="submit" name="pagarBoleto">Pagar Boleto</button>
            <hr />
            <!-- Expression Language (EL) -->
            <p>O saldo atual da conta é R$ ${param['saldo']}</p>
        </form>
    </body>
</html>
