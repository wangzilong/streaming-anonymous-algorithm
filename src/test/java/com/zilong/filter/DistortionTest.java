package com.zilong.filter;

import com.zilong.filter.algorithm.KAnonymitySorted;
import com.zilong.model.InputRecord;
import com.zilong.model.OutputRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DistortionTest {

    @InjectMocks
    private KAnonymitySorted kAnonymitySorted;

    @Test
    public void calculate() {
        //given
        List<InputRecord> list = new ArrayList<>();
        list.add(new InputRecord(1, 4));
        list.add(new InputRecord(2, 8));
        list.add(new InputRecord(3, 6));
        list.add(new InputRecord(4, 2));
        list.add(new InputRecord(5, 10));

        int k = 2;
        List<OutputRecord> outputRecords = kAnonymitySorted.anonymize(list, k);

        //when
        double d = Distortion.calculate(outputRecords);

        //then
        assert d == 0.25;
    }

}
