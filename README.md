
# CsvreaderApplication [@Aurumygold](http://github.com/Aurumygold)

## Propiedades del proyecto

Proyecto para Spring Batch Framework de Mercadona.

El proyecto se ha generado con la version spring 2.7.3 y Java 18.

La base de datos utilizada es postgres almacenada en un contenedor docker.

### Descripción del proyecto

Este es proyecto ETL ( Extract, Transform and Load ) consiste en la lectura de un fichero CSV, la tranformación de los datos extraidos y su guardado en una base de datos.

### Fichero CSV

  [1]: Codificacion UTF-8 para poder aceptar caracteres extraños [ ñ , ç ... ]
  
  [2]: Separador por ';', para poder admitir la coma decimal europea
  

### Transformación de la información

  [1]: Cambio a mayúsculas para el campo nombre.
  
  [2]: Cambio de fecha del formato americano (mm/dd/yyyy) al europeo (dd/mm/yyyy).
  
  [3]: Cambio de separador decimal europeo (000.000,00) al americano, estandar de postgres (000,000.00).



### Arbol de estructura
Este arbol respresenta los directorios del repositorio:



## Instalación en local
//To do

```bash
$ npm install md-file-tree -g
```

## Autor

Pablo Catalán Tendero
