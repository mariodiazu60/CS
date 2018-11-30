
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
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
/**
 *
 * @author Mario
 */

public class RSAenc {
    
    public static String txtruta;
    public static byte[] clave_AES_cif;
    
    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException, IOException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);    
             

            
           //PrivateKey privateKey = keyPairGenerator.genKeyPair().getPrivate();
           // RSAPrivateKey priv = (RSAPrivateKey) privateKey;
           //System.out.println("MODULO DE LA CLAVE RSA ---> " + priv.getModulus());
           //System.out.println("EXPONENTE DE LA CLAVE RSA ---> " + priv.getPrivateExponent());

            byte[] bytesPriv = keyPairGenerator.genKeyPair().getPrivate().getEncoded();
            
          
            //GUARDAR bytesPriv en un archivo
            File ruta = FXMLDocumentController.selectedDirectory;
            txtruta = ruta + "\\clave_descifrado_" + ".txt";
            File txt = new File(txtruta);

            FileOutputStream fop;    
            fop = new FileOutputStream(txt);
            fop.write(bytesPriv);
            fop.close();
                 
        return keyPairGenerator.genKeyPair();
    }
    
    //Podriamos devolverlo en un string en vez de byte[]
    public static void encrypt(String clave_AES) throws Exception {
        
        //Encriptamos la clave AES con RSA y su clave publica
        KeyPair keyPair = buildKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        
        clave_AES_cif = cipher.doFinal(clave_AES.getBytes());         
    }
    
    public static byte[] decrypt(byte [] encrypted) throws Exception {
                
        Cipher cipher = Cipher.getInstance("RSA");  
        System.out.println("descifrando la clave AES");
        
        //En privateKey le pasaríamos la clave privada que esta en el txt, habría que pasar de string a privateKe 
        Path path = Paths.get("C:\\Users\\Mario\\Desktop\\DES_CIF_clave_privRSA_7468.txt");
        byte[] privateKeyBytes = Files.readAllBytes(path);
        
        
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        return cipher.doFinal(encrypted);
    }
}
