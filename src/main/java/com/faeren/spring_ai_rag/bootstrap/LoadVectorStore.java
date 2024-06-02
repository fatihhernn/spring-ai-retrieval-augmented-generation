package com.faeren.spring_ai_rag.bootstrap;

import com.faeren.spring_ai_rag.config.VectorStoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class LoadVectorStore implements CommandLineRunner {

    @Autowired
    VectorStore vectorStore;

    @Autowired
    VectorStoreProperties vectorStoreProperties;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loading Vector Store...");
        if (vectorStore.similaritySearch("Sportsman").isEmpty()){
            System.out.println("Loading documents into vector store");
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                System.out.println("Loading document : "+document.getFilename());

                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documents = documentReader.get();

                TokenTextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocument = textSplitter.apply(documents);

                vectorStore.add(splitDocument);
            });
        }
        log.debug("Vector store loaded");
    }
}
