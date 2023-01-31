//package com.example.workflow;
//
//import camundajar.impl.com.google.gson.Gson;
//import com.example.workflow.entity.DummyEntity;
//import com.example.workflow.validator.enschematronValidator.EnSchemaValidator;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ByteArrayEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.camunda.bpm.engine.delegate.DelegateExecution;
//import org.camunda.bpm.engine.delegate.JavaDelegate;
//import org.camunda.connect.Connectors;
//import org.camunda.connect.httpclient.HttpConnector;
//import org.camunda.connect.httpclient.HttpRequest;
//import org.camunda.connect.httpclient.HttpResponse;
//import org.springframework.http.HttpEntity;
//import org.springframework.stereotype.Component;
//
//import java.io.ByteArrayInputStream;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpHeaders;
//
//import java.nio.file.Path;
//import java.util.HashMap;
//
//import static org.springframework.http.RequestEntity.post;
//
//@Component
//public class dummy implements JavaDelegate {
//    private static final Logger LOGGER = LogManager.getLogger(EnSchemaValidator.class);
//
//    @Override
//    public void execute(DelegateExecution delegateExecution) throws Exception {
//       // delegateExecution.getVariable("output");
//        DummyEntity body= new DummyEntity(78L, delegateExecution.getVariable("output").toString());
//        ObjectWriter ow=new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json=ow.writeValueAsString(body);
//        HttpConnector http = Connectors.getConnector(HttpConnector.ID);
//        HttpRequest request = http.createRequest();
//        request.post()
//                .url("http://localhost:8080/add")
//                .payload(json)
//                .header("Content-Type","application/json");
//        HttpResponse response= request.execute();
//        LOGGER.info("Post Response >> " + response.getResponse());
////        Gson json2=new Gson();
////        DummyEntity dto=json2.fromJson(response.getResponse(), DummyEntity.class);
////        LOGGER.info("Post id is "+dto.getId()+" post body >> "+ dto.getMessage());
//    }
//
//}
