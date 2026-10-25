package com.shop.zkmall.app.controller.ai;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ai")
public class AiChatController {

    private static final String PY_AI_URL = "http://localhost:8000/api/chat/stream";

    @PostMapping("/chat/stream")
    public void chatStream(@RequestBody AiChatParam aiChatParam, HttpServletResponse response) {
        response.setContentType("text/plain;charset=UTF-8");
        try {
            URL url = new URL(PY_AI_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(120000);

            OutputStream os = conn.getOutputStream();
            os.write(("{\"req\":\"" + aiChatParam.req + "\"}").getBytes(StandardCharsets.UTF_8));
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter writer = response.getWriter();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("data:")) {
                    String payload = line.substring(5);
                    if ("[DONE]".equals(payload.trim())) break;
                    writer.write(payload);
                    writer.flush();
                }
            }
            conn.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    public static class AiChatParam {
        private String req;
    }
}
