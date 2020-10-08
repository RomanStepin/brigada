package com.example.pet;

public class MyMessage1 {
    private String text;
    private String author;
    private String name;
    private long time;
    private int avatar;

    private MyMessage1() {super();}

    public MyMessage1(String text, String author, String name, long time, int avatar) {
        this.text = text;
        this.author = author;
        this.name = name;
        this.time = time;
        this.avatar = avatar;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public long getTime() {
        return time;
    }

    public int getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MyMessage1{" +  "text='" + text + '\'' + "author='" + author + '\'' + "avatar='" + avatar + '\'' + "time='" + time + '\'' + "name='" + name + '\'' + "}";
    }
}
