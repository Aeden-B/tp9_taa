package controllers.forms;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Récupère les données soumises par le formulaire de login (défini dans la vue `views/login.scala.html`).<br />
 * Exemple d’utilisation (dans un controller) :
 * <pre>
 *     Form&lt;Login&gt; submission = form(Login.class).bindFromRequest();
 * </pre>
 */
public class Login {

    public String name;

    public String password;
    

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
    	if (name == null){
    		   errors.add(new ValidationError("name", "This username is null"));
    	}
    	if (password == null){
    		errors.add(new ValidationError("password", "This password is null"));
    	}
    	else if (password.length() <= 4){
 		   errors.add(new ValidationError("password", "This password is too short"));
    	}
        // TODO Appel de votre service Endomondo
        if (!"toto".equals(name)){
        	errors.add(new ValidationError("", "Invalid user name or password"));
        }
        return errors.isEmpty() ? null : errors;
    }

}
