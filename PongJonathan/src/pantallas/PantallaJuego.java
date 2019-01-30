package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import base.PanelJuego;
import base.Pantalla;
import base.Sprite;

/**
 * 
 * @author JonathanFrancoClemente. Clase PantallaJuego. Esta clase controla como
 *         va a funcionar la pantalla juego y las interacciones que esta tiene
 *         con los componentes de la pantalla (pelota y raquetas).
 *
 */
public class PantallaJuego implements Pantalla {

	private static final int ANCHO_PELOTA = 25;
	private static final int ALTO_PELOTA = 25;
	private static final int ANCHO_RAQUETA = 10;
	private static final int ALTO_RAQUETA = 50;
	private static final Color COLOR_MARCADOR = Color.WHITE;

	/**
	 * Tiene que saber sobre que componente está por eso me creo un objeto de la
	 * clase PanelJuego.
	 */
	PanelJuego panelJuego;

	BufferedImage imagenOriginal;
	Image imagenReescalada;

	/**
	 * Existen unicamente 3 Sprites: La pelota y 2 palas.
	 */
	Sprite pelota;
	Sprite raqueta1, raqueta2;

	// Variables para el contador de tiempo
	double tiempoInicial;
	double tiempoDeJuego;
	private DecimalFormat formatoDecimal;
	Font fuenteTiempo;

	Font fuenteMarcador;

	public PantallaJuego(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
	}

	/**
	 * Inicializo la pantalla con su fondo correspondiente, también inicializo los
	 * Sprites y la fuente. Ameás reescalamos.
	 */
	@Override
	public void inicializarPantalla() {
		try {
			imagenOriginal = ImageIO.read(new File("Imagenes/fondo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pelota = new Sprite(ANCHO_PELOTA, ALTO_PELOTA, 0, 0, 5, 5, "Imagenes/asteroides/asteroide_0.png");
		// Tiene 10 pixeles de separacion con el borde
		raqueta1 = new Sprite(ALTO_RAQUETA, ALTO_RAQUETA, 10, 200, "Imagenes/barra_izq.png");
		raqueta2 = new Sprite(ALTO_RAQUETA, ALTO_RAQUETA, panelJuego.getWidth() - 10 - ALTO_RAQUETA, 200,
				"Imagenes/barra_der.png");
		fuenteMarcador = new Font("Arial", Font.BOLD, 50);
		fuenteTiempo = new Font("Arial", Font.BOLD, 20);

		tiempoInicial = System.nanoTime();
		tiempoDeJuego = 0;
		formatoDecimal = new DecimalFormat("#.##");
		reescalarImagen();
	}

	/**
	 * Pinto en la pantalla la imagen, los Sprites y la linea que divide los campos.
	 */
	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		// Pintamos la pelota
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		pintarMarcador(g);
		pintarTiempo(g);
		pelota.pintarSpriteEnMundo(g);
		raqueta1.pintarSpriteEnMundo(g);
		raqueta2.pintarSpriteEnMundo(g);
		g.drawLine(panelJuego.getWidth() / 2, 0, panelJuego.getWidth() / 2, panelJuego.getHeight());
		moverPalas();
	}

	/**
	 * Este método controla el movimiento de las palas.
	 */
	public void moverPalas() {
		raqueta1.moverRaquetaR1(panelJuego.getBounds());
		raqueta2.moverRaquetaR2(panelJuego.getBounds());
	}

	/**
	 * Método que controla si existe colision con la pelota.
	 * 
	 * @param r
	 * @return
	 */
	public boolean colision(Rectangle2D r) {
		// Detectamos si la pelota se cruza con la raqueta
		// Si dos rectangulos se cruzan devuelve true
		/*
		 * contador++; if(contador == 10) { pelota.setVelocidadX(velocidadInicial++);
		 * pelota.setVelocidadY(velocidadInicial++); contador = 0; }
		 */
		return pelota.devolverPelota().intersects(r);
	}

	/**
	 * Método que pinta la puntuación al instante de los jugadores. Controla también
	 * si existe un ganador en cuyo caso indicando en otra pantalla (Pantalla
	 * GameOver) el ganador.
	 * 
	 * @param g
	 */
	public void pintarMarcador(Graphics g) {
		// Voy a pintar 2 puntuaciones
		Graphics marcador1 = g;
		Graphics marcador2 = g;

		Font fM = g.getFont();
		Color cM = g.getColor();

		g.setColor(COLOR_MARCADOR);
		g.setFont(fuenteMarcador);
		marcador1.drawString(Integer.toString(pelota.getPuntuacion1()),
				(int) (panelJuego.getBounds().getCenterX() - 50), 50);
		marcador2.drawString(Integer.toString(pelota.getPuntuacion2()),
				(int) (panelJuego.getBounds().getCenterX() + 25), 50);
		if (pelota.getPuntuacion1() == 7) {
			PantallaGameOverJ1 pantallaJuego = new PantallaGameOverJ1(panelJuego, tiempoDeJuego);
			pantallaJuego.inicializarPantalla();
			panelJuego.setPantallaActual(pantallaJuego);
		}
		if (pelota.getPuntuacion2() == 7) {
			PantallaGameOverJ2 pantallaJuego = new PantallaGameOverJ2(panelJuego, tiempoDeJuego);
			pantallaJuego.inicializarPantalla();
			panelJuego.setPantallaActual(pantallaJuego);
		}

		g.setColor(cM);
		g.setFont(fM);
	}

	/**
	 * Método que pinta el tiempo de ejecucion del programa desde que se inicia el
	 * juego.
	 * 
	 * @param g
	 */
	private void pintarTiempo(Graphics g) {
		Font f = g.getFont();
		Color c = g.getColor();

		g.setColor(Color.WHITE);
		g.setFont(fuenteTiempo);
		actualizarTiempo();
		g.drawString(formatoDecimal.format(tiempoDeJuego / 1000000000d), 25, 25);

		g.setColor(c);
		g.setFont(f);
	}

	/**
	 * Método que actualiza el tiempo de juego transcurrido.
	 */
	private void actualizarTiempo() {
		tiempoDeJuego = System.nanoTime() - tiempoInicial;
	}

	/**
	 * Método que se utiliza para rellenar el fondo del JPanel.
	 * 
	 * @param g Es el gráficos sobre el que vamos a pintar el fondo.
	 */
	private void rellenarFondo(Graphics g) {
		// Pintar la imagen de fondo reescalada:
		g.drawImage(imagenReescalada, 0, 0, null);
	}

	@Override
	public void ejecutarFrame() {
		moverSprites();
	}

	/**
	 * Método que permite el movimiento de la pelota.
	 */
	private void moverSprites() {
		pelota.moverSprite(panelJuego.getWidth(), panelJuego.getHeight(), colision(raqueta1.devolverRaqueta()),
				colision(raqueta2.devolverRaqueta()));
	}

	@Override
	public void pulsarRaton(MouseEvent e) {

	}

	private void reescalarImagen() {
		imagenReescalada = imagenOriginal.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				Image.SCALE_SMOOTH);
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		reescalarImagen();
	}
	
	public double getTiempoDeJuego() {
		return tiempoDeJuego;
	}

	public void setTiempoDeJuego(double tiempoDeJuego) {
		this.tiempoDeJuego = tiempoDeJuego;
	}
}
