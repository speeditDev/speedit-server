package speedit.bookplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
public class BookplateApplication {

	//eunyoung

	//깃 크라켄 테스트하기

	public static void main(String[] args) {
		SpringApplication.run(BookplateApplication.class, args);
	}

}
