# IpToolBox
Esta ferramenta fornece funcionalidades relacionadas à manipulação e cálculos de endereços IP, incluindo:

## `IpToolBox.java`

Uma classe Java que contém métodos estáticos para várias operações relacionadas a endereços IP.

### Funcionalidades:

- **`converterParaBinario(String ip)`**: Converte um endereço IP para sua representação binária.
- **`determinarClasse(String ipBinario)`**: Determina a classe de um endereço IP binário (A, B, C ou Desconhecido).
- **`determinarMascara(String classe)`**: Determina a máscara de sub-rede com base na classe do endereço IP.
- **`calcularRede(String ip, String mascara)`**: Calcula a rede com base no endereço IP e na máscara de sub-rede fornecida.

## `Programa.java`

Uma aplicação Java Swing que oferece uma interface gráfica para utilizar as funcionalidades da classe `IpToolBox`.

### Funcionalidades:

- Interface gráfica simples para inserir endereço IP e máscara.
- Visualização do endereço IP convertido para binário.
- Visualização da máscara de sub-rede em formato binário.
- Cálculo da rede com base no endereço IP e na máscara fornecida.
- Exibição da classe do endereço IP, rede calculada e máscara fornecida.

## Utilização

1. Compile ambos os arquivos Java (`IpToolBox.java` e `Programa.java`).
2. Execute `Programa` para iniciar a interface gráfica.
3. Insira um endereço IP e uma máscara de sub-rede.
4. Pressione o botão "Calcular" para obter os resultados.

## Requisitos

- JDK instalado para compilar e executar os arquivos Java.
- Ambiente gráfico (Swing) disponível para execução da aplicação.

---

**Autores:** Rodrigo Cestari Maffezzolli e Rubens Antonio Rosa Neto

