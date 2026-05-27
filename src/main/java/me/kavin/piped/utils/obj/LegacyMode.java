package me.kavin.piped.utils.obj;

import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The "legacy" block inside {@link AvailableModes}.
 *
 * <p>Holds everything that used to live as flat top-level fields on
 * {@link Streams} (DASH/HLS manifest URLs and the per-format
 * {@code audioStreams}/{@code videoStreams} arrays). Frontends that pre-date
 * the {@code availableModes} restructure read these from the top level of an
 * older response shape — new frontends read them from here.</p>
 */
@NoArgsConstructor
public class LegacyMode {

    public String dash;
    public String hls;
    public List<PipedStream> audioStreams;
    public List<PipedStream> videoStreams;

    public LegacyMode(String dash, String hls,
                      List<PipedStream> audioStreams, List<PipedStream> videoStreams) {
        this.dash = dash;
        this.hls = hls;
        this.audioStreams = audioStreams;
        this.videoStreams = videoStreams;
    }
}
