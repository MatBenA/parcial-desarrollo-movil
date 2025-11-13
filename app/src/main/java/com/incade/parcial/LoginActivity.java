package com.incade.parcial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar base de datos
        databaseHelper = new DatabaseHelper(this);

        // Inicializar vistas TODO
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Configurar listener del botón
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validar campos vacíos
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "Por favor, complete todos los campos",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar credenciales
                if (databaseHelper.checkUser(username, password)) {
                    Toast.makeText(LoginActivity.this,
                            "Login exitoso",
                            Toast.LENGTH_SHORT).show();

                    // Ir a MainActivity TODO
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Usuario o contraseña incorrectos",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}