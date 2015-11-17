//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha1: Vytvorit funkciu na bezpecne generovanie saltu.              //
// Uloha2: Vytvorit funkciu na hashovanie.                              //
// Je vhodne vytvorit aj dalsie pomocne funkcie napr. na porovnavanie   //
// hesla ulozeneho v databaze so zadanym heslom.                        //
//////////////////////////////////////////////////////////////////////////
package passwordsecurity2;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;


public class Security {
    
    protected static String hash(String password,long salt) throws Exception{  
        /*
        *   Pred samotnym hashovanim si najskor musite ulozit instanciu hashovacieho algoritmu.
        *   Hash sa uklada ako bitovy retazec, takze ho nasledne treba skonvertovat na String (napr. cez BigInteger);
        */
    	
   		 MessageDigest md = MessageDigest.getInstance("SHA-256");
   		 password=Long.toString(salt)+password;
   		 md.update(password.getBytes("UTF-8"));
   		// md.update(ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(salt).array());  
   		 byte[] bytesOfHash = md.digest();
   	     
   	        StringBuffer hexaHash = new StringBuffer();
   	    	for (int i=0;i<bytesOfHash.length;i++) {
                   hexaHash.append(Integer.toHexString((bytesOfHash[i] & 0xFF) | 0x100));
   	    	}
   	    	return hexaHash.toString();
    }
    
    protected static long getSalt(long min, long max) {
        /*
        *   Salt treba generovat cez secure funkciu.
        */
    	final Random r = new SecureRandom();
    	byte[] salt = new byte[32];
    	r.nextBytes(salt);
        long saltValue = ByteBuffer.wrap(salt).getLong();
        System.out.println(saltValue);
        return saltValue;
    }
}

