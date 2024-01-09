package com.example.springbootdemo.controller;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@ExtendWith(DBUnitExtension.class)
@DataSet(cleanBefore = true, skipCleaningFor = { "Users" })
class RecordContentMediumTest {

    private static final ConnectionHolder connectionHolder = () ->
            DriverManager.getConnection("jdbc:mysql://localhost/record_app","root",null);

    private static PreparedStatement ps;

    @BeforeAll
    public static void initDatabase() throws Exception {

        ps = connectionHolder.getConnection()
                .prepareStatement("""
                            INSERT INTO RecordContent(recordContentName, createUserId, isValid)
                            VALUES (recordX, 1, true)
                            """);
//        ps.executeUpdate();
    }

    @AfterAll
    static void closeConnection() throws Exception {
        ps.close();
    }

    @Test
    @DataSet("RecordContentMediumTest/case1/before.yaml")
    @ExpectedDataSet("RecordContentMediumTest/case1/after.yaml")
    public void リソース作成() throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpUriRequest httppost = RequestBuilder.post()
                    .setUri(new URI("http://localhost:8080/recordContent/record1?createUserId=4")).build();
            CloseableHttpResponse httpResponse = httpclient.execute(httppost);
            if (httpResponse.getStatusLine().getStatusCode() != 201) {
                Assertions.fail("status code : " + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet("RecordContentMediumTest/case3/before.yaml")
    @ExpectedDataSet("RecordContentMediumTest/case3/after.yaml")
    public void リソース削除() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpUriRequest httpdelete = RequestBuilder.delete()
                    .setUri(new URI("http://localhost:8080/recordContent/record1")).build();
            CloseableHttpResponse httpResponse = httpclient.execute(httpdelete);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                Assertions.fail("status code : " + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
