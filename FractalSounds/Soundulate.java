import java.util.ArrayList;

public class Soundulate {

    public static void main(String[] args) {

        Generator g1 = new AcceleratingSawToothGenerator(512, 1.1);
        Generator g2 = new SawToothGenerator(500);

        ArrayList<Generator> generators = new ArrayList<Generator>();
        generators.add(g1);
        generators.add(g2);
        MultiGenerator mg = new MultiGenerator(generators);

        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(mg);
        gav.drawAndPlay(5000, 10000000);
    }
}
