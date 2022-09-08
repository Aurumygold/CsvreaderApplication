package com.example.mercadona;

import com.example.mercadona.listener.JobListener;
import com.example.mercadona.model.Factura;
import com.example.mercadona.processor.FacturasItemProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DefaultFieldSetFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.text.NumberFormat;
import java.util.Locale;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(FacturasItemProcessor.class);
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Factura> reader(){

        return new FlatFileItemReaderBuilder<Factura>()
                .name("facturaItemReader")
                .resource(new ClassPathResource("importcsv-ok.csv"))
                .encoding("UTF-8")
                .delimited()
                .delimiter(";")
                .names(new String[]{"identificadorlegacy", "nombre", "fecha", "importe"})
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Factura>(){{
                    setTargetType(Factura.class);
                    }})
                .build();

    }

    @Bean
    public FacturasItemProcessor processor(){
        return new FacturasItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Factura> writer(DataSource dataSource){
        LOG.info("dataSource "+dataSource);
        return new JdbcBatchItemWriterBuilder<Factura>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO factura ( identificador, identificadorlegacy, nombre, fecha, importe) " +
                        "VALUES (nextval('FACTURA_JOB_SEQ'), :identificadorlegacy, :nombre, :fecha, :importe)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importFacturaJob(JobListener listener, Step step1){
        return jobBuilderFactory.get("importFacturaJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Factura> writer){

        return stepBuilderFactory.get("step1")
                .<Factura, Factura> chunk (100)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
