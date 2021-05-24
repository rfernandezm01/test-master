package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Nave {
    Texture texture;
    float x, y, w, h, v;
    List<Bala> balas;
    List<Tecnica> stecnica;
    List<TecnicaW> stecnicaW;
    int vidas = 3;
    int puntos = 0;
    boolean muerta = false;

    Temporizador fireRate;
    Temporizador respawn;

    Nave(){
        texture =  new Texture("nave.png");
        x = 100;
        y = 100;
        w = 100;
        h = 100;
        v = 6;
        balas = new ArrayList<>();
        stecnica = new ArrayList<>();
        stecnicaW = new ArrayList<>();
        fireRate = new Temporizador(10);
        respawn = new Temporizador(120, false);
    }

    void render(SpriteBatch batch){
        batch.draw(texture, x, y, w, h);

        for (Bala bala: balas) {
            bala.render(batch);
        }
        for (Tecnica tecnica: stecnica) {
            tecnica.render(batch);
        }
        for (TecnicaW tecnicaW: stecnicaW) {
            tecnicaW.render(batch, 10 );
        }
    }

    void update() {
        for (Bala bala : balas) {
            bala.update();
        }
        for (Tecnica tecnica : stecnica) {
            tecnica.update();
        }
        for (TecnicaW tecnicaW : stecnicaW) {
            tecnicaW.update();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += v;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= v;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) y += v;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y -= v;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            balas.add(new Bala(x + w / 2, y + h));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            stecnica.add(new Tecnica(x + w / 2, y + h / 2));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            stecnicaW.add(new TecnicaW(x + w / 2, y + h / 2));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {

        }
        if (x < 0) x = 1920;
        if (y < 0) y = 1000;
        if (x > 1920) x = 0;
        if (y > 1000) y = 0;

        if (respawn.suena()) {
            muerta = false;
        }
        }
    public void morir() {
        vidas--;
        muerta = true;
        respawn.activar();
    }
}