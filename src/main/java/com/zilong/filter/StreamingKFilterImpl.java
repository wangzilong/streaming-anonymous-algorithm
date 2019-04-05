package com.zilong.filter;

import com.zilong.filter.algorithm.KAnonymity;
import com.zilong.config.Config;
import com.zilong.model.InputRecord;
import com.zilong.model.OutputRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.Executor;

@Service
public class StreamingKFilterImpl implements StreamingKFilter{

    private static final Logger logger = LoggerFactory.getLogger(StreamingKFilterImpl.class);

    private final Config config;

    //Input Record Queue.
    private final Queue<InputRecord> incomingQueue;

    //output record queue.
    private final Queue<OutputRecord> outgoingQueue;

    //K-Anonymity Algorithm
    private final KAnonymity kAnonymity;

    // It is a consumer.
    // Get records from incomingQueue and generate OuputRecords
    // current version only one worker in the application
    private final Worker worker;

    @Autowired
    public StreamingKFilterImpl(KAnonymity kAnonymity,
                                Executor executor,
                                Queue<InputRecord> incomingQueue,
                                Queue<OutputRecord> outgoingQueue,
                                Config config) {
        this.kAnonymity = kAnonymity;
        this.incomingQueue = incomingQueue;
        this.outgoingQueue = outgoingQueue;
        this.config = config;

        this.worker = this.new Worker();
        executor.execute(worker);
    }

    private class Worker implements Runnable{
        @Override
        public void run() {
            while (true){
                logger.info("Worker run ...");

                execute();

                sleep();
            }
        }

        /**
         * sleep worker to sleep for the specified number of milliseconds
         *
         */
        private void sleep(){
            try {
                long t = generateSleepTime(1,1,1,incomingQueue.size());
                Thread.sleep(t);
            } catch (Exception e) {
            }
        }

        /**
         * Generate Sleep Time of Worker.
         *
         * Current Version: always return 3000ms.
         *
         * Next version:
         *   Dynamically calculate it by inputRecordSpeed, processingSpeed,QueueSize and dataSize in queue.
         *
         *
         * @param inputRecordSpeed: InputRecord API's TPS
         * @param processingSpeed: Speed of processing data in local machine.
         * @param queueSize： The size of InputRecord queue
         * @param dataSize: The size of InputRecord in Queue.
         * @return
         */
        private long generateSleepTime(long inputRecordSpeed, long processingSpeed, long queueSize, long dataSize) {
            return 3000;
        }

        /**
         * Get InputRecord from incomingQueue and generate OutputRecord.
         *
         * 1. get window
         * 2. get Input Records and put them into processing Queue.
         * 3. anonymize records.
         * 4. Store result into outgoing Queue.
         * 5. if incomingQueue's size is empty, return
         *    else execute recursively
         */
        void execute(){
            final long window = window(incomingQueue);
            if (window == 0)
                return;

            List<InputRecord> processingQueue = getInputRecords(window);

            if (!processingQueue.isEmpty()){
                //anonymize
                Collection<OutputRecord> or = kAnonymity.anonymize(processingQueue,config.getK());

                //store it
                //todo: queue is full.
                outgoingQueue.addAll(or);

                execute();
            }

        }
    }


    /**
     * Return Records from incoming queue.
     *
     * @param window: how many record will be add into processing queue.
     * @return: processing queue.
     */
    private LinkedList<InputRecord> getInputRecords(final long window){
        LinkedList<InputRecord> processingQueue = new LinkedList<>();
        for (int i = 0; i < window; i++) {
            InputRecord inputRecord = incomingQueue.poll();
            if (inputRecord != null){
                processingQueue.add(inputRecord);
            }
        }
        return processingQueue;
    }

    /**
     * Windowing lets we control how many records will be generalized in one time
     *
     * case1: queue size > window, return window
     * case2: queue size < K,      return 0
     * case3: queue size < window, return queue size
     *
     * @param incomingQueue:  Input Record Queue.
     * @return
     */
    private long window(Queue<InputRecord> incomingQueue) {
        int size = incomingQueue.size();
        if (size < config.getK())
            return 0;
        if (size < config.getWindow())
            return size;
        return config.getWindow();
    }

    @Override
    public void processNewRecord(@NotNull InputRecord input) {
        //todo: queue is full
        this.incomingQueue.offer(input);
    }

    /**
     * Return All Output Record.
     *
     * @return Output Record List
     */
    @Override
    public Collection<OutputRecord>  returnPublishableRecords(){
        List<OutputRecord> outputRecords = new ArrayList<>();
        while (!outgoingQueue.isEmpty()){
            outputRecords.add(outgoingQueue.poll());
        }
        return outputRecords;
    }

}
