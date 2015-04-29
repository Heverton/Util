package br.com.util.validacao;

/**
 *
 * @author Heverton Cruz
 * @version 1.0
 * @deprecated Utilize ManipuleString.removerAcentos(string)
 */
public class RemoverAcentos {
    // Acentos da lingua postuguesa

    static String acentuado = "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ^'`´~¨";
    // Quyando retirar pelo da posição
    static String semAcento = "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU      ";
    // Tabela
    static char[] tabela;
    // Carregar tabela de acentos

    static {
        tabela = new char[256];

        for (int i = 0; i < tabela.length; ++i) {
            tabela[i] = (char) i;
        }

        for (int i = 0; i < acentuado.length(); ++i) {
            tabela[acentuado.charAt(i)] = semAcento.charAt(i);
        }
    }

    /**
     * Remover acentos
     *
     * @param s
     * @return nova palavra sem acento
     */
    public String remover(final String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);

            if (ch < 256) {
                sb.append(tabela[ch]);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
