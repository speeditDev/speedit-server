package speedit.bookplate.utils.enumTypes;

import lombok.Getter;

@Getter
public enum JobCategory {
    CEO("CEO/사업가"),PM("PO/PM"),DEVELOPER("IT개발자"),
    BUILD("건설/건축"),MANAGEMENT("경영/사무"),PUBLIC("공공/복지"),
    EDUCATION("교육"),FINANCE("금융/투자"),PLAN("기획/전략"),
    DESIGNER("디자이너"),MARKETING("마케팅/광고"),TRADE("무역/유통"),
    MEDIA("방송/미디어"),LAW("법조인"),COUNSELING("상담/컨설팅"),
    SERVICE("서비스업"),SALE("영업/판매"),RESEARCHER("연구개발자"),
    ART("예술인"),MEDICAL("의료종사자"),HR("인사/HR"),
    EDITOR("작가/에디터"),FIANCE("재무/회계"),CREATOR("크리에이터");

    private String title;

    JobCategory(String title) {
        this.title = title;
    }
}
