package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.view.IView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootServer implements IView {
    private String[] args;

    public SpringBootServer(String[] args) {
        this.args = args;
    }

    @Override
    public void start() {
        SpringApplication.run(SpringBootServer.class, args);
    }

    @Override
    public void stop() {

    }
}
