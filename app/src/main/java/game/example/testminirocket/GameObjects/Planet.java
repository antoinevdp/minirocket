package game.example.testminirocket.GameObjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import game.example.testminirocket.graphics.Animator;
import game.example.testminirocket.graphics.ColorSelect;
import game.example.testminirocket.graphics.Sprite;

public class Planet extends GameObject{
    private static final double SPEED_PIXELS_PER_SECOND = 100;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SPAWN_PER_MINUTE = 2.5;
    private static final double SPAWN_PER_SECOND = SPAWN_PER_MINUTE / 60.0;
    private static final double UPDATE_PER_SPAWN = GameLoop.MAX_UPS / SPAWN_PER_SECOND;
    private static double updateUntilNextSpawn = UPDATE_PER_SPAWN;

    public int id; // id unique de la planète
    public double radius; // Rayon de la planète
    private Context context;
    private String infos; // infos sur la planète
    private Paint paint;
    private int randomAndroidColor;

    private Animator animator;
    private PlanetState planetState;

    private ArrayList<Traveller> list_travellers = new ArrayList<Traveller>(); // Liste des voyageurs sur la planète
    private ArrayList<Planet> list_of_arr_planets = new ArrayList<Planet>(); // Liste des planetes sur la planète


    public Trajectory my_trajectory; // La trajectoire associée à cette planète
    public Planet linkedPlanet = null;// La planète d'arr associée à cette planète

    private float currentStrokeWidth = 3f;
    public boolean canAnimate = true;
    public boolean up = true;



    // Constructeur
    public Planet(Context context, int id, double coordX, double coordY, double radius, int randomAndroidColor, String infos, Trajectory my_trajectory, Animator animator){
        super(coordX, coordY);
        this.id = id;
        this.coordX = coordX;
        this.coordY = coordY;
        this.radius = radius;
        this.my_trajectory = my_trajectory;
        this.context = context;
        this.infos = infos;
        this.randomAndroidColor = randomAndroidColor;
        this.animator = animator;
        this.planetState = new PlanetState(this);

        this.list_travellers = new ArrayList<>();
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(currentStrokeWidth);
        //.setPaint(new GradientPaint(0,0,getBackground(),width,0,controlColor));
        this.paint.setColor(this.randomAndroidColor);

    }
    // Check if readyToSpawn
    public static boolean readyToSpawn() {
        if(updateUntilNextSpawn <= 0){
            updateUntilNextSpawn += UPDATE_PER_SPAWN;
            return true;
        }else {
            updateUntilNextSpawn --;
            return false;
        }
    }

    // Affichage
    public void draw(Canvas canvas) {
        float increment = 0.2f;
        float maxValue = 14f;
        float minValue = 3f;

        if (up && currentStrokeWidth <= maxValue) {
            currentStrokeWidth += increment;
            if (currentStrokeWidth >= maxValue) {
                up = false;
            }
        } else {
            up = false;
            currentStrokeWidth -= increment;
            if (currentStrokeWidth <= minValue) {
                up = true;
            }
        }
        //Bitmap img = BitmapFactory.decodeResource(context.getResources(), R.mipmap.galaxie);
        //canvas.drawBitmap(img, null, new RectF(50, 50, 200, 200), null);
        canvas.drawCircle((float)coordX, (float)coordY, (float)radius, paint);
        this.my_trajectory.draw(canvas);

        this.paint.setStrokeWidth(currentStrokeWidth);
        animator.drawPlanet(canvas, this);

        for (int i = 0; i < list_travellers.size(); i++) {
            list_travellers.get(i).draw(canvas);
        }
    }

    // Pour set la trajectoire de sortie de cette planète
    public void setMyTrajectory(Trajectory my_trajectory){
        this.my_trajectory = my_trajectory;
    }
    // Pour unset la trajectoire de sortie de cette planète
    public void unsetMyTrajectory(){
        this.my_trajectory = new Trajectory(-1, 0,0,0,0, null, null, 0);
    }
    // Pour set la planète associée de cette planète
    public void setLinkedPlanet(Planet linkedPlanet){
        this.linkedPlanet = linkedPlanet;
    }
    // Pour unset la planète associée de cette planète
    public void unsetLinkedPlanet(){
        this.linkedPlanet = null;
    }
    // Pour savoir si cette planète est associée à une planète
    public boolean isLinkedWithPlanet(){
        return this.linkedPlanet != null;
    }
    public ArrayList<Planet> getListOfArrPlanets(){
        return this.list_of_arr_planets;
    }
    //update
    public int getRandomAndroidColor(){
        return this.randomAndroidColor;
    }

    public void update() {
        planetState.update();
    }

    public PlanetState getPlanetState(){
        return planetState;
    }

    public void addTraveller(Traveller traveller_test) {
        this.list_travellers.add(traveller_test);
    }
    public void removeTraveller(Traveller traveller_test) {
        this.list_travellers.remove(traveller_test);
    }

    public ArrayList<Traveller> getList_travellers(){
        return this.list_travellers;
    }
    public boolean isEndingPlanetTraj(){
        return this.isLinkedWithPlanet() && this.getListOfArrPlanets().size() == 0;
    }
    public boolean isEndingPlanetArr(){
        return this.getListOfArrPlanets().size() != 0 && !this.isLinkedWithPlanet();
    }
}
