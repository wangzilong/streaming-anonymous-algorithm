package com.zilong.filter.algorithm;

import com.zilong.model.InputRecord;
import com.zilong.model.OutputRecord;

import java.util.List;

public interface KAnonymity {

    /**
     * Accepts a list of input record, anonymize the rawValue and return a list of output record.
     *
     * @param inputRecords: a list of input record
     * @param k: K-Anonymity parameter.
     * @return
     */
    List<OutputRecord> anonymize(List<InputRecord> inputRecords, int k);
}
