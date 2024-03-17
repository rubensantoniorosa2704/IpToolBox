import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Programa extends JFrame implements ActionListener {
    private JTextField campoTextoIP;
    private JTextField campoTextoMascara;
    private JTextField campoTextoBinarioIP;
    private JTextField campoTextoBinarioMascara;
    private JTextField campoTextoBinarioSubrede;
    private JTextArea areaTextoSaida;
    private JButton botaoCalcular;

    public Programa() {
        setTitle("Ferramenta IP");
        setSize(550, 400); // Aumentando o tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel rotuloIP = new JLabel("Endereço IP:");
        painel.add(rotuloIP, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        campoTextoIP = new JTextField();
        painel.add(campoTextoIP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel rotuloMascara = new JLabel("Máscara:");
        painel.add(rotuloMascara, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        campoTextoMascara = new JTextField();
        painel.add(campoTextoMascara, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel rotuloBinarioIP = new JLabel("Binário do IP:");
        painel.add(rotuloBinarioIP, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        campoTextoBinarioIP = new JTextField();
        campoTextoBinarioIP.setEditable(false);
        campoTextoBinarioIP.setPreferredSize(new Dimension(350, 30)); // Aumentando o tamanho do campo de texto
        painel.add(campoTextoBinarioIP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel rotuloBinarioMascara = new JLabel("Binário da Máscara:");
        painel.add(rotuloBinarioMascara, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        campoTextoBinarioMascara = new JTextField();
        campoTextoBinarioMascara.setEditable(false);
        campoTextoBinarioMascara.setPreferredSize(new Dimension(350, 30)); // Aumentando o tamanho do campo de texto
        painel.add(campoTextoBinarioMascara, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel rotuloBinarioSubrede = new JLabel("Binário da Sub-rede:");
        painel.add(rotuloBinarioSubrede, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        campoTextoBinarioSubrede = new JTextField();
        campoTextoBinarioSubrede.setEditable(false);
        campoTextoBinarioSubrede.setPreferredSize(new Dimension(350, 30)); // Aumentando o tamanho do campo de texto
        painel.add(campoTextoBinarioSubrede, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        areaTextoSaida = new JTextArea();
        areaTextoSaida.setLineWrap(true);
        areaTextoSaida.setWrapStyleWord(true);
        areaTextoSaida.setEditable(false);
        JScrollPane painelRolagem = new JScrollPane(areaTextoSaida);
        painel.add(painelRolagem, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        botaoCalcular = new JButton("Calcular");
        botaoCalcular.addActionListener(this);
        painel.add(botaoCalcular, gbc);

        add(painel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Programa gui = new Programa();
            gui.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ipText = campoTextoIP.getText();
        String mascaraText = campoTextoMascara.getText();

        // Verifica se o IP está vazio
        if (ipText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um endereço IP", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String ipBinario = IpToolBox.converterParaBinario(ipText);
            String classe = IpToolBox.determinarClasse(ipBinario);
            campoTextoBinarioIP.setText(ipBinario);

            // Se a máscara for vazia, o programa calcula a rede com base na máscara padrão
            if (mascaraText.isEmpty()) {
                String classeIP = IpToolBox.determinarClasse(ipBinario);
                mascaraText = String.valueOf(IpToolBox.determinarMascara(classeIP)); // Obtém a máscara padrão
                campoTextoMascara.setText(mascaraText); // Define o texto da máscara no campo correspondente
            }

            int mascara = Integer.parseInt(mascaraText);
            String mascaraBinaria = formatarBinarioMascara(mascara);
            campoTextoBinarioMascara.setText(mascaraBinaria); // Define o texto do binário da máscara no campo correspondente

            String rede = IpToolBox.calcularRede(ipText, String.valueOf(mascara));
            campoTextoBinarioSubrede.setText(IpToolBox.converterParaBinario(rede));
            areaTextoSaida.setText("A classe padrão é " + classe + ". A rede à qual o IP pertence é: " + rede + ". A máscara fornecida é /" + mascara);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Máscara inválida. Por favor, insira uma máscara válida.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Endereço IP inválido. Por favor, insira um endereço IP válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para formatar o binário da máscara com pontos entre cada byte
    private String formatarBinarioMascara(int mascara) {
        String binario = String.format("%32s", Integer.toBinaryString(-1 << (32 - mascara))).replace(' ', '0');
        StringBuilder sb = new StringBuilder(binario);
        sb.insert(8, ".");
        sb.insert(17, ".");
        sb.insert(26, ".");
        return sb.toString();
    }
}