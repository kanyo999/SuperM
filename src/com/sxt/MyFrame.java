package com.sxt;

import com.sxt.obj.Enemy;
import com.sxt.obj.Mario;
import com.sxt.obj.Obstacle;
import com.sxt.util.BackGround;
import com.sxt.util.Music;
import com.sxt.util.StaticValue;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.midi.ControllerEventListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

public  class MyFrame extends JFrame implements KeyListener,Runnable {

    //すべての背景をセーブ
    private List<BackGround> allBg = new ArrayList<>();
    //現在の背景をセーブ
    private BackGround nowBg = new BackGround();
    //ダブルセーブ
    private Image offScreenImage = null;
    //マリオ対象
    private Mario mario = new Mario();

    //マリオ移動実現
    private Thread thread = new Thread(this);

    public MyFrame () {
        //ウインドウズの大きさ800*600
        this.setSize(800, 600);
        //中央にする
        this.setLocationRelativeTo(null);
        //ウインドウズ可視性
        this.setVisible(true);
        //ウインドウズ開と閉を設置
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //ウインドウズサイズ変動不可
        this.setResizable(false);
        //対象ウインドウズにキーロガー
        this.addKeyListener(this);
        //ウインドウズ名前
        this.setTitle("SuperM");

        //すべての画像を初期化
        StaticValue.init();
        //マリオ初期化
        mario = new Mario(10,355);
        //すべてのフィールド作成
        for(int i = 1;i<=3;i++){
            allBg.add(new BackGround(i, i == 3 ? true : false));

        }
        //最初のフィールドは現在のフィールド
        nowBg = allBg.get(0);
        mario.setBackGround(nowBg);
        //画像処理
        repaint();
        thread.start();

        try {
            new Music();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(800,600);
        }

        Graphics graphics = offScreenImage.getGraphics();
        graphics.fillRect(0,0,800,600);

        //背景処理
        graphics.drawImage(nowBg.getBgImage(),0,0,this);

        //敵作成
        for (Enemy e : nowBg.getEnemyList()){
            graphics.drawImage(e.getShow(),e.getX(),e.getY(),this);
        }

        //障害物を作成
        for (Obstacle ob : nowBg.getObstacleList()){
            graphics.drawImage(ob.getShow(),ob.getX(),ob.getY(),this);
        }

        //城作成
        graphics.drawImage(nowBg.getTower(),620,270,this);
        //フラグ作成
        graphics.drawImage(nowBg.getGan(),500,220,this);

        //マリオ作成
        graphics.drawImage(mario.getShow(),mario.getX(),mario.getY(),this);

        //ポイント作成
        Color c = graphics.getColor();
        graphics.setColor(Color.ORANGE);
        graphics.setFont(new Font("<UNK>",Font.BOLD,30));
        graphics.drawString("現在のスコア:" + mario.getScore(),300,100);
        graphics.setColor(c);

        //背景をウインドウズに導入
        g.drawImage(offScreenImage,0,0,this);

    }

    public static void main (String[] args) {
        MyFrame myFrame = new MyFrame();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void run() {
        while(true){
            repaint();
            try {
                Thread.sleep(50);
                //マリオが次のレベルに行く判断
                if (mario.getX() >= 775){
                    nowBg = allBg.get(nowBg.getSort());
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(395);
                }

                //マリオ死亡判断
                if (mario.isDeath()){
                    JOptionPane.showMessageDialog(this,"(# ﾟДﾟ)");
                    System.exit(0);
                }

                //ゲームオーバー判断
                if (mario.isOk()){
                    JOptionPane.showMessageDialog(this,"(人''▽｀)ありがとうございます☆");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //キー圧下
    @Override
    public void keyPressed(KeyEvent e) {
        //右移動
        if (e.getKeyCode() == 39){
            mario.rightMove();
        }
        //左移動
        if (e.getKeyCode() == 37){
            mario.leftMove();
        }
        //ジャンプ
        if (e.getKeyCode() == 38){
            mario.jump();
        }
    }

    //キーから手放す
    @Override
    public void keyReleased(KeyEvent e) {
        //左停止
        if (e.getKeyCode() == 37){
            mario.leftStop();
        }
        //右停止
        if (e.getKeyCode() == 39){
            mario.rightStop();
        }
    }
}