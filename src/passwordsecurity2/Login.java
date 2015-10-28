//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha2: Upravte funkciu na prihlasovanie tak, aby porovnavala        //
//         heslo ulozene v databaze s heslom od uzivatela po            //
//         potrebnych upravach.                                         //
// Uloha3: Vlozte do prihlasovania nejaku formu oneskorenia.            //
//////////////////////////////////////////////////////////////////////////
package passwordsecurity2;

import java.io.IOException;
import java.util.StringTokenizer;
import passwordsecurity2.Database.MyResult;

public class Login {
	
	private static long delay=100;
    protected static MyResult prihlasovanie(String meno, String heslo) throws IOException, Exception{
        /*
        *   Delay je vhodne vytvorit este pred kontolou prihlasovacieho mena.
        */
        MyResult account = Database.find("hesla.txt", meno);
        if (!account.getFirst()){
        	Thread.sleep(delay);
        	increaseDelay();
            return new MyResult(false, "Nespravne meno.");
        }
        else {
            StringTokenizer st = new StringTokenizer(account.getSecond(), ":");
            st.nextToken();      //prvy token je prihlasovacie meno
            /*
            *   Pred porovanim hesiel je nutne k heslu zadanemu od uzivatela pridat prislusny salt z databazy a nasledne tento retazec zahashovat.
            */
            String hashFromDb=st.nextToken();
            String salt=st.nextToken();
            
            String calculatedHash=Security.hash(heslo, Long.parseLong(salt));
            boolean rightPassword = hashFromDb.equals(calculatedHash);
            if (!rightPassword){
            	Thread.sleep(delay);
            	increaseDelay();
                return new MyResult(false, "Nespravne heslo.");
            }
         } 
        return new MyResult(true, "Uspesne prihlasenie.");
    }
    
    private static void increaseDelay(){
    	delay+=100;
    }
}
