
---

# **Reglas de Nomenclatura y Exposición de Servicios**

Este documento establece las reglas y convenciones para **nombrar las clases** y **exponer servicios** en el proyecto, asegurando claridad, mantenibilidad y consistencia.

---

## **Reglas de Nomenclatura**

1. **Nombres de clases de servicios:**
    - Las **implementaciones de los servicios** deben llevar una **"I" mayúscula** al final del nombre.
    - Las **interfaces de los servicios** no llevan sufijo adicional.

   **Ejemplo:**
   ```java
   // Interfaz del servicio
   public interface LocationService {
       Location findById(Long id);
   }

   // Implementación del servicio
   @Service
   public class LocationServiceI implements LocationService {
       @Override
       public Location findById(Long id) {
           // Implementación aquí
           return locationRepository.findById(id)
                   .orElseThrow(() -> new EntityNotFoundException("Location not found"));
       }
   }
   ```

2. **Reglas de nombres:**
    - **Interfaces:** `NombreService`
        - Ejemplo: `VehicleService`, `ScheduleService`
    - **Implementaciones:** `NombreServiceI`
        - Ejemplo: `VehicleServiceI`, `ScheduleServiceI`

---

## **Organización de Paquetes**

La estructura de paquetes debe ser lógica y organizada, como se muestra a continuación:

```
src/
├── ports/
│   ├── input/
│   │   └── repository/ // Interfaces (accesos a repositorios)
│   └── out/
│       └── services/
│           └── LocationService.java  // Interfaces (expocision de servicio)
├── implementation/
│   └── LocationServiceI.java
└── utils/
    └── ValidationUtils.java
```

- **Interfaz del servicio:** `ports.input`
- **Implementación del servicio:** `ports.out.services` o `implementation`

---

## **Exposición de Servicios**

1. **Interfaz y lógica concreta:**
    - Los servicios deben estar **expuestos a través de una interfaz** y su implementación concreta debe usar la `I` mayúscula al final.

   **Ejemplo completo:**

   **Interfaz:**
   ```java
   public interface VehicleService {
       Vehicle findById(Long id);
   }
   ```

   **Implementación:**
   ```java
   @Service
   public class VehicleServiceI implements VehicleService {
       @Override
       public Vehicle findById(Long id) {
           return vehicleRepository.findById(id)
                   .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with ID: " + id));
       }
   }
   ```

2. **Uso en controladores:**
    - Los controladores deben depender únicamente de la interfaz.

   ```java
   @RestController
   @RequestMapping("/api/v1/vehicles")
   public class VehicleController {
       private final VehicleService vehicleService;

       public VehicleController(VehicleService vehicleService) {
           this.vehicleService = vehicleService;
       }

       @GetMapping("/{id}")
       public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
           return ResponseEntity.ok(vehicleService.findById(id));
       }
   }
   ```

---

## **Validaciones**

1. **Validaciones de entrada (DTOs):**
    - Usa **Jakarta Bean Validation** con anotaciones estándar:
        - `@NotNull`, `@NotEmpty`, `@Size`, etc.

   ```java
   public class VehicleRequestDTO {
       @NotNull(message = "ID no puede ser nulo")
       private Long id;

       @NotEmpty(message = "El nombre es obligatorio")
       @Size(max = 200, message = "El nombre no puede tener más de 200 caracteres")
       private String name;
   }
   ```

2. **Validaciones en controladores:**
    - Usa `@Valid` en los parámetros del controlador.

   ```java
   @PostMapping
   public ResponseEntity<Void> createVehicle(@Valid @RequestBody VehicleRequestDTO request) {
       vehicleService.create(request);
       return ResponseEntity.ok().build();
   }
   ```

3. **Validaciones de negocio:**
    - Lógica compleja se valida en los servicios:
      ```java
      if (vehicleRepository.existsByName(request.getName())) {
          throw new BusinessException("El vehículo ya existe con este nombre.");
      }
      ```

---

## **Resumen**

1. **Nombres de clases:**
    - **Interfaz del servicio:** `NombreService`
    - **Implementación:** `NombreServiceI`

2. **Paquetes:**
    - **Interfaz:** `ports.input`
    - **Implementación:** `ports.out.services` o `implementation`

3. **Exposición de servicios:**
    - Los controladores usan únicamente la **interfaz**.
    - La implementación concreta está en una clase con `I` al final.

4. **Validaciones:**
    - Entrada (DTOs): `Jakarta Bean Validation`.
    - Lógica compleja: Validaciones de negocio en los servicios.

---

### Ejemplo Final

```java
// Interfaz del servicio
public interface VehicleService {
    Vehicle findById(Long id);
}

// Implementación del servicio
@Service
public class VehicleServiceI implements VehicleService {
    @Override
    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with ID: " + id));
    }
}

// Controlador
@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.findById(id));
    }
}
```

---

