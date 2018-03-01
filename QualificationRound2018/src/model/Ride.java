package model;

public class Ride {
    public int id;
    public int a, b; //start intersection
    public int x, y; //finish intersection
    public int s, f;

    public Ride(int id, int a, int b, int x, int y, int s, int f) {
        this.id = id;
        this.a = a;
        this.b = b;
        this.x = x;
        this.y = y;
        this.s = s;
        this.f = f;
    }

    public int dist() {
        return Math.abs(a - x) + Math.abs(b - y);
    }
}
