import java.util.Random;

/**
 * Esta clase representa un parking con una capacidad limitada.
 * Sigue el modelo Singleton para asegurar que solo exista una instancia del Parking.
 * Cada plaza está representada por un array y los coches se aparcan dependiendo de la disponibilidad de cada plaza.
 * */
public class Parking {
    // Atributos
    private static boolean[] control; // Lista de booleanos. Representa la disponibilidad de cada plaza de garaje
    private static int capacidad; // Capacidad máxima del garaje
    private final Random random = new Random(); // Objeto random para la seleccion de las plazas asignadas

    private static Coche[] coches = null; // Lista de coches. Representa los coches que entran y salen
    public static Parking parking = null; // Instancia única de la clase Parking. Singleton

    // Constructores
    private Parking() {}

    private Parking(int capacidad, int numCoches) { // Inicializa diversos atributos de la clase desde main
        Parking.capacidad = capacidad;
        Parking.coches = new Coche[numCoches];
        Parking.control = new boolean[capacidad];
    }

    /**
     * Metodo getInstanceOf empleado para el retorno de la única instancia de Parking.
     * Una vez de instancia el objeto, se inicializan los atributos.
     * @param capacidad Maximo numero de coches permitidos
     * @param numCoches Numero de coches instanciados
     * @return Parking
     * */
    public static Parking getInstanceOf(int capacidad, int numCoches) {
        if (parking == null) parking = new Parking(capacidad, numCoches);  // Inicializa el construcor parametrizado
        return parking;
    }

    /**
     * Metodo entrada empleado para el control de los coches al parking.
     * 1. Se asigna una plaza aleatoria, estando esta disponible o no. Más explicación en el metodo "getPlaza()"
     * 2. Se comprueba el estado de la plaza asignada: si está ocupada, el hilo espera a que se desocupe.
     * 3. Se añade la instancia del objeto coche pasado por parametro a la lista de Coches e inmediatamente el estado de esa plaza de garaje pasa a ser TRUE (ocupado)
     * 4. Se llama al metodo "parkingTag" para informar del estado del parking (plazas libres)
     * 5. Se avisa al resto de hilos de que la plaza está disponible
     *
     * @param coche Instancia de Coche de la cual se consigue el id.
     * */
    protected synchronized void entrada(Coche coche) {
        int id = coche.getid(); // Identificador
        int plaza = getPlaza();

        // Espera a que la plaza esté disponible
        while (control[plaza]) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(e.getMessage());
            }
        }

        coches[plaza] = coche; // Aparca el coche
        control[plaza] = true; // Plaza ocupada

        System.out.println("--Entrada--");
        System.out.printf("Coche %d aparca en %s%n", id, plaza+1);

        parkingTag(); // Llamada al metodo que recorre el parking para ver que lugares estan ocupados y cuales no

        notifyAll(); // Avisa a los Threads que se encuentran durmiendo
    }

    /**
     * Maneja la salida de los coches del parking, liberando la plaza en el acto
     *
     * @param id Identificador del coche
     * */
    protected synchronized void saida(int id) {
        System.out.println("--Salida--");

        // Encuentra la plaza asignada al coche con el ID pasado por parametro
        int plaza = -1;
        for (int i = 0; i < capacidad; i++) {
            if (coches[i] != null && coches[i].getid() == id) {
                plaza = i;
                break;
            }
        }

        // Si plaza equiva a "-1" entonces quiere decir que el coche no está en el parking por lo que no continua la ejecucion del codigo
        if (plaza == -1) {
            System.out.println("No se encontro el coche");
            return;
        }

        // Libera la plaza del parking
        coches[plaza] = null;
        control[plaza] = false;

        // Informa de la plaza liberada
        System.out.printf("Coche %d sale de %s%n", id, plaza+1);

        // Informa de las plazas libres en total
        parkingTag();

        // Avisa al resto de Threads de que la plaza se ha liberado
        notifyAll();
    }

    /**
     * Retorna una plaza aleatoria del parking esté ocupada o no
     *
     * @return La plaza random
     * */
    private synchronized Integer getPlaza(){
        // Podria hacerlo de manera que luego los hilos no tengan que esperar a que las plazas se liberen ya que se les
        // asignarian plazas unicamente libres pero quiero que haya concurrencia.
        /* De esa manenera tendria que hacer un bucle que no retorne hasta que la plaza este libre:
        int plaza;
        do {
            plaza = random.nextInt(capacidad);
        } while (control[plaza]); // Mientras plaza el contenido de control en dicha plaza sea true, sigue iterando
        return plaza;
        */
        return random.nextInt(capacidad); // retorna una plaza cualquiera, esté ocupada o no, de esta manera el hilo ha de esperar y le da más "juego" al programa
    }

    /**
     * Muestra por pantalla el estado general del parking
     * */
    private void parkingTag() {
        StringBuilder mensaje = new StringBuilder();
        int plazasLibres = 0;

        // Recorre la lista de coches en busca de plazas libres
        for (Coche coches : coches) {
            if (coches == null) {
                mensaje.append("[0]");
                plazasLibres ++;
            } else {
                mensaje.append("[").append(coches.getid()).append("]");
            }
        }

        // Informa al usuario de las plazas libres
        System.out.println("Plazas libres: " + plazasLibres);
        System.out.println("Parking: " + mensaje);
        System.out.println(); // Salto de linea
    }
}
