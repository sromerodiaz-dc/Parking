import java.util.Random;

/**
 * La clase Coche representa el coche individual manejado por el hilo de ejecución.
 * Cada coche intenta entrar al parking y salir del mismo cada X tiempo.
 * */
public class Coche extends Thread {
    private final int id; // Identificador único
    private final Parking parking; // Objeto único de Parking compartido entre los hilos
    private final Random random = new Random(); // Simula tiempo aleatorio entre entradas y salidas

    /**
     * Constructor parametrizado para la creación de una nueva instancia
     *
     * @param id Identidicador único
     * @param parking Objeto compartido
     * */
    public Coche(int id, Parking parking) {
        this.id = id;
        this.parking = parking;
    }

    /**
     * Retorna el identificador
     *
     * @return ID del coche
     * */
    public int getid() {
        return id;
    }

    /**
     * El metodo "run" se ejecuta cuando el hilo comienza.
     * El coche intentará entrar al parking y despues de un tiempo aleatorio saldrá.
     * */
    @Override
    public void run(){
        try {
            parking.entrada(this); // Entra al parking dando su id y su instancia
            sleep(random.nextInt(1000)); // Espera un numero aleatorio de milisegundos
            parking.saida(id); // Se marcha del parking
        } catch (InterruptedException e) {
            System.err.println(e.getMessage()); // Muestra mensaje de error
            Thread.currentThread().interrupt(); // Restaura el estado interrumpido
        }
    }
}
