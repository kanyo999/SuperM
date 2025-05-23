package com.sxt.obj;

import com.sxt.util.BackGround;
import com.sxt.util.StaticValue;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy implements Runnable {
    //座標
    private int x;
    private int y;
    //敵種類
    private int type;
    //敵向き方向判断
    private boolean face_to = true;
    //敵画像
    private BufferedImage show;
    //背景
    private BackGround bg;
    //花動く限界
    private int max_up = 0;
    private int max_down = 0;
    //スレッド定義
    Thread thread = new Thread(this);
    //現在画像状態
    private int image_type = 0;

    public Enemy() {
    }

    //キノコ
    public Enemy(int x, int y, int type, boolean face_to, BackGround bg) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.face_to = face_to;
        this.bg = bg;
        show = StaticValue.mushroom.get(0);
        thread.start();
    }

    //花
    public Enemy(int x, int y, int type, boolean face_to, BackGround bg, int max_up, int max_down) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.face_to = face_to;
        this.max_up = max_up;
        this.max_down = max_down;
        this.bg = bg;
        show = StaticValue.flower.get(0);
        thread.start();
    }

    //敵死亡
    public void death (){
        show = StaticValue.mushroom.get(2);
        this.bg.getEnemyList().remove(this);
    }

    @Override
    public void run() {

        while (true) {
            //キノコ敵種類
            if (type == 1) {
                if (face_to) {
                    this.x -= 2;
                }else {
                    this.x += 2;
                }
                image_type = image_type == 1 ? 0 : 1;
                show = StaticValue.mushroom.get(image_type);
            }

            boolean canLeft = true;
            boolean canRight = true;

            for (int i = 0; i < bg.getObstacleList().size(); i++) {
                Obstacle ob1 = bg.getObstacleList().get(i);
                //キノコ左に行けるか
                if (ob1.getX() == this.x - 36 && (ob1.getY() + 65 > this.y && ob1.getY() - 35 < this.y)) {
                    canLeft = false;
                }
                //キノコ右に行けるか
                if (ob1.getX() == this.x + 36 && (ob1.getY() + 65 > this.y && ob1.getY() - 35 < this.y)) {
                    canRight = false;
                }

            }
            //方向判断
            if (face_to && !canLeft || this.x ==0) {
                face_to = false;
            }else if ((!face_to) && (!canRight) || this.x ==764) {
                face_to = true;
            }

            //花敵判断
            if (type == 2){
                if (face_to){
                    this.y -= 2;
                }else{
                    this.y += 2;
                }
                image_type = image_type == 1 ? 0 : 1;
                //花が動く限界までに至る判断
                if (face_to && (this.y == max_up)){
                    face_to = false;
                }
                if ((!face_to) && (this.y == max_down)){
                    face_to = true;
                }
                show = StaticValue.flower.get(image_type);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isFace_to() {
        return face_to;
    }

    public void setFace_to(boolean face_to) {
        this.face_to = face_to;
    }

    public BufferedImage getShow() {
        return show;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }

    public BackGround getBg() {
        return bg;
    }

    public void setBg(BackGround bg) {
        this.bg = bg;
    }

    public int getMax_up() {
        return max_up;
    }

    public void setMax_up(int max_up) {
        this.max_up = max_up;
    }

    public int getMax_down() {
        return max_down;
    }

    public void setMax_down(int max_down) {
        this.max_down = max_down;
    }

    public int getImage_type() {
        return image_type;
    }

    public void setImage_type(int image_type) {
        this.image_type = image_type;
    }
}