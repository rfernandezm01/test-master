package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Alien {
    Texture texture;
    float x, y, w, h, vx, vy;
    Temporizador cambioVelocidad;

    Alien(){
        texture = new Texture("alien.png");
        x = MyGdxGame.random.nextInt(1920);
        y = 1000;
        w = 80;
        h = 100;
        vx = 2;
        vy = 1;
        cambioVelocidad = new Temporizador(30);
    }

    void render(SpriteBatch batch){
        batch.draw(texture, x, y, w, h);
    }

    public void update() {
        y += vy;
        x += vx;

        if(cambioVelocidad.suena()){
            vy = MyGdxGame.random.nextInt(6)-3;
            vx = MyGdxGame.random.nextInt(6)-3;
        }
        if (x < 0) x = 1920;
        if (y < 0) y = 1000;
        if (x > 1920) x = 0;
        if (y > 1000) y = 1000;
    }
}