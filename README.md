# BitLearn
Exploramos a traves de código Java los principales fundamentos de Bitcoin

## Quick start

Instalar Java Development Kit [JDK](https://www.oracle.com/java/technologies/downloads/)

Instalar IntelliJ IDEA [IntelliJ IDEA](https://www.jetbrains.com/es-es/idea/download/)


Abrir Intellj IDEA.

File-New-Project From Version Control-Git

Git Repository URL: https://github.com/ESCCAK/bitlearn.git



# Contenido
## 1. Validacion de Bloques

A continuación veremos la manera en que es usada la función SHA-256 para la validación de bloques.


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

## 3. Firmas Digitales (Digital Signatures)

Por medio de las firmas digitales es posible transferir fondos en la red de Bitcoin.

Una firma digital es un mecanismo que usa la criptografía por medio de la cual se puede demostrar que se posee la llave privada con la que ha sido firmado un mensaje.

Las firmas digitales funcionan gracias a la criptografía de llave pública o criptografía asimétrica.

Por medio de la criptografía asimétrica es posible generar una llave privada y una llave pública. La llave privada servirá para firmar mensajes, mientras que la llave publica se usará para verificar la validez de la firma.

Si alguien desea enviar un mensaje a un tercero y este tercero necesita verificar que efectivamente quien envía el mensaje posee la llave privada con la que se firmó este mensaje, lo podrá verificar haciendo uso de la llave pública de la persona que le envía el mensaje.

Estos mensajes en bitcoin serían las transacciones y el tercero serían los mineros quienes se encargan de verificar la validez de la transacción para poder agregarla a un nuevo bloque.

Veamos un pequeño ejemplo para ilustrar cómo funciona. 

```
    BIT_PLAY_TOKEN {
        owner:"ALICE_PUB_KEY"
    }
    ---after transaction-->
    BIT_PLAY_TOKEN{
        owner:"BOB_PUB_KEY"
    }
```

Alice posee el asset "BIT_PLAY_TOKEN" que desea transferirlo a Bob.

Alice previamente ha generado una llave asimétrica. Ella posee la llave privada y la pública la conocen los terceros.

Para poder transferir el asset a Bob Alice necesitará demostrar que posee la llave privada asociada a ALICE_PUB_KEY

Así podrá asignar el asset BIT_PLAY_TOKEN a Bob firmando la transacción asignando la llave pública del nuevo dueño al asset BIT_PLAYT_TOKEN

```
     SIGN_ALICE = (transaction {
        asset: BIT_PLAYT_TOKEN
        prev_owner:ALICE_PUB_KEY
        new_owner:BOB_PUB_KEY
     }).signed(ALICE_PRIV_KEY)

 ```

El minero tendrá que verificar que esta transacción es válida y para ello deberá asegurarse de que la firma corresponde a Alice.

Para ello usará la transacción firmada por Alice SIGN_ALICE, y la verificará usando la llave pública de Alice

```
    SIGN_IS_VALID = isValid(
       transaction {
            asset: BIT_PLAYT_TOKEN
            prev_owner:ALICE_PUB_KEY
            new_owner:BOB_PUB_KEY
         }, ALICE_PUB_KEY, SIGN_ALICE)
```

Una vez el minero haya verificado la validez de la firma del mensaje la agregará al nuevo bloque que será minado.

Puedes revisar estos conceptos en código en el archivo test: 

[src/test/java/esccak/bitlearn/services/DigitalSignaturesTest.java](https://github.com/ESCCAK/bitlearn/blob/main/src/test/java/esccak/bitlearn/services/DigitalSignaturesTest.java)

## 4. UTXO Model

El modelo UTXO - Unspent Transaction Output- (Transacciones de Salida No Gastadas), es la manera en que se puede determinar el dueño de 
un activo digital. Este modelo establece un "historial" de los dueños del activo a traves del encadenamiento de las transacciones de este activo.

La forma en que se realiza este encadenamiento de las transacciones es a traves de una estructura de entradas y salidas, donde una entrada correspondera a una salida de 
una transaccion anterior.
 
La siguiente es la estructure de una transaccion.  Esta tendrá un input consumiendo un respectivo output de una transaccion anterior y la respectiva firma digital
del dueño de este output que prueba la propiedad de este activo digital. El output será la dirección del nuevo dueño a quien se le va a transferir el activo junto con el valor a transferir.



```
    TRX
    {
        id: [hash from this transaction]
        input:{
            id_tx:[id from the TRX that is going to spends the funds from]
            signature:[A signature that demonstrates the 
                        ownership of the private key 
                        associated to the address from 
                        the output transaction]
        }
        output:{
            value:0.5
            adress:[public adress from the receiver]
        }
        
    }
```


En Bitcoin la única transacción que no consume inputs de salidas anteriores son las transacciones 'coinbase'.
Una transacción 'coinbase' es una transacción en la cual se crean nuevas monedas, estas monedas son creadas como una recompensa al minero
que logra descifrar el 'acertijo criptográfico', lo cual le permite agregar un nuevo bloque a la cadena.




```
    TRX
    {
        id: [hash from this transaction]
        input:{
            id_tx:[000000000]
            signature:[null]
        }
        output:{
            value:6
            adress:[public adress from the miner]
        }
        
    }
```

Cada vez que un output es consumido este queda sin validez y se crean nuevos UTXO'S
 
Veamos un ejemplo donde un minero recibe 6 monedas de recompensa por el bloque que ha minado. Si el minero decidiera gastar las 6 monedas que acaba de obtener,la transacción 'Coinbase' sería el input de nuestra nueva transacción,
 con 3 outputs: el primero, lo que desee enviar a una nueva direccion, en este caso 3 monedas. el segundo seria un output de un valor de 2.7, que es lo que se devuelve asi mismo la persona que esta consumiendo la salida de una transacción anterior. Y por último un output 
 con un valor de 0.3, que es lo que se le asigna de comisión por agregar esta transacción a un nuevo bloque.
 

```
    TRX
    {
        id: [hash from this transaction]
        input:{
            id_tx:[id from the transaction where the outputs to consume are]
            signature:[Signature of the owner from the address output]
        }
        output:{
            value:3
            address:[public address where the coins will be send]
        }
        output:{
            value:2.7
            address:[address from the sender]
        }
        output:{
            value:0.3[transaction fee]
            address:[address from the miner who mine the block]
        }
    }
```


En este punto tendriamos los siguientes UTXO's:

![alt text](https://github.com/esccak/bitlearn/blob/main/image.jpg?raw=true)

Puedes revisar estos conceptos en código en el archivo test: 

[src/test/java/esccak/bitlearn/services/UTXOModelTest.java](https://github.com/ESCCAK/bitlearn/blob/main/src/test/java/esccak/bitlearn/services/UTXOModelTest.java)

