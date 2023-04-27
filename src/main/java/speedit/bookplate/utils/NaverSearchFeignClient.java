package speedit.bookplate.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import speedit.bookplate.config.FeignClientConfig;
import speedit.bookplate.dto.book.NaverBookResDto;

@FeignClient(name = "NaverSearchFeignClient", url = "https://openapi.naver.com/v1/search/book.json",configuration = FeignClientConfig.class)
public interface NaverSearchFeignClient {

    @RequestMapping(method = RequestMethod.GET,value = "?query={search}&start={start}")
    NaverBookResDto getBookDetail(@PathVariable("search")String searchQuery,@PathVariable("start")int start);

}
