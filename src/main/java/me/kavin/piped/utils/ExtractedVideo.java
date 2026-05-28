package me.kavin.piped.utils;

import me.kavin.piped.consts.Constants;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfo;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ExtractedVideo {

    public final StreamInfo info;
    public final String sabrUrl;
    public final String sabrUstreamerConfig;
    public final String sabrCpn;
    public final List<ItagItem> sabrFormats;

    public ExtractedVideo(StreamInfo info,
                          String sabrUrl,
                          String sabrUstreamerConfig,
                          String sabrCpn,
                          List<ItagItem> sabrFormats) {
        this.info = info;
        this.sabrUrl = sabrUrl;
        this.sabrUstreamerConfig = sabrUstreamerConfig;
        this.sabrCpn = sabrCpn;
        this.sabrFormats = sabrFormats != null ? sabrFormats : Collections.emptyList();
    }

    public static ExtractedVideo extract(String url) throws IOException, ExtractionException {
        final StreamExtractor extractor = Constants.YOUTUBE_SERVICE.getStreamExtractor(url);
        final StreamInfo info = StreamInfo.getInfo(extractor);

        String sabrUrl = null;
        String sabrConfig = null;
        String sabrCpn = null;
        List<ItagItem> sabrFormats = Collections.emptyList();
        if (extractor instanceof YoutubeStreamExtractor yt) {
            sabrUrl = yt.getAndroidServerAbrStreamingUrl();
            sabrConfig = yt.getAndroidVideoPlaybackUstreamerConfig();
            sabrCpn = yt.getAndroidCpn();
            sabrFormats = yt.getAndroidSabrAvailableFormats();
            if (sabrUrl == null) {
                sabrUrl = yt.getIosServerAbrStreamingUrl();
                sabrConfig = yt.getIosVideoPlaybackUstreamerConfig();
                sabrCpn = yt.getIosCpn();
                sabrFormats = yt.getIosSabrAvailableFormats();
            }
        }
        return new ExtractedVideo(info, sabrUrl, sabrConfig, sabrCpn, sabrFormats);
    }
}
