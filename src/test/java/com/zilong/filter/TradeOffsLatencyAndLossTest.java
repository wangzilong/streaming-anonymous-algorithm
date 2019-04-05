package com.zilong.filter;

import com.zilong.filter.algorithm.KAnonymitySorted;
import com.zilong.model.InputRecord;
import com.zilong.model.OutputRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TradeOffsLatencyAndLossTest {


    @InjectMocks
    private KAnonymitySorted kAnonymity;

    @Test
    public void test() {

        final int k = 8;
        List<Integer> latencies = Arrays.asList( 10, 20, 30, 40, 50, 100, 200);
        final int sizeOfInputRecord = 3000;

        Random r = new Random();
        List<InputRecord> list = new ArrayList<>();
        for (int i = 1; i <= sizeOfInputRecord; i++) {
            list.add(new InputRecord(i, r.nextInt(100) + 1));
        }

        Map<Integer, Double> result = new HashMap<>();


        for (Integer latency : latencies) {

            List<OutputRecord> outputRecords = new ArrayList<>();

            int  binCount = sizeOfInputRecord/latency + 1;
            for (int i = 0; i < binCount; i++) {
                List<InputRecord> inputRecords = list.stream().skip(i * latency).limit(latency).collect(Collectors.toList());
                if (inputRecords.isEmpty())
                    break;
                List<OutputRecord> ors = kAnonymity.anonymize(inputRecords,8);
                outputRecords.addAll(ors);
            }

            double distortion = Distortion.calculate(outputRecords);

            result.put(latency, distortion);
        }

        System.out.println("latency -> distortion");
        for (Integer latency : result.keySet()) {
            System.out.println(latency + " -> " + result.get(latency));
        }
    }
}
