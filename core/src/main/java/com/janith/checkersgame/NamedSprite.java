package com.janith.checkersgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class NamedSprite extends Sprite {

    private String name;

    public NamedSprite(Texture texture) {
        super(texture);
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

