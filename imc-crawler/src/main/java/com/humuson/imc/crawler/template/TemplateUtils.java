package com.humuson.imc.crawler.template;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtils {
    public static final String WELCOME_TEMPLATE = "[${쇼핑몰이름}]\n"+
            "안녕하세요. ${고객이름}님!\n"+
            "\n"+
            "회원 가입 해주셔서 감사합니다. (축하)\n"+
            "${고객이름}님의 회원가입 정보 안내드립니다.\n"+
            "\n"+
            "${고객이름}님의\n"+
            "[${쇼핑몰이름}] ID : ${고객ID}\n"+
            "\n"+
            "${고객이름}님을 위한 상품들이 많이 준비되어 있습니다.\n"+
            "앞으로 많은 이용 부탁드립니다. (뽀뽀)\n"+
            "\n"+
            "\n"+
            "▷ ${쇼핑몰이름} 바로가기\n"+
            "${쇼핑몰URL}\n"+
            "고객센터\n"+
            "(${쇼핑몰번호})\n";


    // 템플릿의 변수로 되어있는 부분을 개인화
    public static String replaceTemplateVariable(String template, String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(template);

        return matcher.replaceAll(value);
    }

    // 템플릿의 모든 변수가 처리되었는지 확인
    public static boolean isFinishedTemplate(String template) {
        Pattern pattern = Pattern.compile("\\$\\{.+?}");
        Matcher matcher = pattern.matcher(template);

        return !matcher.matches();
    }
}
