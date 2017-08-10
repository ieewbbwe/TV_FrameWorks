package com.videoworks.tvnews.utils;

import com.videoworks.tvnews.BuildConfig;

/**
 * Created by mxh on 2017/8/10.
 * Describe：
 */

public final class EnvironmentUtils {
    public enum BuildType {
        debug,
        dogfood,
        release
    }

    public static boolean isNotDebug() {
        return !BuildType.debug.name().equalsIgnoreCase(BuildConfig.BUILD_TYPE);
    }

    public static boolean isDebug() {
        return BuildType.debug.name().equalsIgnoreCase(BuildConfig.BUILD_TYPE);
    }

    public static boolean isDogfood() {
        return BuildType.dogfood.name().equalsIgnoreCase(BuildConfig.BUILD_TYPE);
    }

    public static boolean isRelease() {
        return BuildType.release.name().equalsIgnoreCase(BuildConfig.BUILD_TYPE);
    }

    public static boolean isNotRelease() {
        return !BuildType.release.name().equalsIgnoreCase(BuildConfig.BUILD_TYPE);
    }
}
