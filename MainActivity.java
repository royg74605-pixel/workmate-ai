package com.example.myapplication3;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    private TextView statusText;
    private TextView resultText;
    private TextToSpeech tts;

    private static final int VOICE_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.status_text);
        resultText = findViewById(R.id.result_text);
        Button voiceButton = findViewById(R.id.btn_voice);

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(new Locale("bn", "IN"));
            }
        });

        voiceButton.setOnClickListener(v -> startVoiceInput());
    }

    private void startVoiceInput() {

        statusText.setText("🎤 শুনছি...");

        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        );

        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );

        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                "bn-IN"
        );

        intent.putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "কথা বলুন..."
        );

        startActivityForResult(intent, VOICE_REQUEST);
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_REQUEST &&
                resultCode == RESULT_OK &&
                data != null) {

            ArrayList<String> results =
                    data.getStringArrayListExtra(
                            RecognizerIntent.EXTRA_RESULTS
                    );

            if (results != null && !results.isEmpty()) {

                String userText = results.get(0);

                statusText.setText("আপনার কথা:");
                resultText.setText(userText);

                tts.speak(
                        "আপনি বলেছেন " + userText,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "agent_response"
                );
            }
        }
    }

    @Override
    protected void onDestroy() {

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
    }
}