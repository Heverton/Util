package com.hsc.data;

import com.hsc.exception.ParameterException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Heverton Cruz
 * @version 1.3
 */
public abstract class DataHora {

    public static String converterStringEmDate(String text, tipo formato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Enum de constantes com máscaras para DateFormat.
     *
     * Autor: Herberts Cruz Versão: 1.0
     */
    public enum Formato {

        TEMPO_HORA_MINUTOS_SEGUNDOS("HH:mm:ss"),
        EN_DATA_SEPARADOR_TRACO_ANO_4DIGITOS("yyyy-MM-dd"),
        EN_DATA_SEPARADOR_TRACO_ANO_4DIGITOS_E_TEMPO("yyyy-MM-dd HH:mm:ss"),
        EN_DATA_SEPARADOR_TRACO_ANO_2DIGITOS("yy-MM-dd"),
        EN_DATA_SEPARADOR_TRACO_ANO_2DIGITOS_E_TEMPO("yy-MM-dd HH:mm:ss"),
        PT_DATA_SEPARADOR_BARRA_ANO_4DIGITOS("dd/MM/yyyy"),
        PT_DATA_SEPARADOR_BARRA_ANO_4DIGITOS_E_TEMPO("dd/MM/yyyy HH:mm:ss"),
        PT_DATA_SEPARADOR_BARRA_ANO_2DIGITOS("dd/MM/yy"),
        PT_DATA_SEPARADOR_BARRA_ANO_2DIGITOS_E_TEMPO("dd/MM/yy HH:mm:ss"),
        PT_DATA_SEPARADOR_BARRA_DIA_MES_2DIGITOS("dd/MM");

        private String formato;

        private Formato(String formato) {
            this.formato = formato;
        }

        public String getFormato() {
            return formato;
        }
    };

    public enum tipo {

        /**
         * Formato: "dd/MM/yyyy"
         */
        FORMATO_BRASILEIRO_DATA_SEP_BARRA,
        /**
         * Formato: "dd-MM-yyyy"
         */
        FORMATO_BRASILEIRO_DATA_SEP_TRACO,
        /**
         * Formato: "yyyy-MM-dd"
         */
        FORMATO_AMERICANO_DATA_SEP_TRACO,
        /**
         * Formato: "ddMMyyyy"
         */
        FORMATO_BRASILEIRO_DATA_SEP_VAZIO,
        /**
         * Formato: "HH:mm:ss"
         */
        FORMATO_BRASILEIRO_HORA_SEP_PONTO,
        /**
         * Formato: "HHmmss"
         */
        FORMATO_BRASILEIRO_HORA_SEP_VAZIO,
        /**
         * Formato: "dd"
         */
        FORMATO_BRASILEIRO_DATA_DIA,
        /**
         * Formato: "HH:mm"
         */
        FORMATO_BRASILEIRO_HORA_E_MIN_SEP_PONTO
    };

    /**
     * Retorna objeto Date da data atual
     * É correspondente ao new Date() padrão
     * 
     * @return Date
     */
    public static Date dataHoje() {
        return new Date();
    }

    /**
     * Formata a data atual no padrÃ£o brasileiro ou americano
     * FORMATO_BRASILEIRO_DATA_SEP_BARRA = PT = dd/MM/yyyy
     * FORMATO_BRASILEIRO_DATA_SEP_TRACO = PT2 = dd-MM-yyyy
     * FORMATO_BRASILEIRO_DATA_SEP_VAZIO = P4 = ddMMyyyy null = yyyy/MM/dd
     *
     * @param formato
     * @return String com a data de hoje já no formato
     * @throws ParameterException
     */
    public static String formatoData(DataHora.tipo formato) throws ParameterException {
        return formatoData(dataHoje(), formato);
    }

    /**
     * Data no formato dd-mm-YYYY por parametro
     * 
     * @param data
     * @param formato
     * @return
     * @throws ParameterException
     * @throws NullPointerException 
     */
    public static String formatoData(Date data, DataHora.tipo formato) throws ParameterException, NullPointerException {
        SimpleDateFormat formtVisual = null;

        if (formato == DataHora.tipo.FORMATO_BRASILEIRO_DATA_SEP_BARRA) {
            formtVisual = new SimpleDateFormat("dd/MM/yyyy");
            return formtVisual.format(data);
        } else if (formato == DataHora.tipo.FORMATO_BRASILEIRO_DATA_SEP_TRACO) {
            formtVisual = new SimpleDateFormat("dd-MM-yyyy");
            return formtVisual.format(data);
        } else if (formato == DataHora.tipo.FORMATO_BRASILEIRO_DATA_SEP_VAZIO) {
            formtVisual = new SimpleDateFormat("ddMMyyyy");
            return formtVisual.format(data);
        } else if (formato == DataHora.tipo.FORMATO_AMERICANO_DATA_SEP_TRACO) {
            formtVisual = new SimpleDateFormat("yyyy-MM-dd");
            return formtVisual.format(data);
        } else if (formato == null) {
            formtVisual = new SimpleDateFormat("yyyy/MM/dd");
            return formtVisual.format(data);
        } else {
            throw new NullPointerException("Formato inexistênte.");
        }
    }

    /**
     * Formata um objeto Date no padrÃ£o brasileiro ou americano
     *
     * FORMATO_BRASILEIRO_DATA_SEP_BARRA = PT = dd/MM/yyyy
     * FORMATO_BRASILEIRO_DATA_SEP_TRACO = PT2 = dd-MM-yyyy
     * FORMATO_BRASILEIRO_DATA_SEP_VAZIO = P4 = ddMMyyyy null = yyyy/MM/dd
     *
     * @param data
     * @param formato
     * @return String com a data já no formato
     * @throws ParameterException
     */
    public static String formatoData(String data, DataHora.tipo formato) throws ParameterException, NullPointerException {
        SimpleDateFormat formtVisual = null;

        if (formato == DataHora.tipo.FORMATO_BRASILEIRO_DATA_SEP_BARRA) {
            formtVisual = new SimpleDateFormat("dd/MM/yyyy");
            return formtVisual.format(data);
        } else if (formato == DataHora.tipo.FORMATO_BRASILEIRO_DATA_SEP_TRACO) {
            formtVisual = new SimpleDateFormat("dd-MM-yyyy");
            return formtVisual.format(data);
        } else if (formato == DataHora.tipo.FORMATO_BRASILEIRO_DATA_SEP_VAZIO) {
            formtVisual = new SimpleDateFormat("ddMMyyyy");
            return formtVisual.format(data);
        } else if (formato == DataHora.tipo.FORMATO_AMERICANO_DATA_SEP_TRACO) {
            formtVisual = new SimpleDateFormat("yyyy-MM-dd");
            return formtVisual.format(data);
        } else if (formato == null) {
            //Americano
            formtVisual = new SimpleDateFormat("yyyy/MM/dd");
            return formtVisual.format(data);
        } else {
            throw new NullPointerException("Formato inexistênte.");
        }
    }

    public static String formatoHora(String hora, DataHora.tipo formato) throws NullPointerException {
        if (formato == DataHora.tipo.FORMATO_BRASILEIRO_HORA_SEP_PONTO) {
            SimpleDateFormat formtVisual = new SimpleDateFormat("HH:mm:ss");
            return formtVisual.format(hora);
        } else if (formato == DataHora.tipo.FORMATO_BRASILEIRO_HORA_SEP_VAZIO) {
            SimpleDateFormat formtVisual = new SimpleDateFormat("HHmmss");
            return formtVisual.format(hora);
        } else if (formato == DataHora.tipo.FORMATO_BRASILEIRO_HORA_E_MIN_SEP_PONTO) {
            SimpleDateFormat formtVisual = new SimpleDateFormat("HH:mm");
            return formtVisual.format(hora);
        } else {
            throw new NullPointerException("Formato inexistênte.");
        }
    }

    /**
     * Formata um objeto Date no padrão FORMATO_BRASILEIRO_HORA_SEP_PONTO = 1 -
     * HH:mm:ss FORMATO_BRASILEIRO_HORA_SEP_VAZIO = 2 - HHmmss
     * FORMATO_BRASILEIRO_HORA_E_MIN_SEP_PONTO = 3 - HH:mm
     *
     * @param String formato da hora
     * @return String com a hora no formato HH:mm:ss
     */
    public static String formatoHora(Date hora, DataHora.tipo formato) throws NullPointerException {
        if (formato == DataHora.tipo.FORMATO_BRASILEIRO_HORA_SEP_PONTO) {
            SimpleDateFormat formtVisual = new SimpleDateFormat("HH:mm:ss");
            return formtVisual.format(hora);
        } else if (formato == DataHora.tipo.FORMATO_BRASILEIRO_HORA_SEP_VAZIO) {
            SimpleDateFormat formtVisual = new SimpleDateFormat("HHmmss");
            return formtVisual.format(hora);
        } else if (formato == DataHora.tipo.FORMATO_BRASILEIRO_HORA_E_MIN_SEP_PONTO) {
            SimpleDateFormat formtVisual = new SimpleDateFormat("HH:mm");
            return formtVisual.format(hora);
        } else {
            throw new NullPointerException("Formato inexistênte.");
        }
    }

    /**
     * Converte uma String no padrÃ£o brasileiro "dd/MM/yyyy" em um objeto Date
     *
     * FORMATO_BRASILEIRO_DATA_SEP_BARRA = dd/MM/yyyy
     * FORMATO_BRASILEIRO_DATA_DIA = dd FORMATO_BRASILEIRO_DATA_SEP_TRACO =
     * dd-MM-yyyy NULL = yyyy-MM-dd
     *
     * @param String data
     * @param String padrao
     * @return Date
     *
     * @deprecated Pode ser substituído por "converterStringEmDate()"
     */
    public static Date converteStringInDate(String data, DataHora.tipo formato) throws NullPointerException, ParseException, ParameterException {
        DateFormat formatter = null;

        if (data.isEmpty() || data == null) {
            throw new NullPointerException("String data vazia ou nula.");
        }

        // formato.equalsIgnoreCase("PT")
        if (formato == DataHora.tipo.FORMATO_BRASILEIRO_DATA_SEP_BARRA) {
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            return (Date) formatter.parse(data);
        } // formato.equalsIgnoreCase("PT3")
        else if (formato == DataHora.tipo.FORMATO_BRASILEIRO_DATA_DIA) {
            formatter = new SimpleDateFormat("dd");
            return (Date) formatter.parse(data);
        } // formato.equalsIgnoreCase("PT2")
        else if (formato == DataHora.tipo.FORMATO_BRASILEIRO_DATA_SEP_TRACO) {
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            return (Date) formatter.parse(data);
        } else if (formato == null) {
            //Americano
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            return (Date) formatter.parse(data);
        } else {
            throw new NullPointerException("Formato inexistênte.");
        }
    }

    /**
     * Formata a data quando vir neste formato ddmmYYYY para
     * dd(Separado)mm(Separado)YYYY
     *
     * @param String data
     * @param char separado
     * @return String no formato dd-mm-YYYY
     */
    public static String formatarDataComMascara(String data, char separado) {
        char[] array = data.toCharArray();

        String dia = "", mes = "", ano = "";

        for (int j = 0; j < array.length; j++) {
            if (j < 2) {
                dia = dia + array[j];
            } else if (j < 4) {
                mes = mes + array[j];
            } else if (j < 8) {
                ano = ano + array[j];
            }
        }

        return dia + separado + mes + separado + ano;
    }

    /**
     * Formata a data que esta no formato ##-##-#### ou ##/##/###
     *
     * @param data
     * @return Date
     */
    public static Date formatarData(String data) throws NullPointerException {
        GregorianCalendar dtGregorianCalendar = new GregorianCalendar();
        char[] array = (data.replaceAll("/", "").replaceAll("-", "")).toCharArray();
        String dia = "", mes = "", ano = "";

        for (int j = 0; j < array.length; j++) {
            if (j < 2) {
                dia = dia + array[j];
            } else if (j < 4) {
                mes = mes + array[j];
            } else if (j < 8) {
                ano = ano + array[j];
            }
        }

        // Tratamento de ano.
        if (ano.length() == 2) {
            ano = "20" + ano;
        }

        dtGregorianCalendar.set(Integer.parseInt(ano), Integer.parseInt(mes) - 1, Integer.parseInt(dia), 0, 0, 0);
        return dtGregorianCalendar.getTime();
    }

    /**
     * Formata a data quando vir neste formato hhmmss
     *
     * @param String hora
     * @return String no formato hh:mm:ss
     */
    public static String formatarHoraSemMascara(String hora) throws NullPointerException {
        char[] array = hora.toCharArray();

        String h = "", m = "", s = "";

        for (int j = 0; j < array.length; j++) {
            if (j < 2) {
                h = h + array[j];
            } else if (j < 4) {
                m = m + array[j];
            } else if (j < 6) {
                s = s + array[j];
            }
        }

        return h + ":" + m + ":" + s;
    }

    /**
     * Formato de entrada dd/MM/yyyy ou dd-MM-yyyy
     *
     * @param data
     * @return
     */
    public static boolean validaData(String data) throws NullPointerException {
        GregorianCalendar calendar = new GregorianCalendar();

        int dia = 0, mes = 0, ano = 0;
        String diaStr = data.substring(0, 2);
        String mesStr = data.substring(3, 5);
        String anoStr = data.substring(6, 10);

        dia = Integer.parseInt(diaStr);
        //Ajuste menos um para adaptação ao java 
        mes = Integer.parseInt(mesStr)-1;
        ano = Integer.parseInt(anoStr);

        boolean retorno = false;

        //Primeira validação 
        if (dia < 1 || mes < 1 || ano < 1) {
            return retorno;
        } else {
            switch (mes) {
                case GregorianCalendar.FEBRUARY://28 - 29
                    if (calendar.isLeapYear(ano)) {
                        if (dia <= 29) {
                            retorno = true;
                        }
                    } else {
                        if (dia <= 28) {
                            retorno = true;
                        }
                    }
                    break;
                case GregorianCalendar.MAY://Mês de 31
                case GregorianCalendar.JULY://Mês de 31
                case GregorianCalendar.AUGUST://Mês de 31
                case GregorianCalendar.OCTOBER://Mês de 31
                case GregorianCalendar.DECEMBER://Mês de 31
                case GregorianCalendar.JANUARY://Mês de 31
                case GregorianCalendar.MARCH://Mês de 31
                    if (dia <= 31) {
                        retorno = true;
                    }
                    break;
                case GregorianCalendar.APRIL:
                case GregorianCalendar.JUNE:
                case GregorianCalendar.SEPTEMBER:
                case GregorianCalendar.NOVEMBER:
                    if (dia <= 30) {
                        retorno = true;
                    }
                    break;
            }

            return retorno;
        }
    }

    /**
     * Compara e verifica se a primeira data e maior que a segunda.
     *
     * @param d1
     * @param d2
     * @return boolean
     */
    public static boolean compararDataMaior(Date d1, Date d2) {
        if (d1.compareTo(d2) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Compara e verifica se a primeira data e maior e igual que a segunda.
     *
     * @param Date
     * @param Date
     * @return boolean
     */
    public static boolean compararDataMaiorEIgual(Date d1, Date d2) {
        if (d1.compareTo(d2) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Compara e verifica se a primeira data e menor e igual que a segunda.
     *
     * @param Date
     * @param Date
     * @return boolean
     */
    public static boolean compararDataMenorEIgual(Date d1, Date d2) {
        if (d1.compareTo(d2) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Compara e verifica se a primeira data e menor que a segunda.
     *
     * @param Date
     * @param Date
     * @return boolean
     */
    public static boolean compararDataMenor(Date d1, Date d2) {
        if (d1.compareTo(d2) < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Compara e verifica se a primeira data e igual a segunda.
     *
     * @param Date
     * @param Date
     * @return boolean
     */
    public static boolean compararDataIgual(Date d1, Date d2) {
        if (d1.compareTo(d2) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calcula a diferença da dtInicial para a dtFinal.
     *
     * @param Date dtInicial
     * @param Date dtFinal
     * @return quantidade de dias no intervalo.
     */
    public static int qtdDias(Date dtInicial, Date dtFinal) throws Exception {
        // Data inicial.
        Calendar dataInicio = Calendar.getInstance();
        dataInicio.setTime(dtInicial);

        // Data de hoje.
        Calendar dataFinal = Calendar.getInstance();
        dataFinal.setTime(dtFinal);

        // Calcula a diferença entre hoje e da data de inicio.
        long diferenca = dataFinal.getTimeInMillis() - dataInicio.getTimeInMillis();

        // Quantidade de milissegundos em um dia.
        int tempoDia = 1000 * 60 * 60 * 24;
        long diasDiferenca = diferenca / tempoDia;

        return Integer.parseInt(diasDiferenca + "");
    }

    /**
     *
     * @param dtInicial
     * @param qtdDias
     * @return
     * @throws ParameterException
     * @throws java.text.ParseException
     */
    public static String dataAnterior(Date dtInicial, int qtdDias) throws ParameterException, NullPointerException, ParseException {
        // Data inicial.
        GregorianCalendar dataInicio = new GregorianCalendar();
        dataInicio.setTime(dtInicial);

        // Calendar dataInicio = Calendar.getInstance();
        // dataInicio.setTime(dtInicial);
        while (qtdDias >= 0) {
            dataInicio.add(Calendar.DAY_OF_YEAR, -1);
            qtdDias--;
        }

        return DataHora.formatoData(dataInicio.getTime(), DataHora.tipo.FORMATO_BRASILEIRO_DATA_SEP_BARRA);
    }

    /**
     * Converte uma String em um tipo Date, espera uma String de data no formato
     * americano dividido por traço com hora, minuto e segundos.
     *
     * Autor: Herberts Cruz Versão: 1.0
     *
     * @param data
     * @return String
     */
    public static Date converterStringEmDate(String data) throws ParseException {
        return converterStringEmDate(data, Formato.EN_DATA_SEPARADOR_TRACO_ANO_4DIGITOS_E_TEMPO);
    }

    /**
     * Converte um String em um tipo Date, necessita que seja informado em qual
     * formato de data que a String está formatada.
     *
     * Autor: Herberts Cruz Versão: 1.0
     *
     * @param data
     * @param Formato
     * @return String
     */
    public static Date converterToStringEmDate(String data) throws ParseException {
        //Verifica se a string é nula
        if (data == null) {
            throw new NullPointerException("String data nula.");
        } else if (data.isEmpty()) {
            throw new NullPointerException("String data vazia.");
        } else {
            //Converte o ToString do Date em Date
            return new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.US).parse(data);
        }
    }

    /**
     * Converte um String em um tipo Date, necessita que seja informado em qual
     * formato de data que a String está formatada.
     *
     * Autor: Herberts Cruz Versão: 1.0
     *
     * @param data
     * @param Formato
     * @return String
     */
    public static Date converterStringEmDate(String data, Formato formato) throws ParseException {
        //Verifica se a string é nula
        if (data == null) {
            throw new NullPointerException("String data nula.");
        } else if (data.isEmpty()) {
            throw new NullPointerException("String data vazia.");
        }
        //Cria a regex de verificação de data em String válida
        String regex = formato.getFormato().replaceAll("[dMyHms]", "[0-9]");
        //Verifica se é uma string de data válida
        if (data.matches(regex)) {
            DateFormat formatter = new SimpleDateFormat(formato.getFormato());
            return formatter.parse(data);
        } else {
            throw new ParseException("A String " + data + " não é uma String de data "
                    + "válida ou não está no formato esperado.", 0);
        }

    }

    /**
     * Converte um tipo Date em uma String no formato americano dividido por
     * traço com hora, minuto e segundos.
     *
     * Autor: Herberts Cruz Versão: 1.0
     *
     * @param data
     * @return String
     */
    public static String converterDateEmString(Date data) {
        return converterDateEmString(data, Formato.EN_DATA_SEPARADOR_TRACO_ANO_4DIGITOS_E_TEMPO);
    }

    /**
     * Converte um tipo Date em uma String no formato enviado.
     *
     * Autor: Herberts Cruz Versão: 1.0
     *
     * @param data
     * @param Formato
     * @return String
     */
    public static String converterDateEmString(Date data, Formato formato) {
        DateFormat formatter = new SimpleDateFormat(formato.getFormato());
        return formatter.format(data);
    }

    /**
     * Converte o número do mês na String do mês por extenso;
     *
     * Autor: Herberts Cruz Versão: 1.0
     *
     * @param mes
     * @return String
     * @throws ParseException
     */
    public static String converterEmMesPorExtenso(int mes)
            throws ParseException {
        // Instância o SimpleDateFormat com o formato MM (esse formato indica o
        // formato de numeros).
        SimpleDateFormat sdf = new SimpleDateFormat("MM", new java.util.Locale("pt", "BR"));
        // Faz o parse ("transforma") a String que contêm o mês em um Date.
        Date mesDate = sdf.parse(mes + "");
        // Altera o pattern do SimpleDateFormat.
        sdf.applyPattern("MMMM");
        // Retorna o nome do Mês.
        return sdf.format(mesDate);
    }

    /**
     * Verifica se a String enviada é uma data válida, espera uma String de data
     * no formato americano dividido por traço.
     *
     * Autor: Herberts Cruz Versão: 1.0
     *
     * @param data
     * @return String
     */
    public static boolean isDate(String data) {
        return isDate(data, Formato.EN_DATA_SEPARADOR_TRACO_ANO_4DIGITOS);
    }

    /**
     * Converte um String em um tipo Date, necessita que seja informado em qual
     * formato de data que a String está formatada.
     *
     * Autor: Herberts Cruz Versão: 1.0
     *
     * @param data
     * @param Formato
     * @return String
     */
    public static boolean isDate(String data, Formato formato) {
        SimpleDateFormat f = new SimpleDateFormat(formato.getFormato());
        try {
            f.parse(data);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }
}
