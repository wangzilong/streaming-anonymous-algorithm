package com.zilong.filter;

import com.zilong.model.InputRecord;
import com.zilong.model.OutputRecord;

import java.util.Collection;

public interface StreamingKFilter {

    /**
     * Accepts a new raw input record, the anonymised version of which will later be released
     * from {@link #returnPublishableRecords()}
     */
    void processNewRecord(InputRecord input);

    /**
     * Return all of the anonymised records that are available for release.  If we are currently
     * unable to release any records this method will return an empty collection.
     */
    Collection<OutputRecord> returnPublishableRecords();

}
