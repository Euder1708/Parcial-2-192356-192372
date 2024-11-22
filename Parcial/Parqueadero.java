import java.util.ArrayList;
import java.util.Scanner;

class Vehiculo {
    String placa;
    String tipo; // "carro" o "moto"
    String propietario;

    public Vehiculo(String placa, String tipo, String propietario) {
        this.placa = placa;
        this.tipo = tipo;
        this.propietario = propietario;
    }
}

class EspacioParqueadero {
    int numeroEspacio;
    String tipoPermitido; // "carro" o "moto"
    String estado; // "disponible" o "ocupado"
    Vehiculo vehiculo; // Referencia al vehículo que ocupa el espacio

    public EspacioParqueadero(int numeroEspacio, String tipoPermitido) {
        this.numeroEspacio = numeroEspacio;
        this.tipoPermitido = tipoPermitido;
        this.estado = "disponible"; // Inicialmente disponible
        this.vehiculo = null;
    }

    public boolean asignarVehiculo(Vehiculo vehiculo) {
        if (this.estado.equals("disponible") && this.tipoPermitido.equals(vehiculo.tipo)) {
            this.estado = "ocupado";
            this.vehiculo = vehiculo;
            System.out.println("Vehículo con placa " + vehiculo.placa + " asignado al espacio " + this.numeroEspacio);
            return true;
        }
        return false;
    }

    public void liberarEspacio() {
        if (this.estado.equals("ocupado")) {
            System.out.println("Espacio " + this.numeroEspacio + " liberado. Vehículo con placa " + vehiculo.placa + " ha salido.");
            this.estado = "disponible";
            this.vehiculo = null; // Quitar referencia al vehículo
        } else {
            System.out.println("El espacio " + this.numeroEspacio + " ya está disponible.");
        }
    }
}

class GestionParqueadero {
    static ArrayList<EspacioParqueadero> espacios = new ArrayList<>();
    static ArrayList<Vehiculo> vehiculosRegistrados = new ArrayList<>();

    public static void registrarVehiculo(Vehiculo vehiculo) {
        vehiculosRegistrados.add(vehiculo);
        System.out.println("Vehículo con placa " + vehiculo.placa + " registrado.");
    }

    public static void agregarEspacio(int numeroEspacio, String tipoPermitido) {
        if (!tipoPermitido.equals("carro") && !tipoPermitido.equals("moto")) {
            System.out.println("Tipo de espacio inválido. Debe ser 'carro' o 'moto'.");
            return;
        }
        for (EspacioParqueadero espacio : espacios) {
            if (espacio.numeroEspacio == numeroEspacio) {
                System.out.println("El espacio " + numeroEspacio + " ya existe.");
                return;
            }
        }
        EspacioParqueadero espacio = new EspacioParqueadero(numeroEspacio, tipoPermitido);
        espacios.add(espacio);
        System.out.println("Espacio " + numeroEspacio + " añadido.");
    }

    public static void mostrarEspaciosDisponibles() {
        System.out.println("Espacios disponibles:");
        for (EspacioParqueadero espacio : espacios) {
            if (espacio.estado.equals("disponible")) {
                System.out.println("Espacio " + espacio.numeroEspacio + " (" + espacio.tipoPermitido + ")");
            }
        }
    }

    public static void asignarVehiculo(String placa) {
        Vehiculo vehiculo = vehiculosRegistrados.stream()
                .filter(v -> v.placa.equals(placa))
                .findFirst()
                .orElse(null);

        if (vehiculo == null) {
            System.out.println("El vehículo con placa " + placa + " no está registrado.");
            return;
        }

        for (EspacioParqueadero espacio : espacios) {
            if (espacio.asignarVehiculo(vehiculo)) return;
        }

        System.out.println("No hay espacios disponibles para el vehículo con placa " + placa);
    }

    public static void liberarEspacio(int numeroEspacio) {
        for (EspacioParqueadero espacio : espacios) {
            if (espacio.numeroEspacio == numeroEspacio) {
                espacio.liberarEspacio();
                return;
            }
        }
        System.out.println("El espacio " + numeroEspacio + " no existe.");
    }
}

public class Parqueadero {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicializar algunos datos
        GestionParqueadero.agregarEspacio(1, "carro");
        GestionParqueadero.agregarEspacio(2, "moto");

        Vehiculo vehiculo1 = new Vehiculo("ABC123", "carro", "Juan Pérez");
        Vehiculo vehiculo2 = new Vehiculo("XYZ456", "moto", "Ana Gómez");
        GestionParqueadero.registrarVehiculo(vehiculo1);
        GestionParqueadero.registrarVehiculo(vehiculo2);

        int opcion = 0;
        while (opcion != 5) {
            System.out.println("\n=== Menú Gestión de Parqueadero ===");
            System.out.println("1. Agregar espacio");
            System.out.println("2. Mostrar espacios disponibles");
            System.out.println("3. Asignar vehículo");
            System.out.println("4. Liberar espacio");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = scanner.nextInt();
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Número del espacio: ");
                        int numeroEspacio = scanner.nextInt();
                        String tipoPermitido;
                        do {
                            System.out.print("Tipo de espacio (carro/moto): ");
                            tipoPermitido = scanner.next().toLowerCase();
                            if (!tipoPermitido.equals("carro") && !tipoPermitido.equals("moto")) {
                                System.out.println("Tipo inválido. Intenta de nuevo.");
                            }
                        } while (!tipoPermitido.equals("carro") && !tipoPermitido.equals("moto"));
                        GestionParqueadero.agregarEspacio(numeroEspacio, tipoPermitido);
                    }
                    case 2 -> GestionParqueadero.mostrarEspaciosDisponibles();
                    case 3 -> {
                        System.out.print("Placa del vehículo: ");
                        String placa = scanner.next();
                        GestionParqueadero.asignarVehiculo(placa);
                    }
                    case 4 -> {
                        System.out.print("Número del espacio a liberar: ");
                        int numeroEspacio = scanner.nextInt();
                        GestionParqueadero.liberarEspacio(numeroEspacio);
                    }
                    case 5 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opción inválida. Intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Entrada no válida. Por favor, intenta de nuevo.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
        scanner.close();
    }
}