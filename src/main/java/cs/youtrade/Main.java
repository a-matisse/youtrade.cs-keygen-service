package cs.youtrade;

import cs.youtrade.service.InnerKeyService;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.stream.Collectors;

@Log
@SpringBootApplication
@EnableScheduling
public class Main {
    private static final SpringApplication app = new SpringApplication(Main.class);

    public static void main(String[] args) {
        if (args.length == 0) {
            log.warning("You need to specify at least one key to generate");
            return;
        }
        InnerKeyService.setKeyNames(Arrays.stream(args).collect(Collectors.toSet()));
        app.run();
    }
}