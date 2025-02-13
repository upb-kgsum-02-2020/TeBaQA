package de.uni.leipzig.tebaqa.controller;

import com.google.common.collect.Lists;
import de.uni.leipzig.tebaqa.helper.DBpediaPropertiesProvider;
import de.uni.leipzig.tebaqa.helper.NTripleParser;
import de.uni.leipzig.tebaqa.model.CustomQuestion;
import de.uni.leipzig.tebaqa.model.QueryTemplateMapping;
import de.uni.leipzig.tebaqa.model.ResultsetBinding;
import de.uni.leipzig.tebaqa.model.SPARQLResultSet;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.uni.leipzig.tebaqa.model.SPARQLResultSet.*;
import static org.junit.Assert.*;

public class SemanticAnalysisHelperTest {
    /*@Test
    public void testExtractTemplatesMapsGraph() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("PREFIX res: <http://dbpedia.org/resource> " +
                "PREFIX dbo: <http://dbpedia.org/ontology> " +
                "SELECT DISTINCT ?uri " +
                "WHERE { " +
                "        res:Douglas_Hofstadter dbo:award ?uri . " +
                "}", "Which awards did Douglas Hofstadter win?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        assertTrue(mappings.size() == 1);
        assertTrue(mappings.containsKey(graph));
    }*/

    /*@Test
    public void testExtractTemplatesContainsSelectQueryPattern() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("PREFIX res: <http://dbpedia.org/resource> " +
                "PREFIX dbo: <http://dbpedia.org/ontology> " +
                "SELECT DISTINCT ?uri " +
                "WHERE { " +
                "        res:Douglas_Hofstadter dbo:award ?uri . " +
                "}", "Which awards did Douglas Hofstadter win?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        Set<String> expectedSelectPatterns = new HashSet<>();
        expectedSelectPatterns.add("SELECT DISTINCT ?uri WHERE { <^VAR_0^> <^VAR_1^> ?uri . }");

        assertEquals(1, mappings.size());
        assertEquals(expectedSelectPatterns, mappings.get(graph).getSelectTemplates());
    }*/

    /*@Test
    public void testExtractTemplatesContainsSelectQueryPattern2() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("SELECT DISTINCT ?uri WHERE {  <http://dbpedia.org/resource/San_Pedro_de_Atacama> <http://dbpedia.org/ontology/timeZone> ?uri . }",
                "What is the timezone in San Pedro de Atacama?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        Set<String> expectedSelectPatterns = new HashSet<>();
        expectedSelectPatterns.add("SELECT DISTINCT ?uri WHERE { <^VAR_0^> <^VAR_1^> ?uri . }");

        assertEquals(1, mappings.size());
        assertEquals(expectedSelectPatterns, mappings.get(graph).getSelectTemplates());
    }*/

    /*@Test
    public void testExtractTemplatesDetectsIsomorphTemplates() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("SELECT DISTINCT ?uri WHERE {  <http://dbpedia.org/resource/San_Pedro_de_Atacama> <http://dbpedia.org/ontology/timeZone> ?uri . }",
                "What is the timezone in San Pedro de Atacama?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT DISTINCT ?num WHERE {  <http://dbpedia.org/resource/Colombo_Lighthouse> <http://dbpedia.org/ontology/height> ?num . } ",
                "How high is the lighthouse in Colombo?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        Set<String> expectedSelectPatterns = new HashSet<>();
        expectedSelectPatterns.add("SELECT DISTINCT ?uri WHERE { <^VAR_0^> <^VAR_1^> ?uri . }");

        assertEquals(1, mappings.size());
        assertEquals(expectedSelectPatterns, mappings.get(graph).getSelectTemplates());
    }*/

