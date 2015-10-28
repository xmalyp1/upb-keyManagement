//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha1: Do suboru s heslami ulozit aj sal.                           //
// Uloha2: Pouzit vytvorenu funkciu na hashovanie a ulozit heslo        //
//         v zahashovanom tvare.                                        //
//////////////////////////////////////////////////////////////////////////
package passwordsecurity2;

import java.security.NoSuchAlgorithmException;
import passwordsecurity2.Database.MyResult;


public class Registration {
	private Security security;
    protected static MyResult registracia(String meno, String heslo) throws NoSuchAlgorithmException, Exception{
        if (Database.exist("hesla.txt", meno)){
            System.out.println("Meno je uz zabrate.");
            return new MyResult(false, "Meno je uz zabrate.");
        }
        else { 
        	
        	long salt=Security.getSalt(0,Long.MAX_VALUE);
            String hash = Security.hash(heslo, salt);
        	/*
            *   Salt sa obvykle uklada ako tretia polozka v tvare [meno]:[heslo]:[salt].
            */
            Database.add("hesla.txt", meno + ":" + hash + ":" + salt);
        }
        return new MyResult(true, "");
    }
    
}
