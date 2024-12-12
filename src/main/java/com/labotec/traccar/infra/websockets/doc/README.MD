# Cliente WebSocket con SockJS y STOMP

Este cliente está diseñado para conectarse a un servidor WebSocket utilizando SockJS y STOMP. Permite a los usuarios conectarse, suscribirse a mensajes privados y mostrarlos en la interfaz.

---

## Requisitos Previos

1. **Servidor WebSocket Activo**:
    - El backend debe estar configurado para aceptar conexiones WebSocket con SockJS y STOMP.
    - El servidor debe permitir mensajes privados utilizando el prefijo `/user`.

2. **Entorno Local**:
    - Asegúrate de que el servidor está corriendo en `http://localhost:9010/ws`.

---

## Estructura del Cliente

El cliente consiste en un archivo HTML que incluye:
1. Un campo para ingresar el `User ID`.
2. Botones para conectar y desconectar.
3. Un área para mostrar los mensajes recibidos.

---

## Pasos para Integrar el Cliente

1. **Incluir el Archivo HTML en Tu Proyecto**
    - Copia el archivo HTML en la carpeta de tu frontend.
    - Por ejemplo, colócalo en `public/websocket-client.html`.

2. **Acceder al Cliente**
    - Abre el archivo HTML directamente en el navegador:
      ```bash
      open public/websocket-client.html
      ```
    - Si tu frontend utiliza un servidor como Vite, React o Angular, asegúrate de configurar una ruta que permita servir este archivo.

3. **Modificar la URL del Servidor**
    - En el script del archivo HTML, verifica la línea donde se crea la conexión con SockJS:
      ```javascript
      const socket = new SockJS(`http://localhost:9010/ws?userId=${userId}`);
      ```
    - Cambia `http://localhost:9010/ws` por la URL de tu servidor WebSocket en producción si es necesario.

---

## Cómo Usar el Cliente

1. **Abrir el Cliente**
    - Ingresa a la página HTML en tu navegador.

2. **Conectarte al Servidor**
    - Ingresa un `User ID` en el campo de texto.
    - Haz clic en el botón "Connect".

3. **Recibir Mensajes**
    - El cliente se suscribe automáticamente al canal privado del usuario: `/user/queue/messages`.
    - Cualquier mensaje enviado a este destino será mostrado en la sección "Messages".

4. **Desconectarte del Servidor**
    - Haz clic en el botón "Disconnect" para cerrar la conexión WebSocket.

---

## Ejemplo de Uso

1. **Conexión Exitosa**
    - Ingresa un `User ID`, como `123`, y haz clic en "Connect".
    - Deberías ver el mensaje:
      ```
      Conectado como Usuario ID: 123
      ```

2. **Recepción de Mensajes**
    - Si el servidor envía un mensaje al destino `/user/123/queue/messages`, aparecerá en la sección "Messages".

---

## Personalización

1. **Estilo CSS**:
    - Puedes personalizar el contenedor de mensajes modificando el atributo `style` en el `<div id="messages">`:
      ```html
      <div id="messages" style="border: 1px solid #000; padding: 10px; width: 50%; height: 200px; overflow-y: scroll;"></div>
      ```

2. **Agregar Funcionalidades**:
    - Si necesitas enviar mensajes desde el cliente, puedes agregar un formulario con un botón que envíe mensajes al servidor utilizando `stompClient.send()`.

---

## Limitaciones

- El cliente no maneja errores en tiempo de ejecución, como pérdida de conexión.
- Si se conecta más de una vez con el mismo `User ID`, el servidor podría sobrescribir sesiones previas.

---

¡Listo! Ahora puedes integrar y usar este cliente WebSocket en tu proyecto frontend.
