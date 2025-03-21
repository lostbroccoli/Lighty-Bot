package net.neoooo.modules.ticketsystem.utils.impl;


import net.neoooo.modules.ticketsystem.utils.IFormatHelper;

import java.util.Arrays;
import java.util.List;

public class VideoFormat implements IFormatHelper {
    final List<String> formats = Arrays.asList("mp4", "webm", "mkv", "avi", "mov", "flv", "wmv", "mpg", "mpeg");
    @Override
    public List<String> formats() {
        return formats;
    }
}
