package com.ranisaurus.newtorklayer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by muzammilpeer on 11/4/15.
 */
public class Channel
{
    private ArrayList<Programme> programme;

    @SerializedName("display-name")
    @Expose    private String displayName;

    private String channeldisplayname;

    private String channellogourl;

    private String isfav;

    private String channelid;

    @SerializedName("-id")
    @Expose
    private String id;

    public ArrayList<Programme> getProgramme ()
    {
        return programme;
    }

    public void setProgramme (ArrayList<Programme> programme)
    {
        this.programme = programme;
    }

    public String getDisplayName ()
{
    return displayName;
}

    public void setDisplayName (String displayname)
{
    this.displayName = displayname;
}

    public String getChanneldisplayname ()
    {
        return channeldisplayname;
    }

    public void setChanneldisplayname (String channeldisplayname)
    {
        this.channeldisplayname = channeldisplayname;
    }

    public String getChannellogourl ()
    {
        return channellogourl;
    }

    public void setChannellogourl (String channellogourl)
    {
        this.channellogourl = channellogourl;
    }

    public String getIsfav ()
    {
        return isfav;
    }

    public void setIsfav (String isfav)
    {
        this.isfav = isfav;
    }

    public String getChannelid ()
    {
        return channelid;
    }

    public void setChannelid (String channelid)
    {
        this.channelid = channelid;
    }

    public String getid ()
{
    return id;
}

    public void setid (String id)
{
    this.id = id;
}

    @Override
    public String toString()
    {
        return "ClassPojo [programme = "+programme+", display-name = "+displayName+", channeldisplayname = "+channeldisplayname+", channellogourl = "+channellogourl+", isfav = "+isfav+", channelid = "+channelid+", -id = "+id+"]";
    }
}