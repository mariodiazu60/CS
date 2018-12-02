package baulify;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESenc {
    
    private static byte[] keyValue = new byte[16]; 
    private static final String algoritmo = "AES";
         
    protected AESenc(){}

    protected static byte[] encrypt(String data, int it ,boolean txt) throws Exception{
        
        /* Si lo que le pasamos es un archivo (y no el txt con la clave privada) y ademas estamos en la primera iteracion 
        entra para generar la RSA y cifrar la clave AES */
        if(!txt && it==0 && FXMLDocumentController.clave!=null){
            //Generamos una semilla aleatoria segura con secure random
            SecureRandom random = new SecureRandom();
            random.nextBytes(keyValue); 
            System.out.println("keyValue ----> " + Arrays.toString(keyValue));
            Key aesKey = generateKey(true);
            RSAenc.encrypt(keyValue);
        }
            
        Key aesKey = generateKey(txt);
	Cipher c = Cipher.getInstance("AES");
	c.init(Cipher.ENCRYPT_MODE, aesKey);
	byte[] encVal = c.doFinal(data.getBytes());

        //Si estamos cifrando el txt con la clave RSA priv return aqui y no concatenamos nda detras
        if(it==-1){
            return encVal;
        }
            
        byte[] result;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream( )) {
            outputStream.write( encVal );
            outputStream.write( RSAenc.clave_AES_cif );
            result = outputStream.toByteArray( );
        }
	return result;    
    }
   
    protected static String decrypt(byte[] encryptedData) throws Exception {      
        Key key = null;
        boolean esClave = false;     
        int tam_clave = 256;
        int tam_archivo = encryptedData.length - tam_clave;
        byte[] encryptedArchivo = null;
        byte[] encryptedClave = new byte[tam_clave];
        
        //Descifrando el txt con la rsa 
        if(FXMLDocumentController.clave.equalsIgnoreCase("") == false){
            esClave = true;
            key = generateKey(esClave); 
            encryptedArchivo = new byte[encryptedData.length];
            for(int i = 0; i<encryptedData.length ; ++i){ 
                    encryptedArchivo[i] = encryptedData[i];                
                }
        }
        //Desciframos los archivos
            else {
                //Desconcatenar la clave AES cifrada, se la pasas a RSAenc, y el return de RSAenc.decrypt sera el keyValue
                int pos_encClave = -1;
                encryptedArchivo = new byte[tam_archivo];
                
                //Sacamos el archivo cifrado
                for(int i = 0; i<tam_archivo ; ++i){ 
                    encryptedArchivo[i] = encryptedData[i];                
                }
                //Sacamos la clave cifrada
                for(int i = tam_archivo; i<encryptedData.length ; ++i){ 
                    pos_encClave ++;
                    encryptedClave[pos_encClave] = encryptedData[i];  
                }
        
                keyValue = RSAenc.decrypt(encryptedClave);
                System.out.println("key value --> " + Arrays.toString(keyValue));
                key = generateKey(esClave);
            }      
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decValue = c.doFinal(encryptedArchivo);
        return new String(decValue);
    }
  
    private static Key generateKey(boolean txt) throws Exception {
        
        Key aesKey = null;
        if(txt){
            String clave = FXMLDocumentController.clave;   
            aesKey=new SecretKeySpec(clave.getBytes(), algoritmo);
        }    
        else{
            aesKey = new SecretKeySpec(keyValue, algoritmo);
        } 

        return aesKey; 
    }
}

