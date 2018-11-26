package baulify;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESenc {

    //esto es lo que hay que cif en rsa y concatenar al archivo
    private static final byte[] keyValue = new byte[]{-59, -126, 79, -43, 93, 39, 100, 94, -127, -46, -90, -46, -59, 9, -60, 18}; 
    private static final String algoritmo = "AES";
  
    public AESenc(){}

    public static byte[] encrypt(String data, boolean txt) throws Exception {

    	try{
	    Key aesKey = generateKey(txt);
            
            
            //RSAenc.encrypt(Arrays.toString(keyValue));
            
	    Cipher c = Cipher.getInstance(algoritmo);
	    c.init(Cipher.ENCRYPT_MODE, aesKey);
	    byte[] encVal = c.doFinal(data.getBytes());
	    return encVal;    
        }catch(Exception e){return null;}
    }
   
    public static String decrypt(byte[] encryptedDataArray) throws Exception {
        Key key = generateKey(false);
        Cipher c = Cipher.getInstance(algoritmo);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decValue = c.doFinal(encryptedDataArray);
        return new String(decValue);
    }
  
    private static Key generateKey(boolean txt) throws Exception {
        
        Key aesKey;

        /*if(txt){
            String clave = FXMLDocumentController.clave;
            aesKey=new SecretKeySpec(clave.getBytes(), algoritmo);
        }
        
        else{
        /*SecureRandom random = new SecureRandom();
        byte[] keyValue = new byte[16];
        random.nextBytes(keyValue); 
        System.out.println("16 bits ---> " +  Arrays.toString(keyValue));
         }
        */
       
       
     aesKey=new SecretKeySpec(keyValue, algoritmo);
        return aesKey;
    }

}




