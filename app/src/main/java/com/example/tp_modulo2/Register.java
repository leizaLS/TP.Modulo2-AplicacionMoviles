package com.example.tp_modulo2;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.rpc.context.AttributeContext;

import java.util.ArrayList;
import java.util.HashMap;

public class Register extends AppCompatActivity {
    EditText name, email, password, confirmPassword, phoneNumber;
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
        //Obtener numero
        phoneNumber = findViewById(R.id.phoneNumber);

        fAuth = FirebaseAuth.getInstance();

        //Evento de registro
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Leo los datos ingresados
                String nameT = name.getText().toString();
                String emailT = email.getText().toString();
                String passwordT = password.getText().toString().trim();
                String passwordConfT = confirmPassword.getText().toString().trim();
                String phoneNumberT = phoneNumber.getText().toString();

                if (nameT.isEmpty() | emailT.isEmpty() | passwordT.isEmpty() | passwordConfT.isEmpty() | phoneNumberT.isEmpty()) {
                    Toast.makeText(Register.this, "Tiene campos sin completar", Toast.LENGTH_SHORT).show();
                } else if (!passwordT.equals(passwordConfT)) {
                    Toast.makeText(Register.this, "Las contraseñas no coinciden...", Toast.LENGTH_SHORT).show();
                }

                if (!nameT.isEmpty() && !emailT.isEmpty() && !passwordT.isEmpty() && !passwordConfT.isEmpty() && passwordT.equals(passwordConfT) && !phoneNumberT.isEmpty()) {
                    Toast.makeText(Register.this, "Registrando nuevo usuario... ", Toast.LENGTH_SHORT).show();

                    fAuth.createUserWithEmailAndPassword(emailT, passwordT).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authresult) {
                            //Guardamos datos en firebase
                            //progressDialog.dismiss();
                            FirebaseUser user = fAuth.getCurrentUser();

                            String email = user.getEmail();
                            String uid = user.getUid();


                            //CAMBIE STRING POR OBJECT
                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name", nameT);
                            hashMap.put("image", "");
                            hashMap.put("phoneNumber", phoneNumberT);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");
                            reference.child(uid).setValue(hashMap);

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