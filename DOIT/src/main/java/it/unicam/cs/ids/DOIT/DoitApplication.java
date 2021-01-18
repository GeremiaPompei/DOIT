package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.user.IUserHandler;
import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.view.ConsoleView;
import it.unicam.cs.ids.DOIT.view.IView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
