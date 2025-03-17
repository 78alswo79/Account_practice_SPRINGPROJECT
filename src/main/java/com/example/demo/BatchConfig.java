//package com.example.demo;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import com.example.demo.dto.Test;
//import com.example.demo.service.AccountService;
//
//@Configuration
//@EnableBatchProcessing
//public class BatchConfig {
//
//	@Autowired
//	AccountService accountService;
////	@Autowired
////    private DataSource dataSource; // application.properties에서 설정된 DataSource 주입
////
//////    @Autowired
//////    private PlatformTransactionManager transactionManager; // 트랜잭션 매니저 주입
////    
////	@Bean
////    public PlatformTransactionManager transactionManager() {
////        return new DataSourceTransactionManager(dataSource);
////    }
////
////    @Bean
////    public JobRepository jobRepository() throws Exception {
////        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
////        factory.setDataSource(dataSource);
////        factory.setTransactionManager(transactionManager());
////        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
////        factory.afterPropertiesSet();
////        return factory.getObject();
////    }
////
////    @Bean
////    public Job job() throws Exception {
////        System.out.println("시발>>>>>>>>>>>>>>>>" + jobRepository());
////        return new JobBuilder("account_batch", jobRepository())
////                .incrementer(new RunIdIncrementer()) // Job 실행 시마다 새로운 ID를 부여
////                .start(batchStep(jobRepository()))
////                .build();
////    }
////
////    @Bean
////    public Step batchStep(JobRepository jobRepository) {
////        return new StepBuilder("batch_step", jobRepository) // JobRepository를 인자로 추가
////                .tasklet(batchTask(jobRepository))
////                .build();
////    }
//
//	@Autowired
//    private DataSource dataSource; // application.properties에서 설정된 DataSource 주입
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean
//    public JobRepository jobRepository() throws Exception {
//        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setTransactionManager(transactionManager());
//        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
//        factory.afterPropertiesSet();
//        return factory.getObject();
//    }
//
//    @Bean
//    public Job job() throws Exception {
//        return new JobBuilder("account_batch", jobRepository())
//                .incrementer(new RunIdIncrementer()) // Job 실행 시마다 새로운 ID를 부여
//                .start(batchStep(jobRepository()))
//                .build();
//    }
//
//    @Bean
//    public Step batchStep(JobRepository jobRepository) {
//        return new StepBuilder("batch_step", jobRepository) // JobRepository를 인자로 추가
//                .tasklet(batchTask(jobRepository))
//                .build();
//    }
//    //Tasklet 정의
//    @Bean
//    public Tasklet batchTask(JobRepository jobRepository) {
//        return (contribution, chunkContext) -> {
//            // 마스트 가계부 테이블의 모든 데이터 조회
//            List<Test> masterAccountList = accountService.getMasterAllAccoutList();
//            
//            // batch 타겟 가계부 테이블의 모든 데이터 조회
//            List<Test> batchAccoutList = accountService.getBatchAllAccoutList();
//            
//            List<Test> resultList = new ArrayList<>();
//
//            // master 테이블의 seq 값을 batch_target 테이블에 삽입
//            for (Test masterItem : masterAccountList) {
//                // batch_target 테이블에 seq 값이 존재하는지 확인
//                boolean existsInBatchAccount = batchAccoutList.stream().anyMatch(batchItem -> batchItem.getSeq() == masterItem.getSeq());
//                if (!existsInBatchAccount) {
//                    // batch_target 테이블에 새로운 test 객체 생성 및 저장
//                    Test test = new Test();
//                    test.setSeq(masterItem.getSeq());
//                    test.setYear(masterItem.getYear());
//                    test.setMonth(masterItem.getMonth());
//                    test.setDays(masterItem.getDays());
//                    test.setContent(masterItem.getContent());
//                    test.setSpending(masterItem.getSpending());
//                    test.setBalance(masterItem.getBalance());
//                    test.setIncome(masterItem.getIncome());
//
//                    resultList.add(test);
//                    
//                    
//                } else {
//                	// update 
//                	batchAccoutList.stream()
//                		.filter(batchItem -> batchItem.getSeq() == masterItem.getSeq())
//                		.findFirst()
//                		.ifPresent(batchItem -> {
//                			batchItem.setYear(masterItem.getYear());
//                            batchItem.setMonth(masterItem.getMonth());
//                            batchItem.setDays(masterItem.getDays());
//                            batchItem.setContent(masterItem.getContent());
//                            batchItem.setSpending(masterItem.getSpending());
//                            batchItem.setBalance(masterItem.getBalance());
//                            batchItem.setIncome(masterItem.getIncome());
//
//                            // 업데이트된 항목을 데이터베이스에 저장
//                            accountService.updateBatchAccount(batchItem);
//                		});
//                }
//            }
//            accountService.insertBatchAccount(resultList);
//            // 무조건 초기화!
//            resultList.clear();
//
//            // a2 테이블에서 a1 테이블에 없는 seq 값을 가진 데이터 삭제
//            for (Test batchItem : batchAccoutList) {
//                // a1 테이블에 seq 값이 존재하는지 확인
//                boolean existsInMasterAccount = masterAccountList.stream().anyMatch(masterItem -> masterItem.getSeq() != batchItem.getSeq());
//                if (!existsInMasterAccount) {
//                    // a2 테이블에서 해당 데이터 삭제
//                	Test test = new Test();
//                	test.setSeq(batchItem.getSeq());
//                	
//                	resultList.add(test);
//                	// TODO mapper.xml에 insert, delete부분 분기문으로 써서 관리하기
//                	// 업데이트 배치는 어떻게 할지..생각해보기.
//                }
//            }
//            accountService.batchDelete(resultList);
//
//            return RepeatStatus.FINISHED; // Tasklet 완료
//        };
//    }
//}
