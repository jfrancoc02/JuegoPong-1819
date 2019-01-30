package sonido;

import java.applet.AudioClip;

/**
 * 
 * @author JonathanFrancoClemente. Clase Sonido. Clase que revibe un sonido a
 *         través de una ruta.
 *
 */

public class Sonido {

	public AudioClip getAudio(String direccion) {
		return java.applet.Applet.newAudioClip(getClass().getResource(direccion));
	}

}
