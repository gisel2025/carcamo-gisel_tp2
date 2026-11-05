# TP2 — Sistema de Gestión de Envíos y Distribución

## Cómo correrlo
```
cd src
javac *.java
java App
```

## ¿Qué hace cada clase?

**Paquete**
Es la caja o producto que se envía. Guarda sus datos, como qué contiene y
cuánto pesa. También tiene una etapa en la que se encuentra, por ejemplo:
recibido, en preparación, en viaje o entregado. Para cambiar de etapa
tiene métodos que controlan que el cambio sea correcto.

**Cliente**
Es la persona que realiza el envío. Guarda sus datos, como el nombre y el
contacto, y también guarda una lista de los envíos que realizó. Cuando se
crea un envío, este se agrega automáticamente a la lista del cliente.

**Sucursal**
Es el lugar físico por donde pasa el envío. La sucursal no toma decisiones
sobre el envío, sino que solamente informa cuando recibe un envío y deja
que la clase "Envio" se encargue de realizar las acciones correspondientes.

**Movimiento**
Es el registro de lo que va pasando con el envío. Cada vez que ocurre algo
importante, se guarda la fecha, el lugar y la acción realizada. De esta
manera se puede saber por qué lugares pasó el envío y qué ocurrió en cada
momento.

**Envio**
Es la clase principal porque se encarga de controlar el recorrido del
envío. Puede tener de uno a tres paquetes. Cuando recibe información de
una sucursal, verifica que todo esté correcto, cambia las etapas de los
paquetes y registra el movimiento correspondiente en su historial.

**EnvioEstandar, EnvioExpress y EnvioInternacional**
Son diferentes tipos de envío. Los tres tienen las características de un
"Envio", pero cada uno calcula el precio de una manera diferente. Por
ejemplo, el express tiene un costo mayor y el internacional puede agregar
un costo de aduana. Esto se logra mediante la herencia.

**App**
Es la parte donde se prueba el funcionamiento del programa. Ahí se crean
los clientes, las sucursales y los envíos, y se realizan las acciones
para comprobar que todo funcione correctamente. La "App" no se encarga
de tomar las decisiones, sino que inicia las acciones y cada objeto
realiza lo que le corresponde.

---

## Preguntas de reflexión

**1. ¿Qué responsabilidades asignaste a cada clase?**

Yo traté de que cada clase tuviera una responsabilidad diferente. Por
ejemplo, Paquete se encarga de su estado, Cliente de los datos y sus
envíos, Sucursal de recibirlos, Movimiento de guardar el historial y
Envio de controlar todo el recorrido. Los tipos de envío se encargan de
calcular el costo según corresponda.

**2. ¿Qué decisiones fueron más difíciles?**

Lo que más me costó fue decidir qué debía hacer cada clase y dónde poner
cada función. También tuve que pensar cómo hacer para que los cambios de
estado del paquete fueran correctos y no se pudiera pasar de una etapa a
otra sin sentido.

**3. ¿Qué parte del trabajo te resultó más difícil?**

La parte que más me costó fue entender cómo se relacionaban las clases
entre sí, especialmente Envio, Sucursal y Paquete. Al principio me
parecía complicado, pero después entendí que cada objeto tenía que
encargarse de una tarea específica.

**4. ¿Qué aprendiste realizando el trabajo?**

Aprendí mejor cómo funciona la programación orientada a objetos,
principalmente la herencia, las clases y la responsabilidad que tiene
cada objeto. También entendí que es importante organizar bien el código
para que cada clase haga lo que corresponde.

**5. ¿Qué cambiarías o mejorarías del trabajo?**

Si tuviera que hacerlo nuevamente, trataría de organizar algunas partes
desde el principio para no tener que corregirlas después. También
revisaría mejor las relaciones entre las clases antes de empezar a
programar. Creo que con la práctica voy a poder hacerlo de una manera
más ordenada y rápida.


---

## Correcciones de la reentrega

**1. Tipo de evento en Movimiento**
Antes el tipo de evento era un texto ("RECIBIDO", "DESPACHADO" o "ENTREGADO").
Ahora es un `enum`. Así no se puede escribir
mal ni poner un evento que no existe.

**2. Las preguntas ahora son funciones**
Antes, en `agregarPaquete`, guardaba las preguntas en variables (`hayLugar` y
`todaviaNoSalio`). Ahora cada pregunta es una función privada. Así se puede usar
en cualquier parte de la clase sin repetir el código. Hice lo mismo con
`puedeDespacharse` y `puedeViajar`.

**3. Saqué el atributo `sucursalDondeEstoyAhora`**
Ya no guardo en una variable en qué sucursal está el envío. Ahora lo averiguo
mirando el último movimiento del historial: si fue "recibido", el envío está en
esa sucursal; si fue "despachado", está viajando. Así no tengo que usar `null`
ni acordarme de actualizar la variable.
