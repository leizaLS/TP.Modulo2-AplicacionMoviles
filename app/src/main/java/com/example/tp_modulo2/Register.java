package com.example.tp_modulo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    EditText name, email, password, confirmPassword;
    Button registerBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Asignaciones
        name = findViewById(R.id.nameRegister);
        email = findViewById(R.id.emailRegister);
        password = findViewById(R.id.passwordRegister);
        confirmPassword = findViewById(R.id.passwordConfRegister);
        registerBtn = findViewById(R.id.registerButton);

        fAuth = FirebaseAuth.getInstance();

        //Evento de registro
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Leo los datos ingresados
                String nameT = name.getText().toString();
                String emailT = email.getText().toString();
                String passwordT = password.getText().toString();
                String passwordConfT = confirmPassword.getText().toString();

                if (nameT.isEmpty() | emailT.isEmpty() | passwordT.isEmpty() | passwordConfT.isEmpty()) {
                    Toast.makeText(Register.this, "Tiene campos sin completar", Toast.LENGTH_SHORT).show();
                }
                else if (!passwordT.equals(passwordConfT)) {
                    Toast.makeText(Register.this, "Las contraseñas no coinciden...", Toast.LENGTH_SHORT).show();
                }

                if (!nameT.isEmpty() && !emailT.isEmpty() && !passwordT.isEmpty() && !passwordConfT.isEmpty() && passwordT.equals(passwordConfT)) {
                    Toast.makeText(Register.this, "Registrando nuevo usuario... ", Toast.LENGTH_SHORT).show();

                    fAuth.createUserWithEmailAndPassword(emailT, passwordT).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authresult) {
                            //Enviar usuario a la otra pagina
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                            //Toast.makeText(Register.this, "Email de verificación enviado!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    //--
                }
            }
        });
    }
}