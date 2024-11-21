package Ejercicio18;

import Ejercicio17.Parking;

import java.util.Random;

/**
 * La clase Swifter representa al alumno promedio con el cerebro completamente fundido
 * como para escuchar "el karma es un gato" y pensar que es la mejor cancion que ha escuchado en su vida
 * */
public class Swifter extends Thread {
    private final int id; // Identificador único
    private final PuntoDeVentaSwifty entrada; // Objeto único de Parking compartido entre los hilos
    private final Random random = new Random(); // Simula tiempo aleatorio entre entradas y salidas
    private int intentos = 5;

    /**
     * Constructor parametrizado para la creación de una nueva instancia
     *
     * @param id Identidicador único
     * @param entrada Objeto compartido
     * */
    public Swifter(int id, PuntoDeVentaSwifty entrada) {
        this.id = id;
        this.entrada = entrada;
    }

    /**
     * El metodo "desesperacion" representa el numero de intentos fallidos antes de convertirse en fan de Ye
     * */
    private desesperacion()

    /**
     * El metodo "run" se ejecuta cuando el hilo comienza.
     * El swifter ante la imposibilidad de conseguir una entrada intenta competir salvajemente para conseguir una.
     * */
    @Override
    public void run(){
        try {

            sleep(random.nextInt(1000)); // Espera un numero aleatorio de milisegundos

        } catch (InterruptedException e) {
            System.err.println(e.getMessage()); // Muestra mensaje de error
            Thread.currentThread().interrupt(); // Restaura el estado interrumpido
        }
    }
}
