package ncc.up.pt.updateBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("ncc.up.pt.updateBackend.Model")
public class UpdateBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpdateBackendApplication.class, args);
	}

}
