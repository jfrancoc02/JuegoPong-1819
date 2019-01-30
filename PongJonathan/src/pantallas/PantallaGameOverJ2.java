package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import base.PanelJuego;
import base.Pantalla;

/**
 * 
 * @author JonathanFrancoClemente. Clase PantallaGameOver2. Esta clase muestra
 *         una imagen de game over cuando gana el jugador2.
 *
 */
public class PantallaGameOverJ2 implements Pantalla {

	/**
	 * Tiene que saber sobre que componente está por eso me creo un objeto de la
	 * clase PanelJuego.
	 */
	PanelJuego panelJuego;

	BufferedImage imagenOriginalGameOver;
	Image imagenReescaladaGameOver;
	
	double tiempoJuego;
	private DecimalFormat formatoDecimal;

	Font fuenteInicial;

	// Inicio pantalla
	Color colorLetras = Color.RED;
	int contadorColorFrames = 0;
	static final int CAMBIO_COLOR_INICIO = 10;

	public PantallaGameOverJ2(PanelJuego panelJuego, double tiempoJuego) {
		this.panelJuego = panelJuego;
		this.tiempoJuego = tiempoJuego;
	}

	/**
	 * Inicializo la pantalla con su fondo correspondiente y reescalo.
	 */
	@Override
	public void inicializarPantalla() {
		try {
			imagenOriginalGameOver = ImageIO.read(new File("Imagenes/winj2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		fuenteInicial = new Font("Times New Roman", Font.BOLD, 30);
		formatoDecimal = new DecimalFormat("#.##");
		reescalarImagen();
	}

	/**
	 * Pinto en la pantalla la imagen y la información que deseo mostrar.
	 */
	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(imagenReescaladaGameOver, 0, 0, null);
		g.setColor(colorLetras);
		g.setFont(fuenteInicial);
		g.drawString("Inicio", panelJuego.getWidth() / 2 - 222, panelJuego.getHeight() / 2 + 130);
		g.drawString("Jugar", panelJuego.getWidth() / 2 + 135, panelJuego.getHeight() / 2 + 130);
		g.drawString("Tiempo jugado: " + formatoDecimal.format(tiempoJuego / 1000000000d), panelJuego.getWidth() / 2 - 150, panelJuego.getHeight() / 2 + 80);
	}

	/**
	 * Método que cambia de color las letras gracias al reescalado.
	 */
	@Override
	public void ejecutarFrame() {
		contadorColorFrames++;
		if (contadorColorFrames % CAMBIO_COLOR_INICIO == 0) {
			if (colorLetras.equals(Color.RED)) {
				colorLetras = Color.YELLOW;
			} else {
				colorLetras = Color.RED;
			}
		}
	}

	/**
	 * Método que carga un panel u otro dependiendo de la opción que eliga el
	 * usuario.
	 */
	@Override
	public void pulsarRaton(MouseEvent e) {
		if (e.getButton() == 1) {
			PantallaInicial2 pantallaJuego = new PantallaInicial2(panelJuego);
			pantallaJuego.inicializarPantalla();
			panelJuego.setPantallaActual(pantallaJuego);
		}
		if (e.getButton() == 3) {
			PantallaJuego pantallaJuego = new PantallaJuego(panelJuego);
			pantallaJuego.inicializarPantalla();
			panelJuego.setPantallaActual(pantallaJuego);
		}
	}

	private void reescalarImagen() {
		imagenReescaladaGameOver = imagenOriginalGameOver.getScaledInstance(panelJuego.getWidth(),
				panelJuego.getHeight(), Image.SCALE_SMOOTH);
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		reescalarImagen();
	}

}
