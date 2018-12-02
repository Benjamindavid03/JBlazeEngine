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
