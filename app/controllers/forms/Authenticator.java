package controllers.forms;

import controllers.Application;
import play.mvc.Http;
import play.mvc.Result;

public class Authenticator extends play.mvc.Security.Authenticator{

	
	public Result onUnauthorized(Http.Context ctx) {
		return Application.login();
	}
}
