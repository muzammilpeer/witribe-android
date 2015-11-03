package com.ranisaurus.newtorklayer.models;

/**
 * Created by muzammilpeer on 11/4/15.
 */
public class ChannelScheduleResponseModel
{
    private ScheduleGrid ScheduleGrid;

    public ScheduleGrid getScheduleGrid ()
    {
        return ScheduleGrid;
    }

    public void setScheduleGrid (ScheduleGrid ScheduleGrid)
    {
        this.ScheduleGrid = ScheduleGrid;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ScheduleGrid = "+ScheduleGrid+"]";
    }
}
