package app;

import java.util.*;
import estructuras.*;
import modelo.Centro_Montesdeoca;
import modelo.Producto_Montesdeoca;

public class SistemaLogistica_Montesdeoca {
    private static ListaProductos_Montesdeoca catalogoGeneral = new ListaProductos_Montesdeoca();
    private static Map<String, Centro_Montesdeoca> centros = new HashMap<>();
    private static GrafoMatriz redDistribucion = new GrafoMatriz();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        cargarDatosIniciales();

        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: gestionarCatalogo(); break;
                case 2: gestionarInventarioCentro(); break;
                case 3: gestionarRedRutas(); break;
                case 4: ejecutarDijkstra(); break; 
                case 5: System.out.println("Saliendo del sistema..."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }

    private static void mostrarMenu() {
        System.out.println("\n==== SISTEMA DE RUTAS Y PAQUETERIA ====");
        System.out.println("\n==== Montesdeoca Diego ====");
        System.out.println("1. Gestionar Catálogo (con Lista Enlazada)");
        System.out.println("2. Gestionar Inventario (con ABB)");
        System.out.println("3. Gestionar Rutas (con Grafo)");
        System.out.println("4. Calcular Rutas Óptimas (Dijkstra)"); 
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void cargarDatosIniciales() {
        // ... (código existente igual)
        catalogoGeneral.insertarAlFinal(new Producto_Montesdeoca(203, "Carne", 80));
        catalogoGeneral.insertarAlFinal(new Producto_Montesdeoca(201, "Leche", 30));
        catalogoGeneral.insertarAlFinal(new Producto_Montesdeoca(205, "Pollo", 25));
        catalogoGeneral.insertarAlFinal(new Producto_Montesdeoca(202, "Carbón", 70));
        catalogoGeneral.insertarAlFinal(new Producto_Montesdeoca(204, "Manzana", 35));

        centros.put("Centro Norte", new Centro_Montesdeoca("Centro Norte"));
        centros.put("Centro Sur", new Centro_Montesdeoca("Centro Sur"));
        centros.put("Centro Este", new Centro_Montesdeoca("Centro Este"));
        centros.put("Centro Oeste", new Centro_Montesdeoca("Centro Oeste"));
        centros.put("Centro Central", new Centro_Montesdeoca("Centro Central"));
        
        redDistribucion.definirCiudad(0, "Centro Norte");
        redDistribucion.definirCiudad(1, "Centro Sur");
        redDistribucion.definirCiudad(2, "Centro Este");
        redDistribucion.definirCiudad(3, "Centro Oeste");
        redDistribucion.definirCiudad(4, "Centro Central");

        redDistribucion.nuevoArco(0, 1, 50);   // Norte - Sur
        redDistribucion.nuevoArco(0, 4, 30);   // Norte - Central
        redDistribucion.nuevoArco(1, 4, 40);   // Sur - Central
        redDistribucion.nuevoArco(2, 4, 25);   // Este - Central
        redDistribucion.nuevoArco(3, 4, 35);   // Oeste - Central
    }

    // NUEVO MÉTODO PARA EJECUTAR DIJKSTRA
    private static void ejecutarDijkstra() {
        System.out.println("\n=== CÁLCULO DE RUTAS ÓPTIMAS (ALGORITMO DE DIJKSTRA) ===");
        System.out.println("1. Usar red actual del sistema");
        System.out.println("2. Usar ejemplo del laboratorio (PDF)");
        System.out.print("Seleccione opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        
        if (opcion == 1) {
            System.out.println("\nCentros disponibles:");
            for (int i = 0; i < 5; i++) {
                System.out.println(i + ". " + redDistribucion.getNombreCiudad(i));
            }
            System.out.print("Seleccione el centro de origen (índice): ");
            int origen = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println("\nCalculando rutas óptimas desde " + redDistribucion.getNombreCiudad(origen) + "...");
            redDistribucion.dijkstra(origen);
        } else if (opcion == 2) {
            // Crear un nuevo grafo para el ejemplo 
            GrafoMatriz grafoEjemplo = new GrafoMatriz();
            grafoEjemplo.pruebaDijkstraEjemplo();
        } else {
            System.out.println("Opción no válida.");
        }
    }

    private static void gestionarCatalogo() {
        System.out.println("\n=== GESTIÓN DE CATÁLOGO GENERAL ===");
        System.out.println("1. Mostrar catálogo ordenado por ID");
        System.out.println("2. Buscar producto por ID");
        System.out.println("3. Agregar nuevo producto");
        System.out.print("Seleccione opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("\nCatálogo ordenado por ID:");
                catalogoGeneral.ordenarPorId();
                catalogoGeneral.mostrarProductos();
                System.out.println("Total productos: " + contarProductos());
                break;
                
            case 2:
                System.out.print("Ingrese ID del producto a buscar: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                Producto_Montesdeoca producto = catalogoGeneral.buscarPorId(id);
                if (producto != null) {
                    System.out.println("Producto encontrado:");
                    System.out.println(producto);
                } else {
                    System.out.println("Producto no encontrado.");
                }
                break;
                
            case 3:
                System.out.println("\nAgregar nuevo producto:");
                System.out.print("ID: ");
                int nuevoId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Stock inicial: ");
                int stock = scanner.nextInt();
                scanner.nextLine();
                
                catalogoGeneral.insertarAlFinal(new Producto_Montesdeoca(nuevoId, nombre, stock));
                System.out.println("Producto agregado al catálogo.");
                break;
                
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static int contarProductos() {
        int contador = 0;
        NodoProducto_Montesdeoca actual = catalogoGeneral.head;
        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }
        return contador;
    }

    private static void gestionarInventarioCentro() {
        System.out.println("\n=== GESTIÓN DE INVENTARIO POR CENTRO ===");
        System.out.println("Centros disponibles:");
        int i = 1;
        for (String nombre : centros.keySet()) {
            System.out.println(i + ". " + nombre);
            i++;
        }
        
        System.out.print("Seleccione centro (número o nombre): ");
        String entrada = scanner.nextLine();
        
        Centro_Montesdeoca centro;
        try {
            int indice = Integer.parseInt(entrada) - 1;
            List<String> nombresCentros = new ArrayList<>(centros.keySet());
            centro = centros.get(nombresCentros.get(indice));
        } catch (Exception e) {
            centro = centros.get(entrada);
        }
        
        if (centro == null) {
            System.out.println("Centro no encontrado.");
            return;
        }

        System.out.println("\n=== CENTRO: " + centro.getNombre() + " ===");
        System.out.println("1. Agregar producto al inventario");
        System.out.println("2. Buscar producto por nombre");
        System.out.println("3. Mostrar inventario completo (inorden)");
        System.out.println("4. Mostrar total de productos en inventario");
        System.out.println("5. Recorrer inventario (preorden/postorden)");
        System.out.print("Seleccione opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("\nAgregar producto al inventario:");
                System.out.print("ID producto: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Nombre producto: ");
                String nombreProducto = scanner.nextLine();
                System.out.print("Stock inicial: ");
                int stock = scanner.nextInt();
                scanner.nextLine();

                try {
                    centro.getInventario().insertar(new Producto_Montesdeoca(id, nombreProducto, stock));
                    System.out.println("Producto añadido al inventario.");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
                
            case 2:
                System.out.print("Nombre del producto a buscar: ");
                String buscarNombre = scanner.nextLine();
                Producto_Montesdeoca productoBusqueda = new Producto_Montesdeoca(0, buscarNombre, 0);
                if (centro.getInventario().buscar(productoBusqueda) != null) {
                    System.out.println("Producto encontrado en el inventario.");
                } else {
                    System.out.println("Producto no encontrado.");
                }
                break;
                
            case 3:
                System.out.println("\nInventario (inorden - orden alfabético):");
                centro.getInventario().inorden(centro.getInventario().getRaiz());
                System.out.println();
                break;
                
            case 4:
                int total = centro.getInventario().contarNodos(centro.getInventario().getRaiz());
                System.out.println("Total de productos en inventario: " + total);
                break;
                
            case 5:
                System.out.println("\nRecorrido Preorden (Raíz-Izquierda-Derecha):");
                centro.getInventario().preorden(centro.getInventario().getRaiz());
                System.out.println("\n\nRecorrido Postorden (Izquierda-Derecha-Raíz):");
                centro.getInventario().postorden(centro.getInventario().getRaiz());
                System.out.println();
                break;
                
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void gestionarRedRutas() {
        System.out.println("\n=== GESTIÓN DE RED DE RUTAS ===");
        System.out.println("1. Mostrar matriz completa de distancias");
        System.out.println("2. Agregar nueva ruta");
        System.out.println("3. Consultar distancia entre centros");
        System.out.print("Seleccione opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("\nMATRIZ DE ADYACENCIA CON DISTANCIAS (km):");
                redDistribucion.imprimirMatriz();
                break;
                
            case 2:
                System.out.println("\nAgregar nueva ruta:");
                
                System.out.println("Centros disponibles:");
                for (int i = 0; i < 5; i++) {
                    System.out.println(i + ". " + redDistribucion.getNombreCiudad(i));
                }
                
                System.out.print("Índice del primer centro: ");
                int centroA = scanner.nextInt();
                System.out.print("Índice del segundo centro: ");
                int centroB = scanner.nextInt();
                System.out.print("Distancia en km: ");
                int distancia = scanner.nextInt();
                scanner.nextLine();
                
                if (centroA >= 0 && centroA < 5 && centroB >= 0 && centroB < 5) {
                    redDistribucion.nuevoArco(centroA, centroB, distancia);
                    System.out.println("Ruta agregada exitosamente.");
                } else {
                    System.out.println("Índices inválidos. Deben estar entre 0 y 4.");
                }
                break;
                
            case 3:
                System.out.println("\nConsultar distancia:");
                System.out.print("Nombre del primer centro: ");
                String origen = scanner.nextLine();
                System.out.print("Nombre del segundo centro: ");
                String destino = scanner.nextLine();
                redDistribucion.mostrarRuta(origen, destino);
                break;
                
            default:
                System.out.println("Opción no válida.");
        }
    }
}