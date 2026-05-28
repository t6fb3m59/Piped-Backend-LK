package me.kavin.piped.utils;

import me.kavin.piped.consts.Constants;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.schabi.newpipe.extractor.Image;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static me.kavin.piped.consts.Constants.PROXY_HASH_SECRET;

public class URLUtils {

    public static String silentEncode(String s) {
        try {
            return URLEncoder.encode(s, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // ignored
        }
        return s;
    }

    public static String silentDecode(String s) {
        try {
            return URLDecoder.decode(s, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // ignored
        }
        return s;
    }

    public static String substringYouTube(String s) {
        return StringUtils.isEmpty(s) ? null : StringUtils.substringAfter(s, "youtube.com");
    }

    public static String rewriteURL(final String old) {
        return rewriteURL(old, Constants.IMAGE_PROXY_PART, Map.of());
    }

    public static String getLastThumbnail(final List<Image> thumbnails) {
        return thumbnails.isEmpty() ? null : rewriteURL(thumbnails.getLast().getUrl());
    }

    public static String rewriteVideoURL(final String old, final Map<String, String> extraParams) {
        return rewriteURL(old, Constants.PROXY_PART, extraParams);
    }

    public static String rewriteURL(final String old, final String proxy, final Map<String, String> extraParams) {

        if (StringUtils.isEmpty(old)) return null;

        URL url = null;
        try {
            url = new URL(old);
        } catch (MalformedURLException e) {
            ExceptionHandler.handle(e);
        }
        assert url != null;

        final String host = url.getHost();

        String query = url.getQuery();

        boolean hasQuery = query != null && query.length() > 0;

        Comparator<List<String>> listComparator = (o1, o2) -> {
            for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
                int result = o1.get(i).compareTo(o2.get(i));
                if (result != 0) {
                    return result;
                }
            }
            return Integer.compare(o1.size(), o2.size()); // compare list sizes if all elements are equal
        };

        Set<List<String>> queryPairs = new TreeSet<>(listComparator);

        if (hasQuery) {
            String[] pairs = query.split("&");

            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                queryPairs.add(List.of(
                        silentDecode(pair.substring(0, idx)),
                        silentDecode(pair.substring(idx + 1))
                ));
            }
        }

        // look for host param, and add it if it doesn't exist
        boolean hasHost = false;
        for (List<String> pair : queryPairs) {
            if (pair.get(0).equals("host")) {
                hasHost = true;
                break;
            }
        }
        if (!hasHost) {
            queryPairs.add(List.of("host", host));
        }

        for (var entry : extraParams.entrySet()) {
            queryPairs.add(List.of(entry.getKey(), entry.getValue()));
        }

        String path = url.getPath();

        if (path.contains("=")) {
            path = StringUtils.substringBefore(path, "=") + "=" + StringUtils.substringAfter(path, "=").replace("-rj", "-rw");
        }

        if (PROXY_HASH_SECRET != null)
            try {
                MessageDigest md = MessageDigest.getInstance("BLAKE3-256");
                for (List<String> pair : queryPairs) {
                    md.update(pair.get(0).getBytes(StandardCharsets.UTF_8));
                    md.update(pair.get(1).getBytes(StandardCharsets.UTF_8));
                }

                md.update(path.getBytes(StandardCharsets.UTF_8));

                md.update(PROXY_HASH_SECRET);

                queryPairs.add(List.of("qhash", Hex.encodeHexString(md.digest()).substring(0, 8)));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        String newUrl = proxy + path;

        StringBuilder qstring = null;

        for (List<String> pair : queryPairs) {
            if (qstring == null) {
                qstring = new StringBuilder();
            } else {
                qstring.append("&");
            }

            qstring.append(silentEncode(pair.get(0)));
            qstring.append("=");
            qstring.append(silentEncode(pair.get(1)));
        }

        newUrl += "?" + qstring;

        return newUrl;

    }

    public static String signSabrUrl(final String sabrUrl, final String cpn) {
        final Map<String, String> params = sabrQueryParams(sabrUrl);
        final StringBuilder prepared = new StringBuilder(sabrUrl);
        if (!params.containsKey("alr"))
            prepared.append(sabrUrl.indexOf('?') >= 0 ? "&alr=yes" : "?alr=yes");
        if (cpn != null && !cpn.isEmpty() && !params.containsKey("cpn"))
            prepared.append("&cpn=").append(cpn);
        return rewriteVideoURL(prepared.toString(), Map.of());
    }

    public static String resolveSabrRedirect(final String rawUrl, final String cpn) throws MalformedURLException {
        if (StringUtils.isEmpty(rawUrl))
            throw new IllegalArgumentException("Missing url parameter");
        final URL parsed = new URL(rawUrl);
        if (!parsed.getHost().endsWith(".googlevideo.com"))
            throw new IllegalArgumentException("URL host is not googlevideo");
        if (!"/videoplayback".equals(parsed.getPath()))
            throw new IllegalArgumentException("URL is not a videoplayback request");
        final Map<String, String> params = sabrQueryParams(rawUrl);
        if (!"1".equals(params.get("sabr")))
            throw new IllegalArgumentException("Not a SABR URL");
        if (!params.containsKey("expire"))
            throw new IllegalArgumentException("URL is not time-limited");
        return signSabrUrl(rawUrl, cpn);
    }

    private static Map<String, String> sabrQueryParams(final String url) {
        final Map<String, String> params = new HashMap<>();
        final int q = url.indexOf('?');
        if (q >= 0)
            for (String pair : url.substring(q + 1).split("&")) {
                final int eq = pair.indexOf('=');
                if (eq > 0)
                    params.put(pair.substring(0, eq), eq + 1 < pair.length() ? pair.substring(eq + 1) : "");
                else if (!pair.isEmpty())
                    params.put(pair, "");
            }
        return params;
    }
}
