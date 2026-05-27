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

/**
 * Carries the result of one extraction across the {@code Multithreading.supplyAsync}
 * boundary in {@code StreamHandlers.streamsResponse}: the typed {@link StreamInfo}
 * plus the SABR-specific data that isn't exposed on StreamInfo itself.
 *
 * <p>{@link #sabrUrl}, {@link #sabrUstreamerConfig}, and {@link #sabrFormats} are
 * pre-picked at the boundary (Android first, iOS fallback) so the downstream
 * {@code CollectionUtils} layer doesn't need to know about service-specific
 * extractor types.</p>
 */
public class ExtractedVideo {

    public final StreamInfo info;
    public final String sabrUrl;
    public final String sabrUstreamerConfig;

    /**
     * Per-format metadata for SABR-served adaptive formats. Empty list (not null)
     * when SABR isn't available for this video. Sourced from NPE's
     * {@code getAndroidSabrAvailableFormats()} (Android preferred, iOS as fallback).
     */
    public final List<ItagItem> sabrFormats;

    public ExtractedVideo(StreamInfo info,
                          String sabrUrl,
                          String sabrUstreamerConfig,
                          List<ItagItem> sabrFormats) {
        this.info = info;
        this.sabrUrl = sabrUrl;
        this.sabrUstreamerConfig = sabrUstreamerConfig;
        this.sabrFormats = sabrFormats != null ? sabrFormats : Collections.emptyList();
    }

    /**
     * Extract a video by URL, returning the {@link StreamInfo} plus pre-picked SABR
     * fields. The YouTube-specific extractor cast lives only here — callers stay
     * service-agnostic.
     */
    public static ExtractedVideo extract(String url) throws IOException, ExtractionException {
        final StreamExtractor extractor = Constants.YOUTUBE_SERVICE.getStreamExtractor(url);
        final StreamInfo info = StreamInfo.getInfo(extractor);

        String sabrUrl = null;
        String sabrConfig = null;
        List<ItagItem> sabrFormats = Collections.emptyList();
        if (extractor instanceof YoutubeStreamExtractor yt) {
            sabrUrl = yt.getAndroidServerAbrStreamingUrl();
            sabrConfig = yt.getAndroidVideoPlaybackUstreamerConfig();
            sabrFormats = yt.getAndroidSabrAvailableFormats();
            if (sabrUrl == null) {
                sabrUrl = yt.getIosServerAbrStreamingUrl();
                sabrConfig = yt.getIosVideoPlaybackUstreamerConfig();
                sabrFormats = yt.getIosSabrAvailableFormats();
            }
        }
        return new ExtractedVideo(info, sabrUrl, sabrConfig, sabrFormats);
    }
}
