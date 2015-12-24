package com.ranisaurus.newtorklayer.models;

import com.ranisaurus.utilitylayer.base.BaseModel;

/**
 * Created by muzammilpeer on 11/4/15.
 */
public class Programme extends BaseModel
{
    private String stop;

    private String duration;

    private String title;

    private String reminderid;

    private String programmeid;

    private String isprogrammefavorite;

    private String start;

    private String subgenre;

    private String date;

    private String isreminder;

    private String channelid;

    private String programmeurl;

    public String getStop ()
    {
        return stop;
    }

    public void setStop (String stop)
    {
        this.stop = stop;
    }

    public String getDuration ()
    {
        return duration;
    }

    public void setDuration (String duration)
    {
        this.duration = duration;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getReminderid ()
    {
        return reminderid;
    }

    public void setReminderid (String reminderid)
    {
        this.reminderid = reminderid;
    }

    public String getProgrammeid ()
    {
        return programmeid;
    }

    public void setProgrammeid (String programmeid)
    {
        this.programmeid = programmeid;
    }

    public String getIsprogrammefavorite ()
    {
        return isprogrammefavorite;
    }

    public void setIsprogrammefavorite (String isprogrammefavorite)
    {
        this.isprogrammefavorite = isprogrammefavorite;
    }

    public String getStart ()
    {
        return start;
    }

    public void setStart (String start)
    {
        this.start = start;
    }

    public String getSubgenre ()
    {
        return subgenre;
    }

    public void setSubgenre (String subgenre)
    {
        this.subgenre = subgenre;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getIsreminder ()
    {
        return isreminder;
    }

    public void setIsreminder (String isreminder)
    {
        this.isreminder = isreminder;
    }

    public String getChannelid ()
    {
        return channelid;
    }

    public void setChannelid (String channelid)
    {
        this.channelid = channelid;
    }

    public String getProgrammeurl ()
    {
        return programmeurl;
    }

    public void setProgrammeurl (String programmeurl)
    {
        this.programmeurl = programmeurl;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [stop = "+stop+", duration = "+duration+", title = "+title+", reminderid = "+reminderid+", programmeid = "+programmeid+", isprogrammefavorite = "+isprogrammefavorite+", start = "+start+", subgenre = "+subgenre+", date = "+date+", isreminder = "+isreminder+", channelid = "+channelid+", programmeurl = "+programmeurl+"]";
    }
}