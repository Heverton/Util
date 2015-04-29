package br.com.util.string;

/**
 * Tranforma uma String que possua números em uma String em formato de número ou
 * formato de número com ponto flutuante.
 * 
 * @author Herberts Cruz
 * @version 1.0
 */
public class FloatString implements FormatStringInterface {
    
    private String prefix = "";
    private String suffix = "";
    private int precision = 8;
    private int scale = 2;
    private boolean allowsNegative = true;
    private boolean fillDecimalPlacesWithZeros = true;
    private boolean showFloatWithComma = false;

    /**
     * Pega o prefixo atual.
     * 
     * @return 
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Permite inserir um prefixo ao número. 
     * Ex.: R$ 0.00
     * 
     * @param prefix 
     */
    public void setPrefix(String prefix) {
        if (prefix.matches("[0-9,.+-]+")) {
            throw new RuntimeException("O suffix não pode conter caracteres numéricos e nem os caracteres \",.+-\".");
        }
        
        this.prefix = prefix + " ";
    }

    /**
     * Pega o sufixo atual.
     * 
     * @return 
     */
    public String getSuffix() {
        return suffix;
    }
    
    /**
     * Permite inserir um sufixo ao número.
     * Ex.: 0.00 %
     * 
     * @param suffix 
     */
    public void setSuffix(String suffix) {
        if (suffix.matches("[0-9,.+-]+")) {
            throw new RuntimeException("O suffix não pode conter caracteres numéricos e nem os caracteres \",.+-\".");
        }
        
        this.suffix = " " + suffix;
    }

    /**
     * Pega o valor definido para a precisão.
     * 
     * @return 
     */
    public int getPrecision() {
        return precision;
    }
    
    /**
     * Define a precisão do número, ou seja, o limite de dígitos númericos que 
     * o número pode ter, para tanto ele não considera o ponto e o prefixo, o
     * sufixo e os espaços entre eles. O padrão é 8 dígitos numéricos.
     * 
     * 
     * @param precision 
     */
    public void setPrecision(int precision) {
        if(precision == 0) {
            throw new NumberFormatException("A precisão não pode ser igual a zero.");
        }
        this.precision = precision;
    }

    /**
     * Pega o valor definido para a escala.
     * 
     * @return 
     */
    public int getScale() {
        return scale;
    }
    
    /**
     * Informa o número de casas decimais que se deseja ter no número resultante.
     * O padrão é 2(duas) casas decimais. Se for definido como zero, retorna um 
     * número inteiro, independente de existirem ou não casas decimais no número 
     * original, neste caso o número é truncado.
     * 
     * @param scale 
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * Configura o número resultante para permitir ou não números negativos. O padrão é 
     * true, ou seja, permite números negativos.
     * 
     * @param allowsNegative 
     */
    public void setAllowsNegative(boolean allowsNegative) {
        this.allowsNegative = allowsNegative;
    }

    /**
     * Define se o número resultante terá suas casas decimais preenchidas com zeros,
     * caso a quantidade de casas decimais do número originário não alcance o limite 
     * da escala definida.
     * 
     * @param fillDecimalPlacesWithZeros 
     */
    public void setFillDecimalPlacesWithZeros(boolean fillDecimalPlacesWithZeros) {
        this.fillDecimalPlacesWithZeros = fillDecimalPlacesWithZeros;
    }

    /**
     * Verifica se foi configurada a vírgula como símbolo do ponto flutuate do 
     * número resultante.
     * 
     * @return 
     */
    public boolean isShowFloatWithComma() {
        return showFloatWithComma;
    }
    
    /**
     * Define a exibição do número resultante como o ponto flutuante simbolizado por
     * vírgula caso seja definido com true. O padrão é false.
     * 
     * @param showFloatWithComma 
     */
    public void setShowFloatWithComma(boolean showFloatWithComma) {
        this.showFloatWithComma = showFloatWithComma;
    }
    
