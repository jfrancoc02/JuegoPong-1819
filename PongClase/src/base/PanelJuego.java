package base;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import pantallas.PantallaInicial;

/**
 * 
 * @author JonathanFrancoClemente Clase PanelJuego. Controla los gr�ficos del
 *         Juego. Por ahora tambi�n controla la l�gica del Juego. Extiende de
 *         JPanel. Todos los gr�ficos se gestionan mediante los gr�ficos de un
 *         JPanel. Implementa Runnable porque en el constructor se lanza un hilo
 *         que permite actualizar el Juego peri�dicamente. Implementa
 *         MouseListener para que pueda capturar las pulsaciones del rat�n.
 *         Implementa ComponentListener para que si por alg�n casual se
 *         redimensiona la pantalla se redimensione la imagen. Implementa
 *         KeyListener permitiendo la comunicaci�n de las teclas con nuestro
 *         programa.
 */
public class PanelJuego extends JPanel implements Runnable, MouseListener, ComponentListener, KeyListener {

	private static final long serialVersionUID = 1L;
	Pantalla pantallaActual;

	/**
	 * Me creo 4 variables booleanas que van a hacer referencia a la tecla que
	 * pulsemos, poniendo a true la variable si estamos pulsado esa tecla o bien a
	 * false si no la estamos pulsando.
	 */

	static boolean w, s, up, down;

	/**
	 * Constructor de PanelJuego. - Asigna el mouse listener que implementa la
	 * propia clase para saltar de pantalla una vez hagamos clic. - Asigna el
	 * componentListener. - Asigna KeyListener permitiendo el movimiento de nuestros
	 * Sprites.- Inicia un hilo para actualizar el juego peri�dicamente.
	 */
	public PanelJuego() {
		this.setFocusable(true);
		// Mantengo los listeners en esta clase
		this.addMouseListener(this);
		this.addComponentListener(this);
		// Me creo el evento de teclado
		this.addKeyListener(this);
		// Me creo el hilo y lo ejecutamos
		new Thread(this).start();

		// Arrancamos con pantallaInicial
		pantallaActual = new PantallaInicial(this);
		// Y la inicializamos
		pantallaActual.inicializarPantalla();
	}

	/**
	 * Sobreescritura del m�todo paintComponent. Este m�todo se llama
	 * autom�ticamente cuando se inicia el componente, se redimensiona o bien cuando
	 * se llama al m�todo "repaint()".
	 * 
	 * @param g Es un Graphics que nos provee JPanel para poner pintar el componente
	 *          a nuestro antojo.
	 */
	@Override
	public void paintComponent(Graphics g) {
		// Sea la pantalla que sea la pintamos
		pantallaActual.pintarPantalla(g);
	}

	@Override
	public void run() {
		while (true) {
			// Repinto
			repaint();
			try {
				// Actualizamos
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Sea lo que sea lo que queremos ejecutar lo ejecutamos
			pantallaActual.ejecutarFrame();
			Toolkit.getDefaultToolkit().sync();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pantallaActual.pulsarRaton(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		pantallaActual.redimensionarPantalla(e);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public Pantalla getPantallaActual() {
		return pantallaActual;
	}

	public void setPantallaActual(Pantalla pantallaActual) {
		this.pantallaActual = pantallaActual;
	}

	/**
	 * Cada vez que presionamos una tecla hacemos referencia al m�todo "keyPressed".
	 * Este m�todo pone a true el booleano que hace referencia a esa tecla que hemos
	 * pulsado.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int id = e.getKeyCode(); // Almacenar la informaci�n de la tecla que presionamos

		if (id == KeyEvent.VK_W) {
			w = true;
		}
		if (id == KeyEvent.VK_S) {
			s = true;
		}
		if (id == KeyEvent.VK_UP) {
			up = true;
		}
		if (id == KeyEvent.VK_DOWN) {
			down = true;
		}
	}

	/**
	 * Cada vez que dejemos de presionar una tecla hacemos referencia al m�todo
	 * "keyReleased". Este m�todo pone a false el booleano que hace referencia a esa
	 * tecla que hemos pulsado
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W) {
			w = false;
		}
		if (id == KeyEvent.VK_S) {
			s = false;
		}
		if (id == KeyEvent.VK_UP) {
			up = false;
		}
		if (id == KeyEvent.VK_DOWN) {
			down = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}