package com.example.spctn.Service;

import com.example.spctn.Dto.Response.SongDetailsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;


    @Value("${gemini.api.url}")
    private String apiUrl;
    

    public GeminiService() {
        this.restClient = RestClient.create();
        this.objectMapper = new ObjectMapper();
    }

    public SongDetailsDTO generateFullSongDetails(String songTitle, String categoryName,String tvSeries) {
        // Endpoint completo y actualizado de Google Gemini
    	String url =  apiUrl;
        String prompt = String.format("""
            Genera información detallada sobre la canción o tema musical '%s' del artista/proyecto/tvSeriesName '%s'.
            
            REGLAS DE FORMATO OBLIGATORIAS:
            1. Responde ÚNICAMENTE con un JSON válido.
            2. NO utilices comillas dobles (") dentro de los textos. Si necesitas citar algo, usa comillas simples (').
            3. NO incluyas saltos de línea dentro de las cadenas de texto.
            
            Responde ÚNICAMENTE con un objeto JSON válido con las siguientes 4 claves exactas:
            {
              "aboutStory": "Un texto narrativo MUY breve (máximo 2 frases cortas, menos de 220 caracteres en total) la serie de la canción, en árabe.",
              "trivia": "Un dato curioso o anécdota interesante sobre la canción en árabe.",
              "description": "una descripción breve de qué recuerdos trae esta canción o qué significa para la generación que la vió y escuchó (en árabe)",
              "language": "Idioma principal de la canción perioreza la categoría dada para determinar el idioma (en inglés)",
              "year": "año de emisión de la serie (numero entero)"
            }
            """, songTitle, categoryName,tvSeries);

        Map<String, Object> requestBody = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(Map.of("text", prompt)))
            ),
            "generationConfig", Map.of(
                "responseMimeType", "application/json",
                "temperature", 0.1
            )
        );

        try {
            Map response = restClient.post()
                    .uri(url)
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .retrieve()
                    .body(Map.class);

            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                
                String jsonText = (String) parts.get(0).get("text");
                return objectMapper.readValue(jsonText, SongDetailsDTO.class);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error al generar detalles con Gemini: " + e.getMessage());
        }

        // Fallback en caso de error
        return new SongDetailsDTO(
            "معلومات عن " + songTitle,
            "لا توجد معلومات إضافية",
            "N/A",
            "Arabic",
            2000
        );
    }
}