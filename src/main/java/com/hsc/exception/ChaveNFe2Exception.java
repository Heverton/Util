package com.hsc.exception;

/**
 * Retorna as exceções de erros na composição do código da chave da nota fiscal 
 * eletrônica na versão 2.0.
 * 
 * @author Herberts Cruz
 * @version 1.0
 */
public class ChaveNFe2Exception extends Exception {
    
    public enum Coluna {CODIGOUF, ANOMESEMISSAO, CNPJEMITENTE, MODELO, SERIE, 
        NUMERONFE, FORMAEMISSAO, CODIGONUMERICO, DV};
    private final Coluna coluna;
    
    public ChaveNFe2Exception(String message, Coluna coluna) {
        super(message);
        this.coluna = coluna;
    }

    /**
     * Retorna a coluna da qual o erro se refere.
     * 
     * @return Coluna
     */
    public Coluna getColuna() {
        return coluna;
    }
}
