-------------- Versión 0.5 --- 22/11/18 ------------------------

------------------Novedades------------------------
-Ahora concatenamos la extensión del archivo al archivo en b64 ANTES de cifrarlo ---> extensión + # (separador) + (archivo en b64)---> jpg#9/hds62jndskl...
	De este modo, al descifrarlo desconcatenamos la extensión y la añadimos al archivo descifrado.

-Añadidas alertas para evitar cifrar/descifrar sin haber elegido antes los archivos  y/o la ruta donde guardarlo.


---------------Fallos encontrados------------------
-La lista de archivos no se vacía en la interfaz, a pesar de internamente estar vacia.




-------------- Versión 0.51 --- 24/11/18 ------------------------

------------------Novedades------------------------
-Añadida la clase para el cifrado RSA, cifra la clave AES. No hace nada más.
-Añadidos los archivos de ejemplo fotos y videos al .Zip para probar el programa.

---------------Fallos encontrados------------------
-El mensaje de ESTAMOS TRABAJANDO se queda abierto aún reiciando el programa.


---------------Fallos solucionados------------------
-La lista de archivos no se vacía en la interfaz, a pesar de internamente estar vacia. -----> ARREGLADO.
	----> Ahora el programa se reinicia cada vez que se cifra o descifra.