    /*@Test
    public void testExtractTemplatesIgnoresCount() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("SELECT (COUNT(DISTINCT ?x) as ?c) WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . } ",
                "How many languages are spoken in Turkmenistan?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT DISTINCT ?num WHERE {  <http://dbpedia.org/resource/Colombo_Lighthouse> <http://dbpedia.org/ontology/height> ?num . } ",
                "How high is the lighthouse in Colombo?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT (COUNT(DISTINCT ?x) as ?c) WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . } ",
                "How many languages are spoken in Turkmenistan?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        Set<String> actualTemplates = mappings.get(graph).getSelectTemplates();

        assertTrue(mappings.size() == 1);
        assertEquals("SELECT DISTINCT ?num WHERE { <^VAR_0^> <^VAR_1^> ?num . }", actualTemplates.stream().findFirst().get());
    }*/

    /*@Test
    public void testExtractTemplatesIgnoresSum() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("SELECT (SUM(DISTINCT ?x) as ?c) WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . } ",
                "How many languages are spoken in Turkmenistan?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT DISTINCT ?num WHERE {  <http://dbpedia.org/resource/Colombo_Lighthouse> <http://dbpedia.org/ontology/height> ?num . } ",
                "How high is the lighthouse in Colombo?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT (SUM(DISTINCT ?x) as ?c) WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . } ",
                "How many languages are spoken in Turkmenistan?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        assertEquals(1, mappings.size());
        assertEquals("SELECT DISTINCT ?num WHERE { <^VAR_0^> <^VAR_1^> ?num . }", mappings.get(graph).getSelectTemplates().stream().findFirst().get());
    }*/

    /*@Test
    public void testExtractTemplatesIgnoresAvg() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("SELECT (AVG(DISTINCT ?x) as ?c) WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . } ",
                "How many languages are spoken in Turkmenistan?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT DISTINCT ?num WHERE {  <http://dbpedia.org/resource/Colombo_Lighthouse> <http://dbpedia.org/ontology/height> ?num . } ",
                "How high is the lighthouse in Colombo?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT (AVG(DISTINCT ?x) as ?c) WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . } ",
                "How many languages are spoken in Turkmenistan?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        Set<String> actualTemplates = mappings.get(graph).getSelectTemplates();

        assertTrue(mappings.size() == 1);
        assertEquals("SELECT DISTINCT ?num WHERE { <^VAR_0^> <^VAR_1^> ?num . }", actualTemplates.stream().findFirst().get());
    }*/

    /*@Test
    public void testExtractTemplatesIgnoresMin() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("SELECT (MIN(DISTINCT ?x) as ?c) WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . } ",
                "How many languages are spoken in Turkmenistan?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT DISTINCT ?num WHERE {  <http://dbpedia.org/resource/Colombo_Lighthouse> <http://dbpedia.org/ontology/height> ?num . } ",
                "How high is the lighthouse in Colombo?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT (MIN(DISTINCT ?x) as ?c) WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . } ",
                "How many languages are spoken in Turkmenistan?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        assertEquals(1, mappings.size());
        assertEquals("SELECT DISTINCT ?num WHERE { <^VAR_0^> <^VAR_1^> ?num . }", mappings.get(graph).getSelectTemplates().stream().findFirst().get());
    }*/

    /*@Test
    public void testExtractTemplatesIgnoresMax() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("SELECT (MAX(DISTINCT ?x) as ?c) WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . } ",
                "How many languages are spoken in Turkmenistan?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT DISTINCT ?num WHERE {  <http://dbpedia.org/resource/Colombo_Lighthouse> <http://dbpedia.org/ontology/height> ?num . } ",
                "How high is the lighthouse in Colombo?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT (MAX(DISTINCT ?x) as ?c) WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . } ",
                "How many languages are spoken in Turkmenistan?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        Set<String> actualTemplates = mappings.get(graph).getSelectTemplates();

        assertTrue(mappings.size() == 1);
        assertEquals("SELECT DISTINCT ?num WHERE { <^VAR_0^> <^VAR_1^> ?num . }", actualTemplates.stream().findFirst().get());
    }*/

