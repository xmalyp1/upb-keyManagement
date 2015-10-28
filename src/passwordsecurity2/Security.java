//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha1: Vytvorit funkciu na bezpecne generovanie saltu.              //
// Uloha2: Vytvorit funkciu na hashovanie.                              //
// Je vhodne vytvorit aj dalsie pomocne funkcie napr. na porovnavanie   //
// hesla ulozeneho v databaze so zadanym heslom.                        //
//////////////////////////////////////////////////////////////////////////
package passwordsecurity2;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;


public class Security {
    
    private static String hash(String password) throws Exception{  
        /*
        *   Pred samotnym hashovanim si najskor musite ulozit instanciu hashovacieho algoritmu.
        *   Hash sa uklada ako bitovy retazec, takze ho nasledne treba skonvertovat na String (napr. cez BigInteger);
        */
        String hash = "";
        return hash;
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

