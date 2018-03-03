package com.example.android.myquiz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
    The following variables are declared in the class itself, so that they can be used in different
    functions without having to include them in the function calls, as it gets messy.
     */
    boolean hasFirstQuestionFirstCheckBox;
    boolean hasFirstQuestionSecondCheckBox;
    boolean hasFirstQuestionThirdCheckBox;
    boolean hasThirdQuestionFirstCheckBox;
    boolean hasThirdQuestionSecondCheckBox;
    boolean hasThirdQuestionThirdCheckBox;
    boolean hasSecondQuestionRadioButton;
    boolean hasFourthQuestionRadioButton;
    boolean hasSixthQuestionSwitch;
    String fifthQuestionAnswer;
    String correctAnswer = "Not yet!";
    /*
    correctAnswer is initialized with the value "Not yet!", so that if the quiz hasn't been checked yet, it can't be shared and
    "Not yet!" will be displayed as a toast message. If it has been checked the value will have been changed by the Check function.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Check(View view) {

        //A function which checks the answers and keeps track of how many are correct and how many aren't.
        int score = 0;
        correctAnswer = getString(R.string.correctAnswer);
        score = Evaluate(score);
        Toast.makeText(this, composeToast(score), Toast.LENGTH_SHORT).show();

    }

    public void Share(View view) {
        //A function which creates an e-mail intent if the quiz has been checked.
        int score = 0;
        if (correctAnswer.compareTo("Not yet!") == 0)
            Toast.makeText(this, correctAnswer, Toast.LENGTH_SHORT).show();
        else {

            score = Evaluate(score);
            String message = composeMessage(score);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));
            intent.putExtra(Intent.EXTRA_TEXT, message);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }
    }

    public int Evaluate(int score) {
        //A function which goes through all of the question and checks if they are correct or not.
        CheckBox firstQuestionFirstCheckBox = findViewById(R.id.martial_art);
        hasFirstQuestionFirstCheckBox = firstQuestionFirstCheckBox.isChecked();

        CheckBox firstQuestionSecondCheckBox = findViewById(R.id.dance);
        hasFirstQuestionSecondCheckBox = firstQuestionSecondCheckBox.isChecked();

        CheckBox firstQuestionThirdCheckBox = findViewById(R.id.culture);
        hasFirstQuestionThirdCheckBox = firstQuestionThirdCheckBox.isChecked();

        if (hasFirstQuestionFirstCheckBox && !hasFirstQuestionSecondCheckBox && hasFirstQuestionThirdCheckBox)
            score += 1;

        RadioButton secondQuestionFirstRadioButton = findViewById(R.id.brazil);
        hasSecondQuestionRadioButton = secondQuestionFirstRadioButton.isChecked();

        if (hasSecondQuestionRadioButton)
            score += 1;

        CheckBox thirdQuestionFirstCheckBox = findViewById(R.id.atabaque);
        hasThirdQuestionFirstCheckBox = thirdQuestionFirstCheckBox.isChecked();

        CheckBox thirdQuestionSecondCheckBox = findViewById(R.id.didgeridoo);
        hasThirdQuestionSecondCheckBox = thirdQuestionSecondCheckBox.isChecked();

        CheckBox thirdQuestionThirdCheckBox = findViewById(R.id.berimbau);
        hasThirdQuestionThirdCheckBox = thirdQuestionThirdCheckBox.isChecked();

        if (hasThirdQuestionFirstCheckBox && !hasThirdQuestionSecondCheckBox && hasThirdQuestionThirdCheckBox)
            score += 1;

        RadioButton fourthQuestionFirstRadioButton = findViewById(R.id.regional);
        hasFourthQuestionRadioButton = fourthQuestionFirstRadioButton.isChecked();

        if (hasFourthQuestionRadioButton)
            score += 1;

        EditText fifthQuestionEditText = findViewById(R.id.answer);
        fifthQuestionAnswer = fifthQuestionEditText.getText().toString();

        if (fifthQuestionAnswer.compareTo(correctAnswer) == 0)
            score += 1;

        Switch switchView = findViewById(R.id.switch_button);
        hasSixthQuestionSwitch = switchView.isChecked();

        return score;
    }

    public String composeToast(int score) {
        //A function which creates the Toast used by the Check function.

        String message;
        //This long if else statement is used so that the app be a bit more interactive.
        if (score == 0 && hasSixthQuestionSwitch)
            message = getString(R.string.fail_capoerist);
        else if (score == 0 && !hasSixthQuestionSwitch)
            message = getString(R.string.fail_non_capoerist);
        else if (score != 0 && score != 5 && hasSixthQuestionSwitch)
            message = getString(R.string.success_capoerist, score);
        else if (score != 0 && score != 5 && !hasSixthQuestionSwitch)
            message = getString(R.string.success_non_capoerist, score);
        else if (score == 5 && hasSixthQuestionSwitch)
            message = getString(R.string.total_success) + " " + getString(R.string.capoerist);
        else if (score == 5 && !hasSixthQuestionSwitch)
            message = getString(R.string.total_success) + " " + getString(R.string.non_capoerist);
        else
            message = getString(R.string.error);

        return message;
    }

    public String composeMessage(int score) {
        //A function which creates the message for the e-mail intent, created in the Share function.

        String message = getString(R.string.message_1);
        if (score == 0 && hasSixthQuestionSwitch)
            message += " " + getString(R.string.message_2_1) + " " + getString(R.string.message_3_1);
        else if (score == 0 && !hasSixthQuestionSwitch)
            message += " " + getString(R.string.message_2_1) + " " + getString(R.string.message_3_2);
        else if (score != 0 && hasSixthQuestionSwitch)
            message += " " + getString(R.string.message_2_2) + " " + getString(R.string.message_3_3);
        else if (score != 0 && !hasSixthQuestionSwitch)
            message += " " + getString(R.string.message_2_2) + " " + getString(R.string.message_3_4);
        else message += " " + getString(R.string.mail_error);


        return message;
    }

    public void SwitchChange(View view) {
        //gets the text displayed for the switch button
        TextView switchText = findViewById(R.id.switch_view);
        //checks for the contained text
        if (switchText.getText().toString().compareTo(getString(R.string.yes)) == 0)
            //changes the displayed text appropriately
            switchText.setText(getString(R.string.no));
        else
            switchText.setText(getString(R.string.yes));
    }

}
