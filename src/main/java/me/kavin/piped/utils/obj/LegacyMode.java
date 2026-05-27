package me.kavin.piped.utils.obj;

import lombok.NoArgsConstructor;

import java.util.List;

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
