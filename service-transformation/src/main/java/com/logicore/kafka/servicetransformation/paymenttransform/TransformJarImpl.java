package com.logicore.kafka.servicetransformation.paymenttransform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import model.payment.PaymentMessage;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

@Slf4j
@Component("TransformJar")
public class TransformJarImpl implements Transform{

    ObjectMapper objectMapper;

    public TransformJarImpl() {
        this.objectMapper = new ObjectMapper();
    }

    public PaymentMessage transformPaymentMessage(String jarName, PaymentMessage paymentMessage) throws IOException, InterruptedException, URISyntaxException {

        String path = this.getClass().getClassLoader().getResource(jarName).toURI().getPath();
        String jarPathWin = path.replace('/', '\\').substring(1);
        String jarPathLinux = this.getClass().getClassLoader().getResource(jarName).getPath();

        String paymentMessageToStr = transformArg(paymentMessage);
        String output = process(jarPathWin, paymentMessageToStr);
        PaymentMessage paymentMessageTransform = objectMapper.readValue(output, PaymentMessage.class);

        return paymentMessageTransform;
    }


    private String transformArg(PaymentMessage paymentMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String paymentMessageToStr = objectMapper.writeValueAsString(paymentMessage);

        paymentMessageToStr = paymentMessageToStr.replace("John Doe","JohnDoe");
        paymentMessageToStr = paymentMessageToStr.replaceAll("\"", "\"\"\"");

        return paymentMessageToStr;
    }

    private String process(String path, String arg) throws IOException, InterruptedException {
        // Create a ProcessBuilder object to run the JAR file

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", path, arg);
        pb.redirectErrorStream(true);

        // Start the process and obtain a Process object
        Process p = pb.start();
        // Collect the output of the process
        String output = collectOutput(p);

        // Wait for the process to finish
        int exitCode = p.waitFor();

        // Print the output and exit code
        System.out.println(output);
        System.out.println("Exit code: " + exitCode);
        return output;

    }

    private String collectOutput(Process p) throws IOException {
        // Collect the output of the process into a string
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }

        return sb.toString();
    }

}
