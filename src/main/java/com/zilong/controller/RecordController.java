package com.zilong.controller;

import com.zilong.config.Config;
import com.zilong.filter.StreamingKFilter;
import com.zilong.model.OutputRecord;
import com.zilong.viewModel.ConfigViewModel;
import com.zilong.viewModel.InputRecordViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class RecordController {

    private final StreamingKFilter streamingKFilter;

    private final Config config;

    @Autowired
    public RecordController(StreamingKFilter streamingKFilter,

                            Config config) {
        this.streamingKFilter = streamingKFilter;
        this.config = config;
    }

    @RequestMapping(value = "/processNewRecord", method = RequestMethod.POST)
    public boolean processNewRecord(@RequestBody InputRecordViewModel inputRecordViewModel){
        streamingKFilter.processNewRecord(inputRecordViewModel.build());
        return true;
    }

    @GetMapping(value = "/returnPublishableRecords")
    public Collection<OutputRecord> returnPublishableRecords(){
        return streamingKFilter.returnPublishableRecords();
    }


    @PostMapping(value = "/config")
    public boolean config(@RequestBody ConfigViewModel configViewModel){
        config.setK(configViewModel.getK());
        config.setWindow(configViewModel.getWindow());
        return true;
    }

}
