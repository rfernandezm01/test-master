package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	static Random random = new Random();
	SpriteBatch batch;
	BitmapFont bitmapFont;
	Fondo fondo;
	Nave nave;
	List<Alien> aliens = new ArrayList<>();
	List<BolaF> bolas = new ArrayList<>();
	List<Bala> balasAEliminar = new ArrayList<>();
	List<Alien> aliensAEliminar = new ArrayList<>();
	List<Tecnica> tecnicaAEliminar = new ArrayList<>();
	List<TecnicaW> tecnicaWAEliminar = new ArrayList<>();
	List<BolaF> bolaFeliminar = new ArrayList<>();
	Temporizador temporizadorNuevoAlien;
	private ScoreBoard scoreboard;
	private boolean gameover;


	@Override
	public void create () {
		batch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		bitmapFont.setColor(Color.WHITE);
		fondo = new Fondo();
		nave = new Nave();
		aliens = new ArrayList<>();
		scoreboard = new ScoreBoard();
        bolas = new ArrayList<>();

		aliens.add(new Alien());
        bolas.add(new BolaF());

		temporizadorNuevoAlien = new Temporizador(120);
	}
	void update() {
		Temporizador.tiempoJuego += 1;

		if (temporizadorNuevoAlien.suena()) {
			aliens.add(new Alien());
			bolas.add(new BolaF());
		}
		if (!gameover) {
			nave.update();
		}
		for (Alien alien : aliens) alien.update();
		for (BolaF bola : bolas) bola.update();

		balasAEliminar.clear();
		aliensAEliminar.clear();
		bolaFeliminar.clear();
		for (Alien alien : aliens) {
			for (Bala bala : nave.balas) {
				if (solapan(bala.x, bala.y, bala.w, bala.h, alien.x, alien.y, alien.w, alien.h)) {
					balasAEliminar.add(bala);
					aliensAEliminar.add(alien);
					nave.puntos++;
					break;
				}
			}
			for (Tecnica tecnica : nave.stecnica) {
				if (solapan(tecnica.x, tecnica.y, tecnica.w, tecnica.h, alien.x, alien.y, alien.w, alien.h)) {
					tecnicaAEliminar.add(tecnica);
					aliensAEliminar.add(alien);
					nave.puntos++;
					break;
				}
			}
			for (TecnicaW tecnicaW : nave.stecnicaW) {
				if (solapan(tecnicaW.x, tecnicaW.y, tecnicaW.w, tecnicaW.h, alien.x, alien.y, alien.w, alien.h)) {
					tecnicaWAEliminar.add(tecnicaW);
					aliensAEliminar.add(alien);
					nave.puntos++;
					break;
				}
			}
			if (!gameover && !nave.muerta && solapan(alien.x, alien.y, alien.w, alien.h, nave.x, nave.y, nave.w, nave.h)) {
				nave.morir();
				if (nave.vidas == 0) {
					gameover = true;
					scoreboard.guardarPuntuacion(nave.puntos);
				}
			}
			if (!nave.muerta && solapan(alien.x, alien.y, alien.w, alien.h, nave.x, nave.y, nave.w, nave.h)) {
				nave.vidas--;
				nave.muerta = true;
				nave.respawn.activar();
			}
		}
		for (Bala bala : balasAEliminar) nave.balas.remove(bala);
		for (Alien alien : aliensAEliminar) aliens.remove(alien);
		for (Tecnica tecnica : tecnicaAEliminar) nave.stecnica.remove(tecnica);

		for (BolaF bolaF : bolas) {
			for (Tecnica tecnica : nave.stecnica) {
				if (solapan(tecnica.x, tecnica.y, tecnica.w, tecnica.h, bolaF.x, bolaF.y, bolaF.w, bolaF.h)) {
					tecnicaAEliminar.add(tecnica);
					bolaFeliminar.add(bolaF);
					nave.puntos++;
					break;
				}
			}
			for (TecnicaW tecnicaW : nave.stecnicaW) {
				if (solapan(tecnicaW.x, tecnicaW.y, tecnicaW.w, tecnicaW.h, bolaF.x, bolaF.y, bolaF.w, bolaF.h)) {
					tecnicaWAEliminar.add(tecnicaW);
					bolaFeliminar.add(bolaF);
					nave.puntos++;
					break;
				}
			}
			if (!gameover && !nave.muerta && solapan(bolaF.x, bolaF.y, bolaF.w, bolaF.h, nave.x, nave.y, nave.w, nave.h)) {
				nave.morir();
				if (nave.vidas == 0) {
					gameover = true;
					scoreboard.guardarPuntuacion(nave.puntos);
				}
			}
			if (!nave.muerta && solapan(bolaF.x, bolaF.y, bolaF.w, bolaF.h, nave.x, nave.y, nave.w, nave.h)) {
				nave.vidas--;
				nave.muerta = true;
				nave.respawn.activar();
			}
			for (BolaF bola : bolaFeliminar) bolas.remove(bola);
			for (Tecnica tecnica : tecnicaAEliminar) nave.stecnica.remove(tecnica);
		}
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,0 );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update();


		batch.begin();
		fondo.render(batch);
		nave.render(batch);
		for(Alien alien:aliens) alien.render(batch);
		for(BolaF bola : bolas) bola.render(batch);
		bitmapFont.draw(batch, "VIDAS: "+nave.vidas, 1850, 1050);
		bitmapFont.draw(batch, "PUNTOS: "+ 100 * nave.puntos, 10, 1050);
		if (gameover){

			scoreboard.render(batch, bitmapFont);
		}
		batch.end();
	}

	boolean solapan(float x, float y, float w, float h, float x2, float y2, float w2, float h2){
		return !(x > x2 + w2) && !(x + w < x2) && !(y > y2 + h2) && !(y + h < y2);
	}
}

/*
create();
while(true) render();
 */