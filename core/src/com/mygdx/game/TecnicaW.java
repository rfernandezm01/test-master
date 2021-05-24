package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Tecnica {
    static Texture texture = new Texture("kame5.png");

    float x, y, w, h, v;


    Tecnica(float xNave, float yNave) {
        w =  100 ;
        h =  1000 ;
        x = xNave - w /  2 ;
        y = yNave;
        v = 7;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, w, h);
    }

    void update() {
        y += v;
    }
}