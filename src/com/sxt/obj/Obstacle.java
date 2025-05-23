package com.sxt.obj;

import com.sxt.util.BackGround;
import com.sxt.util.StaticValue;

import java.awt.image.BufferedImage;

public class Obstacle implements Runnable{
    //障害物座標
    private int x;
    private int y;

    //障害物種類
    private int type;
    //画像表示
    private BufferedImage show = null;

    //背景定義
    private BackGround bg = null;
    //スレッド定義
    Thread thread = new Thread(this);

    public Obstacle(int x, int y, int type, BackGround bg) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.bg = bg;
        show = StaticValue.obstacle.get(type);
        //はたなら起動
        if (type == 8){
            thread.start();
        }
    }

    @Override
    public void run() {
        while (true) {
            //判断是否达到旗子位置
            if (bg.isReach()) {
                if (y < 374) {
                    this.y += 5;
                } else {
                    bg.setBase(true);
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public BackGround getBg() {
        return bg;
    }

    public BufferedImage getShow() {
        return show;
    }
}
