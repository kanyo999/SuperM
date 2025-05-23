package com.sxt.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaticValue {
    //背景
    public static BufferedImage bg = null;
    public static BufferedImage bg2 = null;

    //左ジャンプ
    public static BufferedImage jump_L = null;
    //右ジャンプ
    public static BufferedImage jump_R = null;
    //左向き
    public static BufferedImage stand_L = null;
    //右向き
    public static BufferedImage stand_R = null;
    //城
    public static BufferedImage tower = null;
    //フラグ
    public static BufferedImage gan = null;
    //左走り
    public static List<BufferedImage> run_L = new ArrayList<>();
    //右走り
    public static List<BufferedImage> run_R = new ArrayList<>();
    //障害物
    public static List<BufferedImage> obstacle = new ArrayList<>();
    //キノコ敵
    public static List<BufferedImage> mushroom = new ArrayList<>();
    //花敵
    public static List<BufferedImage> flower = new ArrayList<>();
    //imgパス接頭語
    public static String path = System.getProperty("user.dir") + "/src/images/";

    //初期化
    public static void init() {
        try {
            //背景ロード
            bg = ImageIO.read(new File(path + "bg.png"));
            bg2 = ImageIO.read(new File(path + "bg2.png"));
            //マリオロード
            jump_L = ImageIO.read(new File(path + "s_mario_jump1_L.png"));
            jump_R = ImageIO.read(new File(path + "s_mario_jump1_R.png"));
            stand_L = ImageIO.read(new File(path + "s_mario_stand_L.png"));
            stand_R = ImageIO.read(new File(path + "s_mario_stand_R.png"));
            //建物ロード
            tower = ImageIO.read(new File(path + "tower.png"));
            gan = ImageIO.read(new File(path + "gan.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        //マリオ左走り
        for (int i = 1; i <= 2; i++){
            try {
                run_L.add(ImageIO.read(new File(path + "s_mario_run" + i + "_L.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //マリオ右走り
        for (int i = 1; i <= 2; i++){
            try {
                run_R.add(ImageIO.read(new File(path + "s_mario_run" + i + "_R.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        //障害物ロード

        try {
            obstacle.add(ImageIO.read(new File(path + "brick.png")));
            obstacle.add(ImageIO.read(new File(path + "soil_base.png")));
            obstacle.add(ImageIO.read(new File(path + "soil_up.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //パイプ
        for (int i = 1; i <= 4;i++){
            try {
                obstacle.add(ImageIO.read(new File(path + "pipe" + i +".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        //破壊できない障害物とフラグ
        try {
            obstacle.add(ImageIO.read(new File(path + "brick2.png")));
            obstacle.add(ImageIO.read(new File(path + "flag.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //キノコ敵ロード
        for (int i = 1; i <= 3; i++) {

            try {
                mushroom.add(ImageIO.read(new File(path + "fungus" + i +".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //花敵ロード
        for (int i = 1; i <= 2; i++) {
            try {
                flower.add(ImageIO.read(new File(path + "flower1." + i +".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
