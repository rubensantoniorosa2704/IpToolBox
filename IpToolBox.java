/**
 * Classe utilitária para manipulação de endereços IP.
 */
public class IpToolBox {

    /**
     * Converte um endereço IP para binário.
     *
     * @param ip O endereço IP em formato decimal.
     * @return O endereço IP em formato binário.
     * @throws IllegalArgumentException Se algum octeto do IP for inválido.
     */
    public static String converterParaBinario(String ip) {
        String[] octetos = ip.split("\\.");
        StringBuilder ipBinario = new StringBuilder();

        // Processa cada octeto do IP
        for (String octeto : octetos) {
            // Valida o octeto e adiciona ao IP binário
            validarOcteto(octeto);
            ipBinario.append(formatarOctetoEmBinario(octeto)).append(".");
        }

        // Remove o último ponto e retorna o resultado
        return removerUltimoPonto(ipBinario);
    }

    /**
     * Determina a classe de um endereço IP binário.
     *
     * @param ipBinario O endereço IP em formato binário.
     * @return A classe do endereço IP.
     * @throws NullPointerException Se o IP binário for nulo.
     */
    public static String determinarClasse(String ipBinario) {
        // Determina a classe com base nos dois primeiros bits do IP binário
        if (ipBinario.startsWith("00") || ipBinario.startsWith("01")) {
            return "A";
        } else if (ipBinario.startsWith("10")) {
            return "B";
        } else if (ipBinario.startsWith("11")) {
            return "C";
        } else {
            return "Desconhecido";
        }
    }

    /**
     * Determina a máscara de sub-rede com base na classe do endereço IP.
     *
     * @param classe A classe do endereço IP.
     * @return A máscara de sub-rede.
     * @throws IllegalArgumentException Se a classe do endereço IP for inválida.
     */
    public static int determinarMascara(String classe) {
        // Determina a máscara com base na classe do IP
        switch (classe) {
            case "A":
                return 8;
            case "B":
                return 16;
            case "C":
                return 24;
            default:
                throw new IllegalArgumentException("Classe inválida: " + classe);
        }
    }

    /**
     * Calcula a rede com base no endereço IP e na máscara de sub-rede.
     *
     * @param ip O endereço IP.
     * @param mascara A máscara de sub-rede.
     * @return O endereço IP da rede.
     * @throws IllegalArgumentException Se a máscara de sub-rede for inválida.
     */
    public static String calcularRede(String ip, String mascara) {
        String ipBinario = converterParaBinario(ip);
        String classe = determinarClasse(ipBinario);
        int mascaraPadrao = determinarMascara(classe);

        // Se uma máscara foi fornecida, valida e usa em vez da máscara padrão
        if (mascara != null && !mascara.isEmpty()) {
            validarMascara(mascara);
            mascaraPadrao = Integer.parseInt(mascara);
        }

        String[] octetosIP = ip.split("\\.");
        StringBuilder rede = new StringBuilder();

        // Calcula o endereço da rede com base no IP e na máscara
        for (int i = 0; i < 4; i++) {
            if (mascaraPadrao >= 8) {
                rede.append(octetosIP[i]).append(".");
                mascaraPadrao -= 8;
            } else if (mascaraPadrao > 0) {
                int octetoIP = Integer.parseInt(octetosIP[i]);
                int mascaraCalculada = 256 - (int) Math.pow(2, 8 - mascaraPadrao);
                rede.append(octetoIP & mascaraCalculada).append(".");
                mascaraPadrao = 0;
            } else {
                rede.append("0.");
            }
        }

        // Remove o último ponto e retorna o resultado
        return removerUltimoPonto(rede);
    }

    /**
     * Valida um octeto de um endereço IP.
     *
     * @param octeto O octeto a ser validado.
     * @throws IllegalArgumentException Se o octeto for inválido.
     */
    private static void validarOcteto(String octeto) {
        int valorOcteto = Integer.parseInt(octeto);
        if (valorOcteto < 0 || valorOcteto > 255) {
            throw new IllegalArgumentException("Octeto inválido: " + valorOcteto);
        }
    }

    /**
     * Formata um octeto de um endereço IP em binário.
     *
     * @param octeto O octeto a ser formatado.
     * @return O octeto em formato binário.
     */
    private static String formatarOctetoEmBinario(String octeto) {
        return String.format("%8s", Integer.toBinaryString(Integer.parseInt(octeto))).replace(' ', '0');
    }

    /**
     * Valida uma máscara de sub-rede.
     *
     * @param mascara A máscara a ser validada.
     * @throws IllegalArgumentException Se a máscara for inválida.
     */
    private static void validarMascara(String mascara) {
        int mascaraFornecida = Integer.parseInt(mascara);
        if (mascaraFornecida < 0 || mascaraFornecida > 32) {
            throw new IllegalArgumentException("Máscara de sub-rede inválida: " + mascara);
        }
    }

    /**
     * Remove o último ponto de uma string.
     *
     * @param stringBuilder A string a ser processada.
     * @return A string sem o último ponto.
     */
    private static String removerUltimoPonto(StringBuilder stringBuilder) {
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }
}
