package speedit.bookplate.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import speedit.bookplate.dto.book.AladinResDto;

@FeignClient(name = "AladinSearchFeignClient", url = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx")
public interface AladinSearchFeignClient {

    @RequestMapping(method = RequestMethod.GET,value = "?ttbkey=ttbeun9709231746001&Query={search}&QueryType=Title&MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101&Cover=Big")
    AladinResDto getBookDetail(@PathVariable("search")String searchQuery);

}
