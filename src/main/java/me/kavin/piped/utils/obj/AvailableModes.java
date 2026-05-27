package me.kavin.piped.utils.obj;

import lombok.NoArgsConstructor;

/**
 * Container for the playback modes a video supports.
 *
 * <p>Either field may be {@code null}: {@link #legacy} is null when YouTube
 * provided neither a DASH/HLS manifest nor adaptive formats (rare); {@link #sabr}
 * is null when SABR isn't available for the video (e.g. livestreams, which are
 * force-legacy per design decision #17).</p>
 *
 * <p>Frontend dispatch picks one based on the user's "Playback engine" setting
 * combined with {@link Streams#defaultMode}. See the architecture doc.</p>
 */
@NoArgsConstructor
public class AvailableModes {

    public LegacyMode legacy;
    public SabrSession sabr;

    public AvailableModes(LegacyMode legacy, SabrSession sabr) {
        this.legacy = legacy;
        this.sabr = sabr;
    }
}
