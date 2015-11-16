package com.ranisaurus.newtorklayer.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ranisaurus.utilitylayer.base.BaseModel;

/**
 * Created by muzammilpeer on 9/27/15.
 */
public class Data extends BaseModel implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
    public Response response;

    public String message;

    public String result;


    ////////all properties

    public String displayTitle;

    public String dittoId;

    public String video_iosStreamUrlLow;

    public String packageId;

    public String totalViews;

    public String isVOD;

    public String favouriteId;

    public String isFav;

    public String favouriteType;

    public String image;

    public String mobile_large_image;

    public String mobile_small_image;

    public String videoURL;

    public String total;

    public String name;

    public String position;

    public String link_target;

    public String link_url;

    @SerializedName("class")
    public String clazz; //peer

    public String id_menu;

    public String channel_type;

    public String id_category;

    public String id_parent;

    public String id_channel;

    public String slug;

    public String category;

    public String sdvideo;

    public String clipid;

    public String video_iosStreamUrl;

    public String video_streamUrl;

    public String vodId;

    public String mob_large;

    public String video;

    public String vodCategoryId;

    public String views_complete;

    public String img_icon;

    public String id_import;

    public String embed_html5;

    public String allow_comments;

    public String socialize;

    public String live_ios;

    public String type;

    public String ad_first_clip;

    public String live_ms;

    public String id_user;

    public String ad_link;

    public String live_html5_dash;

    public String is_featured;

    public String date_lastmod;

    public String status_moderation;

    public String description;

    public String is_ad;

    public String clicks;

    public String img_thumbnail;

    public String vod_html5_webm;

    public String live_rtsp;

    public String description_seo;

    public String status;

    public String privacy;

    public String vod_html5_webm_trailer;

    public String is_skippable_after;

    public String img_poster;

    public String aspect;

    public String likes;

    public String downloadable_xfiles;

    public String id_clip;

    public String downloadable_condition;

    public String is_skippable;

    public String interactivity_randomization;

    public String mob_small;

    public String vod_html5_h264_trailer;

    public String img_social;

    public String date;

    public String downloadable;

    public String id;

    public String id_quality;

    public String title;

    public String views_embed;

    public String vod_flash_trailer;

    public String ad_insertion_pattern;

    public String privacy_access_level;

    public String store_on_sale;

    public String title_url;

    public String store_play_trailer;

    public String dislikes;

    public String embed_flash;

    public String vod_flash;

    public String ad_insertion_offset;

    public String tags;

    public String interactivity_start_delay;

    public String vod_html5_h264;

    public String views_page;

    public String interactivity_spacing;

    public String duration;

    public String live_flash;

    public String views;

    public String interactivity_timing;

    public String is_3d;

    public String ad_type;


    public Data() {
    }

    protected Data(Parcel in) {
        response = (Response) in.readValue(Response.class.getClassLoader());
        message = in.readString();
        result = in.readString();
        displayTitle = in.readString();
        dittoId = in.readString();
        video_iosStreamUrlLow = in.readString();
        packageId = in.readString();
        totalViews = in.readString();
        isVOD = in.readString();
        favouriteId = in.readString();
        isFav = in.readString();
        favouriteType = in.readString();
        mobile_large_image = in.readString();
        mobile_small_image = in.readString();
        videoURL = in.readString();
        total = in.readString();
        name = in.readString();
        position = in.readString();
        link_target = in.readString();
        link_url = in.readString();
        clazz = in.readString();
        id_menu = in.readString();
        channel_type = in.readString();
        id_category = in.readString();
        id_parent = in.readString();
        id_channel = in.readString();
        slug = in.readString();
        category = in.readString();
        sdvideo = in.readString();
        clipid = in.readString();
        video_iosStreamUrl = in.readString();
        video_streamUrl = in.readString();
        vodId = in.readString();
        mob_large = in.readString();
        video = in.readString();
        vodCategoryId = in.readString();
        views_complete = in.readString();
        img_icon = in.readString();
        id_import = in.readString();
        embed_html5 = in.readString();
        allow_comments = in.readString();
        socialize = in.readString();
        live_ios = in.readString();
        type = in.readString();
        ad_first_clip = in.readString();
        live_ms = in.readString();
        id_user = in.readString();
        ad_link = in.readString();
        live_html5_dash = in.readString();
        is_featured = in.readString();
        date_lastmod = in.readString();
        status_moderation = in.readString();
        description = in.readString();
        is_ad = in.readString();
        clicks = in.readString();
        img_thumbnail = in.readString();
        vod_html5_webm = in.readString();
        live_rtsp = in.readString();
        description_seo = in.readString();
        status = in.readString();
        privacy = in.readString();
        vod_html5_webm_trailer = in.readString();
        is_skippable_after = in.readString();
        img_poster = in.readString();
        aspect = in.readString();
        likes = in.readString();
        downloadable_xfiles = in.readString();
        id_clip = in.readString();
        downloadable_condition = in.readString();
        is_skippable = in.readString();
        interactivity_randomization = in.readString();
        mob_small = in.readString();
        vod_html5_h264_trailer = in.readString();
        img_social = in.readString();
        date = in.readString();
        downloadable = in.readString();
        id = in.readString();
        id_quality = in.readString();
        title = in.readString();
        views_embed = in.readString();
        vod_flash_trailer = in.readString();
        ad_insertion_pattern = in.readString();
        privacy_access_level = in.readString();
        store_on_sale = in.readString();
        title_url = in.readString();
        store_play_trailer = in.readString();
        dislikes = in.readString();
        embed_flash = in.readString();
        vod_flash = in.readString();
        ad_insertion_offset = in.readString();
        tags = in.readString();
        interactivity_start_delay = in.readString();
        vod_html5_h264 = in.readString();
        views_page = in.readString();
        interactivity_spacing = in.readString();
        duration = in.readString();
        live_flash = in.readString();
        views = in.readString();
        interactivity_timing = in.readString();
        is_3d = in.readString();
        ad_type = in.readString();
        image = in.readString();
    }

    @Override
    public String toString() {
        return "Data{" +
                "response=" + response +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                ", displayTitle='" + displayTitle + '\'' +
                ", dittoId='" + dittoId + '\'' +
                ", video_iosStreamUrlLow='" + video_iosStreamUrlLow + '\'' +
                ", packageId='" + packageId + '\'' +
                ", totalViews='" + totalViews + '\'' +
                ", isVOD='" + isVOD + '\'' +
                ", favouriteId='" + favouriteId + '\'' +
                ", isFav='" + isFav + '\'' +
                ", favouriteType='" + favouriteType + '\'' +
                ", mobile_large_image='" + mobile_large_image + '\'' +
                ", mobile_small_image='" + mobile_small_image + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", total='" + total + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", link_target='" + link_target + '\'' +
                ", link_url='" + link_url + '\'' +
                ", clazz='" + clazz + '\'' +
                ", id_menu='" + id_menu + '\'' +
                ", channel_type='" + channel_type + '\'' +
                ", id_category='" + id_category + '\'' +
                ", id_parent='" + id_parent + '\'' +
                ", id_channel='" + id_channel + '\'' +
                ", slug='" + slug + '\'' +
                ", category='" + category + '\'' +
                ", sdvideo='" + sdvideo + '\'' +
                ", clipid='" + clipid + '\'' +
                ", video_iosStreamUrl='" + video_iosStreamUrl + '\'' +
                ", video_streamUrl='" + video_streamUrl + '\'' +
                ", vodId='" + vodId + '\'' +
                ", mob_large='" + mob_large + '\'' +
                ", video='" + video + '\'' +
                ", vodCategoryId='" + vodCategoryId + '\'' +
                ", views_complete='" + views_complete + '\'' +
                ", img_icon='" + img_icon + '\'' +
                ", id_import='" + id_import + '\'' +
                ", embed_html5='" + embed_html5 + '\'' +
                ", allow_comments='" + allow_comments + '\'' +
                ", socialize='" + socialize + '\'' +
                ", live_ios='" + live_ios + '\'' +
                ", type='" + type + '\'' +
                ", ad_first_clip='" + ad_first_clip + '\'' +
                ", live_ms='" + live_ms + '\'' +
                ", id_user='" + id_user + '\'' +
                ", ad_link='" + ad_link + '\'' +
                ", live_html5_dash='" + live_html5_dash + '\'' +
                ", is_featured='" + is_featured + '\'' +
                ", date_lastmod='" + date_lastmod + '\'' +
                ", status_moderation='" + status_moderation + '\'' +
                ", description='" + description + '\'' +
                ", is_ad='" + is_ad + '\'' +
                ", clicks='" + clicks + '\'' +
                ", img_thumbnail='" + img_thumbnail + '\'' +
                ", vod_html5_webm='" + vod_html5_webm + '\'' +
                ", live_rtsp='" + live_rtsp + '\'' +
                ", description_seo='" + description_seo + '\'' +
                ", status='" + status + '\'' +
                ", privacy='" + privacy + '\'' +
                ", vod_html5_webm_trailer='" + vod_html5_webm_trailer + '\'' +
                ", is_skippable_after='" + is_skippable_after + '\'' +
                ", img_poster='" + img_poster + '\'' +
                ", aspect='" + aspect + '\'' +
                ", likes='" + likes + '\'' +
                ", downloadable_xfiles='" + downloadable_xfiles + '\'' +
                ", id_clip='" + id_clip + '\'' +
                ", downloadable_condition='" + downloadable_condition + '\'' +
                ", is_skippable='" + is_skippable + '\'' +
                ", interactivity_randomization='" + interactivity_randomization + '\'' +
                ", mob_small='" + mob_small + '\'' +
                ", vod_html5_h264_trailer='" + vod_html5_h264_trailer + '\'' +
                ", img_social='" + img_social + '\'' +
                ", date='" + date + '\'' +
                ", downloadable='" + downloadable + '\'' +
                ", id='" + id + '\'' +
                ", id_quality='" + id_quality + '\'' +
                ", title='" + title + '\'' +
                ", views_embed='" + views_embed + '\'' +
                ", vod_flash_trailer='" + vod_flash_trailer + '\'' +
                ", ad_insertion_pattern='" + ad_insertion_pattern + '\'' +
                ", privacy_access_level='" + privacy_access_level + '\'' +
                ", store_on_sale='" + store_on_sale + '\'' +
                ", title_url='" + title_url + '\'' +
                ", store_play_trailer='" + store_play_trailer + '\'' +
                ", dislikes='" + dislikes + '\'' +
                ", embed_flash='" + embed_flash + '\'' +
                ", vod_flash='" + vod_flash + '\'' +
                ", ad_insertion_offset='" + ad_insertion_offset + '\'' +
                ", tags='" + tags + '\'' +
                ", interactivity_start_delay='" + interactivity_start_delay + '\'' +
                ", vod_html5_h264='" + vod_html5_h264 + '\'' +
                ", views_page='" + views_page + '\'' +
                ", interactivity_spacing='" + interactivity_spacing + '\'' +
                ", duration='" + duration + '\'' +
                ", live_flash='" + live_flash + '\'' +
                ", views='" + views + '\'' +
                ", interactivity_timing='" + interactivity_timing + '\'' +
                ", is_3d='" + is_3d + '\'' +
                ", ad_type='" + ad_type + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(response);
        dest.writeString(message);
        dest.writeString(result);
        dest.writeString(displayTitle);
        dest.writeString(dittoId);
        dest.writeString(video_iosStreamUrlLow);
        dest.writeString(packageId);
        dest.writeString(totalViews);
        dest.writeString(isVOD);
        dest.writeString(favouriteId);
        dest.writeString(isFav);
        dest.writeString(favouriteType);
        dest.writeString(mobile_large_image);
        dest.writeString(mobile_small_image);
        dest.writeString(videoURL);
        dest.writeString(total);
        dest.writeString(name);
        dest.writeString(position);
        dest.writeString(link_target);
        dest.writeString(link_url);
        dest.writeString(clazz);
        dest.writeString(id_menu);
        dest.writeString(channel_type);
        dest.writeString(id_category);
        dest.writeString(id_parent);
        dest.writeString(id_channel);
        dest.writeString(slug);
        dest.writeString(category);
        dest.writeString(sdvideo);
        dest.writeString(clipid);
        dest.writeString(video_iosStreamUrl);
        dest.writeString(video_streamUrl);
        dest.writeString(vodId);
        dest.writeString(mob_large);
        dest.writeString(video);
        dest.writeString(vodCategoryId);
        dest.writeString(views_complete);
        dest.writeString(img_icon);
        dest.writeString(id_import);
        dest.writeString(embed_html5);
        dest.writeString(allow_comments);
        dest.writeString(socialize);
        dest.writeString(live_ios);
        dest.writeString(type);
        dest.writeString(ad_first_clip);
        dest.writeString(live_ms);
        dest.writeString(id_user);
        dest.writeString(ad_link);
        dest.writeString(live_html5_dash);
        dest.writeString(is_featured);
        dest.writeString(date_lastmod);
        dest.writeString(status_moderation);
        dest.writeString(description);
        dest.writeString(is_ad);
        dest.writeString(clicks);
        dest.writeString(img_thumbnail);
        dest.writeString(vod_html5_webm);
        dest.writeString(live_rtsp);
        dest.writeString(description_seo);
        dest.writeString(status);
        dest.writeString(privacy);
        dest.writeString(vod_html5_webm_trailer);
        dest.writeString(is_skippable_after);
        dest.writeString(img_poster);
        dest.writeString(aspect);
        dest.writeString(likes);
        dest.writeString(downloadable_xfiles);
        dest.writeString(id_clip);
        dest.writeString(downloadable_condition);
        dest.writeString(is_skippable);
        dest.writeString(interactivity_randomization);
        dest.writeString(mob_small);
        dest.writeString(vod_html5_h264_trailer);
        dest.writeString(img_social);
        dest.writeString(date);
        dest.writeString(downloadable);
        dest.writeString(id);
        dest.writeString(id_quality);
        dest.writeString(title);
        dest.writeString(views_embed);
        dest.writeString(vod_flash_trailer);
        dest.writeString(ad_insertion_pattern);
        dest.writeString(privacy_access_level);
        dest.writeString(store_on_sale);
        dest.writeString(title_url);
        dest.writeString(store_play_trailer);
        dest.writeString(dislikes);
        dest.writeString(embed_flash);
        dest.writeString(vod_flash);
        dest.writeString(ad_insertion_offset);
        dest.writeString(tags);
        dest.writeString(interactivity_start_delay);
        dest.writeString(vod_html5_h264);
        dest.writeString(views_page);
        dest.writeString(interactivity_spacing);
        dest.writeString(duration);
        dest.writeString(live_flash);
        dest.writeString(views);
        dest.writeString(interactivity_timing);
        dest.writeString(is_3d);
        dest.writeString(ad_type);
        dest.writeString(image);
    }
}