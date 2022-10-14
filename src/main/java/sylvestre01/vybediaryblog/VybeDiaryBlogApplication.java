package sylvestre01.vybediaryblog;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.mappers.ModelMapper;
import sylvestre01.vybediaryblog.Security.JwtAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableWebMvc
@SpringBootApplication
@AllArgsConstructor
public class VybeDiaryBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(VybeDiaryBlogApplication.class, args);
	}

}
