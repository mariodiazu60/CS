package baulify;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


//CONCATENAR LA CLAVE AES CIFRADA CON RSA --- GUARDAR LA CLAVE RSA EN UN TXT

public class Controlador {
        
        public static void cifrar(List<File> archivos_originales){
            
            //String[] para guardar todos los archivos codificados en b64
            String[] ArchivosEnB64;
            //Bytes[] para guardar el archivo cifrado en aes en cada iteracion
            byte[] ArchivoEnAES;
            
            //Codificamos los archivos en base64
            ArchivosEnB64 = new String[archivos_originales.size()];
            for(int i=0; i<=archivos_originales.size()-1; i++){ 
                   //Nos guardamos los archivos en b64 en un array de strings
                   ArchivosEnB64[i] = Base_64.codificar_Base64(archivos_originales.get(i), i);
            }
            
            //Ciframos con aes los archivos, que ya estan codificados en b64
            try {
	    	if(ArchivosEnB64!=null) {
                    for (int i = 0; i<ArchivosEnB64.length; i++) {
                        
                        //Ciframos el archivo con aes
                        ArchivoEnAES = AESenc.encrypt(ArchivosEnB64[i],i,false);
                        
                        //Pasamos el archivo cifrado con AES a tipo File para guardarlo
                        /*El metodo ArrayBytes_toFile recibe:
                        el arch cifrado, 
                        la iteracion en la que se encuentra (para saber sobre qué archivo estamos trabajando y 
                        obtener el nombre para concatenarlo a la ruta del archivo),
                        y un booleano que señala si estamos cifrando o no (para elegir el tipo de extension del File
                        Es void, guarda el archivo en la ruta y termina la ejecucion cuando termina de recorrer la List de archivos   
                        */
                        Base_64.ArrayBytes_toFile(ArchivoEnAES, i, true);
                        
                        //Al final de la ultima iteracion informamos de que el cifrado ha terminado y ciframos el txt con la RSA privada
                        if(i==(ArchivosEnB64.length-1)){

                           //obtenemos el archivo txt de la clave priv RSA, pasamos a b64 y lo ciframos con AES
                           File txt = new File(RSAenc.txtruta);                          
                           String txt_b64 = Base_64.codificar_Base64(txt, -1);
                             
                           //txt cifrado con AES aun en un byte[]
                           byte[] txtFile_priv = AESenc.encrypt(txt_b64, -1, true);

                           //pasamos el byte[] a un FILE
                           Base_64.ArrayBytes_toFile(txtFile_priv, -1 , true);
                           
                           //borramos el txt con la RSA privada sin cifrar
                         
                            System.out.println(txt.getAbsolutePath());
                             txt.delete();
                            
                            //vaciamos el file chooser                 
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("¡Terminado!");
                            alert.setHeaderText("¡Ya hemos terminado de cifrar tus archivos!");
                            alert.setContentText("Encontrarás tus archivos en:  " + FXMLDocumentController.selectedDirectory);
                            alert.showAndWait();
                            
                            //reiniciamos la aplicación
                             reiniciar_main();     
                        }
                    }
	    	}
           } catch (Exception e) {}	
        }
        
       
        public static void descifrar(List<File> archivos) throws IOException{
            
            File archivo;  
            byte[] archivoEnBytes; 
            byte[] Archivo_decodificado;
            String ArchivoEnB64;
            
            for(int i=0; i<archivos.size(); i++){
                archivo =  archivos.get(i);
                archivoEnBytes = Base_64.File_toArrayBytes(archivo); 
                try{
                    //desencriptamos y obtenemos el archivo en un string de base64 + la extension concatenada al principio
                    ArchivoEnB64 = AESenc.decrypt(archivoEnBytes);

                    //decodificamos el string en b64 y lo pasamos a array de bytes
                    Archivo_decodificado = Base_64.decodificar_Base64(ArchivoEnB64);

                    //pasamos el array de bytes a tipo File para guardarlo
                    Base_64.ArrayBytes_toFile(Archivo_decodificado, i, false);
                    } catch(Exception e) {}
                    
                    //Al final de la ultima iteracion informamos de que el descifrado ha terminado  
                    if(i==(archivos.size()-1)){
                        //vaciamos el file chooser
                        // new FXMLDocumentController().refrescar_lista();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("¡Terminado!");
                        alert.setHeaderText("¡Ya hemos terminado de descifrar tus archivos!");
                        alert.setContentText("Encontrarás tus archivos en: " + FXMLDocumentController.selectedDirectory);
                        alert.showAndWait();
                        
                        //reiniciamos la aplicación
                        reiniciar_main();
                     }
                }
        }
        
        private static void reiniciar_main(){
            //obtenemos el estado actual y lo cerramos
            Interfaz.getCurrentStage().close();
            //creamos una nueva instancia de la interfaz
            Platform.runLater( () -> {
            try {
                new Interfaz().start( new Stage() );
                } catch (Exception ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            } );
        }
        
}