    /*@Test
    public void testExtractTemplatesIgnoresFilter() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("SELECT ?x WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . FILTER (!BOUND(?x)) }",
                "How many languages are spoken in Turkmenistan?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT DISTINCT ?num WHERE {  <http://dbpedia.org/resource/Colombo_Lighthouse> <http://dbpedia.org/ontology/height> ?num . } ",
                "How high is the lighthouse in Colombo?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT ?x WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . FILTER (!BOUND(?x)) }",
                "How many languages are spoken in Turkmenistan?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        assertEquals(1, mappings.size());
        Set<String> actualTemplates = mappings.get(graph).getSelectTemplates();
        assertEquals("SELECT DISTINCT ?num WHERE { <^VAR_0^> <^VAR_1^> ?num . }", actualTemplates.stream().findFirst().get());
    }*/

    /*@Test
    public void testExtractTemplatesIgnoresBound() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("SELECT ?x WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . FILTER (!BOUND(?x))}",
                "How many languages are spoken in Turkmenistan?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT DISTINCT ?num WHERE {  <http://dbpedia.org/resource/Colombo_Lighthouse> <http://dbpedia.org/ontology/height> ?num . } ",
                "How high is the lighthouse in Colombo?", null, graph));
        customQuestions.add(new CustomQuestion("SELECT ?x WHERE {  <http://dbpedia.org/resource/Turkmenistan> <http://dbpedia.org/ontology/language> ?x . FILTER (!BOUND(?x))}",
                "How many languages are spoken in Turkmenistan?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        assertEquals(1, mappings.size());
        Set<String> actualTemplates = mappings.get(graph).getSelectTemplates();
        
        assertEquals("SELECT DISTINCT ?num WHERE { <^VAR_0^> <^VAR_1^> ?num . }", actualTemplates.stream().findFirst().get());
    }*/

    /*@Test
    public void testExtractTemplatesUsesSuperlativeDesc() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("PREFIX dbo: <http://dbpedia.org/ontology/> PREFIX res: <http://dbpedia.org/resource/> PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> SELECT DISTINCT ?uri WHERE { ?uri rdf:type dbo:Mountain . ?uri dbo:locatedInArea res:Australia . ?uri dbo:elevation ?elevation . } ORDER BY DESC(?elevation) LIMIT 1",
                "What is the highest mountain in Australia?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);
        Set<String> actualTemplates = mappings.get(graph).getSelectSuperlativeDescTemplate();

        assertEquals(1, mappings.size());
        assertEquals("SELECT DISTINCT ?uri WHERE { ?uri <^VAR_0^> <^VAR_1^> . ?uri <^VAR_2^> <^VAR_3^> . ?uri <^VAR_4^> ?elevation . } ORDER BY DESC(?elevation) LIMIT 1",  actualTemplates.stream().findFirst().get());
    }*/

    /*@Test
    public void testExtractTemplatesUsesSuperlativeAsc() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("SELECT DISTINCT ?uri WHERE { ?uri a <http://dbpedia.org/ontology/Album> . ?uri <http://dbpedia.org/ontology/artist> <http://dbpedia.org/resource/Queen_(band)> . ?uri <http://dbpedia.org/ontology/releaseDate> ?d . } ORDER BY ASC(?d) OFFSET 0 LIMIT 1",
                "What was the first Queen album?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        Set<String> expectedSelectPatterns = new HashSet<>();
        expectedSelectPatterns.add("SELECT DISTINCT ?uri WHERE { ?uri a <^VAR_0^> . ?uri <^VAR_1^> <^VAR_2^> . ?uri <^VAR_3^> ?d . } ORDER BY ASC(?d) OFFSET 0 LIMIT 1");

        assertTrue(mappings.size() == 1);
        assertEquals(expectedSelectPatterns, mappings.get(graph).getSelectSuperlativeAscTemplate());
    }*/

