package de.allmaennitta.dummy.javaws.shared.model.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import de.allmaennitta.dummy.javaws.shared.ModelConfiguration;
import de.allmaennitta.dummy.javaws.shared.model.TestModel;
import de.allmaennitta.dummy.javaws.shared.utils.Utils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ModelConfiguration.class})
public class JsonSchemaTest {
  private static final Logger LOG = LoggerFactory.getLogger(JsonSchemaTest.class);
  public static final String JSON_SCHEMA_SUFFIX = ".schema.json";
  public static final int SUFFIX_LENGTH = JSON_SCHEMA_SUFFIX.length();
  public static final String YML_SCHEMA_SUFFIX = ".schema.yml";

  @Autowired
  private ObjectMapper objectMapper;

  public JsonSchemaTest() {
  }

  @Test //@Ignore
  public void testGenerateSchema() throws Exception {
    generateSchema(TestModel.class);
  }

  private String generateSchema(Class clazz) throws URISyntaxException, IOException {
    JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(objectMapper);
    JsonSchema schema = schemaGen.generateSchema(clazz);
    assertThat(schema).isNotNull();

    URL outdir = this.getClass().getResource(".");
    File file = new File(new URI(outdir.toURI().toString() + "/" + clazz.getSimpleName() + JSON_SCHEMA_SUFFIX));
    file.createNewFile();
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, schema);
    return file.getAbsolutePath();
  }

  @Test //@Ignore
  public void testGenerateYamlFromJson() throws IOException, URISyntaxException {
    generateYamlFromJson("TestModel.schema.json");
  }

  private void generateYamlFromJson(String relativePath) throws IOException, URISyntaxException {
    Path rootPath = Paths.get(this.getClass().getResource(".").toURI());
    Path jsonPath = rootPath.resolve(Paths.get(relativePath));
    JsonNode json = objectMapper.readTree(jsonPath.toFile());

    Path yamlPath;
    String pathString = jsonPath.toString();
    if (pathString.endsWith(JSON_SCHEMA_SUFFIX)){
      yamlPath = Paths.get(pathString.replace(JSON_SCHEMA_SUFFIX, YML_SCHEMA_SUFFIX));}
    else {
      throw new IllegalStateException("Kein Json!");
    }
    File yamlFile = yamlPath.toFile();
    yamlFile.createNewFile();
    new YAMLMapper().writeValue(yamlFile, json);
  }

  @Test @Ignore
  public void JsonNodeTest() throws IOException {
    URL pathUrl = new Object() {}.getClass().getEnclosingClass().getResource(".");
    String payload = Utils.stringFromFile(pathUrl, "xxx.json");
    JsonNode jsonNode = objectMapper.readTree(payload);

    JsonNode deepPath = jsonNode.at("/a/0/b/0/c/0");
    System.out.println(deepPath.path("x"));

    JsonNode combinations = jsonNode.at("/x");
    System.out.println(combinations.path(0).path("y"));

    TestModel testModel = objectMapper.treeToValue(combinations.path(0),TestModel.class);
    testModel.setOne(new BigDecimal("99999.00"));//FIXME
    System.out.println(objectMapper.valueToTree(testModel));

    ((ArrayNode) combinations).set(0,objectMapper.valueToTree(testModel));
    System.out.println(jsonNode.at("/a/0/b").toString());
    System.out.println(jsonNode.textValue());
  }

  @Test @Ignore
  public void EXPECT_JacksonValidationToBeSuccessful() throws IOException {
//        final JsonNode schema = JsonLoader.fromPath(JsonSchemaTest.class.getResource("2_sRulableSchema.json").getPath());
//        final JsonNode good = JsonLoader.fromPath(JsonSchemaTest.class.getResource("pricing-TXL-FCO.json").getPath());
//
//        final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
//        final JsonSchema schema = factory.getJsonSchema(schema);
//        ProcessingReport report = schema.validate(good);
//        LOG.debug(String.valueOf(report));
  }

}