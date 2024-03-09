import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class sistema1 extends JFrame {
    private static final int ESTADO_INICIAL = 0;
    private static final int ESTADO_WEB_1 = 1;
    private static final int ESTADO_WEB_2 = 2;
    private static final int ESTADO_EBAY_1 = 4;
    private static final int ESTADO_EBAY_2 = 5;
    private static final int ESTADO_EBAY_3 = 6;

    private int estadoActual;
    private int contadorWeb = 1;
    private int contadorEbay = 1;

    private JTextArea texto;

    public sistema1() {
        super("Sistema 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 700); // Establecer el tamaño de la ventana
        setLayout(null); // Usar un layout nulo para controlar las dimensiones manualmente
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Crear el JTextArea
        texto = new JTextArea();
        texto.setBounds(10, 10, 1460, 600); // Establecer las dimensiones del JTextArea
        texto.setLineWrap(true); // Establecer el salto de línea al llegar al borde
        texto.setWrapStyleWord(true); // Establecer si el salto se hará por espacios en blanco o por caracteres
        add(texto);

        // Crear el botón
        JButton botonCargar = new JButton("Cargar archivo");
        botonCargar.setBounds(10, 620, 120, 30); // Establecer las dimensiones del botón
        botonCargar.addActionListener(e -> cargarArchivo());
        add(botonCargar);

        JLabel labelWeb = new JLabel("Coincidencias de 'web': ");
        labelWeb.setBounds(340, 620, 200, 30);
        add(labelWeb);

        JLabel labelEbay = new JLabel("Coincidencias de 'ebay': ");
        labelEbay.setBounds(540, 620, 200, 30);
        add(labelEbay);

        // Crear el botón
        JButton botonAnalizar = new JButton("analizar");
        botonAnalizar.setBounds(140, 620, 160, 30); // Establecer las dimensiones del botón
        botonAnalizar.addActionListener(e -> {
            String textoAnalizar = texto.getText();
            analizar(textoAnalizar);
            labelWeb.setText("coincidencias de 'web': " + contadorWeb);
            labelEbay.setText("coincidencias de 'ebay': " + contadorEbay);
        });
        add(botonAnalizar);

        setVisible(true);
    }

    private void cargarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            try {
                FileReader fr = new FileReader(archivoSeleccionado);
                BufferedReader br = new BufferedReader(fr);
                String linea;
                texto.setText(""); // Limpiar el JTextArea antes de cargar el archivo
                while ((linea = br.readLine()) != null) {
                    texto.append(linea + "\n"); // Agregar cada línea al JTextArea
                }
                br.close();
            } catch (Exception ex) {
                System.err.println("Error al cargar el archivo: " + ex.getMessage());
            }
        }
    }

    public void analizar(String texto) {
        for (char caracter : texto.toCharArray()) {
            switch (estadoActual) {
                case ESTADO_INICIAL:
                    if (caracter == 'w') {
                        estadoActual = ESTADO_WEB_1;
                    } else if (caracter == 'e') {
                        estadoActual = ESTADO_EBAY_1;
                    }
                    break;
                case ESTADO_WEB_1:
                    if (caracter == 'e') {
                        estadoActual = ESTADO_WEB_2;
                    } else if(caracter == 'w'){
                        estadoActual = ESTADO_WEB_1;
                    }else {
                        estadoActual = ESTADO_INICIAL;
                    }
                    break;
                case ESTADO_WEB_2:
                    if (caracter == 'b') {
                        contadorWeb++;
                        estadoActual = ESTADO_EBAY_2;
                    } else if(caracter == 'w'){
                        estadoActual = ESTADO_WEB_1;
                    }else {
                        estadoActual = ESTADO_INICIAL;
                    }
                    break;
                case ESTADO_EBAY_1:
                    if (caracter == 'b') {
                        estadoActual = ESTADO_EBAY_2;
                    } else if(caracter == 'w'){
                        estadoActual = ESTADO_WEB_1;
                    }else {
                        estadoActual = ESTADO_INICIAL;
                    }
                    break;
                case ESTADO_EBAY_2:
                    if (caracter == 'a') {
                        estadoActual = ESTADO_EBAY_3;
                    } else if(caracter == 'w'){
                        estadoActual = ESTADO_WEB_1;
                    }else {
                        estadoActual = ESTADO_INICIAL;
                    }
                    break;
                case ESTADO_EBAY_3:
                    if (caracter == 'y') {
                        contadorEbay++;
                        estadoActual = ESTADO_INICIAL;
                    } else if(caracter == 'w'){
                        estadoActual = ESTADO_WEB_1;
                    }else {
                        estadoActual = ESTADO_INICIAL;
                    }
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(sistema1::new);
    }
}
