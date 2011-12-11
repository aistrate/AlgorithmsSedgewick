 /*************************************************************************
 *  Compilation:  javac Particle.java
 *      
 *  A particle moving in the unit box with a given position, velocity,
 *  radius, and mass.
 *
 *************************************************************************/

import java.awt.Color;

public class Particle {
    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private double rx, ry;    // position
    private double vx, vy;    // velocity
    private double radius;    // radius
    private double mass;      // mass
    private Color color;      // color
    private int count;        // number of collisions so far


    // create a new particle with given parameters        
    public Particle(double rx, double ry, double vx, double vy, double radius, double mass, Color color) {
        this.vx = vx;
        this.vy = vy;
        this.rx = rx;
        this.ry = ry;
        this.radius = radius;
        this.mass   = mass;
        this.color  = color;
    }
         
    // create a random particle in the unit box (overlaps not checked)
    public Particle() {
        rx     = Math.random();
        ry     = Math.random();
        vx     = 0.01 * (Math.random() - 0.5);
        vy     = 0.01 * (Math.random() - 0.5);
        radius = 0.01;
        mass   = 0.5;
        color  = Color.BLACK;
    }

    // updates position
    public void move(double dt) {
        rx += vx * dt;
        ry += vy * dt;
    }

    // draw the particle
    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(rx, ry, radius);
    }

    // return the number of collisions involving this particle
    public int count() { return count; }
        
  
    // how long into future until collision between this particle a and b?
    public double timeToHit(Particle b) {
        Particle a = this;
        if (a == b) return INFINITY;
        double dx  = b.rx - a.rx;
        double dy  = b.ry - a.ry;
        double dvx = b.vx - a.vx;
        double dvy = b.vy - a.vy;
        double dvdr = dx*dvx + dy*dvy;
        if (dvdr > 0) return INFINITY;
        double dvdv = dvx*dvx + dvy*dvy;
        double drdr = dx*dx + dy*dy;
        double sigma = a.radius + b.radius;
        double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
        // if (drdr < sigma*sigma) StdOut.println("overlapping particles");
        if (d < 0) return INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }

    // how long into future until this particle collides with a vertical wall?
    public double timeToHitVerticalWall() {
        if      (vx > 0) return (1.0 - rx - radius) / vx;
        else if (vx < 0) return (radius - rx) / vx;  
        else             return INFINITY;
    }

    // how long into future until this particle collides with a horizontal wall?
    public double timeToHitHorizontalWall() {
        if      (vy > 0) return (1.0 - ry - radius) / vy;
        else if (vy < 0) return (radius - ry) / vy;
        else             return INFINITY;
    }

    // update velocities upon collision between this particle and that particle
    public void bounceOff(Particle that) {
        double dx  = that.rx - this.rx;
        double dy  = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;             // dv dot dr
        double dist = this.radius + that.radius;   // distance between particle centers at collison

        // normal force F, and in x and y directions
        double F = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
        double fx = F * dx / dist;
        double fy = F * dy / dist;

        // update velocities according to normal force
        this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        that.vx -= fx / that.mass;
        that.vy -= fy / that.mass;

        // update collision counts
        this.count++;
        that.count++;
    }

    // update velocity of this particle upon collision with a vertical wall
    public void bounceOffVerticalWall() {
        vx = -vx;
        count++;
    }

    // update velocity of this particle upon collision with a horizontal wall
    public void bounceOffHorizontalWall() {
        vy = -vy;
        count++;
    }

    // return kinetic energy associated with this particle
    public double kineticEnergy() { return 0.5 * mass * (vx*vx + vy*vy); }
}