    /*@Test
    public void testExtractTemplatesUsesCountQueryTemplates() {
        List<CustomQuestion> customQuestions = new ArrayList<>();
        String graph = " {\"1\" @\"p\" \"2\"}";
        customQuestions.add(new CustomQuestion("PREFIX dbo: <http://dbpedia.org/ontology/> PREFIX res: <http://dbpedia.org/resource/> SELECT (COUNT(DISTINCT ?uri) AS ?c) WHERE { res:Slovenia dbo:ethnicGroup ?uri }",
                "How many ethnic groups live in Slovenia?", null, graph));
        SemanticAnalysisHelper analysisHelper = new SemanticAnalysisHelper();

        Set<RDFNode> nodes = NTripleParser.getNodes();
        List<String> dBpediaProperties = DBpediaPropertiesProvider.getDBpediaProperties();
        Map<String, QueryTemplateMapping> mappings = analysisHelper.extractTemplates(null);

        Set<String> expectedSelectPatterns = new HashSet<>();
        expectedSelectPatterns.add("SELECT (COUNT(DISTINCT ?uri) AS ?c) WHERE { <^VAR_0^> <^VAR_1^> ?uri }");

        assertTrue(mappings.size() == 1);
        assertEquals(expectedSelectPatterns, mappings.get(graph).getSelectCountTemplates());
    }*/

    @Test
    public void testDetectQuestionAnswerTypeNumberAnswer() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("How many cities exist?");
        assertEquals(SPARQLResultSet.NUMBER_ANSWER_TYPE, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeNumberAnswer2() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("How many companies were founded in the same year as Google?");
        assertEquals(SPARQLResultSet.NUMBER_ANSWER_TYPE, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeNumberAnswer3() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("how big is the total area of North Rhine- Westphalia?");
        assertEquals(SPARQLResultSet.NUMBER_ANSWER_TYPE, answerType);
    }

    @Test
    //TODO implement
    @Ignore
    public void testDetectQuestionAnswerTypeNumberAnswer4() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("What is the population of Cairo?");
        assertEquals(SPARQLResultSet.NUMBER_ANSWER_TYPE, answerType);
    }

