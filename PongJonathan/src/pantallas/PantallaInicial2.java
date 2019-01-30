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

import javax.imageio.ImageIO;

import base.PanelJuego;
import base.Pantalla;

/**
 * 
 * @author JonathanFrancoClemente. Clase PantallaInicial2. Esta clase controla
 *         la primera pantalla mostrando al usuario las diferentes opciones de
 *         juego.
 *
 */
public class PantallaInicial2 implements Pantalla {

	/**
	 * Tiene que saber sobre que componente está por eso me creo un objeto de la
	 * clase PanelJuego.
	 */
	PanelJuego panelJuego;

	BufferedImage imagenOriginalInicial;
	Image imagenReescaladaInicial;

	Font fuenteInicial;

	// Inicio pantalla
	Color colorLetras = Color.RED;
	int contadorColorFrames = 0;
	static final int CAMBIO_COLOR_INICIO = 10;

	public PantallaInicial2(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
	}

	/**
	 * Inicializo la pantalla con su fondo correspondiente y reescalo.
	 */
	@Override
	public void inicializarPantalla() {
		try {
			imagenOriginalInicial = ImageIO.read(new File("Imagenes/fondoDefinitivo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		fuenteInicial = new Font("Times New Roman", Font.BOLD, 30);
		reescalarImagen();
	}

	/**
	 * Pinto en la pantalla la imagen y la información que deseo mostrar.
	 */
	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(imagenReescaladaInicial, 0, 0, null);
		g.setColor(colorLetras);
		g.setFont(fuenteInicial);
		g.drawString("1 VS 1", panelJuego.getWidth() / 2 - 195, panelJuego.getHeight() / 2 + 160);
		g.drawString("1 VS CPU", panelJuego.getWidth() / 2 + 73, panelJuego.getHeight() / 2 + 160);

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
			PantallaJuego pantallaJuego = new PantallaJuego(panelJuego);
			pantallaJuego.inicializarPantalla();
			panelJuego.setPantallaActual(pantallaJuego);
		}
		if (e.getButton() == 3) {
			PantallaJuegoCPU pantallaJuego = new PantallaJuegoCPU(panelJuego);
			pantallaJuego.inicializarPantalla();
			panelJuego.setPantallaActual(pantallaJuego);
		}
	}

	private void reescalarImagen() {
		imagenReescaladaInicial = imagenOriginalInicial.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				Image.SCALE_SMOOTH);
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		reescalarImagen();
	}
}
