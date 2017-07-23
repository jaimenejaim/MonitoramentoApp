package br.com.crudjaimenejaim.app.monitoramentoapp.business;

/**
 * Created by jaimenejaim on 17/07/17.
 */

public class add {

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
