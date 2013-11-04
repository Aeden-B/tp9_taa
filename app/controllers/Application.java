package controllers;

import static play.data.Form.form;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.Twitter;
import controllers.forms.Login;

public class Application extends Controller {

    // Twitter service
    final static Twitter twitter = Twitter.taa();

    /**
     * Affiche le tableau de bord de l’utilisateur courant (p. ex. un résumé de ses dernières activités sportives).
     * Si l’utilisateur ne s’est pas identifié il doit être redirigé vers la page de login.
     */
    @Security.Authenticated(controllers.forms.Authenticator.class)
    public static Result index() {
        return ok(views.html.index.render(request().username()));
    }

    /**
     * Affiche le formulaire d’identification.
     */
    public static Result loginForm() {
        return ok(views.html.login.render(form(Login.class)));
    }

    /**
     * Gère la soumission du formulaire.
     * Si les données soumises ne sont pas valides (p. ex. le mot de passe est incorrect) l’action doit renvoyer un code
     * d’erreur 400 et réafficher le formulaire.
     * Sinon, authentifier l’utilisateur (enregistrer son nom dans sa session) et le rediriger vers son tableau de bord.
     */
    public static Result login() {
    	Form<Login> submission = form(Login.class).bindFromRequest();
    	if (submission.hasErrors()){
    		//System.out.println(submission.globalErrors());
    		return loginForm();
    	}
        session().put("username", submission.get().name);
        return redirect(routes.Application.index());
    }

    /**
     * Déconnecte un utilisateur (retire son nom de sa session) et affiche un message d’au-revoir.
     */
    public static Result logout() {
        session().remove("username");
        return ok(views.html.logout.render());
    }

    /**
     * Envoie un flux de tweets d’utilisateurs d’Endomondo.
     */
    public static Result twitterFeed() {
        final EventSource eventSource = new EventSource() {
            @Override
            public void onConnected() {
            	twitter.publicStream("#rennes", this);
            }

        };
        return ok(eventSource);
    }

}
