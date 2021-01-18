package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.user.IUserHandler;
import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.view.ConsoleView;
import it.unicam.cs.ids.DOIT.view.IView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoitApplication {

	public static void main(String[] args) {
		/*IUserHandler controller = new UserHandler();
		IView view = new ConsoleView();
		view.start(controller);
		view.stop();*/
		SpringApplication.run(DoitApplication.class, args);
	}

}
