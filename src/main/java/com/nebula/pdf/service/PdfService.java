package com.nebula.pdf.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nebula.pdf.entity.Entity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
public class PdfService {
      @Value("${pdf.api.key}")
      private String pdfapi;
      @Value("${gemini.api.key}")
      private String geminiapi;
      private final String pdfurl = "https://api.pdf.co/v1/pdf/convert/to/text-simple";
      private final String geminiurl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";
      private final RestTemplate restTemplate = new RestTemplate();

      public Entity getPdfText(String pdfUrl) throws Exception {
          Entity response = new Entity();
          try {
              // First get PDF text
              String pdfText = extractPdfText(pdfUrl);
              
              // Then summarize using Gemini
              String summary = summarizeWithGemini(pdfText);
              
              response.setBody(summary);
              response.setStatus(200);
              response.setError(false);
          } catch (Exception e) {
              response.setBody("Error: " + e.getMessage());
              response.setStatus(500);
              response.setError(true);
          }
          return response;
      }

      private String extractPdfText(String pdfUrl) throws Exception {
          ObjectMapper mapper = new ObjectMapper();
          PdfRequest request = new PdfRequest(pdfUrl);
          String jsonRequest = mapper.writeValueAsString(request);
          
          HttpClient client = HttpClient.newHttpClient();
          HttpRequest httpRequest = HttpRequest.newBuilder()
              .uri(URI.create(pdfurl))
              .header("x-api-key", pdfapi)
              .header("Content-Type", "application/json")
              .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
              .build();

          HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
          return response.body();
      }

      private String summarizeWithGemini(String text) {
          if (geminiapi == null || geminiapi.isEmpty()) {
              throw new RuntimeException("Gemini API key is not configured.");
          }

          String apiUrl = geminiurl + geminiapi;
          HttpHeaders headers = createHeaders();
          Map<String, Object> requestBody = createRequestBody(text);

          HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
          ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
              apiUrl, 
              HttpMethod.POST, 
              requestEntity,
              new ParameterizedTypeReference<>() {}
          );

          return extractSummaryFromResponse(response);
      }

      private HttpHeaders createHeaders() {
          HttpHeaders headers = new HttpHeaders();
          headers.set("Content-Type", "application/json");
          return headers;
      }

      private Map<String, Object> createRequestBody(String text) {
          return Map.of(
              "contents", List.of(
                  Map.of("parts", List.of(Map.of("text", "Summarize this: " + text)))
              ),
              "generationConfig", Map.of("maxOutputTokens",1000)
          );
      }

      private String extractSummaryFromResponse(ResponseEntity<Map<String, Object>> response) {
          Map<String, Object> responseBody = response.getBody();
          if (responseBody != null && responseBody.containsKey("candidates")) {
              Object candidatesObj = responseBody.get("candidates");
              if (candidatesObj instanceof List<?> candidates && !candidates.isEmpty()) {
                  Object firstCandidateObj = candidates.get(0);
                  if (firstCandidateObj instanceof Map<?, ?> firstCandidate) {
                      Object contentObj = firstCandidate.get("content");
                      if (contentObj instanceof Map<?, ?> content) {
                          Object partsObj = content.get("parts");
                          if (partsObj instanceof List<?> parts && !parts.isEmpty()) {
                              Object firstPartObj = parts.get(0);
                              if (firstPartObj instanceof Map<?, ?> firstPart && firstPart.containsKey("text")) {
                                  return (String) firstPart.get("text");
                              }
                          }
                      }
                  }
              }
              throw new RuntimeException("Could not extract text from response");
          } else {
              throw new RuntimeException("Response body is null or missing expected data");
          }
      }

      public static class PdfRequest {
          private String url;
          private boolean inline = true;
          private boolean async = false;

          public PdfRequest(String url) {
              this.url = url;
          }

          public String getUrl() {
              return url;
          }

          public void setUrl(String url) {
              this.url = url;
          }

          public boolean isInline() {
              return inline;
          }

          public void setInline(boolean inline) {
              this.inline = inline;
          }

          public boolean isAsync() {
              return async;
          }

          public void setAsync(boolean async) {
              this.async = async;
          }
      }
}
