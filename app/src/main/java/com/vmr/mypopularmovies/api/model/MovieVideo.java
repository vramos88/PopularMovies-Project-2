package com.vmr.mypopularmovies.api.model;

import org.parceler.Parcel;

import java.util.Locale;

/**
 * Created by Victor Ramos on 3/13/17.
 */

@Parcel
public class MovieVideo {
    String id;
    String iso_639_1;
    String iso_3166_1;
    String key;
    String name;
    String site;
    Integer size;
    String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoverImg(){
        return String.format(Locale.getDefault(),"https://i1.ytimg.com/vi/%s/mqdefault.jpg",key);
    }
}
