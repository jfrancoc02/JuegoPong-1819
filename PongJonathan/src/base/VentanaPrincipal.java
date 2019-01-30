package base;

import java.awt.GridLayout;

import javax.swing.JFrame;

/**
 * Clase VentanaPrincipal. En ella se pinta el juego.
 * 
 * @author JonathanFrancoClemente
 *
 */
public class VentanaPrincipal {

	JFrame ventana;
	PanelJuego panelJuego;

	public VentanaPrincipal() {
		ventana = new JFrame("Pong");
		ventana.setBounds(200, 150, 900, 500);
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Metodo que inicializa todos los componentes de la ventana
	 */
	public void inicializarComponentes() {
		// Definimos el layout
		ventana.setLayout(new GridLayout(1, 1));

		// Inicializo panelJuego y la añado al JFrame
		panelJuego = new PanelJuego();
		ventana.add(panelJuego);
	}

	/**
	 * Metodo que realiza todas las llamadas necesarias para inicializar la ventana
	 * correctamente.
	 */
	public void inicializar() {
		ventana.setVisible(true);
		inicializarComponentes();
	}
}
