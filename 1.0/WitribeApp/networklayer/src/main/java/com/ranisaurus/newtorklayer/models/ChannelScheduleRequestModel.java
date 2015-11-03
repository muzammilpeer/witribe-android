package com.ranisaurus.newtorklayer.models;

/**
 * Created by muzammilpeer on 11/4/15.
 */

//http://timesofindia.indiatimes.com/tvschedulejson.cms
//        ?userid=0
//        &channellist=SIX,ZING&
//        fromdatetime=201511020000&
//        todatetime=201511030000&
//        deviceview=other&
//        channellogo=0

public class ChannelScheduleRequestModel
{
    private String deviceview;

    private String channellogo;

    private String todatetime;

    private String userid;

    private String fromdatetime;

    private String channellist;

    public String getDeviceview ()
    {
        return deviceview;
    }

    public void setDeviceview (String deviceview)
    {
        this.deviceview = deviceview;
    }

    public String getChannellogo ()
    {
        return channellogo;
    }

    public void setChannellogo (String channellogo)
    {
        this.channellogo = channellogo;
    }

    public String getTodatetime ()
    {
        return todatetime;
    }

    public void setTodatetime (String todatetime)
    {
        this.todatetime = todatetime;
    }

    public String getUserid ()
    {
        return userid;
    }

    public void setUserid (String userid)
    {
        this.userid = userid;
    }

    public String getFromdatetime ()
    {
        return fromdatetime;
    }

    public void setFromdatetime (String fromdatetime)
    {
        this.fromdatetime = fromdatetime;
    }

    public String getChannellist ()
    {
        return channellist;
    }

    public void setChannellist (String channellist)
    {
        this.channellist = channellist;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [deviceview = "+deviceview+", channellogo = "+channellogo+", todatetime = "+todatetime+", userid = "+userid+", fromdatetime = "+fromdatetime+", channellist = "+channellist+"]";
    }
}