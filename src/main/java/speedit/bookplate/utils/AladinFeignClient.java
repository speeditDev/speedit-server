package speedit.bookplate.utils;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import speedit.bookplate.dto.book.NaverBookResDto;

@FeignClient(name = "AladinFeignClient", url = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx")
public interface AladinFeignClient {

    @RequestMapping(method = RequestMethod.GET,value = "?ttbkey=ttbeun9709231746001&ItemIdType=ItemId&ItemId={bookIdx}&Output=JS&Cover=Big")
    NaverBookResDto getBookDetail(@PathVariable("bookIdx")Long bookIdx);

}
