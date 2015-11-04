//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha1: Do suboru s heslami ulozit aj sal.                           //
// Uloha2: Pouzit vytvorenu funkciu na hashovanie a ulozit heslo        //
//         v zahashovanom tvare.                                        //
//////////////////////////////////////////////////////////////////////////
package passwordsecurity2;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import passwordsecurity2.Database.MyResult;


public class Registration {
	private Security security;
    protected static MyResult registracia(String meno, String heslo) throws NoSuchAlgorithmException, Exception{
        if(!isValidPassword(heslo)){
        	System.out.println("Not valid password!");
        	return new MyResult(false,"Heslo nie je bezpecne. Musí obsahovať aspoň 1 číslicu, veľké a malé písmeno a dľžka hesla musí byť vačšia ako 4");
        	
        }
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
    /**
     * Inspired from SO
     * ^                  // the start of the string
     *(?=.*[a-z])        // use positive look ahead to see if at least one lower case letter exists
     *(?=.*[A-Z])        // use positive look ahead to see if at least one upper case letter exists
     *(?=.*\d)           // use positive look ahead to see if at least one digit exists
     *\S      			 // use to check if it does not contain white spaces
     *{5,0}				 // use to check if the length is greater than 4
     *.+                 // gobble up the entire string
     *$                  // the end of the string
     * @param password
     * @return
     */
    private static boolean isValidPassword(String password){
    	//TODO Need to be tested
    	final String REGEX="^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)\\S{5,}$";
    	Pattern p = Pattern.compile(REGEX);
    	return p.matcher(password).matches();    	
    }
    
}
