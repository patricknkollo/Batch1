package com.example.batch1.config;

import com.example.batch1.entities.Customer;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig extends DefaultBatchConfiguration {

    /**
     * private Long customerid;
     *     private String firstname;
     *     private String lastname;
     *     private String email;
     *     private char gender;
     *     private String contactno;
     *     private String country;
     *     private LocalDate dob;
     *     private int age;
     * @return
     */

    @Bean
    public Job jobBean(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("importUserJobName", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                      FlatFileItemReader<Customer> reader, ItemProcessor processor, JdbcBatchItemWriter<Customer> writer) {
        return new StepBuilder("jobStep", jobRepository)
                .<Customer,Customer>chunk(8, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public FlatFileItemReader<Customer> reader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("customerItemReader")
                .resource(new ClassPathResource("customer.csv"))
                .delimited()
                .names("firstname", "lastname", "email", "gender", "contactno", "country", "dob", "age")
                .targetType(Customer.class)
                .build();
    }

    @Bean
    public ItemProcessor<Customer, Customer> itemProcessor() {
        return new CustomItemProcessor();
    }


    //JdbcBatchItemWriter
    @Bean
    public ItemWriter<Customer> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .sql("INSERT INTO Customer (lastname, firstname, email, gender, contactno, country, dob, age) VALUES (:lastname, :firstname, :email, :gender, :contactno, :country, :dob, :age)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }


    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager();
    }

}