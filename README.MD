# **TRACCAR API LABOTEC**

### **Propósito**
Esta API está destinada a **monitorear rutas y paraderos** en el contexto de seguimiento vehicular, gestión de compañías y locales, y otros aspectos relacionados con el mantenimiento y manejo de flotas.

---

## **Arquitectura**
El proyecto está basado en la **Arquitectura Hexagonal**:
- **Referencia:** [¿Qué es Arquitectura Hexagonal?](https://blog.hubspot.es/website/que-es-arquitectura-hexagonal)
- El diseño permite una mayor separación entre la lógica de negocio y las interfaces externas, promoviendo modularidad y facilidad de mantenimiento.

---

## **Tablas Principales**
Las principales entidades del sistema son:

1. **Rutas**
    - Gestión y creación de rutas.
    - Relación con paraderos y compañías.

2. **Paraderos**
    - Definición y administración de paraderos (origen, destino, intermedios).

3. **Locales**
    - Información sobre puntos físicos asociados a la operación.

4. **Compañías**
    - Registro y gestión de las compañías que operan vehículos.

5. **Mantenimiento**
    - Control de mantenimientos preventivos y correctivos de los vehículos.

6. **Comandos**
    - Ejecución de comandos específicos para los dispositivos rastreadores.

7. **Mensajes**
    - Gestión de mensajes generados por dispositivos o la plataforma.

---

## **Features**

### **1. Creación de Paraderos**
- Fecha de implementación: **22/10/2024**
- Realizado por: **cerroteberes@gmail.com**

---

### **2. Creación de Rutas**
- Fecha de implementación: **23/11/2024**
- Realizado por: **cerroteberes@gmail.com**

---

### **3. Seguimiento Vehicular**
- Funcionalidades principales:
    - **Porcentaje de avance**: Muestra el progreso de los vehículos a lo largo de las rutas asignadas.
    - **Porcentaje de manejo**: Indica la cantidad de tiempo manejado en comparación con el plan.
    - **Buenas conductas**: Monitoreo de comportamientos adecuados en la conducción.
    - **Seguimiento de tiempos**: Registro de tiempos en las rutas para optimizar operaciones.

---

## **Próximos Pasos**
1. **Integrar métricas avanzadas para análisis de datos de seguimiento.**
2. **Optimizar el monitoreo de rutas en tiempo real.**
3. **Ampliar soporte para configuraciones dinámicas en la creación de rutas y paraderos.**
4. **Implementar notificaciones automáticas basadas en eventos clave (retrasos, mantenimientos pendientes, etc.).**
