package com.rts.test;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/13/13
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChatTest {
    public static void main(String[] args) {
        ArrayList<Lines> lineses = new ArrayList<Lines>();
        lineses.add(new Lines("1"));
        lineses.add(new Lines("2"));
        lineses.add(new Lines("3"));
        lineses.add(new Lines("4"));
        lineses.add(new Lines("5"));
        lineses.add(new Lines("6"));
        lineses.add(new Lines("7"));
        lineses.add(new Lines("8"));
        lineses.add(new Lines("9"));
        lineses.add(new Lines("0"));
        for (int i = lineses.size() - 5; i < lineses.size(); i++) {
            System.out.println(lineses.get(i).getText());
        }
    }

    static class Lines {
        private String text;

        Lines(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
