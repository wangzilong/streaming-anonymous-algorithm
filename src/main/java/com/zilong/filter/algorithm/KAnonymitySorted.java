package com.zilong.filter.algorithm;

import com.zilong.model.InputRecord;
import com.zilong.model.OutputRecord;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KAnonymitySorted implements KAnonymity {



    /**
     * Accepts a list of input record, anonymize the rawValue and return a list of output record.
     *
     * 1. sort input record by rawValue
     * 2. split records into different bin and make sure that the size of bin is more than K.
     * 3. calculate the average as anonymisedValue in same bin.
     * 4. create output record.
     *
     * @param inputRecords: a list of input record
     * @param k: K-Anonymity parameter.
     * @return
     */
    @Override
    public List<OutputRecord> anonymize(@NotNull List<InputRecord> inputRecords, final int k) {

        List<OutputRecord> result = new ArrayList<>();

        int size = inputRecords.size();

        //The output time may be assumed to be the value of the most recently added InputRecord’s timestamp.
        int outputTime = inputRecords.get(size-1).getTime();

        //sort by rawValue.
        inputRecords = inputRecords.stream().sorted().collect(Collectors.toList());


        int binCount = size / k;
        int mod = size % k;//if mod != 0, size of input records is not a multiple of K.
        for (int i = 0; i < binCount; i++) {

            int start = i * k;
            int binSize = k;

            //if current bin is last bin, the remaining records into the last Bin
            if (i == binCount - 1 && mod != 0)
                binSize = k + mod;

            //calculate anonymised Value of the bin.
            double anonymisedValue = inputRecords.stream().skip(start).limit(binSize).mapToDouble(e -> e.getRawValue()).sum()/binSize;

            //create output record
            for (int j = 0; j < binSize; j++) {
                InputRecord ir = inputRecords.get(start+j);
                result.add(new OutputRecord(ir, outputTime, anonymisedValue));
            }
        }
        return result;
    }
}
