
package baulify;
import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;
import javax.crypto.Cipher;
/**
 *
 * @author Mario
 */

public class RSAenc {
    
    public static String txtruta;
    
    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException, IOException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);    
             
            
           //PrivateKey privateKey = keyPairGenerator.genKeyPair().getPrivate();
           // RSAPrivateKey priv = (RSAPrivateKey) privateKey;
           //System.out.println("MODULO DE LA CLAVE RSA ---> " + priv.getModulus());
           //System.out.println("EXPONENTE DE LA CLAVE RSA ---> " + priv.getPrivateExponent());

            byte[] bytesPriv = keyPairGenerator.genKeyPair().getPrivate().getEncoded();
            String clave_privada = Arrays.toString(bytesPriv);
            
            
            //GUARDAR ESTE STRING EN UN TXT
            File ruta = FXMLDocumentController.selectedDirectory;
            txtruta = ruta+ "\\clave_descifrado_" + ".txt";
            File txt = new File(txtruta);
           
            BufferedWriter bw;            
                 bw = new BufferedWriter (new FileWriter(txt));
                 bw.write(clave_privada);
                 bw.close();
             
                 
                 
        return keyPairGenerator.genKeyPair();
    }
    
    //Podriamos devolverlo en un string en vez de byte[]
    public static byte[] encrypt(String clave_AES) throws Exception {
        
        //Encriptamos la clave AES con RSA y su clave publica
        KeyPair keyPair = buildKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        
        return cipher.doFinal(clave_AES.getBytes());  
    }
    
    public static byte[] decrypt(PublicKey publicKey, byte [] encrypted) throws Exception {
                
        Cipher cipher = Cipher.getInstance("RSA");  
        
        //En privateKey le pasaríamos la clave del txt cifrado con AES, habría que pasar de string a privateKey
        //cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        return cipher.doFinal(encrypted);
    }
}
