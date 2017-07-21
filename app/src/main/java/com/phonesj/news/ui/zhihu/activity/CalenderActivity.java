package com.phonesj.news.ui.zhihu.activity;

import com.phonesj.news.R;
import com.phonesj.news.base.SimpleActivity;
import com.phonesj.news.component.RxBus;
import com.phonesj.news.util.DateUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Phone on 2017/7/21.
 */

public class CalenderActivity extends SimpleActivity {

    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.view_calender)
    MaterialCalendarView viewCalender;
    @BindView(R.id.tv_calender_enter)
    TextView tvCalenderEnter;

    CalendarDay mDate;

    @Override
    protected void onViewCreated() {
        //不做操作
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_calender;
    }

    @Override
    protected void initEventAndData() {
        setToolbar(toolBar, "选择日期");
        viewCalender
            .state()
            .edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(2017, 1, 1))
            .setMaximumDate(CalendarDay.from(DateUtil.getCurrentYear(), DateUtil.getCurrentMonth(), DateUtil
                .getCurrentDay()))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit();
        viewCalender.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                mDate = date;
            }
        });
    }

    @OnClick(R.id.tv_calender_enter)
    public void onViewClicked() {
        if (mDate != null) {
            RxBus.getDefault().post(mDate);//RxBus发送事件
        }
        finish();
    }
}
