
# TDS PRACTICA 3 GRUPO 5

Desarrollo de los servicios de un sistema de compra de billetes de una empresa de transporte de pasajeros creando las debidas clases y sus correspondientes tests.

## Autores
Víctor González Núñez (victogo)

Izan Jiménez Girón (izajime)

Asier García Anta (asigarc)

## Clases 
Billete: Tipo de dato que implementa la funcionalidad de un billete de transporte

Recorrido: Tipo de dato que implementa la funcionalidad de un recorrido de un medio de transporte

Usuario: Tipo de dato que implementa la funcionalidad de un usuario del sistema de compra de billetes

TipoTransporte: Enum de tipos de transporte

Sistema: Tipo de dato que implementa la funcionalidad de un sistema de compra de billetes de transporte

SistemaPersistencia: Tipo de dato que implementa la funcionalidad de un sistema de compra de billetes de transporte

IDatabaseManager: Interface encargada de gestionar una conexion con una base de datos y con diferentes operaciones relacionadas con el almacenamiento y recuperacion de los billetes, recorridos y usuarios.

DatabaseManager: Tipo de dato que implementa la funcionalidad de la interface relacionada con la base de datos

HibernateUtil: Tipo de dato que se utiliza para poder configurar y gestionar la sesión Hibernate esencial para interactuar con la base de datos

SistemaPersistenciaSinAislamiento: Tipo de dato que implementa la funcionalidad de un sistema de compra de billetes utilizando la implementación de IDatabaseManager en vez de la propia interfaz

## Clases tests
BilleteTest: Tests de la clase Billete

RecorridoTest: Tests de la clase Recorrido

UsuarioTest: Tests de la clase Usuario

SistemaTest: Tests de la clase Sistema

SistemaPersistenciaTest: Test de la clase SistemaPersistencia

DatabaseManagerTest: Test de la clase DatabaseManager

SistemaPersistenciaSinAislamientoTest: Test de la clase SistemaPersistenciaSinAislamiento

## Code to test
Billete: 0.9

Recorrido: 0.91

Usuario: 0.94

Sistema: 0.86

SistemaPersistencia: 0.7 

DatabaseManager: 2.1

SistemaPersistenciaSinAislamiento: 1

## Técnicas de refactorización empleadas
### Replace Temp with Query: 
Hemos empleado esta técnica en el método getPrecioTotal en las clases Sistema, SistemaPersistencia y SistemaPersistenciaSinAislamiento, ya que antes calculabamos el precio total dentro del método precioBilletesUsuario

### Hide Method: 
Hemos empleado esta técnica en distintos métodos de nuestras clases: en la clase Sistema en el método getPrecioTotal, en las clases SistemaPersistencia y SistemaPersistenciaSinAislamiento en los métodos actualizarRecorridoMenosPlazas y actualizarRecorridoMasPlazas y en la clase usuario en los métodos comprobarDni y isNumeric

### Replace Error Code with Exception:
Hemos empleado esta técnica en prácticamente todos los métodos de nuestras clases ya que lanzamos las excepciones correspondientes

## Horas empleadas
 Izan: 6h 30m
 Víctor: 6h 30m
 Asier: 6h 30m