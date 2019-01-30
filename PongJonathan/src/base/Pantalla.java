package base;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

/**
 * 
 * @author JonathanFrancoClemente Interfaz Pantalla. Almacena los método que van
 *         a utiliar todas las pantallas que tenemos creadas en el programa.
 */

public interface Pantalla {

	public void inicializarPantalla();

	public void pintarPantalla(Graphics g);

	public void ejecutarFrame();

	public void pulsarRaton(MouseEvent e);

	public void redimensionarPantalla(ComponentEvent e);

}
