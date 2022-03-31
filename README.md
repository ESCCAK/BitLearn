# BitLearn
Exploramos a traves de código Java los principales fundamentos de Bitcoin

## Quick start

Instalar Java Development Kit [JDK](https://www.oracle.com/java/technologies/downloads/)

Instalar IntelliJ IDEA [IntelliJ IDEA](https://www.jetbrains.com/es-es/idea/download/)


Abrir Intellj IDEA.

File-New-Project From Version Control-Git

Git Repository URL: https://github.com/ESCCAK/bitlearn.git



#Contenido
## 1. Validacion de Bloques

En esta sección revisarémos la manera en que es usada la función SHA-256 para la validación de bloques.


SHA-256 es el componentes criptográfico que se usa para la validación de bloques. SHA256 es una función hash.

Una función hash tiene como propósito generar un output de longitud fija para un input de cualquier tamaño.

El hash generado es una especie de “huella” del contenido ingresado como input.

Se puede ingresar por decir algo un video y el hash generado para este video siempre sera el mismo, pero si se cambia un solo bit de este video el hash generado será completamente distinto.

En el caso de SHA-256 el output generado es una cadena de 256 bits.

La manera en que Bitcoin asegura los bloques es generando una cadena hash del bloque actual, y el bloque actual conteniendo el hash del bloque anterior

Se tendría algo así:
Bloque_0{prev_hash:000,hash:xxx, content:”hola”}->Bloque_1{prev_hash:xxx,hash:xx1, content:”mundo”}->Bloque_2{prev_hash:xx1,hash:xx2,content:”bitcoin”]

El primer bloque, al ser el bloque genesis no tiene un hash de un bloque anterior, por tal razón el prev-hash es 000, y tiene un hash de xxx. El siguiente bloque tiene un prev_hash de xxx y un hash de xx1 y el Bloque_2 un prev_hash de xx1 y un hash de xx2

Supongamos que decidamos alterar el bloque_0 con el contenido “bye”

Al aplicarle la función hash a este nuevo bloque el hash de este bloque cambiaría y como el bloque_1 contiene el hash del bloque_0 el hash del bloque_1 también cambiara y así se alterarían todos los bloques sucesivos hacia adelante en la cadena

Como Bitcoin es una red distribuida el estado de la cadena la comparten varios nodos. Cuando un nodo se “sincroniza”, lo hace  conectandose a diferentes nodos y tomando diferentes bloques de diferentes nodos.

Para asegurarse de la validez de la cadena se debe aplicar la función hash a cada bloque sincronizado.

si un solo bloque sincronizado ha sido alterado se podrá saber de inmediato que la cadena esta alterada ya que la generación de la cadena hash de los bloques posteriores no corresponderá si un solo bloque anterior ha sido alterado 


Puedes revisar estos conceptos en código en el archivo test: 

[src/test/java/esccak/bitlearn/services/ValidateBlockServiceTest.java](https://github.com/ESCCAK/bitlearn/blob/main/src/test/java/esccak/bitlearn/services/ValidateBlockServiceTest.java)

## 2. Proof of Work (PoW)

PoW -Proof of Work- es uno de los mecanismo por medio del cual Bitcoin puede operar de manera descentralizada sin la necesidad de un agente central que valide las transacciones.  

Con PoW los nodos 'mineros' compiten para agregar nuevos bloques a la cadena resolviendo un "acertijo criptográfico".

Este "acertijo" consiste en generar un output de la función SHA-256 que contenga al inicio del output cierto número de 0's, teniendo como input el bloque que se quiere agregar.

Para agregar un nuevo bloque a la cadena se debe aplicar la función SHA-256 al bloque que queremos agregar.

Como ya vimos, un mínimo cambio en el input de la funcion hash va a generar un output totalmente diferente. Así, se debe tener una variable que haga parte de nuestro bloque y que podamos modificar de manera aleatoria.

Al modificar esta variable y aplicar la función SHA-256 a nuestro bloque, está nos irá generando outputs diferentes.

Así, se necesitará de un algoritmo que cambie esta variable y le aplique la función hash cuantas veces sea necesario a nuestro bloque hasta que el output dé como resultado una cadena output que contenga los ceros que indique el nivel de dificultad en ese momento.

Este nivel de dificultad dependerá de cuantos 0's se necesitaran en el output al aplicarle la funcion hash a nuestro bloque. Entre más 0's se necesiten el nivel de dificultad sera mayor.

El nivel de dificultad se va actualizando para que el tiempo promedio que tome agregar un nuevo bloque a la cadena sea de 10 minutos.

Una vez uno de los nodo 'mineros' logra resolver el "acertijo" podrá agregar el nuevo bloque a la cadena y recibirá como recompensa bitcoins.


Puedes revisar estos conceptos en código en el archivo test: 

[src/test/java/esccak/bitlearn/services/PoWTest.java](https://github.com/ESCCAK/bitlearn/blob/main/src/test/java/esccak/bitlearn/services/ValidateBlockServiceTest.java)

