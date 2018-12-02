package baulify;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Base_64 {
	
	protected Base_64(){}
	
    /**
     *Aqui se guarda la extension del archivo que estamos tratando.
     * Cada vez que accedemos a cualquier metodo de esta clase, la extension cambia
     */
    public static String extension_iteracion_actual;
        
	protected static String codificar_Base64(File archivo, int it){
	     String encodedfile;
             String resultado = null;
             String extension;
	     try {	    	
                 //leemos la cantidad de bytes del archivo a codificar y creamos un array de bytes para almacenarlo	         
		FileInputStream fileInputStreamReader = new FileInputStream(archivo);
	        byte[] bytesArchivo = new byte[(int)archivo.length()];
 
                //obtenemos la extension del archivo si it = -1 estamos CIFRANDO el txt con la clave privada RSA
                if(it==-1){
                    extension = "txt";
                }else{
                    List <File> nombre = FXMLDocumentController.selectedFiles;
                    extension = nombre.get(it).getName().substring(nombre.get(it).getName().lastIndexOf(".")+1);
                }
                    //pasamos a string el archivo ya codificado,y a ese string le concatenamos delante la extension.
                    fileInputStreamReader.read(bytesArchivo);
                    encodedfile = new String(Base64.getEncoder().encode(bytesArchivo));
                    resultado = extension + "#" + encodedfile + "#";
                    fileInputStreamReader.close();


                
	     } catch (FileNotFoundException e) {
             } catch (IOException e) {}
	     
	     //devolvemos el archivo en Base64, (extension + # + archivo en b64)
	     return resultado;
	 }
	 
	
	protected static byte[] decodificar_Base64(String archivo_B64){
	    
            byte[] ArrayDecodificado = null;
            try {
                //el archivo que recibimos tiene la extension concatenada separada por #---> jpg#2837edhwkqdh...
                String archivo_sin_extension;
                String[] parts = archivo_B64.split("#");
                
                //nos guardamos la extension en una variable de instancia, cada vez que decodifiquemos un archivo cambiará
                extension_iteracion_actual = parts[0];
                archivo_sin_extension = parts[1];
                  
                
                //Pasamos el string en base64 ( ya sin la extension concatenada) a una array de bytes, es decir, a su estado original
	        ArrayDecodificado = Base64.getDecoder().decode(archivo_sin_extension.getBytes("UTF-8"));            
	        //devolvemos el archivo ya en su estado original
	       } catch (IOException e) {}
            
	    return ArrayDecodificado; 
	 }
	
	
	protected static void ArrayBytes_toFile(byte[] ArchivoEnBytes, int it, boolean cifrar) throws FileNotFoundException, IOException{      
            
            //obtenemos el nombre y la extension del archivo
            File ruta = FXMLDocumentController.selectedDirectory;
            List <File> nombre = FXMLDocumentController.selectedFiles;
            String nombre_archivo = null;
            String archivo;
            
            if(it!=-1){
                archivo = nombre.get(it).getName();
                if(archivo.lastIndexOf(".") != -1 && archivo.lastIndexOf(".") != 0){
                    nombre_archivo = archivo.substring(0,archivo.lastIndexOf("."));
                }
            } else{
                int randomNum = ThreadLocalRandom.current().nextInt(0, 10000 + 1);
                nombre_archivo = "clave_privRSA_"; //+ randomNum;
            }
            
            //if para ver si ciframos o descriframos
            if(cifrar){
		try  {    
                    FileOutputStream fos = new FileOutputStream(ruta + "\\CIF_" + nombre_archivo + ".crypt");
			   fos.write(ArchivoEnBytes);         
                }
                catch(IOException e){}
            }
            
            else{
		try  {                   
                    FileOutputStream fos = new FileOutputStream(ruta + "\\DES_" + nombre_archivo + "." + extension_iteracion_actual);
			   fos.write(ArchivoEnBytes);
                }
                catch(IOException e){}
            }
        }

        public static  byte[] File_toArrayBytes(File archivo) throws FileNotFoundException, IOException{
                Path ruta = archivo.toPath();
                byte[] archivoEnBytes = Files.readAllBytes(ruta);
                return archivoEnBytes;
        }	
                
}

	

