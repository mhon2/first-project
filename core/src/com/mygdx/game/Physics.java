package com.mygdx.game;

import java.lang.*;
import com.badlogic.gdx.Gdx;

public class Physics{
final float gravity=9.8f;
final float meterPerPixel=2f;
final float mass=10f;
float Vy=0;
    float Vx=0;
    float x=0;//horizontal position
    float y=0;//vertical position
    long time0=0;//initial time
    long currTime=0;//current time
    boolean isJumping = false;


public Physics()
{
        time0=(System.currentTimeMillis());//resets time
        }
public float getHeight()//update every frame
        {
        currTime=(System.currentTimeMillis());
        //System.out.println("current time: " + currTime);
        //System.out.println("initial time: " + time0);
        y=(float)((Vy*getT())-(0.5f*Math.pow(getT(),2)));
        return y; //return vertical postition
        }
public long getT()
        {
        return (currTime-time0)/1000;
        }


        }
