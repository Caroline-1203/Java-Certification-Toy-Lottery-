import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class ToyLottery {

    private List<Integer> toyIds;
    private List<String> toyNames;
    private List<Integer> toyWeights;
    private PriorityQueue<Integer> lotteryQueue;

    public ToyLottery(String... toyData) {
        this.toyIds = new ArrayList<>();
        this.toyNames = new ArrayList<>();
        this.toyWeights = new ArrayList<>();
        this.lotteryQueue = new PriorityQueue<>();

        for (String data : toyData) {
            String[] parts = data.split(",");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid toy data format: " + data);
            }
            try {
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                int weight = Integer.parseInt(parts[2].trim());

                toyIds.add(id);
                toyNames.add(name);
                toyWeights.add(weight);
                // Добавляем в очередь несколько раз, исходя из веса
                for (int i = 0; i < weight; i++) {
                    lotteryQueue.offer(id);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid toy data: " + data);
            }
        }
    }

    public String getWinner() {
        if (lotteryQueue.isEmpty()) {
            return "Lottery queue is empty!";
        }
        int winnerId = lotteryQueue.poll();
        int index = toyIds.indexOf(winnerId);
        return toyNames.get(index);
    }

    public void runLottery(int iterations, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);

        for (int i = 0; i < iterations; i++) {
            String winner = getWinner();
            fileWriter.write("Winner " + (i + 1) + ": " + winner + "\n");
        }
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException {
        ToyLottery lottery = new ToyLottery("1,Car,5", "2,Doll,3", "3,Ball,2");

        // Запуск лотереи 10 раз и запись в файл
        lottery.runLottery(10, "lottery_results.txt");
    }
}

