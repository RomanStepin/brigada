package com.example.pet;

public class MyMessage1 {
    private String text;

    private MyMessage1() {super();}

    public MyMessage1(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


    @Override
    public String toString() {
        return "MyMessage1{" +
                "text='" + text + '\'' +
                '}';
    }
}
