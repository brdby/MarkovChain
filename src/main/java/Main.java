import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        double[] firstPosition = {1, 0, 0, 0, 0};
        double[][] transitionMatrix = {
                {0, 0.50, 0, 0, 0.50},
                {0, 0, 0, 1, 0},
                {0, 0, 0.75, 0, 0.25},
                {0, 0, 0.40, 0, 0.60},
                {0, 1, 0, 0, 0}
        };
        int[] values = {0, 0, 1, 1, 0};

        ChainEmulator chain = new ChainEmulator(firstPosition, transitionMatrix, values);
        System.out.println(chain.emulateChain(30, true));

    }
}
