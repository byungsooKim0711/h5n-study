package org.kimbs.netty.packet.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Attachment implements Serializable {
    private static final long serialVersionUID = 7702171592157105317L;

    @JsonProperty("button")
    private List<Button> buttons = new ArrayList<>();

    @Data
    public static class Button implements Serializable {
        private static final long serialVersionUID = 7248599502914021380L;

        @JsonProperty("name")
        private String name;

        @JsonProperty("type")
        private String type;

        @JsonProperty("scheme_android")
        private String schemeAndroid;

        @JsonProperty("scheme_ios")
        private String schemeIos;

        @JsonProperty("url_mobile")
        private String urlMobile;

        @JsonProperty("url_pc")
        private String urlPc;
    }
}
