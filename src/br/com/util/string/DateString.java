package br.com.util.string;

import br.com.util.data.DataHora;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Aplica uma máscara aos caracteres de uma String.
 * 
 * @author Herberts Cruz
 * @version 1.0
 */
public final class DateString implements FormatStringInterface {
    
    private String format = "dd/MM/yyyy";
    private String mask = "##/##/####";
    private boolean allowsAccentuation = false;
    
    public String getFormat() {
        return format;
    }
    public String getMask() {
        return mask;
    }

     
    /**
     * Defini se é permitido inserir caracteres acentuados. O padrão é false, ou seja, 
     * não permite que eles sejam inseridos e os substitui por caracteres equivalentes.
     * 
     * @param allowsAccentuation 
     */
    public void setAllowsAccentuation(boolean allowsAccentuation) {
        this.allowsAccentuation = allowsAccentuation;
    }
              
    @Override
    public String apply(String text) {
            //Se for vazio ou nulo
        if(ValidateString.isNullOrEmpty(text)) {
            return "";
        } else {
           if(text.replace("_", "").length() > 10){ 
                try {
                    //Converte o ToString da data em Date
                    Date date = DataHora.converterToStringEmDate(text);
                    //Converte o Date em uma Data no formato brasileiro
                    SimpleDateFormat formtVisual = new SimpleDateFormat(getFormat());
                    text = formtVisual.format(date)+"";
                } catch (ParseException ex) {
                    Logger.getLogger(DateString.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
            //Array de caracteres da String resultante
            char[] caracteresNewText = new char[mask.length()];
            //Contador do índice dos caracteres de String text
            int countText = 0;
            //Contador do índice dos caracteres de String newText
            int countNewText = 0;
            //Percorre os caracteres da Máscara
            while(countNewText < mask.length()) {
                //Pega a letra atual da máscara
                char letraMaskAtual = mask.charAt(countNewText);
                //Inicia a variável vazia
                char letraAtual = '\u0000';
                //Se já passou o tamanho da String
                if(countText < text.length()) {
                    //Pega a letra atual da String text
                    letraAtual = text.charAt(countText);
                    //Se não aceita acentuação
                    if(!allowsAccentuation) {
                        //Substitui o caracter acentuado por seu relativo sem acentuação
                        letraAtual = ManipuleString.removerAcentos(letraAtual+"").charAt(0);
                    }
                }
                //Pega o char atual
                switch(letraMaskAtual) {
                    case '#':
                        //Se já passou o tamanho da String
                        if(countText < text.length()) {
                            //Se for um caracter numérico
                            if(ValidateString.isNumber(letraAtual+"")) {
                                caracteresNewText[countNewText] = letraAtual;
                                countNewText++;
                            }
                            countText++;
                        } else {
                            caracteresNewText[countNewText] = '_';
                            countNewText++;
                        }
                        break;
                    case 'U':
                        //Se já passou o tamanho da String
                        if(countText < text.length()) {
                            //Se for um caracter alfabético
                            if(ValidateString.isAlpha(letraAtual+"", false, allowsAccentuation)) {
                                //Transforma em maiúsculo e insere no array de caracteres
                                //do novo texto
                                caracteresNewText[countNewText] = (letraAtual+"").toUpperCase().charAt(0);
                                countNewText++;
                            }
                            countText++;
                        } else {
                            caracteresNewText[countNewText] = '_';
                            countNewText++;
                        }
                        break;
                    case 'L':
                        //Se já passou o tamanho da String
                        if(countText < text.length()) {
                            //Se for um caracter alfabético
                            if(ValidateString.isAlpha(letraAtual+"", false, allowsAccentuation)) {
                                //Transforma em maiúsculo e insere no array de caracteres
                                //do novo texto
                                caracteresNewText[countNewText] = (letraAtual+"").toLowerCase().charAt(0);
                                countNewText++;
                            }
                            countText++;
                        } else {
                            caracteresNewText[countNewText] = '_';
                            countNewText++;
                        }
                        break;
                    case 'A':
                        //Se já passou o tamanho da String
                        if(countText < text.length()) {
                            //Se for um caracter alfabético
                            if(ValidateString.isAlnum(letraAtual+"", false, allowsAccentuation)) {
                                //Transforma em maiúsculo e insere no array de caracteres
                                //do novo texto
                                caracteresNewText[countNewText] = (letraAtual+"").toUpperCase().charAt(0);
                                countNewText++;
                            }
                            countText++;
                        } else {
                            caracteresNewText[countNewText] = '_';
                            countNewText++;
                        }
                        break;
                    default:
                        //Por padrão adiciona o caracter da máscara
                        caracteresNewText[countNewText] = letraMaskAtual;
                        countNewText++;
                        break;
                }
            }

            return new String(caracteresNewText);
        }
        
        
    }
}
