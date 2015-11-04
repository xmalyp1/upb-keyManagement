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
import java.util.TimerTask;

import com.sun.glass.ui.Timer;

import passwordsecurity2.Database.MyResult;

public class Login {
	
	private long delay;
	private LoginLockThread llt;
    
	public Login(){
    	this.delay=100;
    	llt=null;
    }
	
	protected MyResult prihlasovanie(String meno, String heslo) throws IOException, Exception{
        /*
        *   Delay je vhodne vytvorit este pred kontolou prihlasovacieho mena.
        */
		if(llt!=null && llt.isAlive())
			return new MyResult(false, "Nové heslo bolo zadané príliž rýchlo.");
		
        MyResult account = Database.find("hesla.txt", meno);
        if (!account.getFirst()){
        	if(llt==null || !llt.isAlive()){
        		llt=new LoginLockThread();
        		llt.start();
        		increaseDelay();
        	}
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
            	if(llt==null || !llt.isAlive()){
            		llt=new LoginLockThread();
            		llt.start();
            		increaseDelay();
            	}
                return new MyResult(false, "Nespravne heslo.");
            }
         } 
        return new MyResult(true, "Uspesne prihlasenie.");
    }
    
    private void increaseDelay(){
    	delay+=100;
    }
    
   private class LoginLockThread extends Thread {

	@Override
	public void run() {
		System.out.println("LoginLockThread will sleep for "+delay+" millis.");
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.run();
	}
	   
   }
    

}

