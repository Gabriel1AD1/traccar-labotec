
---

# **Estructura de Paquetes del Proyecto**

Este documento describe la organización de paquetes dentro del proyecto `com.labotec.traccar`. La estructura propuesta sigue principios de **separación de responsabilidades** y buenas prácticas para asegurar que el código sea **mantenible**, **escalable** y **fácil de entender**.

---

## **Paquete Principal**

```plaintext
com.labotec.traccar
```

El paquete principal organiza todas las funcionalidades y componentes del proyecto.

---

## **Subpaquetes y Propósitos**

### **1. app**
Contiene clases **centrales** del proyecto relacionadas con constantes, documentación y lógica principal.

- **`constants`**:  
  Almacena constantes globales utilizadas en toda la aplicación.  
  **Ejemplo:**
  ```java
  public class AppConstants {
      public static final String DEFAULT_TIMEZONE = "UTC";
  }
  ```

- **`doc`**:  
  Almacena la documentación interna del proyecto, como el archivo `README.MD`.

---

### **2. enums**
Contiene **enumeraciones** utilizadas en la aplicación para representar valores constantes predefinidos.

**Ejemplo:**
```java
public enum STATE {
    ACTIVE, INACTIVE, PENDING
}
```

---

### **3. exception**
Contiene las clases de manejo de **excepciones** personalizadas.

- **`EntityNotFoundException`**: Maneja casos cuando una entidad no es encontrada.
- **`BusinessException`**: Maneja errores de lógica de negocio.

**Ejemplo:**
```java
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
```

---

### **4. implementation**
Almacena las **implementaciones concretas** de las interfaces definidas en `ports`.

**Ejemplo:**
- Implementación de servicios:
  ```java
  @Service
  public class VehicleServiceI implements VehicleService {
      // Lógica de negocio aquí
  }
  ```

---

### **5. lib**
Contiene bibliotecas adicionales o **utilidades externas** que pueden ser usadas dentro del proyecto.

---

### **6. log**
Almacena clases relacionadas con la **configuración de logs**.

---

### **7. mapper**
Contiene **mapeadores** que transforman datos entre entidades, DTOs y modelos.

**Ejemplo:**
```java
@Mapper(componentModel = "spring")
public interface VehicleMapper {
    Vehicle toModel(VehicleEntity entity);
    VehicleEntity toEntity(Vehicle model);
}
```

---

### **8. ports**
Define los **puertos de entrada y salida** para seguir el patrón de arquitectura hexagonal (o Clean Architecture).

- **`input`**:  
  Contiene las **interfaces de los repositorios del dominio ** que actúan como entrada para la lógica de negocio.  
  **Ejemplo:**
  ```java
  public interface VehicleRepository {
      Vehicle findById(Long id);
  }
  ```

- **`out.services`**:  
  Contiene servicios externos o implementaciones concretas que interactúan con sistemas externos.

---

### **9. utils**
Contiene **clases de utilidades** reutilizables en todo el proyecto.

**Ejemplo:**
```java
public class ValidationUtils {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
```

---

### **10. Validations**
Contiene **validadores personalizados** utilizados para validaciones complejas en DTOs o entidades.

**Ejemplo:**
```java
@Constraint(validatedBy = LatitudeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLatitude {
    String message() default "Invalid latitude";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

---

## **Resumen de la Estructura**

```plaintext
com.labotec.traccar
├── app
    ├── constants
    ├── doc
    ├── enums
    ├── exception
    ├── implementation
    ├── lib
    ├── log
    ├── mapper
    ├── ports
    │   ├── input.repository
    │   └── out.services
    ├── utils
    └── Validations
