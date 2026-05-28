package me.kavin.piped.utils.obj;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SabrSession {

    public String sessionUrl;
    public String ustreamerConfig;
    public String cpn;
    public List<SabrFormat> formats;

    public SabrSession(String sessionUrl, String ustreamerConfig, String cpn, List<SabrFormat> formats) {
        this.sessionUrl = sessionUrl;
        this.ustreamerConfig = ustreamerConfig;
        this.cpn = cpn;
        this.formats = formats;
    }
}
