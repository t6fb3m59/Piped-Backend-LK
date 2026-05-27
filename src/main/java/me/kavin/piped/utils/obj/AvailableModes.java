package me.kavin.piped.utils.obj;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AvailableModes {

    public LegacyMode legacy;
    public SabrSession sabr;

    public AvailableModes(LegacyMode legacy, SabrSession sabr) {
        this.legacy = legacy;
        this.sabr = sabr;
    }
}
