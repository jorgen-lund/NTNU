package playlistGenerator.restserver;

import com.fasterxml.jackson.databind.Module;
import json.modules.PlaylistGenerationModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The Spring application.
 */
@SpringBootApplication()
public class PlaylistGeneratorModelApplication {

  @Bean
  public Module objectMapperModule() {
    return new PlaylistGenerationModule();
  }

  public static void main(String[] args) {
    SpringApplication.run(PlaylistGeneratorModelApplication.class, args);
    System.out.println("Started Server");
  }
}
