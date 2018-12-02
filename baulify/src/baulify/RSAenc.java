
package baulify;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.*;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import javafx.scene.control.Alert;
import javax.crypto.Cipher;
/**
 *
 * @author Mario
 */

public class RSAenc {
    
    protected static String txtruta;
    protected static byte[] clave_AES_cif;
    private static KeyPair pair;
     
    private static void buildKeyPair() throws NoSuchAlgorithmException, IOException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);    
        pair = keyPairGenerator.genKeyPair();
                    
            byte[] bytesPriv = pair.getPrivate().getEncoded();             
            //GUARDAR bytesPriv en un archivo
            File ruta = FXMLDocumentController.selectedDirectory;
            txtruta = ruta + "\\clave_descifrado_" + ".txt";
            File txt = new File(txtruta);

            FileOutputStream fos;    
            fos = new FileOutputStream(txt);
            fos.write(bytesPriv);
            fos.flush();
            fos.close();
    }
    

    protected static void encrypt(byte[] clave_AES) throws Exception {      
        //Encriptamos la clave AES con RSA y su clave publica
        buildKeyPair();
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());        
        clave_AES_cif = cipher.doFinal(clave_AES);   
    }
    
    protected static byte[] decrypt(byte [] encrypted) throws Exception{
                
        Cipher cipher = Cipher.getInstance("RSA");  
         byte[] privateKeyBytes = null;
        //En privateKey le pasaríamos la clave privada que esta en el txt, habría que pasar de string a privateKe 
       
        try{
            Path path = Paths.get("C:\\Users\\Mario\\Desktop\\DES_CIF_clave_privRSA_8143.txt");      
            privateKeyBytes = Files.readAllBytes(path);
        }   catch(NoSuchFileException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("¡ERROR!");
                        alert.setHeaderText("¡No se encontró el archivo de claves!");
                        alert.setContentText("Revise la ubicacón del archivo por favor");
                        alert.showAndWait();
                        //reiniciamos la aplicación
                        Controlador.reiniciar_main();
            }
        
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));    
        cipher.init(Cipher.DECRYPT_MODE, privateKey);       
            return cipher.doFinal(encrypted);
    }
}
