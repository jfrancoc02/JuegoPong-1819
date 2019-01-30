package base;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import sonido.Sonido;

/**
 * 
 * @author JonathanFrancoClemente. Clase Sprite. Representa un elemento pintable
 *         y colisionable del juego.
 *
 */

public class Sprite {

	private static final int ANCHO_PELOTA = 25;
	private static final int ALTO_PELOTA = 25;
	private static final int ANCHO_RAQUETA = 10;
	private static final int ALTO_RAQUETA = 50;

	private BufferedImage buffer;
	private Color color = Color.WHITE;
	private int ancho;
	private int alto;
	private int posX;
	private int posY;
	private int velocidadX;
	private int velocidadY;
	private int puntuacion1 = 0, puntuacion2 = 0;
	private String rutaImagen;

	Sonido sonido = new Sonido();
	AudioClip golpe1 = sonido.getAudio("/Sonidos/golpe1.wav");
	AudioClip golpe2 = sonido.getAudio("/Sonidos/golpe2.wav");
	AudioClip fuera = sonido.getAudio("/Sonidos/fuera.wav");

	/**
	 * Constructor simple para un Sprite sin velocidad. En este caso las palas de
	 * los laterales.
	 * 
	 * @param ancho      Ancho que ocupa el Sprite (en pixels)
	 * @param alto       Alto que ocupa el Sprite (en pixels)
	 * @param posX       Posición horizontal del sprite en el mundo.
	 * @param posY       Posición vertical del Sprite en el mundo. El origen se
	 *                   sitúa en la parte superior.
	 * @param rutaImagen Dirección de la imagen que vamos a cargar para ese Sprite.
	 */
	public Sprite(int ancho, int alto, int posX, int posY, String rutaImagen) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.rutaImagen = rutaImagen;
		actualizarBuffer(rutaImagen);
	}

	/**
	 * Constructor para Sprite con velocidad. En este caso la pelota.
	 * 
	 * @param ancho      Ancho que ocupa el Sprite (en pixels)
	 * @param alto       Alto que ocupa el Sprite (en pixels)
	 * @param posX       Posición horizontal del sprite en el mundo.
	 * @param posY       Posición vertical del Sprite en el mundo. El origen se
	 *                   sitúa en la parte superior.
	 * @param velocidadX Velocidad horizontal del Sprite en el mundo.
	 * @param velocidadY Velocidad vertical del Sprite en el mundo.
	 * @param rutaImagen Dirección de la imagen que vamos a cargar para ese Sprite.
	 */
	public Sprite(int ancho, int alto, int posX, int posY, int velocidadX, int velocidadY, String rutaImagen) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.rutaImagen = rutaImagen;
		actualizarBuffer(rutaImagen);
	}

	/**
	 * Método para actualizar el buffer que guarda cada Sprite. Por ahora sólo
	 * guarda un bufferedImage que estÃ¡ completamente relleno de un color. Guarda
	 * una imagen y en cuyo caso que diera error, cargaría un fillRect con el tamaño
	 * correspondiente.
	 */
	public void actualizarBuffer(String rutaImagen) {
		buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();

		try {
			BufferedImage imagenSprite = ImageIO.read(new File(rutaImagen));
			// pinto en el buffer la imagen
			g.drawImage(imagenSprite.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);

		} catch (Exception e) {
			g.setColor(color);
			g.fillRect(0, 0, ancho, alto);
			g.dispose();
		}

	}

	/**
	 * Método que devuelve un Sprite pelota.
	 * 
	 * @return
	 */
	public Rectangle2D devolverPelota() {
		return new Rectangle2D.Double(posX, posY, ANCHO_PELOTA, ALTO_PELOTA);
	}

	/**
	 * Método que devuelve un Sprite Raqueta.
	 * 
	 * @return
	 */
	public Rectangle2D devolverRaqueta() {
		return new Rectangle2D.Double(posX, posY, ANCHO_RAQUETA, ALTO_RAQUETA);
	}

	/**
	 * Método para mover la pelota por el mundo. Dentro de este método controlamos
	 * también si existe colision.
	 * 
	 * @param anchoMundo Ancho del mundo sobre el que se mueve el Sprite
	 * @param altoMundo  Alto del mundo sobre el que se mueve el Sprite
	 * @param colisionR1 Booleano que comprueba si a colisionado con la pala número1
	 * @param colisionR2 Booleano que comprueba si a colisionado con la pala número2
	 */
	public void moverSprite(int anchoMundo, int altoMundo, boolean colisionR1, boolean colisionR2) {

		// Si hay colision le digo que cambie de sentido la direccion
		if (colisionR1) {
			velocidadX = -velocidadX;
			golpe1.play();
		}
		// Si hay colision le digo que cambie de sentido la direccion
		if (colisionR2) {
			velocidadX = -1 * Math.abs(velocidadX);
			golpe1.play();
		}

		// Si llega al final de la pantalla por la derecha
		// Suma puntuacion y aparece la bola en el centro
		if (posX >= anchoMundo - ancho) { // por la derecha
			puntuacion1++;
			posX = anchoMundo / 2;
			posY = altoMundo / 2;
			velocidadX = -1 * Math.abs(velocidadX);
			fuera.play();
		}
		// Si llega al final de la pantalla por la izquierda
		// Suma puntuacion y aparece la bola en el centro
		if (posX < 0) {// por la izquierda
			puntuacion2++;
			posX = anchoMundo / 2;
			posY = anchoMundo / 2;
			velocidadX = Math.abs(velocidadX);
			fuera.play();
		}
		if (posY >= altoMundo - alto) {// por abajo
			velocidadY = -1 * Math.abs(velocidadY);
			golpe2.play();
		}
		if (posY <= 0) { // Por arriba
			velocidadY = Math.abs(velocidadY);
			golpe2.play();
		}
		posX = posX + velocidadX;
		posY = posY + velocidadY;

	}

	/**
	 * Método que mueve la pala número 1 en función de las teclas que hayamos
	 * pulsado
	 * 
	 * @param limites Almacena el ancho y alto del mundo en el que se localiza el
	 *                Sprite
	 */
	public void moverRaquetaR1(Rectangle limites) {
		if (PanelJuego.w && posY > limites.getMinY() + 5) {
			for (int i = 0; i < 4; i++) {
				posY--;
			}
		}
		if (PanelJuego.s && posY < limites.getMaxY() - ALTO_RAQUETA - 6) {
			for (int i = 0; i < 4; i++) {
				posY++;
			}
		}
	}

	/**
	 * Método que mueve la pala número 1 en función de las teclas que hayamos
	 * pulsado
	 * 
	 * @param limites Almacena el ancho y alto del mundo en el que se localiza el
	 *                Sprite
	 */
	public void moverRaquetaR2(Rectangle limites) {
		if (PanelJuego.up && posY > limites.getMinY() + 5) {
			for (int i = 0; i < 4; i++) {
				posY--;
			}
		}
		if (PanelJuego.down && posY < limites.getMaxY() - ALTO_RAQUETA - 6) {
			for (int i = 0; i < 4; i++) {
				posY++;
			}
		}
	}

	public void raquetaCpuSubir(Rectangle limites) {
		if (posY > limites.getMinY() + 5) {
			posY -= 5;
		}

	}

	public void raquetaCpuBajar(Rectangle limites) {
		if (posY < limites.getMaxY() - ALTO_RAQUETA - 6) {
			for (int i = 0; i < 4; i++) {
				posY++;
			}
		}
	}

	/**
	 * Método que pinta el Sprite en el mundo teniendo en cuenta las características
	 * propias del Sprite.
	 * 
	 * @param g Es el Graphics del mundo que se utilizará para pintar el Sprite.
	 */
	public void pintarSpriteEnMundo(Graphics g) {
		g.drawImage(buffer, posX, posY, null);
	}

	/**
	 * Métodos Getters y Setters
	 */
	public int getPuntuacion1() {
		return puntuacion1;
	}

	public void setPuntuacion1(int puntuacion1) {
		this.puntuacion1 = puntuacion1;
	}

	public int getPuntuacion2() {
		return puntuacion2;
	}

	public void setPuntuacion2(int puntuacion2) {
		this.puntuacion2 = puntuacion2;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public void setVelocidadX(int i) {
		this.velocidadX = i;
		
	}
	
	public void setVelocidadY(int i) {
		this.velocidadY = i;
		
	}
	
}
