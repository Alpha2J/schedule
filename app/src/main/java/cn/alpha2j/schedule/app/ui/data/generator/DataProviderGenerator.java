package cn.alpha2j.schedule.app.ui.data.generator;

import android.support.annotation.NonNull;

import cn.alpha2j.schedule.app.ui.data.AbstractDataProvider;

/**
 * @author alpha
 */
public interface DataProviderGenerator {

    @NonNull
    AbstractDataProvider create();
}