```

---

## **Buenas Prácticas**

1. **Separación de responsabilidades:**
    - Cada paquete tiene una responsabilidad clara y única.
2. **Nombres descriptivos:**
    - Los nombres de clases y paquetes deben describir su propósito.
3. **Estandarización de nombres:**
    - Interfaces de servicios: `NombreService`
    - Implementaciones: `NombreServiceI`
4. **Reutilización de código:**
    - Utiliza `utils` y `mapper` para evitar duplicación.
5. **Centralización de validaciones:**
    - Todas las validaciones personalizadas deben estar en el paquete `Validations`.

---


---

# **Estructura de Paquetes del Proyecto**

Este documento describe la organización de paquetes dentro del proyecto `com.labotec.traccar`. La estructura propuesta sigue principios de **separación de responsabilidades** y buenas prácticas para asegurar que el código sea **mantenible**, **escalable** y **fácil de entender**.

---

## **Deuda Técnica**

Actualmente, existen áreas identificadas con deuda técnica que requieren atención y mejoras para cumplir con las buenas prácticas de desarrollo:

1. **Anotaciones de Spring en los Servicios:**
    - Falta reemplazar las anotaciones de Spring (`@Service`, `@Component`) por anotaciones de un **dominio propio** que encapsulen la lógica de servicio.
    - Implementar una abstracción para las anotaciones de servicio de Spring y desacoplar la lógica de dominio de la infraestructura.

   **Tarea:**
    - Crear anotaciones personalizadas como `@DomainService` y configurarlas para ser compatibles con el motor de Spring.

   **Ejemplo actual:**
   ```java
   @Service
   public class VehicleServiceI implements VehicleService {
       ...
   }
   ```

   **Ejemplo deseado:**
   ```java
   @DomainService
   public class VehicleServiceI implements VehicleService {
       ...
   }
   ```

---

2. **Uso del `@Component` en los Mappers:**
    - Actualmente, los mapeadores utilizan la anotación `@Mapper` o `@Component` de Spring.
    - Se debe evitar el uso directo de `@Component` y mover las configuraciones de los mapeadores a una **clase de constantes centralizada**.

   **Tarea:**
    - Crear una configuración de mappers en un archivo de constantes o centralizar las configuraciones en una clase propia.

   **Ejemplo actual:**
   ```java
   @Mapper(componentModel = "spring")
   public interface VehicleMapper {
       Vehicle toModel(VehicleEntity entity);
   }
   ```

   **Ejemplo deseado:**
   ```java
   @Mapper(config = MapperConstants.class)
   public interface VehicleMapper {
       Vehicle toModel(VehicleEntity entity);
   }
   ```

   **Nueva constante:**
   ```java
   public class MapperConstants {
       public static final String MAPPER_COMPONENT_MODEL = "spring";
   }
   ```

---

3. **Código Comentado:**
    - Se han detectado fragmentos de **código comentado** en distintas clases.
    - El código comentado dificulta la legibilidad y puede generar confusión sobre qué es funcional y qué no.

   **Tarea:**
    - Eliminar cualquier código comentado que no tenga una justificación clara.
    - Si el código es necesario para referencias futuras, moverlo a un archivo de documentación o histórico.

   **Ejemplo:**
   ```java
   // Esto es código comentado
   // @Autowired
   // private SomeOldRepository oldRepository;

   // System.out.println("Debugging...");
   ```

   **Acción recomendada:**
    - Eliminar el código anterior o archivarlo en un comentario documentado en el historial de control de versiones.

---

## **Resumen de Deuda Técnica**

| **ID** | **Descripción**                                                   | **Prioridad** | **Tarea**                              |
|--------|-------------------------------------------------------------------|--------------|---------------------------------------|
| DT-001 | Reemplazar anotaciones de Spring por anotaciones del dominio.     | Alta         | Crear `@DomainService` y reemplazar.  |
| DT-002 | Mover configuraciones de mappers a constantes centralizadas.      | Media        | Crear `MapperConstants`.              |
| DT-003 | Eliminar código comentado innecesario.                            | Alta         | Revisar y limpiar clases afectadas.   |

---

## **Paquetes Principales**

> (Mantenemos la documentación previa sobre la organización de los paquetes del proyecto, como se describió anteriormente).



