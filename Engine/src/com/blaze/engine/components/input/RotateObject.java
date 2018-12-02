package com.blaze.engine.components.input;

import com.blaze.engine.components.GameComponent;
import com.blaze.engine.core.Input;
import com.blaze.engine.core.Math.Quaternion;
import com.blaze.engine.core.Math.Vector3f;
import com.blaze.engine.core.ObjectType;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Ben
 */
public class RotateObject extends GameComponent {

    public ObjectType type = ObjectType.GAMEOBJECT;
    private float m_speed;
    private int m_forwardKey;
    private int m_backKey;
    private int m_leftKey;
    private int m_rightKey;

    public RotateObject(float speed) {
        this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
    }

    public RotateObject(float speed, int forwardKey, int backKey, int leftKey, int rightKey) {
        this.m_speed = speed;
        this.m_forwardKey = forwardKey;
        this.m_backKey = backKey;
        this.m_leftKey = leftKey;
        this.m_rightKey = rightKey;
    }

    @Override
    public void Update(float delta) {

    }

    @Override
    public void Input(float delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            Quaternion rotation = new Quaternion(this.GetTransform().GetPosition().Add(new Vector3f(delta, 0, 0)), delta);
            GetTransform().SetRotation(rotation);
        }

        float movAmt = m_speed * delta;

        if (Input.GetKey(m_forwardKey)) {
            Move(GetTransform().GetRotation().GetForward(), movAmt);
        }
        if (Input.GetKey(m_backKey)) {
            Move(GetTransform().GetRotation().GetForward(), -movAmt);
        }
        if (Input.GetKey(m_leftKey)) {
            Move(GetTransform().GetRotation().GetLeft(), movAmt);
        }
        if (Input.GetKey(m_rightKey)) {
            Move(GetTransform().GetRotation().GetRight(), movAmt);
        }
    }

    private void Move(Vector3f dir, float amt) {

        //GetTransform().SetPosition(GetTransform().GetPosition().Add(dir.Mul(amt)));
    }
}
