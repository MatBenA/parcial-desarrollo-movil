package com.incade.parcial;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etNewUsername, etNewPassword;
    private Button btnRegister, btnShowUsers, btnLogout;
    private TextView tvUsersList, tvWelcome;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar base de datos
        databaseHelper = new DatabaseHelper(this);

        // Inicializar vistas
        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnShowUsers = findViewById(R.id.btnShowUsers);
        btnLogout = findViewById(R.id.btnLogout);
        tvUsersList = findViewById(R.id.tvUsersList);
        tvWelcome = findViewById(R.id.tvWelcome);

        // Obtener nombre de usuario del intent
        String username = getIntent().getStringExtra("username");
        tvWelcome.setText("Bienvenido, " + username + "!");

        // Botón registrar nuevo usuario
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = etNewUsername.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString().trim();

                if (newUsername.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Por favor, complete todos los campos",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (databaseHelper.addUser(newUsername, newPassword)) {
                    Toast.makeText(MainActivity.this,
                            "Usuario registrado exitosamente",
                            Toast.LENGTH_SHORT).show();
                    etNewUsername.setText("");
                    etNewPassword.setText("");
                    tvUsersList.setText("");
                } else {
                    Toast.makeText(MainActivity.this,
                            "Error: El usuario ya existe",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botón mostrar todos los usuarios
        btnShowUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DatabaseHelper.User> users = databaseHelper.getAllUsers();

                if (users.isEmpty()) {
                    tvUsersList.setText("No hay usuarios registrados");
                } else {
                    StringBuilder userInfo = new StringBuilder();
                    userInfo.append("=== USUARIOS REGISTRADOS ===\n\n");

                    for (DatabaseHelper.User user : users) {
                        userInfo.append("ID: ").append(user.getId()).append("\n");
                        userInfo.append("Usuario: ").append(user.getUsername()).append("\n");
                        userInfo.append("Contraseña: ").append(user.getPassword()).append("\n");
                        userInfo.append("------------------------\n");
                    }

                    tvUsersList.setText(userInfo.toString());
                }
            }
        });

        // Botón cerrar sesión
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}