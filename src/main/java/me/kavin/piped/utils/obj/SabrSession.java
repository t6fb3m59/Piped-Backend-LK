package me.kavin.piped.utils.obj;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class SabrSession {

    public String sessionUrl;
    public String ustreamerConfig;
    public List<SabrFormat> formats;

    public SabrSession(String sessionUrl, String ustreamerConfig, List<SabrFormat> formats) {
        this.sessionUrl = sessionUrl;
        this.ustreamerConfig = ustreamerConfig;
        this.formats = formats;
    }
}
