package com.dreamland.database.entity;

import com.dreamland.database.Entity;
import com.dreamland.database.annotation.Unique;

public class VideoBrief extends Entity {
    @Unique
    public String name;
    public String picUrl;
    public float score;
}
