//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha1: Do suboru s heslami ulozit aj sal.                           //
// Uloha2: Pouzit vytvorenu funkciu na hashovanie a ulozit heslo        //
//         v zahashovanom tvare.                                        //
//////////////////////////////////////////////////////////////////////////
package passwordsecurity2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import passwordsecurity2.Database.MyResult;


public class Registration {
	private static final String PASS_PATH="pass_dict.txt";
//	private Security security;
	private List<String> passwordDictionary;
	
	public Registration(){
		passwordDictionary=new ArrayList<String>();
		loadDictionary();
	}
	
    protected MyResult registracia(String meno, String heslo) throws NoSuchAlgorithmException, Exception{
        if(!isValidPassword(heslo)){
        	System.out.println("Not valid password!");
        	return new MyResult(false,"Heslo nie je bezpecne.Musí obsahovať aspoň 1 číslicu,veľké a male písmeno a dĺžka hesla musí byť väčšia ako 7!");
        	
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
     *{8,0}				 // use to check if the length is greater than 8
     *.+                 // gobble up the entire string
     *$                  // the end of the string
     * @param password
     * @return
     */
    private boolean isValidPassword(String password){
    	
    	if(passwordDictionary.contains(password.toLowerCase())){
    		System.out.println("The password was found in dictionary");
    		return false;
    	}
    	
    	//TODO Need to be tested
    	final String REGEX="^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)\\S{8,}$";
    	Pattern p = Pattern.compile(REGEX);
    	return p.matcher(password).matches();    	
    }
    
    private void loadDictionary(){
    	try(BufferedReader br = new BufferedReader(new FileReader(PASS_PATH))) {
    	    String line = br.readLine();
    	    while (line != null) {
    	    	passwordDictionary.add(line);
    	        line = br.readLine();
    	    }
    	} catch (IOException e) {
			passwordDictionary=null;
		}
    }
    
}
