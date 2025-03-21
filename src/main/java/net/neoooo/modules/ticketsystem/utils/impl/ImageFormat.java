package net.neoooo.modules.ticketsystem.utils.impl;

import net.neoooo.modules.ticketsystem.utils.IFormatHelper;

import java.util.Arrays;
import java.util.List;

public class ImageFormat implements IFormatHelper {

    final List<String> formats = Arrays.asList("png", "jpg", "jpeg", "gif");
    @Override
    public List<String> formats() {
        return formats;
    }
}
