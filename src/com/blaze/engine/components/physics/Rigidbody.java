/*
 * Copyright (C) 2018 Benjamin Fredrick David. H
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blaze.engine.components.physics;

import com.blaze.engine.components.GameComponent;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

public class Rigidbody extends GameComponent {

    public float Mass = 50f;
    public MotionState state;
    //public CollisionShape shape;
    public com.bulletphysics.collision.shapes.BoxShape shape;

    private Transform phyTransform;
    public com.bulletphysics.dynamics.RigidBody rigidBody;

    public Rigidbody() {
        //shape = new BoxShape(new Vector3f(this.GetTransform().GetPosition()));
        state = new MotionState() {
            @Override
            public Transform getWorldTransform(Transform t) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setWorldTransform(Transform t) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        rigidBody = new RigidBody(Mass, state, shape);
        this.rigidBody.setMotionState(state);
        rigidBody.activate();
    }

    @Override
    public void Update(float delta) {
        super.Update(delta); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Input(float delta) {
        super.Input(delta); //To change body of generated methods, choose Tools | Templates.
    }

}
