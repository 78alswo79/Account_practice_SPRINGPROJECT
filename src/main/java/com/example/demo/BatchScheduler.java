//package com.example.demo;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@EnableScheduling
//public class BatchScheduler {
//	@Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    private Job job;
//
//    @Scheduled(fixedRate = 10000) // 60초마다 실행
//    public void runBatchJob() {
//        try {
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis()) // Job 실행 시마다 새로운 파라미터 추가
//                    .toJobParameters();
//            jobLauncher.run(job, jobParameters);
//        } catch (Exception e) {
//            e.printStackTrace(); // 예외 처리
//        }
//    }
//}
