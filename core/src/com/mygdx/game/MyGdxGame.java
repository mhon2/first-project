package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;

public class MyGdxGame extends ApplicationAdapter {
    Physics physics;
    SpriteBatch batch;
    Texture img;
    Sprite player, lWall, rWall, floor, ceiling;
    Rectangle rectBody, lWallBorder, rWallBorder, floorBorder, ceilingBorder;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;


    @Override
    public void create( )
    {
        physics = new Physics();
        batch = new SpriteBatch();
        img = new Texture( "squareMan.jpg" );
        //player = new Sprite(img,400,200,70,90);
        player = new Sprite( img );
        player.setSize( 256, 256 );
        player.setPosition( 400,  0 );//100
        lWall = new Sprite( new Texture( "badlogic.jpg" ), 0, 0, 10, Gdx.graphics.getHeight() );
        rWall = new Sprite( new Texture( "badlogic.jpg" ), Gdx.graphics.getWidth() - 100, 0, 10, Gdx.graphics.getHeight() );
        floor = new Sprite( new Texture( "badlogic.jpg" ), 0, 0, Gdx.graphics.getWidth(), 10 );
        ceiling = new Sprite( new Texture( "badlogic.jpg" ), 0, 50, Gdx.graphics.getWidth(), 10 );
        ceiling.setPosition( 0, Gdx.graphics.getHeight() - 10 );

        System.out.println( Gdx.graphics.getWidth() );
        rWall.setColor( Color.RED );
        rectBody = new Rectangle( 50, 50, 256, 256 );
        lWallBorder = new Rectangle( 0, 0, lWall.getWidth(), lWall.getHeight() );
        rWallBorder = new Rectangle( 990, 0, rWall.getWidth(), rWall.getHeight() );
        floorBorder = new Rectangle( 0, 0, floor.getWidth(), floor.getHeight() );
        ceilingBorder = new Rectangle( 0, Gdx.graphics.getHeight() - 10, ceiling.getWidth(), ceiling.getHeight() );

        camera = new OrthographicCamera();
        camera.setToOrtho( false, 1000, 700 );
        shapeRenderer = new ShapeRenderer();
        //Gdx.graphics.setWidth(1000);
        //Gdx.graphics.setHeight(700);
    }

    @Override
    public void render( )
    {
        Gdx.gl.glClearColor( 0, 0.5f, 0.5f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        if ( !( rectBody.overlaps( lWallBorder ) ) )
        {
            if ( Gdx.input.isKeyPressed( Input.Keys.LEFT ) )
            {
                if ( Gdx.input.isKeyPressed( Input.Keys.CONTROL_LEFT ) )
                    player.translateX( -1f );
                else
                    player.translateX( -10.0f );
            }
        }
        if ( !( rectBody.overlaps( rWallBorder ) ) )
        {
            if ( Gdx.input.isKeyPressed( Input.Keys.RIGHT ) )
            {
                if ( Gdx.input.isKeyPressed( Input.Keys.CONTROL_RIGHT ) )
                    player.translateX( 1f );
                else
                    player.translateX( 10.0f );
            }
        }
        if ( Gdx.input.isKeyPressed( Input.Keys.SHIFT_LEFT ) )//shift + left key pressed
        {
            player.setPosition( 400, 100 );    //resets to og position

        }
        if ( Gdx.input.isKeyPressed( Input.Keys.SPACE ) )//up key pressed
        {
            float currHeight = physics.getHeight();
            physics.isJumping = true;
            physics.time0=(System.currentTimeMillis());
            physics.Vy = 3;
            physics.y = 0;
            player.setPosition(player.getX(), 0);
            //System.out.println( currHeight );
        }
        if(physics.isJumping)
        {
            float currHeight = physics.getHeight();
            float maxHeight = physics.Vy * 0.6f/2;
            player.setPosition(player.getX(), player.getY() + physics.getHeight());
            System.out.println( currHeight );
            System.out.println( "max height " + maxHeight );
            if(currHeight < (-maxHeight))
                physics.isJumping = false;
        }
        rectBody.setPosition( player.getX(), player.getY() );
        lWall.setPosition( 0, 0 );
        rWall.setPosition( 990, 0 );
        batch.setProjectionMatrix( camera.combined );
        camera.update();

        batch.begin();
        player.draw( batch );
        lWall.draw( batch );
        rWall.draw( batch );
        floor.draw( batch );
        ceiling.draw( batch );
        //batch.draw(player,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,200,200);
        batch.end();

        shapeRenderer.begin( ShapeType.Line );
        shapeRenderer.setColor( Color.BLACK );
        shapeRenderer.rect( rectBody.getX(), rectBody.getY(), rectBody.getWidth(), rectBody.getHeight() );
        shapeRenderer.end();
    }

    @Override
    public void dispose( )
    {
        batch.dispose();
        img.dispose();
    }
}
