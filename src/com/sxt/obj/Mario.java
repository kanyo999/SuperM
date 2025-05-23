package com.sxt.obj;

import com.sxt.util.BackGround;
import com.sxt.util.StaticValue;

import java.awt.image.BufferedImage;

public class Mario implements Runnable{
    //座標
    private int x;
    private int y;
    //ステータス
    private String status;
    //現在対応する画像
    private BufferedImage show = null;
    //障害物情報
    private BackGround backGround = new BackGround();
    //マリオ動き
    private Thread thread = null;
    //移動速度
    private int xSpeed;
    //ジャンプ速度
    private int ySpeed;

    //索引定義
    private int index;
    //マリオ向き
    private  boolean face_to = true;
    //マリオ上昇時間
    private int upTime = 0;
    //城に到着判断
    private boolean isOk;
    //マリオ死亡判断記録
    private boolean isDeath = false;
    //ポイント
    private int score = 0;

    public Mario() {
    }

    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
        show = StaticValue.stand_R;
        this.status = "stand--right";
        thread = new Thread(this);
        thread.start();
    }

    //左に移動
    public void leftMove (){
        //速度変動
        xSpeed = - 5;
        //フラグに接触判断
        if (backGround.isReach()){
            xSpeed = 0;
        }
        //空中判断
        if (status.indexOf("jump") != -1){
            status = "jump--left";
        }else {
            status = "move--left";
        }
    }

    //右に移動
    public void rightMove (){
        //速度変動
        xSpeed =  5;
        //フラグに接触判断
        if (backGround.isReach()){
            xSpeed = 0;
        }
        //空中判断
        if (status.indexOf("jump") != -1){
            status = "jump--right";
        }else {
            status = "move--right";
        }
    }
    //左向き停止
    public void leftStop (){
        //速度変動
        xSpeed = 0;
        //空中判断
        if (status.indexOf("jump") != -1){
            status = "jump--left";
        }else {
            status = "stop--left";
        }
    }

    //右向き停止
    public void rightStop (){
        //速度変動
        xSpeed = 0;
        //空中判断
        if (status.indexOf("jump") != -1){
            status = "jump--right";
        }else {
            status = "stop--right";
        }
    }

    //マリオジャンプ方法
    public void jump (){
        if (status.indexOf("jump") == -1){
            if (status.indexOf("left") != -1){
                status = "jump--left";
            }else{
                status = "jump--right";
            }
            ySpeed = -10;
            upTime = 7;
        }
        //フラグに接触判断
        if (backGround.isReach()){
            xSpeed = 0;
        }

    }

    //マリオ落下
    public void fall(){
        if (status.indexOf("left") != -1){
            status = "jump--left";
        }else{
            status = "jump--right";
        }
        ySpeed = 10;
    }
    //マリオ死亡
    public void death(){
        isDeath = true;
    }

    @Override
    public void run() {
        while (true) {
            //マリオ障害物の上にいるか
            boolean onObstacle = false;
            //右に行けるか
            boolean canRight = true;
            //左に行けるか
            boolean canLeft = true;
            //マリオがフラグ位置に着く判断
            if (backGround.isFlag() && this.x >= 500){
                this.backGround.setReach(true);

                //旗落下判断
                if (this.backGround.isBase()){
                    status = "move--right";
                    if (x < 690){
                        x += 5;
                    }else{
                        isOk = true;
                    }
                }else {
                    if (y<395){
                        xSpeed = 0;
                        this.y += 5;
                        status = "jump--right";
                    }
                    if (y > 395){
                        this.y = 395;
                        status = "stop--right";
                    }
                }
            }
            //敵踏む倒す判断
            for (int i = 0;i < backGround.getEnemyList().size();i++){
                Enemy e = backGround.getEnemyList().get(i);
                //踏む死亡
                if (e.getY() == this.y + 20 && (e.getX() -25 <= this.x && e.getX() + 35 >= this.x)){
                    if (e.getType() == 1){
                        e.death();
                        score += 2;
                        upTime = 3;
                        ySpeed = -10;
                    }else if (e.getType() == 2){
                        death();
                    }
                }
                //接触死亡
                if ((e.getX() + 35 > this.x && e.getX()- 25 < this.x)&& (e.getY()+ 35 > this.y && e.getY() - 20 < this.y)){
                    death();
                }
            }

            //すべての障害物
            for (int i = 0; i < backGround.getObstacleList().size(); i++) {
                Obstacle ob = backGround.getObstacleList().get(i);
                //マリオは障害物の上にいるか
                if (ob.getY() == this.y + 25 && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) {
                    onObstacle = true;
                }
                //ジャンプの時レンガ破壊できるか
                if ((ob.getY() >= this.y - 30 && ob.getY() <= this.y - 20) && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)){
                    if (ob.getType() == 0){
                        backGround.getObstacleList().remove(ob);
                        score += 1;
                    }
                    upTime = 0;
                }

                //右に行けるか
                if (ob.getX() == this.x + 25 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) {
                    canRight = false;
                }
                //左に行けるか
                if (ob.getX() == this.x - 30 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) {
                    canLeft = false;
                }

            }
            //マリオジャンプ操作
            if (onObstacle && upTime == 0) {
                if (status.indexOf("left") != -1) {
                    if (xSpeed != 0) {
                        status = "move--left";
                    } else {
                        status = "stop--left";
                    }
                } else {
                    if (xSpeed != 0) {
                        status = "move--right";
                    } else {
                        status = "stop--right";
                    }

                }
            }else {
                if (upTime != 0){
                    upTime--;
                }else{
                    fall();
                }
                y += ySpeed;
            }
            if (canRight && xSpeed > 0){
                x += xSpeed;
                //右端位置判断
                if (x<0){
                    x = 0;
                }
            }

            if (canLeft && xSpeed < 0){
                x += xSpeed;
                //左端位置判断
                if (x<0){
                    x = 0;
                }
            }
            //移動状態判断
            if (status.contains("move")){
                index = index == 0 ? 1 :0;
            }
            //左移動判断
            if ("move--left".equals(status)){
                show = StaticValue.run_L.get(index);
            }
            //右移動判断
            if ("move--right".equals(status)){
                show = StaticValue.run_R.get(index);
            }
            //左停止判断
            if ("stop--left".equals(status)){
                show = StaticValue.stand_L;
            }
            //右停止判断
            if ("stop--right".equals(status)){
                show = StaticValue.stand_R;
            }
            //左にジャンプ判断
            if ("jump--left".equals(status)){
                show = StaticValue.jump_L;
            }
            //右にジャンプ判断
            if ("jump--right".equals(status)){
                show = StaticValue.jump_R;
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

    public int getY() {
        return y;
    }

    public String getStatus() {
        return status;
    }

    public BufferedImage getShow() {
        return show;
    }

    public BackGround getBackground() {
        return backGround;
    }

    public Thread getThread() {
        return thread;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isFace_to() {
        return face_to;
    }

    public void setFace_to(boolean face_to) {
        this.face_to = face_to;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }

    public void setBackGround(BackGround background) {
        this.backGround = background;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public int getUpTime() {
        return upTime;
    }

    public void setUpTime(int upTime) {
        this.upTime = upTime;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public void setDeath(boolean death) {
        isDeath = death;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

