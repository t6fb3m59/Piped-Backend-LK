package me.kavin.piped.utils.obj;

import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The "sabr" block inside {@link AvailableModes}.
 *
 * <p>{@link #sessionUrl} is the SABR endpoint proxied through Piped-Proxy
 * (qhash-signed via the existing {@code rewriteVideoURL} pipeline).
 * {@link #ustreamerConfig} is the opaque base64 protobuf YouTube hands us and
 * which must accompany every SABR POST body. {@link #formats} mirrors LuanRT's
 * {@code SabrFormat[]} so the frontend can pass it to
 * {@code SabrStreamingAdapter.setServerAbrFormats(...)} directly.</p>
 */
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
