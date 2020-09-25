package com.almod.DemoSpring.enginer;

public class ButtonEnginer {
    private static boolean clickedButton = false;

    public static void doFalse(){
        clickedButton = false;
    }

    public static void doTrue(){
        clickedButton = true;
    }

    public static boolean getClickedButton(){
        return clickedButton;
    }
}
