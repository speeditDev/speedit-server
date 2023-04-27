package speedit.bookplate.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return requestTemplate -> {
            requestTemplate.header("X-Naver-Client-Id","imjs7vzWk2zyyFFOThte");
            requestTemplate.header("X-Naver-Client-Secret","ZlhW5AUd2s");
        };
    }

}
