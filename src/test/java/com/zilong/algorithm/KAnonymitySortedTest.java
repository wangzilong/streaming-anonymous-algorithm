package com.zilong.algorithm;

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
public class KAnonymitySortedTest {

    @InjectMocks
    private KAnonymitySorted kAnonymitySorted;

    @Test
    public void anonymize() {
        //given
        List<InputRecord> list = new ArrayList<>();
        list.add(new InputRecord(1, 20));
        list.add(new InputRecord(2, 30));
        int k = 2;
        //when
        List<OutputRecord> outputRecords = kAnonymitySorted.anonymize(list, k);

        //then
        assert outputRecords.size() == 2;
        assert outputRecords.get(0).getAnonymisedValue() == 25;
    }
}
