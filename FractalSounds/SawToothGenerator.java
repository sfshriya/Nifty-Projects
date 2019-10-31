/*  Shriya Kagolanu
 * 11th grade Paley
 *  Stanford Nifty Fractal Sound
 *  http://nifty.stanford.edu/2017/hug-fractal-sound/
 */

public class SawToothGenerator implements Generator {
    private double state;
    private int period;
    private double xmin;
    private double xmax;

    //normalize bwetween -1 and 1 (2 (x- minx )/(maxx-minx)) -1


    public SawToothGenerator(int period) {
        this.period = period;
        state = 0;
        xmin = 0;
        xmax = period;

    }

    public double next() {

        state++;

        state = state % (period);

        double val = 0;

        val = (2 * ((state - xmin) / (xmax - xmin))) - 1;

        return val;
    }
}

