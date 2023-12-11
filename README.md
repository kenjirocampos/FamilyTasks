Requerimientos Funcionales:

1)Registro de Usuario:
  -La aplicación debe permitir que los usuarios se registren proporcionando su nombre, correo electrónico y contraseña.
  
2)Autenticación:
  -Los usuarios registrados deben poder iniciar sesión en la aplicación utilizando su correo electrónico y contraseña.
  -Se debe proporcionar la opción de recordar el estado de inicio de sesión para evitar que los usuarios tengan que ingresar sus credenciales cada vez que abran la aplicación.
  
3)Lista de Tareas Generales:
  -Al iniciar sesión, los usuarios deben ver una lista de "Tareas Generales" que incluye el nombre de la tarea, su estado (completo/incompleto) y un botón para asignarse la tarea.
  
4)Detalle de Tareas:
  -Los usuarios deben poder hacer clic en una tarea en la lista para ver más detalles, como descripción, fecha de inicio y fecha de término.
  
5)Asignación de Tareas:
  -Debe existir la capacidad para que un usuario se asigne una tarea haciendo clic en un botón específico.
  -Cuando un usuario se asigna una tarea, la base de datos debe actualizarse con el nombre del usuario asignado y cambiar el estado de la tarea a "asignado".
  
6)Lista de Mis Tareas:
  -Los usuarios deben tener acceso a una lista separada llamada "Mis Tareas", que muestra las tareas que se han asignado a ellos.
7)Creación de Tareas Nuevas:
  -Se debe proporcionar una funcionalidad para que los usuarios creen nuevas tareas, ingresando información como nombre, detalles, fecha de inicio, fecha de término y estado (completo/incompleto).
  
8)Configuración:
  -La vista de configuración debe incluir un botón para permitir que los usuarios cierren sesión en la aplicación.


Requerimientos No Funcionales:

1)Seguridad:
  -La aplicación debe implementar medidas de seguridad adecuadas para proteger la información del usuario, como contraseñas almacenadas de forma segura y la transmisión segura de datos.
  
2)Usabilidad:
  -La interfaz de usuario debe ser intuitiva y fácil de usar, permitiendo a los usuarios realizar tareas como registro, inicio de sesión y asignación de tareas de manera eficiente.
  
3)Persistencia de Datos:
  -Los datos de usuario, así como la información de las tareas, deben almacenarse de manera persistente en la base de datos para garantizar la coherencia y disponibilidad de la información.
  
4)Disponibilidad:
  -La aplicación debe estar disponible para su uso, con tiempos de inactividad mínimos, asegurando que los usuarios puedan acceder a sus tareas cuando lo necesiten.
  
5)Compatibilidad:
  -La aplicación debe ser compatible con diferentes dispositivos y sistemas operativos para garantizar un amplio alcance de usuarios.
  
6)Mantenibilidad:
  -El código de la aplicación debe estar bien estructurado y documentado para facilitar la mantenibilidad y futuras actualizaciones.
  
7)Privacidad:
  -Se deben implementar políticas de privacidad que protejan la información personal de los usuarios y cumplan con las regulaciones de privacidad aplicables.
  
8)Rendimiento:
  -La aplicación debe ser eficiente en términos de rendimiento, respondiendo rápidamente a las interacciones del usuario y evitando tiempos de carga prolongados.
