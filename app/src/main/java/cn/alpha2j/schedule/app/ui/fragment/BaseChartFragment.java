package cn.alpha2j.schedule.app.ui.fragment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author alpha
 */
public abstract class BaseChartFragment extends BaseFragment {

    @Override
    protected boolean hasView() {
        return true;
    }

    /**
     * 当图标的数据需要更新的时候调用
     * @param taskDateDataList 新的数据
     */
    public abstract void updateChart(List<TaskDateData> taskDateDataList);

    public static class TaskDateData implements Parcelable {

        private int finishedNumber;
        private int unfinishedNumber;

        private long dataBelongingDate;

        public int getFinishedNumber() {
            return finishedNumber;
        }

        public void setFinishedNumber(int finishedNumber) {
            this.finishedNumber = finishedNumber;
        }

        public int getUnfinishedNumber() {
            return unfinishedNumber;
        }

        public void setUnfinishedNumber(int unfinishedNumber) {
            this.unfinishedNumber = unfinishedNumber;
        }

        public long getDataBelongingDate() {
            return dataBelongingDate;
        }

        public void setDataBelongingDate(long dataBelongingDate) {
            this.dataBelongingDate = dataBelongingDate;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

            parcel.writeInt(finishedNumber);
            parcel.writeInt(unfinishedNumber);

            parcel.writeLong(dataBelongingDate);
        }

        public static final Parcelable.Creator<TaskDateData> CREATOR = new Parcelable.Creator<TaskDateData>() {

            @Override
            public TaskDateData createFromParcel(Parcel parcel) {

                TaskDateData taskDateData = new TaskDateData();
                taskDateData.finishedNumber = parcel.readInt();
                taskDateData.unfinishedNumber = parcel.readInt();
                taskDateData.dataBelongingDate = parcel.readLong();

                return taskDateData;
            }

            @Override
            public TaskDateData[] newArray(int i) {
                return new TaskDateData[i];
            }
        };
    }
}
