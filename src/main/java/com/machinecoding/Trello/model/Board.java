package com.machinecoding.Trello.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final String CREATE = "CREATE";
    public static final String NAME = "NAME";
    public static final String PRIVACY = "PRIVACY";
    public static final String ADD_MEMBER = "ADD_MEMBER";
    public static final String REMOVE_MEMBER = "REMOVE_MEMBER";

    private String id, name, url;
    private Privacy privacy = Privacy.PUBLIC;
    private List<User> members = new ArrayList<>();
    private List<BoardList> lists = new ArrayList<>();

    public Board(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<BoardList> getLists() {
        return lists;
    }

    public void setLists(List<BoardList> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", privacy=" + privacy +
                '}';
    }
}
