/*  Shriya Kagolanu
 * 11th grade Paley
 *  Stanford Nifty Fractal Sound
 *  http://nifty.stanford.edu/2017/hug-fractal-sound/
 */

public class StrangeBitwiseGenerator implements Generator {
    private int state;
    private int period;
    private double xmin;
    private double xmax;

    //normalize bwetween -1 and 1   = (2(x- minx )/(maxx-minx)) -1


    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        state = 0;
        xmin = 0;
        xmax = period;

    }

    public double next() {

        state++;

        int weirdState = state & (state >>> 3) % period;

        double val = 0;

        val = (2 * ((weirdState - xmin) / (xmax - xmin))) - 1;

        return val;
    }
}

