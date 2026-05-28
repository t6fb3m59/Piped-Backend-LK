package me.kavin.piped.utils.obj;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The "sabr" block inside {@link AvailableModes}.
 *
 * <p>{@link #sessionUrl} is the SABR endpoint proxied through Piped-Proxy
 * (qhash-signed via the existing {@code rewriteVideoURL} pipeline).
 * {@link #ustreamerConfig} is the opaque base64 protobuf YouTube hands us and
 * which must accompany every SABR POST body. {@link #cpn} is the client
 * playback nonce NPE generated when fetching the player response; the frontend
 * appends it (along with {@code alr=yes}) to the sessionUrl before issuing
 * SABR requests, matching FreeTube's setup.
 * {@link #formats} mirrors FreeTube's SabrManifest format[] shape so the
 * frontend can hand it directly to the vendored SabrManifestParser.</p>
 */
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
