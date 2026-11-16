package com.example.truffiq;

// Importiamo le classi necessarie
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Elementi grafici dell'applicazione
    TextView scoreText;
    TextView questionNumber;
    TextView categoryText;
    TextView senderText;
    TextView messageText;
    TextView linkText;
    TextView feedbackText;
    TextView resultText;

    Button scamButton;
    Button safeButton;
    Button nextButton;
    Button restartButton;

    // Variabili per gestire il quiz
    int questionIndex = 0;
    int score = 0;

    boolean answered = false;

    // Metodo principale
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Colleghiamo il file XML alla schermata
        setContentView(R.layout.activity_main);

        // Colleghiamo le variabili agli elementi XML
        scoreText = findViewById(R.id.scoreText);
        questionNumber = findViewById(R.id.questionNumber);
        categoryText = findViewById(R.id.categoryText);
        senderText = findViewById(R.id.senderText);
        messageText = findViewById(R.id.messageText);
        linkText = findViewById(R.id.linkText);
        feedbackText = findViewById(R.id.feedbackText);
        resultText = findViewById(R.id.resultText);

        scamButton = findViewById(R.id.scamButton);
        safeButton = findViewById(R.id.safeButton);
        nextButton = findViewById(R.id.nextButton);
        restartButton = findViewById(R.id.restartButton);

        // Carichiamo la prima domanda
        loadQuestion();
    }

    // Metodo per visualizzare una domanda
    public void loadQuestion() {

        answered = false;

        // Aggiorniamo il punteggio
        scoreText.setText("XP: " + score);

        // Aggiorniamo il numero della domanda
        questionNumber.setText(
                "MISSIONE " + (questionIndex + 1) + " / 3"
        );

        // Mostriamo il contenuto della domanda
        if (questionIndex == 0) {

            categoryText.setText("PHISHING");
            senderText.setText("Mittente: Your Bank");

            messageText.setText(
                    "La tua carta è stata bloccata! " +
                            "Clicca immediatamente sul link per " +
                            "verificare il tuo account ed evitare " +
                            "la sospensione."
            );

            linkText.setText("Link: bank-security-example.com");

        } else if (questionIndex == 1) {

            categoryText.setText("COMUNICAZIONE SICURA");
            senderText.setText("Mittente: Biblioteca");

            messageText.setText(
                    "Gentile utente, ti informiamo che " +
                            "il libro che hai prenotato è disponibile " +
                            "per il ritiro presso la biblioteca."
            );

            linkText.setText("Nessun link esterno");

        } else if (questionIndex == 2) {

            categoryText.setText("FALSO PREMIO");
            senderText.setText("Mittente: Online Prize");

            messageText.setText(
                    "Congratulazioni! Hai vinto un nuovo " +
                            "smartphone. Per ricevere il premio, " +
                            "inserisci subito i tuoi dati personali."
            );

            linkText.setText("Link: claim-your-prize-example.com");
        }

        // Nascondiamo i messaggi precedenti
        feedbackText.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);

        // Riattiviamo i pulsanti di risposta
        scamButton.setEnabled(true);
        safeButton.setEnabled(true);
    }

    // L'utente sceglie TRUFFA
    public void onScamClick(View view) {
        checkAnswer(true);
    }

    // L'utente sceglie SICURO
    public void onSafeClick(View view) {
        checkAnswer(false);
    }

    // Metodo per controllare la risposta
    public void checkAnswer(boolean userSaysScam) {

        // Evitiamo che l'utente risponda più volte
        if (answered) {
            return;
        }

        answered = true;

        // Variabili per la risposta corretta
        boolean correctAnswer = false;
        String explanation = "";

        // Controlliamo la prima domanda
        if (questionIndex == 0) {

            correctAnswer = true;

            explanation =
                    "Questo è un tentativo di PHISHING. " +
                            "Il messaggio crea urgenza e utilizza " +
                            "un link sospetto per rubare informazioni.";

        }

        // Controlliamo la seconda domanda
        else if (questionIndex == 1) {

            correctAnswer = false;

            explanation =
                    "Questa è una comunicazione informativa. " +
                            "Non chiede password, dati bancari " +
                            "o informazioni personali.";

        }

        // Controlliamo la terza domanda
        else if (questionIndex == 2) {

            correctAnswer = true;

            explanation =
                    "Questo è un falso premio. " +
                            "I truffatori utilizzano premi inventati " +
                            "per convincere le persone a fornire " +
                            "i propri dati personali.";

        }

        // Verifichiamo se la risposta dell'utente è corretta
        if (userSaysScam == correctAnswer) {

            score = score + 100;

            feedbackText.setText(
                    "CORRETTO! +100 XP\n\n" + explanation
            );

        } else {

            feedbackText.setText(
                    "SBAGLIATO!\n\n" + explanation
            );
        }

        // Mostriamo il risultato
        feedbackText.setVisibility(View.VISIBLE);

        // Disattiviamo i pulsanti
        scamButton.setEnabled(false);
        safeButton.setEnabled(false);

        // Mostriamo il pulsante successivo
        nextButton.setVisibility(View.VISIBLE);

        scoreText.setText("XP: " + score);
    }

    // Passiamo alla domanda successiva
    public void onNextClick(View view) {

        questionIndex++;

        // Controlliamo se il quiz è terminato
        if (questionIndex >= 3) {

            showResults();

        } else {

            loadQuestion();
        }
    }

    // Mostriamo i risultati finali
    public void showResults() {

        // Nascondiamo la schermata del quiz
        findViewById(R.id.quizScreen).setVisibility(View.GONE);

        // Mostriamo la schermata dei risultati
        findViewById(R.id.resultScreen).setVisibility(View.VISIBLE);

        // Calcoliamo le risposte corrette
        int correctAnswers = score / 100;

        resultText.setText(
                "HAI COMPLETATO IL QUIZ!\n\n" +
                        "Punteggio: " + score + " XP\n\n" +
                        "Risposte corrette: " +
                        correctAnswers + " / 3\n\n" +
                        "Continua ad allenarti per riconoscere " +
                        "le truffe online!"
        );
    }

    // Ricominciamo il quiz
    public void onRestartClick(View view) {

        questionIndex = 0;
        score = 0;
        answered = false;

        // Mostriamo nuovamente il quiz
        findViewById(R.id.resultScreen).setVisibility(View.GONE);
        findViewById(R.id.quizScreen).setVisibility(View.VISIBLE);

        // Carichiamo la prima domanda
        loadQuestion();
    }
}