    @Test
    @Ignore
    //TODO implement
    public void testDetectQuestionAnswerTypeNumberAnswer5() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("What is the percentage of area water in Brazil?");
        assertEquals(SPARQLResultSet.NUMBER_ANSWER_TYPE, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeNumberAnswer6() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("how large is the area of UK?");
        assertEquals(SPARQLResultSet.NUMBER_ANSWER_TYPE, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeDateAnswer() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("When was the Titanic completed?");
        assertEquals(SPARQLResultSet.DATE_ANSWER_TYPE, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeDateAnswer2() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("When was the death  of  Shakespeare?");
        assertEquals(SPARQLResultSet.DATE_ANSWER_TYPE, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeBooleanAnswer() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("Is there a god?");
        assertEquals(BOOLEAN_ANSWER_TYPE, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeBooleanAnswer2() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("Does Neymar play for Real Madrid?");
        assertEquals(BOOLEAN_ANSWER_TYPE, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeBooleanAnswer3() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("Was the Cuban Missile Crisis earlier than the Bay of Pigs Invasion?");
        assertEquals(BOOLEAN_ANSWER_TYPE, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeListAnswer() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("Give me all professional skateboarders from Sweden.");
        assertEquals(LIST_OF_RESOURCES_ANSWER_TYPE, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeListAnswer2() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("Which ingredients do I need for carrot cake?");
        assertEquals(LIST_OF_RESOURCES_ANSWER_TYPE, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeListAnswer3() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("List all episodes of the first season of the HBO television series The Sopranos.");
        assertEquals(LIST_OF_RESOURCES_ANSWER_TYPE, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeSingleAnswer() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("Which computer scientist won an oscar?");
        assertEquals(SINGLE_ANSWER, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeSingleAnswer2() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("In which UK city are the headquarters of the MI6?");
        assertEquals(SINGLE_ANSWER, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeSingleAnswer3() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("From whom was Adorno influenced by?");
        assertEquals(SINGLE_ANSWER, answerType);
    }

    @Test
    public void testDetectQuestionAnswerTypeUsesLemma() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("Does a question always makes sense?");
        assertEquals(BOOLEAN_ANSWER_TYPE, answerType);
    }


    @Test
    @Ignore
    public void testGetBestAnswerWithSingleResultExpectedWith3Answers() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("To which party does the mayor of Paris belong?");

        Set<ResultsetBinding> results = new HashSet<>();
        ResultsetBinding rs = new ResultsetBinding();
        rs.addResult("http://dbpedia.org/resource/Socialist_Party_(France)");
        rs.addResult("http://dbpedia.org/resource/Feuillant_(political_group)");
        rs.addResult("http://dbpedia.org/resource/Colorado_Party_(Uruguay)");
        rs.setAnswerType(answerType);
        results.add(rs);

        ResultsetBinding bestAnswer = semanticAnalysisHelper.getBestAnswer(results,null, new HashMap<>(), answerType, false);
        assertEquals(1, bestAnswer.getResult().size());
        assertTrue(bestAnswer.getResult().contains("http://dbpedia.org/resource/Socialist_Party_(France)"));
    }

    @Test
    @Ignore
    public void testGetBestAnswerWithSingleResultExpectedWithSingleAnswer() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        int answerType = semanticAnalysisHelper.detectQuestionAnswerType("To which party does the mayor of Paris belong?");

        Set<ResultsetBinding> results = new HashSet<>();
        ResultsetBinding rs = new ResultsetBinding();
        rs.addResult("http://dbpedia.org/resource/Socialist_Party_(France)");
        rs.setAnswerType(answerType);
        results.add(rs);

        ResultsetBinding bestAnswer = semanticAnalysisHelper.getBestAnswer(results,null, new HashMap<>(), answerType, false);
        assertEquals(1, bestAnswer.getResult().size());
        assertTrue(bestAnswer.getResult().contains("http://dbpedia.org/resource/Socialist_Party_(France)"));
    }

    @Test
    public void testGetBestAnswerWithSingleResultExpectedWith11Answers() {
        SemanticAnalysisHelper semanticAnalysisHelper = new SemanticAnalysisHelper();
        Set<ResultsetBinding> results = new HashSet<>();
        ResultsetBinding rs = new ResultsetBinding();
        rs.addResult("http://dbpedia.org/resource/Socialist_Party_(France)");
        rs.addResult("http://dbpedia.org/resource/Feuillant_(political_group)");
        rs.addResult("http://dbpedia.org/resource/Colorado_Party_(Uruguay)");
        rs.addResult("http://dbpedia.org/resource/Socialist_Destourian_Party");
        rs.addResult("http://dbpedia.org/resource/Union_of_Democrats_for_the_Republic");
        rs.addResult("http://dbpedia.org/resource/Italian_Socialist_Party");
        rs.addResult("http://dbpedia.org/resource/The_Plain");
        rs.addResult("http://dbpedia.org/resource/United_Socialist_Party_(Italy,_1922–30)");
        rs.addResult("http://dbpedia.org/resource/Social_Progressive_Party");
        rs.addResult("http://dbpedia.org/resource/Union_for_the_New_Republic");
        rs.addBinding("class_0", "http://dbpedia.org/resource/Socialist_Party_(France)");
        rs.setAnswerType(LIST_OF_RESOURCES_ANSWER_TYPE);
        results.add(rs);

        ResultsetBinding rs2 = new ResultsetBinding();
        rs2.addResult("http://dbpedia.org/resource/Socialist_Party_(France)");
        rs2.setAnswerType(SINGLE_ANSWER);
        rs2.addBinding("class_0", "http://dbpedia.org/resource/Socialist_Party_(France)");
        results.add(rs2);

        HashMap<String, String> entitiyToQuestionMapping = new HashMap<>();
        entitiyToQuestionMapping.put("http://dbpedia.org/resource/Socialist_Party_(France)", "party");
        ResultsetBinding bestAnswer = semanticAnalysisHelper.getBestAnswer(results,null, entitiyToQuestionMapping, SINGLE_ANSWER, false);

        assertEquals(1, bestAnswer.getResult().size());
        assertTrue(bestAnswer.getResult().contains("http://dbpedia.org/resource/Socialist_Party_(France)"));
    }

    @Test
    public void testGetBestAnswerPrefersCorrectAnswerOverMultiple() {
        SemanticAnalysisHelper semanticAnalysisHelper = new SemanticAnalysisHelper();
        Set<ResultsetBinding> results = new HashSet<>();
        ResultsetBinding rs = new ResultsetBinding();
        rs.addResult("http://dbpedia.org/resource/Socialist_Party_(France)");
        rs.addResult("http://dbpedia.org/resource/Feuillant_(political_group)");
        rs.addResult("http://dbpedia.org/resource/Colorado_Party_(Uruguay)");
        rs.addResult("http://dbpedia.org/resource/Socialist_Destourian_Party");
        rs.addResult("http://dbpedia.org/resource/Union_of_Democrats_for_the_Republic");
        rs.addResult("http://dbpedia.org/resource/Italian_Socialist_Party");
        rs.addResult("http://dbpedia.org/resource/The_Plain");
        rs.addResult("http://dbpedia.org/resource/United_Socialist_Party_(Italy,_1922–30)");
        rs.addResult("http://dbpedia.org/resource/Social_Progressive_Party");
        rs.addResult("http://dbpedia.org/resource/Union_for_the_New_Republic");
        rs.setAnswerType(LIST_OF_RESOURCES_ANSWER_TYPE);
        rs.addBinding("http://dbpedia.org/resource/Socialist_Party_(France)", "party");
        results.add(rs);

        ResultsetBinding rs2 = new ResultsetBinding();
        rs2.addResult("http://dbpedia.org/resource/Socialist_Party_(France)");
        rs2.setAnswerType(SINGLE_ANSWER);
        rs2.addBinding("http://dbpedia.org/resource/Socialist_Party_(France)", "party");
        results.add(rs2);

        HashMap<String, String> entitiyToQuestionMapping = new HashMap<>();
        entitiyToQuestionMapping.put("http://dbpedia.org/resource/Socialist_Party_(France)", "party");
        entitiyToQuestionMapping.put("http://dbpedia.org/resource/Union_for_the_New_Republic", "party");
        ResultsetBinding bestAnswer = semanticAnalysisHelper.getBestAnswer(results,null, entitiyToQuestionMapping, SINGLE_ANSWER, true);

        assertEquals(1, bestAnswer.getResult().size());
        assertTrue(bestAnswer.getResult().contains("http://dbpedia.org/resource/Socialist_Party_(France)"));
    }

    @Test
    public void testGetBestAnswerPrefersListsOfResourcesOverListOfStrings() {
        SemanticAnalysisHelper semanticAnalysisHelper = new SemanticAnalysisHelper();
        Set<ResultsetBinding> results = new HashSet<>();
        ResultsetBinding rs = new ResultsetBinding();
        rs.addResult("136906.77151236095");
        rs.addResult("139931.0");
        rs.addResult("http://dbpedia.org/resource/Ontario");
        rs.setAnswerType(SPARQLResultSet.MIXED_LIST_ANSWER_TYPE);
        results.add(rs);

        ResultsetBinding rs2 = new ResultsetBinding();
        rs2.addResult("http://dbpedia.org/resource/Southern_Ontario");
        rs2.addResult("http://dbpedia.org/resource/Loire_Valley_(wine)");
        rs2.setAnswerType(LIST_OF_RESOURCES_ANSWER_TYPE);
        results.add(rs2);

        ResultsetBinding bestAnswer = semanticAnalysisHelper.getBestAnswer(results,null, new HashMap<>(), LIST_OF_RESOURCES_ANSWER_TYPE, true);

        assertTrue(bestAnswer.getResult().size() == 2);
        assertTrue(bestAnswer.getResult().contains("http://dbpedia.org/resource/Southern_Ontario"));
        assertTrue(bestAnswer.getResult().contains("http://dbpedia.org/resource/Loire_Valley_(wine)"));
    }

    @Test
    public void testGetBestAnswerPrefersSingleResources() {
        SemanticAnalysisHelper semanticAnalysisHelper = new SemanticAnalysisHelper();
        Set<ResultsetBinding> results = new HashSet<>();
        ResultsetBinding rs = new ResultsetBinding();
        rs.addResult("http://dbpedia.org/resource/Valparaíso");
        rs.setAnswerType(SINGLE_ANSWER);
        results.add(rs);

        ResultsetBinding rs2 = new ResultsetBinding();
        rs2.addResult("http://dbpedia.org/resource/Colegio_de_la_Preciosa_Sangre_de_Pichilemu__Cheer_C.P.S.__1");
        rs2.addResult("http://dbpedia.org/resource/Valparaíso");
        rs2.setAnswerType(LIST_OF_RESOURCES_ANSWER_TYPE);
        results.add(rs2);

        ResultsetBinding bestAnswer = semanticAnalysisHelper.getBestAnswer(results,null, new HashMap<>(), SINGLE_ANSWER, false);
        assertEquals(1, bestAnswer.getResult().size());
        assertTrue(bestAnswer.getResult().contains("http://dbpedia.org/resource/Valparaíso"));
    }

    @Test
    public void testGetBestAnswerPrefersSingleResourcesWithForceTrue() {
        SemanticAnalysisHelper semanticAnalysisHelper = new SemanticAnalysisHelper();
        Set<ResultsetBinding> results = new HashSet<>();
        ResultsetBinding rs = new ResultsetBinding();
        rs.addResult("http://dbpedia.org/resource/Valparaíso");
        rs.setAnswerType(SINGLE_ANSWER);
        results.add(rs);

        ResultsetBinding rs2 = new ResultsetBinding();
        rs2.addResult("http://dbpedia.org/resource/Colegio_de_la_Preciosa_Sangre_de_Pichilemu__Cheer_C.P.S.__1");
        rs2.addResult("http://dbpedia.org/resource/Valparaíso");
        rs2.setAnswerType(LIST_OF_RESOURCES_ANSWER_TYPE);
        results.add(rs2);

        ResultsetBinding bestAnswer = semanticAnalysisHelper.getBestAnswer(results,null, new HashMap<>(), SINGLE_ANSWER, true);
        assertEquals(1, bestAnswer.getResult().size());
        assertTrue(bestAnswer.getResult().contains("http://dbpedia.org/resource/Valparaíso"));
    }

    @Test
    public void testGetBestAnswerPrefersTrueOverFalse() {
        SemanticAnalysisHelper semanticAnalysisHelper = new SemanticAnalysisHelper();
        Set<ResultsetBinding> results = new HashSet<>();
        ResultsetBinding rs = new ResultsetBinding();
        rs.addResult("true");
        rs.addBinding("class_0", "http://dbpedia.org/resource/Foo");
        rs.setAnswerType(BOOLEAN_ANSWER_TYPE);
        results.add(rs);

        ResultsetBinding rs2 = new ResultsetBinding();
        rs2.addResult("false");
        rs.addBinding("class_0", "http://dbpedia.org/resource/Foo");
        rs2.setAnswerType(BOOLEAN_ANSWER_TYPE);
        results.add(rs2);

        Map<String, String> entityToQuestionMapping = new HashMap<>();
        entityToQuestionMapping.put("http://dbpedia.org/resource/Foo", "foo");

        ResultsetBinding bestAnswer = semanticAnalysisHelper.getBestAnswer(results,null, entityToQuestionMapping, BOOLEAN_ANSWER_TYPE, false);
        assertEquals(1, bestAnswer.getResult().size());
        assertTrue(bestAnswer.getResult().contains("true"));
    }

    @Test
    public void testGetBestAnswerWithMultipleDatesTakesTheLongest() {
        SemanticAnalysisHelper semanticAnalysisHelper = new SemanticAnalysisHelper();
        Set<ResultsetBinding> results = new HashSet<>();
        ResultsetBinding rs = new ResultsetBinding();
        rs.addResult("1616-4-23");
        rs.setAnswerType(DATE_ANSWER_TYPE);
        results.add(rs);

        ResultsetBinding rs2 = new ResultsetBinding();
        rs2.addResult("1616-04-23");
        rs2.setAnswerType(DATE_ANSWER_TYPE);
        results.add(rs2);

        ResultsetBinding bestAnswer = semanticAnalysisHelper.getBestAnswer(results,null, new HashMap<>(), DATE_ANSWER_TYPE, false);

        assertTrue(bestAnswer.getResult().size() == 1);
        assertTrue(bestAnswer.getResult().contains("1616-04-23"));
    }

    @Test
    public void testHasAscAggregation() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertFalse(semanticAnalysisHelper.hasAscAggregation("What is the largest country in the world?"));
    }

    @Test
    public void testHasAscAggregation2() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertFalse(semanticAnalysisHelper.hasAscAggregation("What was the last movie with Alec Guinness?"));
    }

    @Test
    public void testHasAscAggregation3() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertFalse(semanticAnalysisHelper.hasAscAggregation("What is the highest mountain in Australia?"));
    }

    @Test
    public void testHasAscAggregation4() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertFalse(semanticAnalysisHelper.hasAscAggregation("Which city has the most inhabitants?"));
    }

    @Test
    public void testHasAscAggregation5() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertFalse(semanticAnalysisHelper.hasAscAggregation("Which city has the most inhabitants?"));
    }

    @Test
    public void testHasAscAggregation6() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertFalse(semanticAnalysisHelper.hasAscAggregation("Which city has the most inhabitants?"));
    }

    @Test
    public void testHasAscAggregatio7() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertTrue(semanticAnalysisHelper.hasAscAggregation("Which city has the least inhabitants?"));
    }

    @Test
    public void testHasAscAggregation8() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertTrue(semanticAnalysisHelper.hasAscAggregation("What was the first Queen album?"));
    }

