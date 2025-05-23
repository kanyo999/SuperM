package com.sxt.util;

import com.sxt.obj.Enemy;
import com.sxt.obj.Obstacle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
    //現在フィールドに表示する画像
    private BufferedImage bgImage = null;
    //現在のフィールド順番を記録
    private int sort;
    //ラストフィールド判断
    private boolean flag;

    //すべての障害物をセーブ
    private List<Obstacle> obstacleList = new ArrayList<>();
    //すべての敵
    private List<Enemy> enemyList = new ArrayList<>();
    //フラグセーブ
    private BufferedImage gan = null;
    //城をセーブ
    private BufferedImage tower = null;

    //フラグ位置に着く判断
    private boolean isReach = false;
    //旗が落ちる判断
    private boolean isBase = false;

    public BackGround() {

    }

    public BackGround(int sort, boolean flag) {
        this.sort = sort;
        this.flag = flag;
        if (flag){
            bgImage = StaticValue.bg2;
        }else{
            bgImage = StaticValue.bg;
        }

        //レベル1判断
        if (sort == 1){
            //床作成、上type = 2; 下type = 1;
            for (int i = 0;i <= 27; i++){
                obstacleList.add(new Obstacle(i * 30,420,2,this));
            }
            for (int j = 0; j <= 120; j += 30){
                for (int i = 0; i < 27; i++){
                    obstacleList.add(new Obstacle(i * 30,570 - j,1,this));
                }
            }
            //レンガA作成
            for (int i = 120; i <=150; i += 30){
                obstacleList.add(new Obstacle(i,300,7,this));
            }

            //レンガB-F作成
            for (int i = 300;i <= 570; i +=30){
                if (i == 360 || i == 390 || i == 480 || i == 510 || i == 540){
                    obstacleList.add(new Obstacle(i,300,7,this));
                }else{
                    obstacleList.add(new Obstacle(i,300,0,this));
                }
            }

            //レンガG作成
            for (int i = 420; i <= 450; i += 30){
                obstacleList.add(new Obstacle(i,240,7,this));
            }

            //パイプ作成
            for (int i = 360; i <= 600; i += 25){
                if (i == 360){
                    obstacleList.add(new Obstacle(620,i,3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(620,i,5,this));
                    obstacleList.add(new Obstacle(645,i,6,this));
                }
            }
            //キノコ敵作成
            enemyList.add(new Enemy(580,385,1,true,this));
            //花敵作成
            enemyList.add(new Enemy(635,420,2,true,this,328,428));


        }

        //レベル2判断
        if (sort == 2){
            //床作成、上type = 2; 下type = 1;
            for (int i = 0;i <= 27; i++){
                obstacleList.add(new Obstacle(i * 30,420,2,this));
            }
            for (int j = 0; j <= 120; j += 30){
                for (int i = 0; i < 27; i++){
                    obstacleList.add(new Obstacle(i * 30,570 - j,1,this));
                }
            }
            //パイプ1作成
            for (int i = 360; i <= 600; i += 25){
                if (i == 360){
                    obstacleList.add(new Obstacle(60,i,3,this));
                    obstacleList.add(new Obstacle(85,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(60,i,5,this));
                    obstacleList.add(new Obstacle(85,i,6,this));
                }
            }
            //パイプ2作成
            for (int i = 330; i <= 600; i += 25){
                if (i == 330){
                    obstacleList.add(new Obstacle(620,i,3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(620,i,5,this));
                    obstacleList.add(new Obstacle(645,i,6,this));
                }
            }

            //レンガc作成
            obstacleList.add(new Obstacle(300,330,0,this));

            //レンガb,e,g作成
            for (int i = 270; i <=330; i += 30){
                if (i == 270 || i == 330){
                    obstacleList.add(new Obstacle(i,360,0,this));
                }else {
                    obstacleList.add(new Obstacle(i,360,7,this));
                }
            }

            //レンガA,D,F,H,I作成
            for (int i = 240; i <=360; i += 30){
                if (i == 240 || i == 360){
                    obstacleList.add(new Obstacle(i,390,0,this));
                }else {
                    obstacleList.add(new Obstacle(i,390,7,this));
                }
            }

            //レンガ1作成
            obstacleList.add(new Obstacle(240,300,0,this));

            //スペースレンガ1-4作成
            for (int i = 360; i <= 540; i +=60){
                obstacleList.add(new Obstacle(i,270,7,this));
            }

            //花敵1
            enemyList.add(new Enemy(75,420,2,true,this,328,428));
            //花敵2
            enemyList.add(new Enemy(635,420,2,true,this,298,388));
            //キノコ敵1
            enemyList.add(new Enemy(200,385,1,true,this));
            //キノコ敵2
            enemyList.add(new Enemy(500,385,1,true,this));
        }

        //レベル3判断
        if (sort == 3){
            //床作成、上type = 2; 下type = 1;
            for (int i = 0;i <= 27; i++){
                obstacleList.add(new Obstacle(i * 30,420,2,this));
            }
            for (int j = 0; j <= 120; j += 30){
                for (int i = 0; i < 27; i++){
                    obstacleList.add(new Obstacle(i * 30,570 - j,1,this));
                }
            }
            //レンガA-0作成
            int temp = 290;
            for (int i = 390;i >= 270;i -= 30){
                for (int j = temp;j <= 410; j += 30){
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp += 30;
            }

            //レンガp-r作成
            temp = 60;
            for (int i = 390;i >= 360; i -= 30){
                for (int j = temp;j <= 90; j += 30){
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp += 30;
            }
            //キノコ敵
            enemyList.add(new Enemy(150,385,1,true,this));

            //フラグ作成
            gan = StaticValue.gan;
            //城作成
            tower = StaticValue.tower;
            //はた付け
            obstacleList.add(new Obstacle(515,220,8,this));

        }

    }

    public BufferedImage getBgImage() {
        return bgImage;
    }

    public int getSort() {
        return sort;
    }

    public boolean isFlag() {
        return flag;
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public BufferedImage getGan() {
        return gan;
    }

    public BufferedImage getTower() {
        return tower;
    }

    public boolean isReach() {
        return isReach;
    }

    public void setReach(boolean reach) {
        isReach = reach;
    }

    public boolean isBase() {
        return isBase;
    }

    public void setBase(boolean base) {
        isBase = base;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(List<Enemy> enemyList) {
        this.enemyList = enemyList;
    }
}