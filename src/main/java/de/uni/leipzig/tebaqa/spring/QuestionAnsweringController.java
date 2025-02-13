package de.uni.leipzig.tebaqa.spring;

import de.uni.leipzig.tebaqa.controller.KeyWordController;
import de.uni.leipzig.tebaqa.controller.PipelineController;
import de.uni.leipzig.tebaqa.controller.PipelineControllerTripleTemplates;
import de.uni.leipzig.tebaqa.model.AnswerToQuestion;
import de.uni.leipzig.tebaqa.model.ResultsetBinding;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Optional;

import static de.uni.leipzig.tebaqa.helper.KeyWordPipelineProvider.getKeyWordPipeline;
import static de.uni.leipzig.tebaqa.helper.PipelineProvider.getQAPipeline;
import static de.uni.leipzig.tebaqa.helper.PipelineProvider.getQAPipelineTripleTemplates;
import static de.uni.leipzig.tebaqa.spring.ExtendedQALDAnswer.extractAnswerString;

@RestController
public class QuestionAnsweringController {

    private static Logger log = Logger.getLogger(QuestionAnsweringController.class.getName());

    @RequestMapping(method = RequestMethod.POST, path = "/qa")
    public String answerQuestion(@RequestParam String query,
                                 @RequestParam(required = false, defaultValue = "en") String lang,
                                 HttpServletResponse response) {
        log.info(String.format("/qa received POST request with: query='%s' and lang='%s'", query, lang));
        if (!query.isEmpty() && isValidQuestion(query)) {
            PipelineController qaPipeline = getQAPipeline();
            String result;
            try {
                result = new ExtendedQALDAnswer(qaPipeline.answerLimboQuestion(query)).getResult();
            } catch (Exception e) {
                result = new ExtendedQALDAnswer(new AnswerToQuestion(new ResultsetBinding(), new HashMap<>())).getResult();
                log.error(String.format("Got Exception while answering='%s' with lang='%s'", query, lang), e);
            }
            log.info("Answer: " + result);
            return result;
        } else {
            log.error("Received request with empty query parameter!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parameter query cannot be empty!").toString();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/qa")
    public String answerQuestion2(@RequestParam String query,
                                  @RequestParam(required = false, defaultValue = "en") String lang,
                                  HttpServletResponse response) {
        return this.answerQuestion(query, lang, response);
    }
    @RequestMapping(method = RequestMethod.POST, path = "/keyword")
    public String keywordQuery(@RequestParam String query,
                                  @RequestParam(required = false, defaultValue = "en") String lang,
                                  @RequestParam(required = false, defaultValue = "")String type,
                                  @RequestParam(required = false, defaultValue = "")String property,
                                  @RequestParam(required = false, defaultValue = "")String connect,
                                  @RequestParam(required = false, defaultValue = "")String searchIn,
                                  HttpServletResponse response) {
        KeyWordController pipeline=getKeyWordPipeline();
        String result;
        try {
            Optional<String>search=Optional.empty();
            if(!searchIn.equals("all"))search=Optional.of(searchIn);
            Optional<String>connResoure=Optional.empty();
            if(!connect.equals(""))connResoure=Optional.of(connect);
            Optional<String>connProp=Optional.empty();
            if(!property.equals(""))connProp=Optional.of(property);
            Optional<String>connClass=Optional.empty();
            if(!type.equals(""))connClass=Optional.of(type);
            AnswerToQuestion answer= new AnswerToQuestion(pipeline.searchByKeywords(query, search,connClass,connProp,connResoure));
            JsonArrayBuilder resultArray = Json.createArrayBuilder();
            answer.getAnswer().forEach(a -> resultArray.add(extractAnswerString(a)));
            result = Json.createObjectBuilder()
                    .add("answers", resultArray)
                    .add("sparql", answer.getSparqlQuery())
                    .build().toString();

        } catch (Exception e) {
            result = Json.createObjectBuilder().add("answers", Json.createArrayBuilder()).build().toString();
            log.error(String.format("Got Exception while answering='%s' with lang='%s'", query, lang), e);
        }
        log.info("Answer: " + result);
        return result;
    }
    @RequestMapping(method = RequestMethod.POST, path = "/qa-simple")
    public String answerQuestionSimple(@RequestParam String query,
                                       @RequestParam(required = false, defaultValue = "en") String lang,
                                       HttpServletResponse response) {
        log.info(String.format("/qa-simple received POST request with: query='%s' and lang='%s'", query, lang));
        if (!query.isEmpty() && isValidQuestion(query)) {
            PipelineController qaPipeline = getQAPipeline();
            String result;
            try {
                AnswerToQuestion answer = qaPipeline.answerLimboQuestion(query);
                JsonArrayBuilder resultArray = Json.createArrayBuilder();
                answer.getAnswer().forEach(a -> resultArray.add(extractAnswerString(a)));
                result = Json.createObjectBuilder()
                        .add("answers", resultArray)
                        .add("sparql", answer.getSparqlQuery())
                        .build().toString();

            } catch (Exception e) {
                result = Json.createObjectBuilder().add("answers", Json.createArrayBuilder()).build().toString();
                log.error(String.format("Got Exception while answering='%s' with lang='%s'", query, lang), e);
            }
            log.info("Answer: " + result);
            return result;
        } else {
            log.error("Received request with empty query parameter!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parameter query cannot be empty!").toString();
        }
    }

    private boolean isValidQuestion(String q) {
        return q.trim().length() > 0;
    }
}