    @Override
    public String apply(String text) {
        //Se possui os sinais de soma e subtração
        if(text.indexOf("-") != -1 && text.indexOf("+") != -1) {
            throw new NumberFormatException("O número não pode ter dois sinais de "
                    + "definição racional, ou seja, deve ser ou positivo, ou negativo.");
        }
        //Filtro para números
        FilterString filter = new FilterString();
        filter.setTypesFilter(new String[]{"number"});
        filter.setWithSpaces(false);
        filter.setEscapes(",.");
        //Remove todos os caracteres que não são números e substitui as vírgulas
        //por ponto
        String newText = filter.apply(text.replaceAll(",", "."));
        //Acrescenta um zero à esqueda para números válidos sem zero à esquerda
        newText = (newText.matches("\\.[0-9]+")) ? "0" + newText : newText;
        //Se deseja preencher a escala do número com zeros à direita
        if(fillDecimalPlacesWithZeros) {
            //Acrescenta um zero à direita para números válidos sem zero à direita
            newText = (newText.matches("[0-9]+\\.")) ? newText + "0" : newText;
        } else {
            //Retira o ponto de números válidos sem zero à direita
            newText = (newText.matches("[0-9]+\\.")) ? newText.replaceAll("\\.", "") : newText;
        }
        //Define as variáveis
        String newTextSuffix = "";
        String newTextPrefix = "";
        String point = "";
        //Se for um número inteiro
        if(ValidateString.isNumber(newText)) {
            newTextSuffix = Integer.parseInt(newText)+"";
            //Se deseja preencher a escala do número com zeros à direita
            if(fillDecimalPlacesWithZeros) {
                //Adiciona zeros depois do ponto até o número de casas definido
                newTextPrefix = ManipuleString.rpad("", "0", scale);
                //Se for ter casas decimais adiciona o ponto à String
                point = (scale > 0) ? "." : "";
            }
        //Se for um número de ponto flutuante
        } else if(ValidateString.isNumber(newText, true, true)) {
            //Pega a posição do ponto
            int positionPoint = newText.indexOf(".");
            //Pega o valor antes do ponto, retira zeros à esquerda
            newTextSuffix = Integer.parseInt(newText.substring(0, positionPoint))+"";
            //Pega o valor depois do ponto
            newTextPrefix = newText.substring((positionPoint + 1));
            //Se o tamanho do prefixo for maior que a escala desejada
            if(newTextPrefix.length() > scale) {
                //Trunca o valor
                newTextPrefix = newTextPrefix.substring(0, scale);
                //Se for ter casas decimais adiciona o ponto à String
                point = (scale > 0) ? "." : "";
            } else {
                //Se deseja preencher a escala do número com zeros à direita
                if(fillDecimalPlacesWithZeros) {
                    newTextPrefix = ManipuleString.rpad(newTextPrefix, "0", scale);
                }
                //Se for ter casas decimais adiciona o ponto à String
                point = (scale > 0) ? "." : "";
            }
        } else {
            throw new NumberFormatException("A String informada, após retirar os "
                    + "caracteres sobressalentes, não corresponde a um número válido.");
        }

        String negative = "";
        //Se for permitido número negativo
        if(allowsNegative) {
            //Se achou o sinal negativo
            if(text.indexOf("-") != -1) {
                //Retira zeros e o ponto
                String resultant = newText.replaceAll("[0.]", "");
                //Se o resultado não for vazio, ou seja, tiver um número 
                //diferente de zero sem ponto
                if(!resultant.isEmpty()) {
                    negative = "-";
                } 
            }
        }
        //Se a precisão definida é menor que a precisão resultante do número.
        //A precisão resultante é a quantidade de dígitos do sufixo mais a escala.
        if(precision < (newTextSuffix.length() + scale)) {
            throw new NumberFormatException("O número resultante da String enviada "
                    + "tem precisão maior que a esperada.");
        }
        //Monta o valor resultante
        String textResultant = negative + (newTextSuffix + point + newTextPrefix);
        //Adiciona o prefixo e o sufixo, dá um trim() no final para garantir que 
        //não retorne espaços em branco nas laterais na ausência de um deles
        String textFinal = (prefix + textResultant + suffix).trim();
        //Se foi definido que o número resultante vai ter seu ponto flutuante 
        //representado por vírgula, substitui o ponto por vírgula
        return (showFloatWithComma) ? textFinal.replaceAll("\\.", ",") : textFinal;
    }
}
