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
    public long approxDurationMs;
    public String mimeType;
    public Integer width;
    public Integer height;
    public String audioTrackId;
    public Boolean isDrc;

    public SabrFormat(int itag, String lastModified, String xtags, int bitrate,
                      long approxDurationMs, String mimeType,
                      Integer width, Integer height,
                      String audioTrackId, Boolean isDrc) {
        this.itag = itag;
        this.lastModified = lastModified;
        this.xtags = xtags;
        this.bitrate = bitrate;
        this.approxDurationMs = approxDurationMs;
        this.mimeType = mimeType;
        this.width = width;
        this.height = height;
        this.audioTrackId = audioTrackId;
        this.isDrc = isDrc;
    }
}
