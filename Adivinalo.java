package adivinalo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Implemanta el juego &quot;Adivinalo&quot;. <br />
 * Consiste en:<br />
 * <ul>
 * <li>Adivinar un número comprendido entre 0 y 100, que será generado de
 * forma automática.</li>
 * <li>El usuario introducirá por teclado el número y el programa informará
 * si es mayor o menor. <br />
 * </li>
 * <li>El número de intentos será infinito. <br />
 * </li>
 * <li>Al adivinarlo, se le dará la opción de comenzar de nuevo el juego. <br />
 * </li>
 * <li>La aplicación almacenará en &quot;record.txt&quot; el menor número de
 * intentos para informar al usuario en caso de batirlo.</li>
 * </ul>
 * 
 * @author mlmc
 * 
 */
public class Adivinalo {

	private static final String NOMBRE_FICHERO = "record.txt";
	static BufferedReader entrada = new BufferedReader(
			new InputStreamReader(System.in));

	/**
	 * Lanza el juego adivínalo...
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		int record = leerDelFichero();
		int intentos;
		boolean superado = false;
		char c;

		do {
			mostrarInstrucciones();
			intentos = jugar();
			superado = recordSuperado(intentos, record);
			if (superado)
				record = intentos;
			c = preguntarContinuar();
		} while (c == 'S' || c == 's');

		if (superado)
			escribirDelFichero(record);
	}

	/**
	 * Escribe en el fichero el último record
	 * 
	 * @param record
	 * @throws IOException
	 */
	private static void escribirDelFichero(int record) throws IOException {
		PrintWriter salida = new PrintWriter(new FileWriter(NOMBRE_FICHERO));
		salida.println(record);
		salida.close();
	}

	/**
	 * Comprueba si el record se ha superado
	 * 
	 * @param intentos
	 *            el resultado de la última partida
	 * @param record
	 *            el último record
	 * @return true si se ha superado el record. false en otro caso
	 */
	private static boolean recordSuperado(int intentos, int record) {
		if (intentos < record) {
			if (record == Integer.MAX_VALUE)
				System.out.println("Acabas de inaugurar el ranking.");
			else
				System.out
						.format("Has superado el record anterior. De %d intentos a %d intentos.",
								record, intentos);
			return true;
		}
		return false;
	}

	/**
	 * Lee del fichero el record actual
	 * 
	 * @return record actual
	 * @throws IOException
	 */
	private static int leerDelFichero() throws IOException {
		File fichero = new File(NOMBRE_FICHERO);
		//BufferedReader entrada = null;
		if (fichero.exists()) {
			try (BufferedReader entrada = new BufferedReader(new FileReader(NOMBRE_FICHERO))){
				
				int entero = Integer.parseInt(entrada.readLine());
				System.out.println("El record actual es " + entero);
				return entero;
			} catch (FileNotFoundException e) {
				System.out.println("Error en la lectura del fichero. ");
			} catch (NumberFormatException e) {
				System.out
						.println("Error en la lectura del fichero. No es un entero.");
			} 
		//	finally {
//				if (entrada != null) {
//					entrada.close();
//				}
//			}
		}
		return Integer.MAX_VALUE;// si no existe record actual, lo inicializa al
									// máximo
	}

	/**
	 * Juega a adivinar un número
	 * 
	 * @return número de intentos que ha utilizado el jugador
	 * @throws IOException
	 */
	private static int jugar() throws IOException {
		int clave = (int) (Math.random() * 101);
		int num;
		int intentos = 0;
		do {
			num = leerNumero();

			intentos++;

			if (num < clave)
				System.out.println("Mayor...");
			else if (num > clave)
				System.out.println("Menor...");
			else {
				System.out.format("¡¡EUREKA!! En sólo %d intentos.%n",
						intentos);
				return intentos;
			}
		} while (true);

	}

	/**
	 * Lee un número del teclado
	 * 
	 * @return
	 * @throws IOException
	 */
	private static int leerNumero() throws IOException {
		int num;
		
		do {
			try {
				num = Integer.parseInt(entrada.readLine().trim()); // Quita los
																	// espacios
																	// del
				// String y convierte a
				// int
				return num;
			} catch (NumberFormatException e) {
				System.out.println("Introduce un número correcto. ");
			}
		} while (true);
	}

	/**
	 * Pregunta al usuario si desea jugar de nuevo
	 * 
	 * @return
	 * @throws IOException
	 */
	private static char preguntarContinuar() throws IOException {
		String cadena;

		System.out.println("¿Deseas jugar de nuevo?(S/N)");
		char c;
		do {
			cadena = entrada.readLine(); // Lee una línea de texto (hasta
											// intro)
			c = cadena.charAt(0);
		} while (c != 'S' && c != 's' && c != 'N' && c != 'n');
		return c;
	}

	/**
	 * Muestra las instrucciones del juego
	 */
	private static void mostrarInstrucciones() {
		System.out
				.println("Voy a elegir un número entre 0 y 100. Adivínalo. Sólo te diré si es mayor o menor...");
	}
}
