package cn.alpha2j.schedule.app.ui.data.creator;

import android.support.annotation.NonNull;

import cn.alpha2j.schedule.app.ui.data.AbstractDataProvider;

/**
 * @author alpha
 */
public interface DataProviderCreator {

    @NonNull
    AbstractDataProvider create();
}
