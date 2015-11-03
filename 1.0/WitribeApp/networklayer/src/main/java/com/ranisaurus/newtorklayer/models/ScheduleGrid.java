package com.ranisaurus.newtorklayer.models;

import java.util.ArrayList;

/**
 * Created by muzammilpeer on 11/4/15.
 */
public class ScheduleGrid
{
    private ArrayList<Channel> channel;

    public ArrayList<Channel> getChannel ()
    {
        return channel;
    }

    public void setChannel (ArrayList<Channel> channel)
    {
        this.channel = channel;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [channel = "+channel+"]";
    }
}