package edu.eci.arsw.blueprints.config;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * Inicializa datos de ejemplo en PostgreSQL al arrancar la aplicación.
 * Solo se ejecuta si no hay datos previos.
 */
@Configuration
@Profile("!inmemory")
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(BlueprintRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Blueprint bp1 = new Blueprint("john", "house",
                        List.of(new Point(0, 0), new Point(10, 0), new Point(10, 10), new Point(0, 10)));
                Blueprint bp2 = new Blueprint("john", "garage",
                        List.of(new Point(5, 5), new Point(15, 5), new Point(15, 15)));
                Blueprint bp3 = new Blueprint("jane", "garden",
                        List.of(new Point(2, 2), new Point(3, 4), new Point(6, 7)));

                repository.save(bp1);
                repository.save(bp2);
                repository.save(bp3);

                System.out.println("Base de datos inicializada con datos de ejemplo");
            }
        };
    }
}
