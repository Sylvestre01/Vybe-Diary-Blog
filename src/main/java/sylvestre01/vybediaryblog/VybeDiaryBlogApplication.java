package sylvestre01.vybediaryblog;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.mappers.ModelMapper;
import sylvestre01.vybediaryblog.Security.JwtAuthenticationFilter;
import sylvestre01.vybediaryblog.service.UserService;
import sylvestre01.vybediaryblog.serviceimpl.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableWebMvc
@SpringBootApplication
@EntityScan(basePackageClasses = { VybeDiaryBlogApplication.class, Jsr310Converters.class })
public class VybeDiaryBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(VybeDiaryBlogApplication.class, args);
	}

}
