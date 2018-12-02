package com.blaze.engine.components.environments;

import com.blaze.engine.components.GameComponent;
import com.blaze.engine.core.Math.Vector3f;

/**
 *
 * @author Ben
 */
public class Fog extends GameComponent {

    private boolean active;

    private Vector3f colour;

    private float density;

    public static Fog NOFOG = new Fog();

    public Fog() {
        active = false;
        this.colour = new Vector3f(0, 0, 0);
        this.density = 0;
    }

    public Fog(boolean active, Vector3f colour, float density) {
        this.colour = colour;
        this.density = density;
        this.active = active;
    }
}
