package Ejercicio17;

/**
 * La clase Main inicializa el parking y crea diferentes hilos (Coche) para simular el aparcamiento de diferentes coches
 * @author Santiago Romero
 * @version 1.0
 * */
public class Main {
    static int capacidad = 5;
    static Parking parking = Parking.getInstanceOf(capacidad, capacidad); // Guardo el objeto a partir de la instancia Singleton

    public static void main(String[] args) {
        // Se generan los threads de los coches que entraran y saldran del parking
        System.out.println("Generando THREADs...");
        for (int i = 1; i <= capacidad; i++) {
            new Coche(i, parking).start();
        }
    }
}