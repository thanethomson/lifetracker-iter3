package com.thanethomson.lifetracker.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.thanethomson.lifetracker.models.Metric;
import com.thanethomson.lifetracker.models.MetricFamily;
import com.thanethomson.lifetracker.models.Sample;
import com.thanethomson.lifetracker.models.User;
import com.thanethomson.lifetracker.repos.*;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BasicApiTests {

    @Autowired
    private MetricFamilyRepo metricFamilyRepo;

    @Autowired
    private MetricRepo metricRepo;

    @Autowired
    private MetricThemeRepo metricThemeRepo;

    @Autowired
    private SampleGroupRepo sampleGroupRepo;

    @Autowired
    private SampleRepo sampleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws IOException {
        clearDatabase();
        loadFixtures();
    }

    @After
    public void tearDown() {
        //clearDatabase();
    }

    private void loadFixtures() throws IOException {
        loadObjects("classpath:/fixtures/users/*.json", this::loadUser);
        loadObjects("classpath:/fixtures/metric-families/*.json", this::loadMetricFamily);
        loadObjects("classpath:/fixtures/metrics/*.json", this::loadMetric);
        loadObjects("classpath:/fixtures/samples/*.json", this::loadSample);
    }

    private void loadObjects(String resourceBasePath, Consumer<JsonNode> converter) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(getClass().getClassLoader());
        for (Resource resource: resolver.getResources(resourceBasePath)) {
            converter.accept(
                    mapper.readTree(
                            IOUtils.toString(
                                    resource.getInputStream(),
                                    StandardCharsets.UTF_8
                            )
                    )
            );
        }
    }

    private void loadUser(JsonNode json) {
        User user = new User();
        user.setEmail(json.get("email").asText());
        user.setFirstName(json.get("firstName").asText());
        user.setLastName(json.get("lastName").asText());
        user.setPasswordHash(passwordEncoder.encode(json.get("password").asText()));
        userRepo.save(user);
    }

    private void loadMetricFamily(JsonNode json) {
        MetricFamily family = new MetricFamily();
        family.setName(json.get("name").asText());
        metricFamilyRepo.save(family);
    }

    private void loadMetric(JsonNode json) {
        Metric metric = new Metric();
        metric.setName(json.get("name").asText());
        metric.setUnits(json.get("units").asText());
        if (json.hasNonNull("familyName")) {
            metric.setFamily(metricFamilyRepo.findFirstByName(json.get("familyName").asText()));
        }
        metricRepo.save(metric);
    }

    private void loadSample(JsonNode json) {
        Sample sample = new Sample();
        sample.setAmount(json.get("amount").asDouble());
        if (json.hasNonNull("userEmail")) {
            sample.setUser(userRepo.findFirstByEmail(json.get("userEmail").asText()));
        }
        if (json.hasNonNull("metricName")) {
            sample.setMetric(metricRepo.findFirstByName(json.get("metricName").asText()));
        }
        sampleRepo.save(sample);
    }

    @Test
    public void whenWeHaveAUserInTheDatabase_thenWeShouldBeAbleToRetrieveThatUserViaTheApi() throws IOException {
        // first create a user
        User michael = userRepo.findFirstByEmail("michael@anderson.com");

        ResponseEntity<String> response = restTemplate.getForEntity("/api/users", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JsonNode json = mapper.readTree(response.getBody());
        ArrayNode usersJson = (ArrayNode)json.get("_embedded").get("users");
        assertEquals(1, usersJson.size());
        JsonNode userJson = usersJson.get(0);

        assertEquals(michael.getEmail(), userJson.get("email").asText());
        assertEquals(michael.getFirstName(), userJson.get("firstName").asText());
        assertEquals(michael.getLastName(), userJson.get("lastName").asText());
    }

    @Test
    public void whenWeCollectSamplesForAUser_thenWeShouldBeAbleToRetrieveThoseSamplesForThatUser() throws IOException {
        User michael = userRepo.findFirstByEmail("michael@anderson.com");

        // load some samples
        List<Sample> expectedSamples = sampleRepo.findByUserId(michael.getId());
        assertEquals(4, expectedSamples.size());
    }

    private void clearDatabase() {
        sampleRepo.deleteAll();
        metricRepo.deleteAll();
        metricThemeRepo.deleteAll();
        metricFamilyRepo.deleteAll();
        sampleGroupRepo.deleteAll();
        userRepo.deleteAll();
    }

}
