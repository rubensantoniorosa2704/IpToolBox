public class IpToolBox {

    // Método para converter um endereço IP para binário
    public static String converterParaBinario(String ip) {
        try {
            String[] octetos = ip.split("\\."); // Divide o IP em octetos
            StringBuilder ipBinario = new StringBuilder();

            // Converte cada octeto para binário e os concatena
            for (String octeto : octetos) {
                int valorOcteto = Integer.parseInt(octeto);
                if (valorOcteto < 0 || valorOcteto > 255) {
                    throw new IllegalArgumentException("Octeto inválido: " + valorOcteto);
                }
                ipBinario.append(String.format("%8s", Integer.toBinaryString(valorOcteto)).replace(' ', '0')).append(".");
            }
            return ipBinario.deleteCharAt(ipBinario.length() - 1).toString();
        } catch (NumberFormatException e) {
            // Captura exceção se o valor do octeto não puder ser convertido para int
            System.err.println("Erro: Valor do octeto inválido");
            return null;
        }
    }

    // Método para determinar a classe de um endereço IP binário
    public static String determinarClasse(String ipBinario) {
        try {
            if (ipBinario.startsWith("00") || ipBinario.startsWith("01")) {
                return "A";
            } else if (ipBinario.startsWith("10")) {
                return "B";
            } else if (ipBinario.startsWith("11")) {
                return "C";
            } else {
                return "Desconhecido";
            }
        } catch (NullPointerException e) {
            // Captura exceção se o IP binário for nulo
            System.err.println("Erro: IP binário inválido");
            return null;
        }
    }

    // Método para determinar a máscara de sub-rede com base na classe do endereço IP
    public static int determinarMascara(String classe) {
        try {
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
        } catch (IllegalArgumentException e) {
            // Captura exceção se a classe do endereço IP for inválida
            System.err.println("Erro: " + e.getMessage());
            return -1;
        }
    }

    // Método para calcular a rede com base no endereço IP e na máscara de sub-rede
    public static String calcularRede(String ip, String mascara) {
        try {
            String ipBinario = converterParaBinario(ip);
            String classe = determinarClasse(ipBinario);
            int mascaraPadrao = determinarMascara(classe);

            if (mascara != null && !mascara.isEmpty()) {
                int mascaraFornecida = Integer.parseInt(mascara);
                if (mascaraFornecida < 0 || mascaraFornecida > 32) {
                    throw new IllegalArgumentException("Máscara de sub-rede inválida: " + mascara);
                }
                mascaraPadrao = mascaraFornecida;
            }

            String[] octetosIP = ip.split("\\."); // Divide o IP em octetos
            StringBuilder rede = new StringBuilder();

            // Calcula a rede octeto por octeto
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
            return rede.deleteCharAt(rede.length() - 1).toString();
        } catch (NumberFormatException e) {
            // Captura exceção se o valor do octeto não puder ser convertido para int
            System.err.println("Erro: Valor do octeto inválido");
            return null;
        }
    }
}