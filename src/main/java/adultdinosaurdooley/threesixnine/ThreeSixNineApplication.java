package adultdinosaurdooley.threesixnine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ThreeSixNineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreeSixNineApplication.class, args);
    }

}