    @Test
    public void testHasAscAggregation9() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertTrue(semanticAnalysisHelper.hasAscAggregation("Who is the oldest child of Meryl Streep?"));
    }

    @Test
    public void testHasAscAggregationFalseMatch() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertFalse(semanticAnalysisHelper.hasAscAggregation("Is there a company called leasterious?"));
    }

    @Test
    public void testHasDescAggregation() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertTrue(semanticAnalysisHelper.hasDescAggregation("What is the largest country in the world?"));
    }

    @Test
    public void testHasDescAggregation2() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertTrue(semanticAnalysisHelper.hasDescAggregation("What was the last movie with Alec Guinness?"));
    }

    @Test
    public void testHasDescAggregation3() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertTrue(semanticAnalysisHelper.hasDescAggregation("What is the highest mountain in Australia?"));
    }

    @Test
    public void testHasDescAggregation4() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertTrue(semanticAnalysisHelper.hasDescAggregation("Which city has the most inhabitants?"));
    }

    @Test
    public void testHasDescAggregation5() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertTrue(semanticAnalysisHelper.hasDescAggregation("Which city has the most inhabitants?"));
    }


    @Test
    public void testHasDescAggregation6() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertTrue(semanticAnalysisHelper.hasDescAggregation("Which city has the most inhabitants?"));
    }

    @Test
    public void testHasDescAggregationFalseMatch() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        assertFalse(semanticAnalysisHelper.hasDescAggregation("Is there a company called mosterious?"));
    }

    @Test
    public void testGetHypernym() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        List<String> actual = semanticAnalysisHelper.getHypernymsFromWiktionary("wife");
        assertTrue(actual.contains("spouse"));
    }

    @Test
    public void testRemoveQuestionWords() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        String actual = semanticAnalysisHelper.removeQuestionWords("Give me something.");
        assertTrue(actual.equals("something."));
    }

    @Test
    public void testRemoveQuestionWords2() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        String actual = semanticAnalysisHelper.removeQuestionWords("Who invented Slack?");
        assertTrue(actual.equals("invented Slack?"));
    }

    @Test
    public void testRemoveQuestionWordsOnlyRemovesFirstQuestionWord() {
        SemanticAnalysisHelper semanticAnalysisHelper=new SemanticAnalysisHelper();
        String actual = semanticAnalysisHelper.removeQuestionWords("Who plays in the Who?");
        assertTrue(actual.equals("plays in the Who?"));
    }
}
