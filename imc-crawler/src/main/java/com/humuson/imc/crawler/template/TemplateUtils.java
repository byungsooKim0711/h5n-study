package com.humuson.imc.crawler.template;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtils {

    private static final String TEMPLATE_VARIABLE = "#\\{.+?}";

    // 회원 가입 인사 템플릿
    public static final String WELCOME_TEMPLATE = "[#{쇼핑몰이름}]\n"+
            "안녕하세요. #{고객이름}님!\n"+
            "\n"+
            "회원 가입 해주셔서 감사합니다. (축하)\n"+
            "#{고객이름}님의 회원가입 정보 안내드립니다.\n"+
            "\n"+
            "#{고객이름}님의\n"+
            "[#{쇼핑몰이름}] ID : #{고객ID}\n"+
            "\n"+
            "#{고객이름}님을 위한 상품들이 많이 준비되어 있습니다.\n"+
            "앞으로 많은 이용 부탁드립니다. (뽀뽀)\n"+
            "\n"+
            "\n"+
            "▷ #{쇼핑몰이름} 바로가기\n"+
            "#{쇼핑몰URL}\n"+
            "고객센터\n"+
            "(#{쇼핑몰번호})\n";

    // 입금 안내 템플릿
    public static final String DEPOSIT_GUIDE_TEMPLATE = "[#{쇼핑몰이름}]\n" +
            "안녕하세요. #{고객이름}님!\n" +
            "\n" +
            "입금계좌 안내드립니다. (꺄아)\n" +
            "\n" +
            "- 금액 : #{PRICE}원\n" +
            "- 은행 : #{BANK}\n" +
            "- 계좌번호 : #{ACCOUNT}\n" +
            "- 예금주 : #{DEPOSITOR}\n" +
            "\n" +
            "*주문자명 과 입금자명, 주문금액 과 입금금액이 일치해야 빠른 확인이 가능합니다. (우와)\n" +
            "\n" +
            "\n" +
            "▷ #{쇼핑몰이름} 바로가기\n" +
            "[#{쇼핑몰URL}]\n" +
            "고객센터\n" +
            "(#{쇼핑몰번호})\n";

    // 신규 주문 안내 템플릿
    public static final String NEW_ORDER_TEMPLATE = "[#{쇼핑몰이름}]\n" +
            "안녕하세요. #{고객이름}님!\n" +
            "\n" +
            "#{고객이름}님의 소중한 주문 안내드립니다. (굿)\n" +
            "\n" +
            "- 날짜 : #{DATE}\n" +
            "- 주문번호 : #{ORDERID}\n" +
            "- 금액 : #{PRICE}원\n" +
            "\n" +
            "감사합니다. 좋은하루 되세요. (감동)\n" +
            "\n" +
            "\n" +
            "▷ #{쇼핑몰이름} 바로가기\n" +
            "#{쇼핑몰URL}\n" +
            "고객센터\n" +
            "(#{쇼핑몰번호})\n";

    public static final String COMPLETED_ORDER_TEMPLATE = "[#{쇼핑몰이름}]\n" +
            "안녕하세요. #{고객이름}님!\n" +
            "\n" +
            "주문하신 상품이 배송 완료되었습니다. (좋아)\n" +
            "\n" +
            "- 상품명 : #{PRODUCT}\n" +
            "\n" +
            "#{고객이름}님 마음에 쏙 드셨으면 좋겠습니다.\n" +
            "앞으로도 #{쇼핑몰이름}을(를) 많이 사랑해주세요. :-)\n" +
            "\n" +
            "\n" +
            "▷ #{쇼핑몰이름} 바로가기\n" +
            "#{쇼핑몰URL}\n" +
            "고객센터\n" +
            "(#{쇼핑몰번호})\n";

    // 게시물 답변
    public static final String BOARD_ANSWER_TEMPLATE = "[#{쇼핑몰이름}]\n" +
            "안녕하세요. #{고객이름}님\n" +
            "\n" +
            "문의하신 내용에 답변이 등록되었습니다.\n" +
            "감사합니다^^\n" +
            "\n" +
            "\n" +
            "▷ #{쇼핑몰이름} 바로가기\n" +
            "#{쇼핑몰URL}\n" +
            "고객센터\n" +
            "(#{쇼핑몰번호})\n";

    // 주문 결제 완료
//    public static final String

    // 무통장 입금 완료
    public static final String DEPOSIT_WITHOUT_BANKBOOK = "[#{쇼핑몰이름}]\n" +
            "안녕하세요. #{고객이름}님!\n" +
            "\n" +
            "입금확인 되었습니다. (뽀뽀)\n" +
            "\n" +
            "- 날짜 : #{결제일}\n" +
            "- 주문번호 : #{ORDERID}\n" +
            "\n" +
            "#{쇼핑몰이름}을(를) 사랑해주셔서 감사합니다. (굿)\n" +
            "\n" +
            "\n" +
            "▷ #{쇼핑몰이름} 바로가기\n" +
            "#{쇼핑몰URL}\n" +
            "고객센터\n" +
            "(#{발신자번호})";

    // 템플릿의 변수로 되어있는 부분을 개인화
    public static String replaceTemplateVariable(String template, String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(template);

        return matcher.replaceAll(value);
    }

    // 템플릿의 모든 변수가 처리되었는지 확인
    public static boolean isFinishedTemplate(String template) {
        Pattern pattern = Pattern.compile(TEMPLATE_VARIABLE);
        Matcher matcher = pattern.matcher(template);

        return !matcher.find();
    }
}
