/*  Shriya Kagolanu
* 11th grade Paley
*  Stanford Nifty Fractal Sound
*  http://nifty.stanford.edu/2017/hug-fractal-sound/
 */


public class AcceleratingSawToothGenerator implements Generator {

    private double state;
    private int period;
    private double acce;
    private double xmin;
    private double xmax;
    private int cycle;

    public AcceleratingSawToothGenerator(int period, double acce) {
        this.period = period;
        state = 0.0;
        this.acce = acce;
        xmin = 0;
        xmax = period;
    }

    public double next() {

        state++;

        state = state % (period);

        xmax = period;

        double val = 0;

        val = (2 * ((state - xmin) / (xmax - xmin))) - 1;

        cycle++;
        //increase period only after complete cycle of period
        if (cycle > period) {
            period *= acce;
            cycle = 0;
        }

        return val;
    }
}
