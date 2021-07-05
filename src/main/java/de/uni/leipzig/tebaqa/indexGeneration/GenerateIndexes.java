package de.uni.leipzig.tebaqa.indexGeneration;

import info.aduna.io.FileUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.helpers.RDFHandlerBase;
import org.openrdf.rio.ntriples.NTriplesParser;
import org.openrdf.rio.turtle.TurtleParser;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

public class GenerateIndexes {
  private static final org.slf4j.Logger log = LoggerFactory.getLogger(GenerateIndexes.class);

  public static void main(String[] args) {
    if (args.length > 0) {
      log.error("TripleIndexCreator works without parameters. Please use agdistis.properties File");
      return;
    }
    try {
      Properties prop = new Properties();
      InputStream input = new FileInputStream("src/main/resources/entityLinking.properties");
      prop.load(input);

      String resourceIndex = prop.getProperty("resource_index");
      log.info("The resource index will be here: " + resourceIndex);
      // uncomment this to get Data automatically from Limbo gitlab
      // String metadataUrl = prop.getProperty("limbo_metadata_url");
      // DownloadWithMetadataCatalog.getFilesFromMetadataCatalog(metadataUrl);

      String propertyIndex = prop.getProperty("property_index");
      log.info("The property index will be here: " + propertyIndex);
      String classIndex = prop.getProperty("class_index");
      log.info("The class index will be here: " + classIndex);
      ResourceIndexCreator ic = new ResourceIndexCreator();

      boolean generateOntologyIndex =
          Boolean.parseBoolean(prop.getProperty("generateOntologyIndex"));
      boolean extractFromUrl = Boolean.parseBoolean(prop.getProperty("extract_from_url"));
      if (generateOntologyIndex) {
        if (extractFromUrl) {
          List<String> urls = Arrays.asList(prop.getProperty("owl_urls").split(","));
          ic.createOntologieIndexFromUrls(urls, propertyIndex,classIndex);
        } else {
          String folderWithOwlFiles = prop.getProperty("folder_with_owl_files");
          List<File> listOfFiles =
              Arrays.stream(Objects.requireNonNull(new File(folderWithOwlFiles).listFiles()))
                  .filter(GenerateIndexes::isValidExt)
                  .collect(Collectors.toList());

          ic.createOntologieIndexFromFile(listOfFiles, propertyIndex, classIndex);
        }
      }

      boolean generateResourceIndex =
          Boolean.parseBoolean(prop.getProperty("generateResourceIndex"));
      if (generateResourceIndex) {
        if (extractFromUrl) {
          List<String> urls = Arrays.asList(prop.getProperty("urls").split(","));
          ic.createIndexFromUrls(urls, resourceIndex);
        } else {
          //          List<File> listOfFiles = new ArrayList<>();
          String folderWithTtlFiles = prop.getProperty("folder_with_ttl_files");
          List<File> listOfFiles =
              Arrays.stream(Objects.requireNonNull(new File(folderWithTtlFiles).listFiles()))
                  .filter(GenerateIndexes::isValidExt)
                  .collect(Collectors.toList());

          ic.createIndexFromFile(listOfFiles, resourceIndex);
          // ic.writeIndexFromFTP(baseURI,useElasticsearch);
        }
      }

      ic.close();

    } catch (IOException e) {
      log.error("Error while creating index. Maybe the index is corrupt now.", e);
    }
  }

  private static boolean isValidExt(File file) {
    String name = file.getName();
    return name.endsWith("ttl") || name.endsWith("nt") || name.endsWith("owl");
  }
}
