package me.kavin.piped.utils.obj;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SabrFormat {

    public int itag;
    public String lastModified;
    public String xtags;
    public int bitrate;
    public String mimeType;
    public Long approxDurationMs;

    public Range initRange;
    public Range indexRange;

    public Integer width;
    public Integer height;
    public Integer frameRate;
    public String quality;

    public String language;
    public Integer audioSampleRate;
    public Integer audioChannels;
    public String audioTrackId;
    public String label;

    public Boolean isDrc;
    public Boolean isVoiceBoost;
    public Boolean isOriginal;
    public Boolean isDubbed;
    public Boolean isAutoDubbed;
    public Boolean isDescriptive;
    public Boolean isSecondary;
    public Boolean spatialAudio;

    public String colorTransferCharacteristics;
    public String colorPrimaries;

    public static class Range {
        public int start;
        public int end;

        public Range() {}

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
